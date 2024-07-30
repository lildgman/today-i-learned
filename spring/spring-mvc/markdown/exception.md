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