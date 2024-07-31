# 예외처리와 오류페이지
## 서블릿 예외 처리
서블릿은 2가지 방식으로 예외 처리를 지원한다.
- Exception
- response.sendError(HTTP 상태코드, 오류 메시지)

### Exception
**자바 직접 실행**<br>
자바 메인 메서드를 직접 실행할 경우 main 이라는 이름의 쓰레드가 실행된다. 실행 도중에 예외를 처리하지 못하고 main 메서드를 넘어 예외가 던져지면 예외 정보를 남기고 해당 쓰레드는 종료된다.

**웹 애플리케이션**<br>
웹 애플리케이션은 사용자 요청별로 별도 쓰레드가 할당되어 있고, 서블릿 컨테이너 안에서 실행된다. 애플리케이션 내에서 예외가 발생했는데 try, catch로 예뢰를 처리하면 문제가 생기지 않는다. 하지만 애플리케이션에서 예외를 잡지 못하고 서블릿 밖으로 까지 나오게 되는 경우에는 어떻게 동작할까>

~~~
WAS <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러 (여기서 예외발생)
~~~
결국에 WAS까지 예외가 전달된다. WAS는 어떤 식으로 예외를 처리해야 할까?

스프링부트는 기본 예외 페이지를 제공하는데 여기서는 잠시 꺼두자

**applicatio.properties**
~~~
server.error.whitelabel.enabled=false
~~~

~~~java
@GetMapping("/error-ex")
public void errorEx() {
    throw new RuntimeException("예외발생");
}
~~~
- 실행하보면 tomcat이 기본을 제공하는 오류 화면을 볼 수 있다.
- HTTP 상태코드가 500으로 보인다.
- Exception의 경우 서버 내부에서 처리할 수 없는 오류가 발생한 것으로 생각해 HTTP 상태 코드를 500으로 반환한다.

### response.sendError(HTTP 상태코드, 오류 메시지)
이 메서드를 사용하면 서블릿 컨테이너에게 오류가 발생했다는 점을 전달할 수 있다. 또한 HTTP 상태 코드와 오류 메시지도 추가할 수 있다.
~~~java
@GetMapping("/error-404")
public void error404(HttpServletResponse response) throws IOException {
    response.sendError(404, "404 오류");
}

@GetMapping("/error-500")
public void error500(HttpServletResponse response) throws IOException {
    response.sendError(500);
}
~~~

~~~
WAS(sendError 호출 기록 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(response.sendError())
~~~
`response.sendError()` 호출 시 response 내부에는 오류가 발생했다는 상태를 저장한다. 그리고 서블릿 컨테이너는 고객에게 응답 전에 response에 sendError()가 호출되었는지 확인 후, 호출되었다면 오류코드에 맞춰 오류 페이지를 보여준다.

## 서블릿 예외 처리 - 오류 화면 제공
서블릿은 Exception가 발생해서 서블릿 밖으로 전달되거나 response.sendError()가 호출 되었을 때 상황에 맞춘 오류 처리 기능을 제공한다.
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

해당 오류들을 처리하기 위해서는 컨트롤러가 필요하다.

~~~java
@Slf4j
@Controller
public class ErrorPageController {

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");

        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");

        return "error-page/500";
    }

}
~~~

## 서블릿 예외 처리 - 오류 페이지 작동 원리
서블릿은 Exception이 발생해서 서블릿 밖으로 전달되거나 response.sendError()가 호출 되었을 때 설정된 오류 페이지를 찾는다.

**예외 발생 흐름**
~~~
WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외발생)
~~~

**sendError 흐름**
~~~
WAS(sendError 호출 기록 확인) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(response.sendError())
~~~

WAS는 해당 예외를 처리하는 오류 페이지 정보를 확인한다.
~~~java
new ErrorPage(RuntimeException.class, "/error-page/500");
~~~
`RuntimeException`이 WAS까지 전달되면 WAS는 오류 페이지 정보를 확인한다. 확인 결과 `RuntimeException`의 오류 페이지로 `/error-page/500`이 지정되어 있다. WAS는 오류 페이지를 출력하기 위해 `/error-page/500`을 다시 요청한다.

**오류 페이지 요청 흐름**
~~~
WAS "/error-page/500" 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> view
~~~

**여기서 중요한 점은 클라이언트는 서버 내부에서 이러한 작업이 일어나는 것을 전혀 모른다. 서버 내부에서 오류 페이지를 찾기 위해 추가적인 호출을 한 것이다.**

정리하자면, <br>
1. 예외가 발생해 WAS까지 전파된다.
2. WAS는 오류 페이지 경로를 찾아 내부에서 오류 페이지를 호출한다. 이때 오류 페이지 경로로 필터, 서블릿, 인터셉터, 컨트롤러가 모두 호출된다.

### 오류 정보 추가
WAS는 오류페이지를 요청만 하는것이 아니라, 오류 정보를 request의 attribute에 추가해서 넘겨준다. 필요하면 오류 페이지에서 이렇게 전달된 오류 정보를 사용 가능하다.
~~~java
@Slf4j
@Controller
public class ErrorPageController {

    //RequestDispatcher 상수로 정의되어 있음
    public static final String ERROR_EXCEPTION = "jakarta.servlet.error.exception";
    public static final String ERROR_EXCEPTION_TYPE = "jakarta.servlet.error.exception_type";
    public static final String ERROR_MESSAGE = "jakarta.servlet.error.message";
    public static final String ERROR_REQUEST_URI = "jakarta.servlet.error.request_uri";
    public static final String ERROR_SERVLET_NAME = "jakarta.servlet.error.servlet_name";
    public static final String ERROR_STATUS_CODE = "jakarta.servlet.error.status_code";

    @RequestMapping("/error-page/404")
    public String errorPage404(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 404");
        printErrorInfo(request);
        return "error-page/404";
    }

    @RequestMapping("/error-page/500")
    public String errorPage500(HttpServletRequest request, HttpServletResponse response) {
        log.info("errorPage 500");
        printErrorInfo(request);

        return "error-page/500";
    }

    private void printErrorInfo(HttpServletRequest request) {
        log.info("ERROR_EXCEPTION: {}", request.getAttribute(ERROR_EXCEPTION));
        log.info("ERROR_EXCEPTION_TYPE: {}", request.getAttribute(ERROR_EXCEPTION_TYPE));
        log.info("ERROR_MESSAGE: {}", request.getAttribute(ERROR_MESSAGE));
        log.info("ERROR_REQUEST_URI: {}", request.getAttribute(ERROR_REQUEST_URI));
        log.info("ERROR_SERVLET_NAME: {}", request.getAttribute(ERROR_SERVLET_NAME));
        log.info("ERROR_STATUS_CODE: {}", request.getAttribute(ERROR_STATUS_CODE));
        log.info("dispatcherType={}",request.getDispatcherType());
    }

}
~~~

~~~
ERROR_EXCEPTION_TYPE: class java.lang.RuntimeException
ERROR_MESSAGE: Request processing failed: java.lang.RuntimeException: 예외 발생!
ERROR_REQUEST_URI: /error-ex
ERROR_SERVLET_NAME: dispatcherServlet
ERROR_STATUS_CODE: 500
dispatcherType=ERROR
~~~

## 서블릿 예외 처리 - 필터
**예외 발생과 오류 페이지 요청 흐름**
~~~
1. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외 발생)
2. WAS "/error-page/500" 다시 요청 -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러(/error-page/500) -> View
~~~
오류 발생 시 오류 페이지를 출력하기 위해 WAS 내부에서 다시 한번 호출 발생, 이때 필터, 서블릿, 인터셉터도 다시 호출된다. 이때 이미 필터나 인터셉터에서 체크가 완료되있는데 또 다시 호출 되는 것은 비효율적이다.

따라서 클라이언트로부터 발생한 정상 요청인지, 오류 페이지를 출력하기 위한 내부 요청인지 구분할 수 있어야한다. 서블릿은 이러한 문제를 해결하기 위해 `DispatcherType`이라는 추가 정보를 제공한다.

### DispatcherType
서블릿 스펙은 실제 클라이언트로부터 발생한 요청인지, 서버 내부에서 오류 페이지를 요청한 갓인지 구분할 수 있는 `DispatcherType`을 제공한다.

~~~java
public enum DispatcherType {
    FORWARD,
    INCLUDE,
    REQUEST,
    ASYNC,
    ERROR;

    private DispatcherType() {
    }
}    
~~~
- REQUEST: 클라이언트 요청
- ERROR: 오류 요청
- FORWORD: 서블릿에서 다른 서블릿이나 JSP 호출할 때
- INCLUDE: 서블릿에서 다른 서블릿이나 JSP의 결과를 포함할 때
- ASYNC: 서블릿 비동기 호출

~~~java
@Slf4j
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("log filter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();

        try {
            log.info("REQUEST  [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.info("EXCEPTION {}", e.getMessage());
            throw e;
        } finally {
            log.info("RESPONSE [{}][{}][{}]", uuid, request.getDispatcherType(), requestURI);
        }
    }

    @Override
    public void destroy() {
        log.info("log filter destroy");
    }
}
~~~
~~~java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return filterFilterRegistrationBean;
    }
}
~~~
- `filterFilterRegistrationBean.serDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR)`: 클라이언트 요청, 오류 페이지 요청에서도 필터가 호출된다. 아무것도 넣지 않으면 기본값인 DispatcherType.REQUEST만 적용

## 서블릿 예외 처리 - 인터셉터
~~~java

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

        String uuid = UUID.randomUUID().toString();
        request.setAttribute(LOG_ID, uuid);

        log.info("REQUEST [{}][{}][{}][{}]", uuid, request.getDispatcherType(), requestURI, handler);
        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("postHandle [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String) request.getAttribute(LOG_ID);
        log.info("RESPONSE [{}][{}][{}]", logId, request.getDispatcherType(), requestURI);

        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
~~~

전에 필터를 등록할 때 어떤 `DispatcherType`를 필터에 적용할 지 선택할 수 있었다. 인터셉터는 스프링이 제공하는 기능이다. 즉, `DispatcherType`과 무관하게 항상 호출된다.

대신 요청 경로에 따라 추가하거나 제외할 수 있게 설계되어있어 따로 설정을 해줄 수 있다.
~~~java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor())
                .order(1)
                .addPathPatterns("/*")
                .excludePathPatterns("/css/**", "*.ico", "/error", "/error-page/**");

    }

//    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
        filterFilterRegistrationBean.setFilter(new LogFilter());
        filterFilterRegistrationBean.setOrder(1);
        filterFilterRegistrationBean.addUrlPatterns("/*");
        filterFilterRegistrationBean.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ERROR);

        return filterFilterRegistrationBean;
    }
}
~~~

정상 요청 시 흐름
~~~
WAS(dispatcherType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러 -> View
~~~

오류 요청
- 필터는 DispatcherType으로 중복 호출 제거
- 인터셉터는 경로 정보로 중복 호출 제거(excludePathPatterns())

~~~
1. WAS(/error-ex, dispatcherType=REQUEST) -> 필터 -> 서블릿 -> 인터셉터 -> 컨트롤러
2. WAS(여기까지 전파) <- 필터 <- 서블릿 <- 인터셉터 <- 컨트롤러(예외 발생)
3. WAS 오류 페이지 확인
4. WAS(/error-page/500, dispatcherType=ERROR) -> 필터(x) -> 서블릿 -> 인터셉터(x) -> 컨트롤러(/error-page/500) -> view
~~~

## 스프링 부트 - 오류페이지 1
스프링부트는 앞서 했던 과정들을 기본으로 제공한다.
- ErrorPage를 자동으로 등록한다. /error라는 경로로 기본 오류 페이지를 설정한다.
    - `new ErrorPage("/error")`, 상태코드와 예외를 설정하지 않으면 기본 오류 페이지로 사용
    - 서블릿 밖으로 예외가 발생하거나, `response.sendError()`가 호출되면 모든 오류는 /error를 호출하게 된다
- BasicErrorController라는 스프링 컨트롤러를 자동으로 등록한다.
    - ErrorPage에서 등록한 /error를 매핑해서 처리하는 컨트롤러

앞서 만들었던 WebServerCustomizer에 있는 @Component를 주석처리하자.

이제 오류 발생 시 오류 페이지로 /error를 기본 요청한다. 스프링 부트가 자동으로 등록한 BasicErrorController는 이 경로를 기본으로 받는다.

덕분에 개발자는 오류페이지만 BasicErrorController의 룰과 우선순위대로 등록하기만 하면 된다.

### 뷰 선택 우선순위
1. 뷰 템플릿
    - resources/templates/error/500.html
    - resources/templates/error/5xx.html
2. 정적 리소스(static, public)
    - resources/static/error/400.html
    - resources/static/error/404.html
    - resources/static/error/4xx.html
3. 적용 대상이 없을 때 뷰 이름

해당 경로 위치에 HTTP 상태 코드 이름의 뷰 파일을 넣어두면 된다. 구체적인 HTTP 상태 코드가 우선순위가 높다.

## 스프링 부트 - 오류 페이지 2
### BasicErrorController가 제공하는 기본 정보들
~~~
timestamp: Wed Jul 31 15:24:08 KST 2024
path: /error-ex
status: 500
message: null
error: Internal Server Error
exception: java.lang.RuntimeException
errors: null
trace: 예외 trace
~~~