# 서블릿 2
## HttpServletRequest
### start-line 정보
~~~java
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
    }

    private void printStartLine(HttpServletRequest request) {
        System.out.println("==== REQUEST LINE - START ====");

        System.out.println("request.getMethod() = " + request.getMethod());
        System.out.println("request.getProtocol() = " + request.getProtocol());
        System.out.println("request.getScheme() = " + request.getScheme());

        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        System.out.println("request.getQueryString() = " + request.getQueryString());

        System.out.println("request.isSecure() = " + request.isSecure());

        System.out.println("=== REQUEST LINE - END ====");
        System.out.println();
    }
}
~~~

~~~
==== REQUEST LINE - START ====
request.getMethod() = GET
request.getProtocol() = HTTP/1.1
request.getScheme() = http
request.getRequestURL() = http://localhost:8080/request-header
request.getRequestURI() = /request-header
request.getQueryString() = username=oh
request.isSecure() = false
=== REQUEST LINE - END ====
~~~

### 헤더 정보
~~~ java
private void printHeaders(HttpServletRequest request) {
        System.out.println("==== Headers - start ====");

        request.getHeaderNames()
                .asIterator().
                forEachRemaining(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));
        System.out.println("==== Headers - end ====");
    }
~~~
~~~
==== Headers - start ====
host: localhost:8080
connection: keep-alive
cache-control: max-age=0
sec-ch-ua: "Not/A)Brand";v="8", "Chromium";v="126", "Google Chrome";v="126"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "macOS"
upgrade-insecure-requests: 1
user-agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36
accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
sec-fetch-site: none
sec-fetch-mode: navigate
sec-fetch-user: ?1
sec-fetch-dest: document
accept-encoding: gzip, deflate, br, zstd
accept-language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7
==== Headers - end ====
~~~

### Header 편리 조회
~~~java
private void printHeaderUtils(HttpServletRequest request) {

        System.out.println("==== Header 편의 조회 start ====");
        System.out.println("Host 편의 조회");
        System.out.println("request.getServerName() = " + request.getServerName());
        System.out.println("request.getServerPort() = " + request.getServerPort());
        System.out.println();

        System.out.println("Accept-Language 편의 조회");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();

        System.out.println("Cookie 편의 조회");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println("cookie.getName() = " + cookie.getName() + ", cookie.getValue() = " + cookie.getValue());
            }
        }
        System.out.println();

        System.out.println("Content 편의 조회");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " + request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());

        System.out.println("=== Header 편의 조회 end ===");
        System.out.println();
    }
~~~
~~~
==== Header 편의 조회 start ====
Host 편의 조회
request.getServerName() = localhost
request.getServerPort() = 8080

Accept-Language 편의 조회
locale = ko_KR
locale = ko
locale = en_US
locale = en
request.getLocale() = ko_KR

Cookie 편의 조회

Content 편의 조회
request.getContentType() = null
request.getContentLength() = -1
request.getCharacterEncoding() = UTF-8
=== Header 편의 조회 end ===
~~~
### 기타 - 이 기타정보는 HTTP 메시지의 정보가 아님
~~~java
private void printEtc(HttpServletRequest request) {
        System.out.println("==== 기타 조회 start");
        System.out.println("Remote 조회");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost());
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr());
        System.out.println("request.getRemotePort() = " + request.getRemotePort());
        System.out.println();

        System.out.println("Local 정보");
        System.out.println("request.getLocalName() = " + request.getLocalName());
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr());
        System.out.println("request.getLocalPort() = " + request.getLocalPort());

        System.out.println("==== 기타 조회 end ====");
        System.out.println();

    }
~~~
~~~
==== 기타 조회 start
Remote 조회
request.getRemoteHost() = 0:0:0:0:0:0:0:1
request.getRemoteAddr() = 0:0:0:0:0:0:0:1
request.getRemotePort() = 57557

Local 정보
request.getLocalName() = localhost
request.getLocalAddr() = 0:0:0:0:0:0:0:1
request.getLocalPort() = 8080
==== 기타 조회 end ====
~~~

---
## HTTP 요청 데이터 전달 방법
- GET - 쿼리 파라미터
  - (url)~~~?username=oh&age=20
  - 메시지 바디 없이 url의 쿼리 파라미터에 데이터를 포함해서 전달
  - 검색, 페이징 등에서 많이 사용
- POST - HTML Form
  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파라미터 형식으로 전달
  - 회원가입, 상품주문 등, HTML Form 사용
- HTTP message body에 데이터 직접 담아서 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH

### HTTP 요청 데이터 - GET 쿼리 파라미터
- http://localhost:8080/request-param?username=oh&age=20
- 전달 데이터
  - username=oh
  - age=20
~~~java
@WebServlet(name="requestParamServlet", urlPatterns = "/request-param")
public class RequestParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("전체 파라미터 조회 - start");

        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName + " = " + request.getParameter(paramName)));

        System.out.println("전체 파라미터 조회 - end");
        System.out.println();

        System.out.println("단일 파라미터 조회");
        String username = request.getParameter("username");
        System.out.println("request.getParameter(\"username\") = " + username);

        String age = request.getParameter("age");
        System.out.println("request.getParameter(\"age\") = " + age);

        System.out.println("이름이 같은 복수 파라미터 조회");
        System.out.println("request.getParameterValues(username)");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }

        response.getWriter().write("ok");
    }
}
~~~
~~~
==== 전체 파라미터 조회 - start ====
username = oh
age = 20
==== 전체 파라미터 조회 - end ====

==== 단일 파라미터 조회 ====
request.getParameter("username") = oh
request.getParameter("age") = 20
==== 이름이 같은 복수 파라미터 조회 ====
request.getParameterValues(username)
username = oh
~~~
동일 파라미터 전송 시
- http://localhost:8080/request-param?username=oh&username=kim&age=20

~~~
==== 전체 파라미터 조회 - start ====
username = oh
age = 20
==== 전체 파라미터 조회 - end ====

==== 단일 파라미터 조회 ====
request.getParameter("username") = oh
request.getParameter("age") = 20
==== 이름이 같은 복수 파라미터 조회 ====
request.getParameterValues(username)
username = oh
username = kim
~~~

파라미터가 하나인데 값이 두개일 경우에는 request.getParameterValues()를 사용하자

### HTTP 요청 데이터 - POST HTML FORM
특징
- content-type: application/x-www-form-urlencoded
- 메시지 바디에 쿼리 파라미터 형식으로 데이터 전달
~~~html
<form action="/request-param" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
~~~
POST의 HTML Form을 전송하면 웹 브라우저가 아래와 같이 HTTP 메시지를 만듦
- 요청 url: http://localhost:8080/request-param
- content-type: application/x-www-form-urlencoded
- message body: username=oh&age=20

application/x-www-form-urlencoded는 GET의 쿼리 파라미터 형식과 같다. 서버입장에서는 둘의 형식이 같아 request.getParameter()로 조회 가능

여기서 content-type은 HTTP 메시지 바디의 데이터 형식을 지정함<br>
GET방식으로 서버로 데이터 전송 시 HTTP 메시지 바디를 사용하지 않기 때문에 content-type이 없다. <br>
POST HTML Form 형식으로 데이터 전달 시 HTTP 메시지 바디에 데이터를 넣어 보내기 때문에 데이터가 어떠한 형식인지 content-type을 지정해야함

### HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트
- HTTP message body에 데이터 직접 담아 요청
  - HTTP API에서 주로 사용, JSON, XML, TEXT
  - 주로 JSON 사용
  - POST, PUT, PATCH
  - HTTP 메시지 바디에 데이터를 InputStream을 사용해서 직접 읽을 수 있음
~~~java
@WebServlet(name="requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("ok");
    }
}
~~~

### HTTP 요청 데이터 - API 메시지 바디 - JSON
- JSON 형식 전송
  - context-type: application/json
  - message body: "{"username" : "oh", "age" : 20}"
~~~java
@WebServlet(name="requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        System.out.println("helloData.getUsername() = " + helloData.getUsername());
        System.out.println("helloData.getAge() = " + helloData.getAge());

        response.getWriter().write("ok");
    }
}
~~~
~~~
messageBody = {"username" : "oh", "age" : 20}
helloData.getUsername() = oh
helloData.getAge() = 20
~~~

## HttpServletResponse
### HttpServletResponse 역할
HTTP 응답 메시지 설정
- HTTP 응답코드 지정
- 헤더 생성
- 바디 생성

### HTTP 응답 데이터 - 단순 데이터, HTML
~~~java
@WebServlet(name="responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println("    <div>이거 빡센데?</div>");
        writer.println("</body>");
        writer.println("<html>");

    }
}
~~~
HTTP응답으로 HTML 반환 시 content-type을 text/html로 지정해야 함

### HTTP 응답 데이터 - API JSON
~~~java
@WebServlet(name="responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setHeader("content-type", "application/json");
        response.setCharacterEncoding("utf-8");

        HelloData data = new HelloData();
        data.setUsername("oh");
        data.setAge(20);

        String dataResult = objectMapper.writeValueAsString(data);
        response.getWriter().write(dataResult);

    }
}
~~~
결과: {"username":"oh","age":20}

application/json은 utf-8 형식을 사용하도록 정의되어있음


