# 스프링 MVC - 기본기능
## 로깅

스프링 부트 라이브러리 사용 시 스프링 부트 로깅 라이브러리가 함께 포함돼있다.(spring-boot-starter-logging)
- SLF4J
- Logback

Logback, Log4J, Log4J2등 라이브러리들을 통합해 인터페이스로 제공하는 것이 SLF4J 라이브러리 <br>
- SLF4J는 인터페이스, 구현체로 Logback 같은 라이브러리를 선택해 사용하면 됨
- Logback 많이 사용 

~~~java
//@Slf4j
@RestController
public class LogTestController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/log-test")
    public String logTest() {

        String name = "Spring";
        log.trace("trace log={}", name);
        log.debug("debug log={}", name);
        log.info("info log={}", name);
        log.warn("warn log={}", name);
        log.error("error log={}", name);

        return "ok";
    }
}
~~~

@RestController
- @Controller는 반환 값이 String이면 뷰 이름으로 인식 -> 뷰를 찾고 뷰가 렌더링
- @RestController는 HTTP 메시지 바디에 바로 입력

- 로그가 출력되는 포멧
  - 시간, 로그레벨, 프로세스 ID, 쓰레드 명, 클래스명, 로그메시지
- 로그 레벨 설정에 따라 출력이 달라짐
  - level: TRACE > DEBUG > INFO > WARN > ERROR
  - 주로 개발서버에서는 debug
  - 운영 서버에서는 info

로그 레벨 설정
~~~
# 전체 로그 레벨 설정 (기본값: info)
loggin.level.root=info

# com.odg.springmvc 패키지와 하위 패키지 로그 레벨
logging.level.com.odg.springmvc=trace
~~~

올바른 로그 사용방법
- log.debug("data="+data)
  - 로그 출력 레벨이 info로 설정되있다 하더라도 해당 코드에서 "data="+data 연산이 실제로 실행이 된다.
- log.debug("data={}", data)
  - 로그 출력 레벨을 info로 설정하면 아무일도 발생하지 않는다. 의미 없는 연산이 발생하지 않음

로그 사용 시 장점
- 부가 정보 확인 가능, 출력 모양 조정 가능
- 상황에 맞게 로그를 출력할 수 있음
- 콘솔에만 출력하는 게 아니라 별도 위치에 파일 등으로 따로 로그를 남길 수 있다.

## 요청 매핑
~~~java
@Slf4j
@RestController
public class MappingController {

//    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping("/hello-basic")
    public String helloBasic() {
        log.info("helloBasic");
        return "ok";
    }
}
~~~
@RequestMapping
- /hello-basic URL 호출이 오면 이 메서드가 실행
- 속성을 배열로 제공하므로 여러 개를 둘 수 있다. <br>
-> @RequestMapping({"/hello-basic", "/hello-go"})

**HTTP 메서드** <br>
@RequestMapping에 method 속성으로 HTTP 메서드를 지정하지 않으면 HTTP 메서드에 상관없이 호출된다. -> GET, POST, PUT, PATCH, DELETE 모두

~~~java
    @RequestMapping(value = "/hello-basic", method = RequestMethod.GET)
~~~
- 메서드 지정 시 GET 방식 요청만 허용
- 다른 방식으로 요청 시 405 상태코드 반환(Method Not Allowed)

**HTTP 메서드 매핑 축약**
~~~
@GetMapping
@PostMapping
@PutMapping
@PatchMapping
@DeleteMapping
~~~
~~~java
@GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("helloBasic");
        return "ok";
    }
~~~
@GetMapping 내부를 보면 @RequestMapping과 method를 지정해서 사용하고 있다.

**PathVariable(경로 변수) 사용**
~~~java
@GetMapping("/mapping/{userId}")
    public String mappingPath(
            @PathVariable("userId") String data) {
        log.info("mappingPath userId={}", data);
        return "ok";
    }
~~~
- @RequestMapping은 url 경로를 템플릿화 할 수 있음
- @PathVariable을 사용하면 매칭되는 부분을 편리하게 조회할 수 있음
- @PathVariable의 이름과 파라미터 이름이 같으면 생략 가능

**PathVariable 다중 사용**
~~~java
@GetMapping("/mapping/users/{userId}/orders/{orderId}")
public String mappingPath(
        @PathVariable String userId, @PathVariable Long orderId) {
    log.info("mappingPath userId={}, orderId={}", userId, orderId);
    return "ok";
}
~~~

**특정 파라미터 조건 매핑**
~~~java
@GetMapping(value = "/mapping-param", params = "mode=debug")
public String mappingParam() {
    log.info("mappingParam");
    return "ok";
}
~~~
특정 파라미터가 있거나 없는 조건을 추가 가능, 잘 사용하지 않음

**특정 헤더 조건 매핑**
~~~java
@GetMapping(value = "/mapping-header", headers = "mode=debug")
public String mappingHeader() {
    log.info("mappingHeader");
    return "ok";
}
~~~
파라미터 조건과 유사, HTTP 헤더를 사용

**미디어 타입 조건 매핑 content-type, consume**
~~~java
// @PostMapping(value = "/mapping-consume", consumes = "application/json")
@PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
public String mappingConsumes() {
    log.info("mappingConsumes");
    return "ok";
}
~~~
HTTP 요청의 Content-Type 헤더를 기반으로 미디어 타입으로 매핑, 맞지 않으면 415상태코드 반환

**미디어 타입 조건 매핑 Accept, produce**
~~~java
//@PostMapping(value = "/mapping-produce", produces = "text/html")
@PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
public String mappingProduces() {
    log.info("mappingProduces");
    return "ok";
}
~~~
HTTP 요청의 Accept 헤더를 기반으로 미디어 타입으로 매핑, 맞지 않으면 406 상태코드 반환

## 요청 매핑 - 예시
~~~java
@RestController
@RequestMapping("/mapping/users")
public class MappingClassController {

    // 회원 목록 조회
    @GetMapping
    public String user() {
        return "get users";
    }

    // 회원 등록
    @PostMapping
    public String addUser() {
        return "post user";
    }

    // 회원 조회
    @GetMapping("/{userId}")
    public String findUser(@PathVariable String userId) {
        return "get userId = " + userId;
    }

    // 회원 수정
    @PatchMapping("/{userId}")
    public String updateUser(@PathVariable String userId) {
        return "update userId = " + userId;
    }

    // 회원 삭제
    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable String userId) {
        return "delete userId = " + userId;
    }
}
~~~
@RequestMapping("/mapping/users") 클래스 레벨에서 매핑 정보를 두면 메서드 레벨에 있는 매핑 정보와 조합해서 사용 가능

## HTTP 요청 - 기본, 헤더 조회
~~~java
@Slf4j
@RestController
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMethod httpMethod,
            Locale locale,
            @RequestHeader MultiValueMap<String, String> headerMap,
            @RequestHeader("host") String host,
            @CookieValue(value="myCookie" , required = false) String cookie
            ) {

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";

    }
}
~~~
- HttpMethod: HTTP 메서드 조회
- Locale: Locale 정보 조회
- @RequestHeader MultiValueMap<String, String> headerMap
  - 모든 HTTP 헤더를 MultiValueMap 형식으로 조회
  - MultivalueMap: 하나의 키에 여러 값을 받을 수 있음
- @RequestHeader("host") String host
  - 특정 HTTP 헤더 조회
  - 속성
    - 필수값 여부: required
    - 기본 값 속성: defaultValue
- @CookieValue(value="myCookie", required = false)
  - 특정 쿠키 조회
  - 속성
    - 필수 값 여부: required
    - 기본 값: defaultValue

## HTTP 요청 파라미터 - @RequestParam
~~~java
@ResponseBody
@RequestMapping("/request-param-v2")
public String requestParamV2(
        @RequestParam("username") String memberName,
        @RequestParam("age") int memberAge) {

    log.info("username={}, age={}", memberName, memberAge);

    return "ok";
}
~~~
- @RequestParam: 파라미터 이름으로 바인딩
- @ResponseBody: HTTP message Body에 직접 내용 입력

~~~java
@ResponseBody
@RequestMapping("/request-param-v3")
public String requestParamV3(
        @RequestParam String username,
        @RequestParam int age) {

    log.info("username={}, age={}", username, age);

    return "ok";
}
~~~
- HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="") 생략 가능

~~~java
@ResponseBody
@RequestMapping("/request-param-v4")
public String requestParamV4(String username, int age) {

    log.info("username={}, age={}", username, age);

    return "ok";
}
~~~
- String, int 등 단순 타입이면 @RequestParam도 생략 가능

~~~java
@ResponseBody
@RequestMapping("/request-param-required")
public String requestParamRequired(
        @RequestParam(required = true) String username,
        @RequestParam(required = false) Integer age) {

    log.info("username={}, age={}", username, age);

    return "ok";
}
~~~
- 파라미터 필수 여부 지정 가능
- 기본값이 true
- /request-param-required -> username이 없으므로 예외 400 예외
- /request-param-required?username= -> 빈문자가 넘어감
- int 는 null을 받아주지 못하므로 (500 예외) Integer로 해줘야함

~~~java
@ResponseBody
@RequestMapping("/request-param-default")
public String requestParamDefault(
        @RequestParam(required = true, defaultValue = "guest") String username,
        @RequestParam(required = false, defaultValue = "-1") Integer age) {

    log.info("username={}, age={}", username, age);

    return "ok";
}
~~~
- 파라미터 값이 없는 경우 defaultValue를 사용해 기본값 지정 가능
- 여기서 required는 무의미해짐

~~~java
@ResponseBody
@RequestMapping("/request-param-map")
public String requestParamMap(
        @RequestParam Map<String, Object> paramMap) {

    log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));

    return "ok";
}
~~~
- 파라미터를 Map, MultivalueMap으로 조회 가능

## HTTP 요청 파라미터 - @ModelAttribute
~~~java
@ResponseBody
@RequestMapping("/model-attribute-v1")
public String modelAttributeV1(@ModelAttribute HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    log.info("helloData={}", helloData);
    return "ok";
}
~~~

- 스프링 mvc는 @ModelAttribute가 있으면
  - HelloData 객체를 생성
  - 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을 입력(바인딩)한다.

**프로퍼티**
객체에 getUsername(), setUsername() 메서드가 있으면 이 객체는 username이라는 프로퍼티를 갖고 있다고 말한다. <br>
username 프로퍼티 값을 변경하면 setUsername() 호출, 조회하면 getUsername() 호출

~~~java
@ResponseBody
@RequestMapping("/model-attribute-v2")
public String modelAttributeV2(HelloData helloData) {
    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
    log.info("helloData={}", helloData);
    return "ok";
}
~~~

- @ModelAttribute 생략 가능

스프링은 애노테이션 생략 시 아래 규칙을 적용
- String, int, Integer 같은 단순 타입은 = @RequestParam
- 나머지 = @ModelAttribute

## HTTP 요청 메시지 - 단순 텍스트
HTTP message body를 통해 데이터가 직접 넘어오는 경우에는 @RequestParam, @ModelAttribute를 사용할 수 없다

~~~java
@PostMapping("/request-body-string-v1")
public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {

    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody={}", messageBody);

    response.getWriter().write("ok");

}
~~~
- HTTP message body의 데이터를 InputStream을 사용해 직접 읽을 수 있다.

~~~java
@PostMapping("/request-body-string-v2")
public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody={}", messageBody);

    responseWriter.write("ok");

}
~~~
- InputStream(Reader): HTTP 요청 메시지 바디의 내용을 직접 조회
- OutputStream(Writer): HTTP 응답 메시지 바디에 직접 결과 출력

~~~java
@PostMapping("/request-body-string-v3")
public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

    String messageBody = httpEntity.getBody();

    log.info("messageBody={}", messageBody);

    return new HttpEntity<>("ok");
}
~~~

- HttpEntity: HTTP header, body 정보를 편리하게 조회
  - 메시지 바디 정보를 직접 조회
  - HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

응답에서도 HttpEntity 사용
- 메시지 바디 정보 직접 반환
- HttpMessageConverter 사용 -> StringHttpMessageConverter 적용

~~~java
@ResponseBody
@PostMapping("/request-body-string-v4")
public String requestBodyStringV4(
        @RequestBody String messageBody
) throws IOException {

    log.info("messageBody={}", messageBody);

    return "ok";
}
~~~

- @RequestBody를 사용하면 HTTP 메시지 바디 정보를 편리하게 조회할 수 있다. 헤더 정보 필요 시 HttpEntity를 사용하거나 @RequestHeader 사용하자

## HTTP 요청 메시지 - JSON
~~~java
private ObjectMapper objectMapper = new ObjectMapper();

@PostMapping("/request-body-json-v1")
public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
    ServletInputStream inputStream = request.getInputStream();
    String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

    log.info("messageBody={}", messageBody);

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    response.getWriter().write("ok");

}
~~~

- HttpServletRequest를 사용해 직접 HTTP 메시지 바디에서 데이터를 읽어와 문자로 변환
- 문자로된 JSON 데이터를 objectMapper를 사용해 자바 객체로 변환

~~~java
@ResponseBody
@PostMapping("/request-body-json-v2")
public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException {

    log.info("messageBody={}", messageBody);

    HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

    log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

    return "ok";

}
~~~
- @RequestBody로 HTTP 메시지에서 데이터를 꺼내고 messageBody에 저장
- messageBody를 objectMapper를 통해 객체로 변환

~~~java
@ResponseBody
@PostMapping("/request-body-json-v3")
public String requestBodyJsonV3(@RequestBody HelloData data) throws IOException {

    log.info("username={}, age={}", data.getUsername(), data.getAge());

    return "ok";

}
~~~
- @RequestBody에 직접 만든 객체 지정 가능
- @RequestBody 생략 불가능 -> @ModelAttribute가 적용됨
- HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter (content-type: application/json)
- HttpEntity, @RequestBody 사용 시 HTTP 메시지 컨버터가 HTTP 메시지 바디의 내용을 우리가 원하는 형식으로 변환해줌
- JSON 객체로도 변환해줌 v2에서 객체로 만들어줬던 부분을 대신 처리

~~~java
@ResponseBody
@PostMapping("/request-body-json-v5")
public HelloData requestBodyJsonV5(@RequestBody HelloData data) throws IOException {
    log.info("username={}, age={}", data.getUsername(), data.getAge());
    return data;
}
~~~

- @ResponseBody 적용 -> 메시지 바디 정보 직접 반환
- HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)

## HTTP 응답 - 정적 리소스, 뷰 템플릿
### 정적 리소스
- 스프링 부트는 클래스패스의 다음 디렉토리에 잇는 정적 리소스를 제공
`/static`, `/public`, `/resources`, `/META-INF/resources`

`src/main/resources`는 리소스를 보관하는 곳이며 클래스패스의 시작 경로

정적 리소스 경로
`src/main/resources/static`

예를 들어
- `src/main/resources/static/basic/hello-form.html` 경로가 이와 같다면
- `http://localhost:8080/basic/hello-form.html` 처럼 실행하면 된다.

### 뷰 템플릿
뷰 템플릿을 거쳐 HTML이 생성되고 뷰가 응답을 만들어 전달한다. 일반적으로 HTML을 동적으로 생성하는 용도로 사용하지만 다른 것들도 가능.

스프링 부트는 기본 뷰 템플릿 경로를 제공
- `src/main/resources/templates`

뷰 템플릿 호출하는 컨트롤러
~~~java
@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello! v1");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello! v2");
        return "response/hello";
    }

    @RequestMapping("/response/hello")
    public void responseViewV3(Model model) {
        model.addAttribute("data", "hello! v3");
    }

}
~~~
String을 반환하는 경우
- @ResponseBody가 없으면 response/hello로 뷰 리졸버가 실행되어 뷰를 찾고 렌더링
- @ResponseBody가 있으면 HTTP 메시지 바디에 response/hello라는 문자가 입력됨

void를 반환하는 경우
- @Controller를 사용하고 HttpServletResponse, OutputStream(Writer) 같은 HTTP 메시지 바디를 처리하는 파라미터가 없을 때는 요청 URL을 참고해서 논리 뷰 이름으로 사용
  - 요청 url: /response/hello
  - 실행: template/response/hello.html
- 명시성이 너무 떨어지고 딱 맞는 경우도 많지 않아 권장하지 않음

HTTP 메시지
- @ResponseBody, HttpEntity를 사용하면 HTTP 메시지 바디에 직접 응답 데이터를 출력
