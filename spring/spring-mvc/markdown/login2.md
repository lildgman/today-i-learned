# 로그인 처리 2 - 필터, 인터셉터
로그인 한 사용자에게만 상품 관리 페이지에 들어갈 수 있게 처리하고자한다.
컨트롤러에서 로그인 여부를 체크하는 로직을 작성해주면 되겠지만 모든 컨트롤러 로직에서 공통으로 로그인 여부를 확인해야한다. 로그인과 관련된 로직이 변경될 때 작성했던 로직들을 모두 수정해야한다.

애플리케이션 여러 로직에서 공통으로 관심 있는 것을 공통 관심사라고 한다. 여기서는 등록, 수정, 삭제, 조회 등 여러 로직에서 공통으로 인증에 대해 관심을 갖고 있다.

여기서는 필터와 인터셉터를 사용해보도록 하자

## 서블릿 필터
~~~
HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러
~~~
필터 적용 시 필터가 호출된 다음 서블릿이 호출된다.

필터 제한
~~~
로그인 사용자
HTTP 요청 -> WAS -> 필터 -> 서블릿 -> 컨트롤러

비 로그인 사용자
HTTP 요청 -> WAS -> 필터(적절치 못한 요청, 서블릿 호출 x)
~~~

필터 체인
~~~
HTTP 요청 -> WAS -> 필터1 -> 필터2 -> 필터3 -> 서블릿 -> 컨트롤러
~~~
필터는 체인으로 구성, 필터를 추가할 수 있다
~~~java
public interface Filter {
  public default void init(FilterConfig filterConfig) throws ServletException{}
  
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;
  
  public default void destroy() {}
}
~~~

- init(): 필터 초기화 메서드, 서블릿 컨테이너가 생성될 때 호출
- doFilter(): 요청이 올 때마다 이 메서드가 호출, 필터 로직을 구현하면 된다.
- destory(): 필터 종료 메서드, 서블릿 컨테이너가 종료될 때 호출

~~~java
@Slf4j
public class LogFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

      log.info("log filter init");
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
      log.info("log filter doFilter");

      HttpServletRequest httpRequest = (HttpServletRequest) request;
      String requestURI = httpRequest.getRequestURI();

      String uuid = UUID.randomUUID().toString();

      try {
          log.info("REQUEST [{}][{}]", uuid, requestURI);
          filterChain.doFilter(request, response);
      } catch (Exception e) {
          throw e;
      } finally {
          log.info("RESPONSE [{}][{}]", uuid, requestURI);
      }
  }

  @Override
  public void destroy() {
      log.info("log filter destroy");

  }
}

~~~

~~~ java
@Configuration
public class WebConfig {

  @Bean
  public FilterRegistrationBean logFilter() {
      FilterRegistrationBean<Filter> filterFilterRegistrationBean = new FilterRegistrationBean<>();
      filterFilterRegistrationBean.setFilter(new LogFilter());
      filterFilterRegistrationBean.setOrder(1);
      filterFilterRegistrationBean.addUrlPatterns("/*");

      return filterFilterRegistrationBean;
  }
}
~~~
스프링 부트를 사용하고 있을 때 FilterRegistrationBean을 사용해서 등록
- setFilter(new Filter): 등록할 필터 지정
- setOrder(1): 필터는 체인으로 동작하는데 순서가 필요하다.
- addUrlPatterns("/*"): 필터를 적용할 URL 패턴 지정, 한번에 여러 패턴 지정 가능