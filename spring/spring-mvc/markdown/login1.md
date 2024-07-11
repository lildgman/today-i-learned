# 로그인 처리 1
## 로그인 처리하기 - 쿠키

로그인을 유지하기 위해서 로그인 성공 시 서버에서 HTTP 응답에 쿠키를 담아서 브라우저에 전달하도록 할 수 있다. 그러면 브라우저는 해당 쿠키를 지속해서 보내준다.

- 영속 쿠키: 만료 날짜를 입력하면 해당 날짜까지 유지
- 세션 쿠키: 만료 날짜를 생략하면 브라우저 종료될때까지만 유지

~~~java
Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
response.addCookie(idCookie);
~~~
로그인 성공 시 쿠키를 생성하고 HttpServletResponse에 담는다. 

~~~java
@GetMapping("/")
public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

    if (memberId == null) {
        return "home";
    }

    // 로그인
    Member loginMember = memberRepository.findById(memberId);
    if (loginMember == null) {
        return "home";
    }

    model.addAttribute("member", loginMember);

    return "loginHome";
}
~~~
- `@CookieValue` 사용 시 쿠키를 편리하게 조회할 수 있다.
- 로그인 하지 않은 사용자도 홈에 접급할 수 있기 때문에 `required = false`를 사용한다.
- 로그인 쿠키가 없는 사용자와 로그인 쿠키가 있어도 회원이 없을 경우 home으로 보낸다.
- 로그인 쿠키가 있는 사용자는 로그인한 사용자 전용 화면인 loginHome으로 보낸다. 화면에서는 회원 관련 정보도 출력해야하므로 model에 member를 담아 전달한다.

### 로그아웃
~~~java
@PostMapping("/logout")
public String logout(HttpServletResponse response) {

    expireCookie(response, "memberId");

    return "redirect:/";
}

private static void expireCookie(HttpServletResponse response, String cookieName) {
    Cookie cookie = new Cookie(cookieName, null);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
}
~~~
로그아웃 시 쿠키의 age를 0으로 설정해 쿠키가 즉시 종료된다.

### 쿠키의 보안 문제
- 쿠키 값은 임의로 변경할 수 있다.
- 쿠키에 보관된 정보를 훔쳐갈 수 있다.

대안
- 쿠키에 중요한 값을 노출시키지 않고 예측 불가능한 값을 노출하고 서버에서 토큰과 사용자 id를 매핑해서 인식한다. 그리고 서버에서 토큰을 관리한다.
- 토큰은 예측 불가능해야한다.
- 토큰을 털어가도 시간이 지나면 사용할 수 없도록 만료시간을 짧게 유지한다.

## 로그인 처리하기 - 세션
쿠키에는 여러 보안 이슈가 있었다. 이를 해결하기 위해서는 중요 정보들을 모두 서버에 저장해야한다.

### 직접 세션 구현해보기

- 세션 생성
  - sessionId 생성(추정 불가능한 랜덤 값)
  - 세션 저장소에 sessionId와 보관할 값 저장
  - sessionId로 응답 쿠키를 생성해 클라이언트에 저장
- 세션 조회
  - 클라이언트가 요청한 sessionId의 쿠키의 값, 세션 저장소에 보관한 값 조회
- 세션 만료
  - 클라이언트가 요청한 SessionId 쿠키의 값, 세션 저장소에 보관한 sessionId와 값 제거

~~~java
@Component
public class SessionManager {

  public static final String SESSION_COOKIE_NAME = "mySessionId";
  private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

  // 세션 생성
  public void createSession(Object value, HttpServletResponse response) {

      // 세션 id 생성 후 값을 세션에 저장
      String sessionId = UUID.randomUUID().toString();
      sessionStore.put(sessionId, value);

      // 쿠키 생성
      Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
      response.addCookie(mySessionCookie);
  }

  // 세션 조회
  public Object getSession(HttpServletRequest request) {
      Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
      if (sessionCookie == null) {
          return null;
      }

      return sessionStore.get(sessionCookie.getValue());
  }

  // 세션 만료
  public void expire(HttpServletRequest request) {
      Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);

      if (sessionCookie != null) {
          sessionStore.remove(sessionCookie.getValue());
      }

  }

  public Cookie findCookie(HttpServletRequest request, String cookieName) {
      if (request.getCookies() == null) {
          return null;
      }

      return Arrays.stream(request.getCookies())
              .filter(cookie -> cookie.getName().equals(cookieName))
              .findAny()
              .orElse(null);
  }
}
~~~

## 로그인 처리하기 - 직접 만든 세션 적용
~~~java
@PostMapping("/login")
  public String login2(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletResponse response) {

      if (bindingResult.hasErrors()) {
          return "login/loginForm";
      }

      Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

      if (loginMember == null) {
          bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
          return "login/loginForm";

      }

      // 로그인 성공 처리
      sessionManager.createSession(loginMember, response);

      return "redirect:/";
  }
~~~

~~~java
@PostMapping("/logout")
public String logout2(HttpServletRequest request) {

    sessionManager.expire(request);
    return "redirect:/";
}
~~~

~~~java
@GetMapping("/")
public String homeLogin2(HttpServletRequest request, Model model) {

    // 세션 관리자에 저장된 회원 정보 조회
    Member member = (Member) sessionManager.getSession(request);

    // 로그인
    if (member == null) {
        return "home";
    }

    model.addAttribute("member", member);

    return "loginHome";
}
~~~

## 로그인 처리하기 - 서블릿 HTTP 세션 1
서블릿은 세션을 위해 HttpSession 기능을 제공, 직접 만든 SessionManager와 같은 방식으로 동작한다.

~~~java
@PostMapping("/login")
public String login3(@Validated @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

    if (bindingResult.hasErrors()) {
        return "login/loginForm";
    }

    Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

    if (loginMember == null) {
        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
        return "login/loginForm";

    }

    // 로그인 성공 처리
    // 세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
    HttpSession session = request.getSession();
    // 세션에 로그인 회원 정보 보관
    session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
    
    return "redirect:/";
}
~~~
- 세션을 생성하려면 `request.getSession()` 사용하면 된다.
  - `getSession(true)`
    - 세션이 있으면 기존 세션 반환
    - 없을 때 새로운 세션 생성 후 반환
  - `getSession(false)`
    - 세션이 있을 때 기존 세션 반환
    - 없을 때 새로운 세션 생성하지 않음, null 반환
  
~~~java
@PostMapping("/logout")
public String logout3(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session != null) {
        session.invalidate();
    }

    return "redirect:/";
}
~~~
- `session.invalidate()`: 세션을 제거

~~~java
@GetMapping("/")
public String homeLogin3(HttpServletRequest request, Model model) {

    HttpSession session = request.getSession(false);
    if (session == null) {
        return "home";
    }


    // 세션 관리자에 저장된 회원 정보 조회
    Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
    // 로그인
    // 세션에 회원 데이터가 없으면 home
    if (loginMember == null) {
        return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);

    return "loginHome";
}
~~~
`request.getSession(false)`: 로그인 하지 않은 사용자도 의미가 없는 세션이 만들어지므로 세션 생성 방지

## 로그인 처리하기 - 서블릿 HTTP 세션 2
스프링에선 `@SessionAttribute`를 지원한다. 로그인 된 사용자를 찾을 때 사용해보자. 이 기능은 세션을 생성하지 않는다.
~~~java
@GetMapping("/")
public String homeLogin3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

    if (loginMember == null) {
        return "home";
    }

    // 세션이 유지되면 로그인으로 이동
    model.addAttribute("member", loginMember);

    return "loginHome";
}
~~~

### TrackingModes
처음 로그인 시도 시 url이 jsessionid를 포함하고 있다. 
이것은 브라우저가 쿠키를 지원하지 않을 때 쿠키 대신 url을 통해 세션을 유지하는 방법이다.
이 방법을 사용하려면 url에 세션 값을 계속 포함해서 전달해야 한다. 
타임리프 같은 템플릿 엔진을 통해 링크를 걸면 jsessionid를 url에 자동으로 포함해준다. 
서버 입장에서 브라우저가 쿠키를 지원하는지 안하는지 최초에는 판단하지 못하기 때문에 쿠키 값도 전달하고 url에 jsessionid도 같이 전달한다.

이 방식을 끄고 쿠키를 통해서만 세션을 유지하고자 할 때
`application.properties`
~~~
server.servlet.session.tracking-modes=cookie
~~~

## 세션 정보와 타임아웃 설정
~~~java
@Slf4j
@RestController
public class SessionInfoController {

  @GetMapping("/session-info")
  public String sessionInfo(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session == null) {
          return "세션이 없습니다.";
      }

      // 세션 데이터 출력
      session.getAttributeNames().asIterator()
              .forEachRemaining(name -> log.info("session name={}, value={}", name, session.getAttribute(name)));

      log.info("sessionId={}", session.getId());
      log.info("getMaxInactiveInterval={}", session.getMaxInactiveInterval());
      log.info("creationTime={}", new Date(session.getCreationTime()));
      log.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
      log.info("isNew={}", session.isNew());

      return "세션 출력";
  }
}
~~~

- `session.getId()`: 세션 Id
- `MaxInactiveInterval`: 세션의 유효시간 
- `creationTime`: 세션 생성일시
- `lastAccessedTime`: 세션과 연결된 사용자가 최근 서버에 접근한 시간
- `isNew`: 새로 생성된 세션인지, 과거에 만들어졌고 클라이언트에서 서버로 요청해서 조회된 세션인지 여부

### 세션 타입 아웃
세션은 사용자가 로그아웃 시 `session.invalidate()`가 호출 할 때 삭제된다.
로그아웃 누르지 않고 브라우저를 종료하는 경우가 많다.
HTTP는 비연결성이므로 서버 입장에서는 사용자가 브라우저를 종료한 것인지 아닌지 인식할 수가 없다.
즉, 서버에서는 세션 데이터를 언제 삭제해야 하는지 판단하기 어렵다.

이 때 세션이 계속 남아있을 수 있다.
- 세션과 관련된 쿠키를 탈취당했을 경우 오랜 시간 지나도 해당 쿠키로 악의적 요청이 가능하다
- 메모리 크기가 무한하지 않기 때문에 필요한 경우에만 생성해야한다.

**세션 종료 시점**
세션 생성 시점으로부터 시간을 지정해주는게 아닌 세션 최근 요청 시간을 기준으로 시간을 지정해주자.

`application.properties`
~~~
server.servlet.session.timeout=1800
~~~

특정 세션 단위로 시간 설정
~~~java
session.setMaxInactiveInterval(1800);
~~~