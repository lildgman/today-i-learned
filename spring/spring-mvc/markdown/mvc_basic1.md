# MVC 패턴
## 개요
### 너무 많은 역할을 하고 있음
하나의 서블릿이나 JSP만으로 비즈니스 로직, 뷰 렌더링까지 처리하게 되면 너무 많을 역할을 하게되고 유지보수가 어려워진다. 비즈니스 로직을 호출하는 부분에 변경이 있을 경우 해당 코드를 건드려야하고, ui를 변경할 때에도 비즈니스 로직이 같이 있는 파일을 수정해야한다.
### 변경 라이플 사이클
비즈니스 로직과 뷰 렌더링 변경의 라이프 사이클이 다르다. ui 수정과 비즈니스 로직 수정은 각각 다르게 발생할 가능성이 높고 서로 영향을 주지 않는다.
### 기능특화
뷰 템플릿은 화면을 렌더링하는데 최적화, 이 부분의 업무만 담당하는게 더욱 좋다.

## MVC
MVC패턴은 하나의 서블릿이나 JSP로 처리하던 것을 Controller, View라는 영역으로 역할을 나눈 것을 말한다.

**컨트롤러**: HTTP 요청을 받아 파라미터를 검증하고 비즈니스로직 실행, 뷰에 전달할 데이터를 조회해서 모델에 담아줌, 컨트롤러에 비즈니스로직을 둘 수 있지만 컨트롤러가 너무 많은 역할을 하게된다. 그래서 서비스(Service)라는 계층을 만들어 여기서 비즈니스 로직을 처리한다. 이때 컨트롤러는 서비스를 호출하는 역할을 담당

**모델**: 뷰에 출력할 데이터를 담아둔다. 뷰가 필요한 데이터를 담아주는 역할을 해주기 때문에 뷰는 비즈니스 로직이나 데이터 접근을 모르고 있어도 되고 화면을 렌더링 하는 기능에 집중할 수 있다.

**뷰**: 모델에 담겨있는 데이터를 가지고 화면을 그리는 일을 맡는다.

---
~~~java
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
~~~
forward(): 다른 서블릿이나 JSP로 이동할 수 있는 기능, 서버 내부에서 다시 호출

/WEB-INF: 이 경로안에 JSP가 있으면 외부에서 직접 JSP를 호출할 수 없음, 컨트롤러 통해서 JSP를 호출해야함

redirect vs forward
- redirect: 실제 클라이언트에 응답이 갔다가 클라이언트가 redirect 경로로 다시 요청 -> 클라이언트가 인지할 수 있고, url경로도 변경
- forward: 서버 내부에서 일어나는 호출, 클라이언트가 인지 x

~~~html
<form action="save" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
 </form>
~~~
절대경로(앞에 / 로 시작)이 아닌 상대경로(/ 로 시작x)하고 있음. 이렇게 하면 폼 전송 시 현재 URL이 속한 계층 경로 + save 가 호출된다.

## MVC 한계
컨트롤러에 중복이 많고 불필요한 코드들이 많다.
### 포워드 중복
~~~java
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response);
~~~
view로 이동하는 코드가 항숭 중복 호출됨, 메서드로 빼도되지만 그 메서드도 항상 직접 호출해줘야함

### ViewPath 중복
~~~java
String viewPath = "/WEB-INF/views/new-form.jsp";
~~~
- prefix: /WEB-INF/views/
- suffix: .jsp
jsp가 아닌 다른 뷰 템플릿으로 변경 시 이 부분이 들어간 곳을 모두 수정해줘야 함

### 사용하지 않는 코드
request, response를 사용하는 곳도 있고 사용하지 않는 곳도 있다

### 공통 처리의 어려움
기능이 복잡해질수록 컨트롤러에서 공통으로 처리하는 부분이 점점 많아질 것이다. 메서드로 빼면 될 것 같지만 그 메서드도 항항 호출해야하고 호출하는것 자체도 중복이다.

### 결론은 공통 처리가 어렵다!
이걸 해결하려면 컨트롤러 호출 전에 공통 기능을 처리해줘야함. **프론트 컨트롤러 패턴**을 도입해 문제를 해결할 수 있다.

## 프론트 컨트롤러 패턴
특징
- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받음
- 프론트 컨트롤러 요청에 맞는 컨트롤러를 찾아서 호출
- 공통 처리가 가능해짐

### 스프링 웹 MVC와 프론트 컨트롤러
스프링 웹 MVC의 핵심도 이 프론트 컨트롤러! <br>
스프링 웹 MVC의 DispatcherServlet이 프론트 컨트롤러 패턴으로 구현

## 프론트 컨트롤러 도입 - v1
1. 클라이언트 HTTP 요청
2. URL 매핑 정보에서 컨트롤러 조회
3. 컨트롤러 호출
4. 컨트롤러에서 JSP forward
5. HTML 응답

~~~java
public interface ControllerV1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
~~~
각 컨트롤러는 이 인터페이스를 구현