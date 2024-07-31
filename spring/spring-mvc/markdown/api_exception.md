# API 예외 처리
API 예외 처리는 각 오류 상황에 맞는 오류 응답 스펙을 정하고 JSON으로 데이터를 내려줘야한다.

~~~java
@Component
public class WebServerCustomizer implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {

        ErrorPage errorPage404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error-page/404");
        ErrorPage errorPage500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error-page/500");

        ErrorPage errorPageEx = new ErrorPage(RuntimeException.class, "/error-page/500");

        factory.addErrorPages(errorPage404, errorPage500, errorPageEx);
    }
}
~~~
- WAS에 예외가 전달되거나 response.sendError() 호출 시 등록한 예외 페이지 경로가 호출된다.

~~~java
@Slf4j
@RestController
public class ApiExceptionController {

    @GetMapping("/api/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {

        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }

        return new MemberDto(id, "hello " + id);
    }

    @Data
    @AllArgsConstructor
    static class MemberDto {

        private String memberId;
        private String name;
    }
}
~~~

아이디가 ex인 경우 예외가 발생하도록 하였다.

http://localhost:8080/api/members/odgman 요청 시
~~~JSON
{
    "memberId": "odgman",
    "name": "hello odgman"
}
~~~
- API로 요청했는데 정상일 경우 API로 JSON 형식으로 데이터가 정상 반환된다.

http://localhost:8080/api/members/ex 예외 발생
~~~html
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
</head>
<body>
<div class="container" style="max-width: 600px">
    <div class="py-5 text-center">
        <h2>500 오류 화면</h2> </div>
    <div>
        <p>오류 화면 입니다.</p>
    </div>
    <hr class="my-4">
</div> <!-- /container -->
</body>
</html>
~~~
- 오류 발생 시 미리 만들어두었던 오류 페이지가 반환된다.
- 기대한 것은 JSON이 반환되는 것이다.

JSON 응답을 할 수 있도록 수정하자
~~~java
@RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Map<String, Object>> errorPage500Api(
        HttpServletRequest request, HttpServletResponse response) {

    log.info("API errorPage 500");
    HashMap<String, Object> result = new HashMap<>();
    Exception ex = (Exception) request.getAttribute(ERROR_EXCEPTION);
    result.put("status", request.getAttribute(ERROR_STATUS_CODE));
    result.put("message", ex.getMessage());

    Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    return new ResponseEntity<>(result, HttpStatus.valueOf(statusCode));
}
~~~
- `@RequestMapping(value = "/error-page/500", produces = MediaType.APPLICATION_JSON_VALUE)`: HTTP 헤더의 Accept값이 application/json 일 때 해당 메서드가 호출된다. 즉 클라이언트가 받고 싶은 미디어타입이 json이면 이 컨트롤러의 메서드가 호출된다.

응답 데이터를 위해 Map을 만들고 status, message키에 값을 할당했다.<br>
ResponseEntity를 사용해서 응답하기 때문에 메시지 컨버터가 동작하면서 클라이언트에 JSON이 반환된다.

http://localhost:8080/api/members/ex
~~~
{
    "message": "잘못된 사용자",
    "status": 500
}
~~~

## API 예외 처리 - 스프링 부트 기본 오류 처리
API 예외 처리도 스프링 부트가 제공하는 기본 오류 방식을 사용할 수 있다.

**BasicErrorController 코드 일부 발췌**
~~~java
@RequestMapping(
        produces = {"text/html"}
    )
public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
    HttpStatus status = this.getStatus(request);
    Map<String, Object> model = Collections.unmodifiableMap(this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.TEXT_HTML)));
    response.setStatus(status.value());
    ModelAndView modelAndView = this.resolveErrorView(request, response, status, model);
    return modelAndView != null ? modelAndView : new ModelAndView("error", model);
}

@RequestMapping
public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    HttpStatus status = this.getStatus(request);
    if (status == HttpStatus.NO_CONTENT) {
        return new ResponseEntity(status);
    } else {
        Map<String, Object> body = this.getErrorAttributes(request, this.getErrorAttributeOptions(request, MediaType.ALL));
        return new ResponseEntity(body, status);
    }
}
~~~
/error 동일한 경로를 처리하는 errorHtml(), error() 두 메서드를 확인할 수 있다.
- errorHtml(): 클라이언트 요청의 Accept 해더 값이 text/html인 경우 errorHtml()이 호출되어 view를 반환한다.
- error(): 그 외 경우 호출되고 ResponseEntity로 HTTP Body에 JSON 데이터를 반환한다.

### 스프링 부트의 예외 처리
스프링 부트의 기본 설정은 오류 발생 시 /error를 오류 페이지로 요청한다.
~~~java
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class BasicErrorController extends AbstractErrorController {
}
~~~

http://localhost:8080/api/members/ex 예외 발생
~~~json
{
    "timestamp": "2024-07-31T07:44:29.826+00:00",
    "status": 500,
    "error": "Internal Server Error",
    "exception": "java.lang.RuntimeException",
    "path": "/api/members/ex"
}
~~~

### HTML 페이지 vs API 오류
BasicErrorController를 확장하면 JSON 메시지도 변경이 가능하지만 `@ExceptionHandler`가 제공하는 기능을 사용하는 것이 더 나은 방법이다.

오류 페이지를 보여줘야 할 때는 BasicErrorController를 사용하고 API 오류 처리는 `@ExceptionHandler`를 사용하자

## API 예외 처리 - HandlerExceptionResolver 
