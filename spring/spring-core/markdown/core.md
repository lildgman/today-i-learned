# 스프링 핵심 원리
## 스프링 컨테이너 생성
~~~java
ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
~~~

- ApplicationContext를 스프링 컨테이너라 한다.(인터페이스)

## 스프링 컨테이너 생성 과정
### 1. 스프링 컨테이너 생성
- new AnnotationConfigApplicationContext(AppConfig.class);
- 스프링 컨테이너 생성 시 설정 정보를 지정해야 함(AppConfig.class)

### 2. 스프링 빈 등록
~~~java
    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {

        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {

        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
~~~
파라미터로 넘어온 설정 정보 클래스를 가지고 스프링 빈을 등록
- 메서드 이름으로 빈 등록
- 빈 이름 직접 부여 가능
- 빈 이름은 항상 다른 이름을 부여해야 함 -> 같을 시 다른 빈이 무시되거나 기존 빈을 덮어버리거나 오류 발생

### 3. 스프링 빈 의존관계 설정
- 스프링 컨테이너가 설정 정보를 참고해 의존관계를 주입(DI)함

자바 코드로 스프링 빈을 등록하면 생성자를 호출하면서 DI도 한번에 처리된다.

## BeanFactory와 ApplicationContext
### BeanFactory
- 스프링 컨테이너의 최상위 인터페이스
- 스프링 빈 관리 및 조회 역할 담당
- getBean() 메서드 제공
### ApplicationContext
- BeanFactory 기능 모두 상속받아 제공
- 빈 관리 및 조회 기능 외 부가기능 제공
- ex) 국제화기능, 리소스 조회 등

## 스프링 빈 설정 메타 정보 - BeanDefinition
스프링은 BeanDefinition 이라는 추상화 덕분에 다양한 설정 형식을 지원한다.
- BeanDefinition을 빈 설정 메타정보라고 한다.
- @Bean, &lt;bean&gt; 당 각각 하나씩 메타 정보가 생성된다.
- 스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성

- AnnotationConfigApplicationContext는 AnnotatedBeanDefinitionReader를 사용해 AppConfig.class를 읽고 BeanDefinition을 생성
- GenericXmlApplicationContext는 XmlBeanDefinitionReader를 사용해 appConfig.xml를 읽고 BeanDefinition을 생성


## 싱글톤 컨테이너
- 스프링 없는 DI 컨테이너에서는 요청을 할 때마다 객체를 새로 생성한다.
- 만약 트래픽이 1초에 100개씩 온다고 가정하면 초당 100개의 객체가 생성되고 소멸이 된다. -> 메모리 낭비가 심함
- 이를 해결하기 위해 해당 객체를 1개만 설정하고 공유하도록 설계하면 된다. -> 싱글톤 패턴

~~~java
public class SingletonService {

    // static 영역에 객체를 1개만 생성
    private static final SingletonService instance = new SingletonService();

    // 객체 인스턴스 필요 시 이 static 메서드를 통해서만 조회하도록 하자
    public static SingletonService getInstance() {
        return instance;
    }

    // 생성자를 private으로 선언하여 외부에서 new를 사용해서 객체 생성하는 것을 막음
    private SingletonService() {

    }

    public void logic() {
        System.out.println("싱글톤 객체 로직 호출");
    }

}
~~~

이러한 싱글톤 패턴은 다음과 같은 문제점이 존재한다.
- 싱글톤 패턴을 구현하는 코드가 많다.
- 클라이언트가 구현체에 의존한다 -> DIP 위반
- 구현체에 의존하기 때문에 OCP를 위반할 가능성이 있다.
- 테스트가 어렵다.
- 유연성이 떨어진다.

스프링 빈이 바로 싱글톤으로 관리되는 빈이다.
### 스프링 컨테이너
- 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도 객체 인스턴스를 싱글톤으로 관리
- 싱글톤 컨테이너 역할 -> 싱글톤 객체를 생성하고 관리하는 기능을 싱글톤 레지스트리라 함

스프링의 기본 빈 등록 방식은 싱글톤이지만 싱글톤 방식만 지원하는 것은 아니다. 요청때마다 새로운 객체를 생성해서 반환하는 기능도 제공한다.

---

싱글톤 방식은 여러 클라이언트가 하나의 객체를 공유하기 때문에 싱글톤 객체는 상태를 유지하게(stateful) 설계하면 안된다. -> 무상태(stateless)로 설계해야 함
- 특정 클라이언트에 의존적인 필드가 있으면 안된다.
- 특정 클라리언트가 값을 변경할 수 있는 필드가 있으면 안된다.
- 되도록이면 읽기만 가능해야 한다.
- 필드 대신 자바에서 공유되지 않는 지역변수, 파라미터 등을 사용해야 한다.

~~~java
public class StatefulService {

    private int price;

    public void order(String name, int price) {
        System.out.println("name = " + name + ", price = " + price);
        this.price = price; // 호출 시에 필드값이 바뀜
    }

    public int getPrice() {
        return price;
    }
}
~~~

~~~java
public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {

        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // A사용자 10000원 주문
        statefulService1.order("user1", 10000);

        // B사용자 20000원 주문
        statefulService2.order("user2", 20000);

        int price = statefulService1.getPrice();
        // user1의 주문 금액인 10000이 나와야하지만 20000이 나온다
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }


    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
~~~

스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.
~~~java
@Test
 void configurationDeep() {
    ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    //AppConfig도 스프링 빈으로 등록된다.
    AppConfig bean = ac.getBean(AppConfig.class);
    System.out.println("bean = " + bean.getClass());
    //bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70 
}
~~~
순수한 클래스라면 'class hello.core.AppConfig' 처럼 출력되어야한다.

스프링이 CGLIB 바이트코드 조작 라이브러리를 사용해 AppConfig를 상속받은 다른 클래스를 만들고 그 클래스를 스프링 빈으로 등록한다.
- @Bean이 붙은 메서드마다 이미 존재하는 스프링 빈이면 그 스프링 빈을 반환하고, 스프링 빈이 없으면 생성해서 스프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다.

---

## 컴포넌트 스캔
- 컴포넌트 스캔을 사용하려면 설정 정보에 @ComponentScan을 설정 정보에 붙여줘야 한다.
- 컴포넌트 스캔은 @Component 애노테이션이 붙은 클래스를 스캔해 스프링 빈으로 등록한다.
- @Configuration에도 @Component 애노테이션이 붙어있다.
- 의존관계 주입도 해당 클래스에서 해줘야한다. 이때 사용하는게 @Autowired

컴포넌트 스캔과 자동 의존관계 주입은 어떻게 동작하는가?
### 1. @ComponentScan
- @ComponentScan은 @Component가 붙은 모든 클래스를 스프링 빈으로 등록
- 이때 스프링 빈 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자로 사용한다
- ex) MemberServiceImpl -> memberServiceImpl

### 2. @Autowired 의존관계 자동 주입
- 생성자에 @Autowired 지정 시, 스프링 컨테이너가 자동으로 해당 스프링 빈을 찾아 주입한다.
- 이때 타입이 같은 빈을 찾아서 주입한다.
- getBean()와 동일하다고 생각하자 

---
## 탐색 위치와 기본 스캔 대상
### 탐색할 패키지의 시작 위치 지정
~~~java
@ComponentScan(
  basePackages = "com.odg"
)
~~~
- basePackages: 탐색할 패키지의 시작 위치 지정, 이 패키지를 포함해 하위 패키지 모두 탐색
  - basePackage = {"com.odg", "com.hi"} 처럼 여러 시작 위치 지정 가능
- 지정하지 않을 경우 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치
- 그냥 설정 정보 클래스의 위치를 프로젝트 최상단에 두자

### 컴포넌트 스캔 대상
- @Component
- @Controller
- @Service
- @Repository
- @Configuration

모두 @Component를 포함하고 있다.

---
## 중복 등록과 충돌
자동 빈 등록 vs 자동 빈 등록
- 컴포넌트 스캔으로 자동으로 스프링 빈이 등록되는데 이름이 같은 경우 오류 발생

수동 빈 등록 vs 자동 빈 등록
- 수동 빈 등록이 우선 -> 수동 빈이 자동 빈을 오버라이딩해버림
-> 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌할 경우 오류가 발생하도록 기본 값을 바꿈

---
## 빈 생명주기 콜백
스프링 빈은 다음과 같은 라이프사이클을 갖고 있다. <br>
객체 생성 -> 의존관계 주입

스프링 빈은 객체를 생성하고, 의존관계 주입이 다 끝난 다음에야 필요한 데이터를 사용할 수 있는 준비가 완료된다.<br>
-> 초기화 작업은 의존관계 주입이 모두 완료된 이후에 호출해야 한다.

### 스프링 빈의 이벤트 라이프사이클
스프링 컨테이너 생성 -> 스프링 빈 생성 -> 의존관계 주입 -> 초기화 콜백 -> 사용 -> 소멸전 콜백 -> 스프링 종료
- 초기화 콜백: 빈이 생성되고, 빈의 의존관계 주입이 완료된 이후에 호출
- 소멸전 콜백: 빈이 소멸되기 직전에 호출

### 스프링에서 지원하는 빈 생명주기 콜백 지원 방법
- 인터페이스(InitializingBean, DisposableBean)
- 설정 정보에 초기화 메서드, 종료 메서드 지정
- @PostConstruct, @PreDestory 애노테이션

## 인터페이스 InitializingBean, DisposableBean
~~~java
public class NetworkClient implements InitializingBean, DisposableBean {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message: " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: "+ url);
    }

    @Override
    public void destroy() throws Exception {
        disconnect();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
        call("초기화 연결 메시지");
    }
}
~~~
- InitializingBean은 afterPropertiesSet() 메서드로 초기화 지원
- DisposableBean은 destory() 메서드로 소멸 지원

초기화, 소멸 인터페이스 단점
- 이 인터페이스는 스프링 전용 인터페이스, 코드가 스프링 전용 인터페이스에 의존
- 초기화, 소멸 메서드 이름 변경 불가
- 내가 코드를 고칠 수 없는 외부 라이브러리에 적용 불가

## 빈 등록 초기화, 소멸 메서드 지정
~~~java
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message: " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: "+ url);
    }

    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
~~~

설정 정보에 초기화 소멸 메서드 지정
~~~java
@Configuration
    static class LifeCycleConfig {

        @Bean(initMethod = "init", destroyMethod = "close")
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hi-odg.gg");
            return networkClient;
        }
    }
~~~

### 설정 정보 사용 특징
- 메서드 이름을 자유롭게 줄 수 있음
- 스프링 빈이 스프링 코드에 의존하지 않음
- 설정 정보를 사용하기 때문에 코드를 고칠 수 없는 외부 라이브러리에도 초기화, 종료 메서드를 적용할 수 있음

- @Bean의 destoryMethod는 기본값이 (inferred)(추론)으로 등록되어있다.
- close, shutdown 메서드를 자동으로 호출, 종료 메서드를 추론해서 호출해줌
- -> 직접 스프링 빈으로 등록하면 종료 메서드를 적어주지 않아도 작동한다.

## 애노테이션 @PostConstruct, @PreDestory
~~~java
public class NetworkClient{

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + ", message: " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: "+ url);
    }

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }
}
~~~

~~~java
@Configuration
static class LifeCycleConfig {

    @Bean
    public NetworkClient networkClient() {
        NetworkClient networkClient = new NetworkClient();
        networkClient.setUrl("http://hi-odg.gg");
        return networkClient;
    }
}
~~~

- 스프링에서 가장 권장하는 방법
- 외부 라이브러리에 적용하지 못한다는 것이 단점, 외부 라이브러리를 초기화, 종료해야 한다면 @Bean 기능을 사용하자

---
## 빈 스코프
스코프: 빈이 존재할 수 있는 범위

스프링에서 지원하는 스코프
- 싱글톤: 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위 스코프
- 프로토타입: 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 짧은 범위 스코프
- 웹 관련 스코프
  - request: 웹 요청이 들어오고 나갈때 까지 유지되는 스코프
  - session: 웹 세션이 생성되고 종료될 때 까지 유지되는 스코프
  - application: 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프 

## 프로토타입 스코프
싱글톤 스코프의 빈을 조회 시 스프링 컨테이너는 항상 같은 인스턴스의 스프링 빈을 반환
1. 싱글톤 스코프의 빈을 스프링 컨테이너에 요청
2. 스프링 컨테이너는 본인이 관리하는 스프링 빈을 반환
3. 이후 스프링 컨테이너에 같은 요청이 와도 같은 객체 인스턴스의 스프링 빈을 반환

프로토타입 스코프를 스프링 컨테이너에 조회 시 스프링 컨테이너는 항상 새로운 인스턴스를 생성해 반환
1. 프로토타입 스코프의 빈을 스프링 컨테이너에 요청
2. 스프링 컨테이너는 이 때 프로토타입 빈을 생성하고 필요한 의존관계 주입
3. 스프링 컨테이너는 생성한 프로토타입 빈을 클라이언트에 반환
4. 이후 스프링 컨테이너에 같은 요청이 오면 항상 새로운 프로토타입 빈을 생성해서 반환<br>
-> 스프링 컨테이너는 프로토타입 빈을 생성하고, 의존관계 주입, 초기화까지만 관여 <br>
-> 프로토타입 빈을 조회한 클라이언트가 관리해야 함

~~~java
public class PrototypeBeanTest {

    @Test
    public void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototypeBean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);

        System.out.println("find prototypeBean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);

        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2);
    }

    @Scope("prototype")
    static class PrototypeBean {

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init");

        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}
~~~

~~~
find prototypeBean1
PrototypeBean.init
find prototypeBean2
PrototypeBean.init
prototypeBean1 = com.odg.spring.spring_core.scope.PrototypeBeanTest$PrototypeBean@20a8a64e
prototypeBean2 = com.odg.spring.spring_core.scope.PrototypeBeanTest$PrototypeBean@62f4ff3b
~~~

싱글톤 빈은 스프링 컨테이너 생성 시점에 초기화 메서드 실행<br>
프로토타입 스코프 빈은 조회할 때 생성되고 초기화 메서드도 실행<br>
프로토타입 빈은 스프링 컨테이너가 생관과 의존관계 주입, 초기화까지만 관여하기 때문에 종료 메서드 실행 안됨

## 프로토타입 스코프 - 싱글톤 빈과 함께 사용시 문제점
### 프로토타입 빈 직접 요청
~~~java
public class SingletonWithPrototypeTest1 {

    @Test
    void protoTypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ProtoTypeBean.class);
        ProtoTypeBean protoTypeBean1 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean1.addCount();
        Assertions.assertThat(protoTypeBean1.getCount()).isEqualTo(1);
        // protoTypeBean1의 count = 1

        ProtoTypeBean protoTypeBean2 = ac.getBean(ProtoTypeBean.class);
        protoTypeBean2.addCount();
        Assertions.assertThat(protoTypeBean2.getCount()).isEqualTo(1);
        // protoTypeBean2의 count = 1

    }

    @Scope("prototype")
    static class ProtoTypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destory");
        }
    }
}
~~~

### 싱글톤 빈에서 프로토타입 빈 사용
~~~java
public class SingletonWithPrototypeTest2 {

    @Test
    void singletonClientUsePrototype() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        Assertions.assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        Assertions.assertThat(count2).isEqualTo(2);
    }

    static class ClientBean {

        private final PrototypeBean prototypeBean;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");

        }
    }
}
~~~
- clientBean은 싱글톤, 스프링 컨테이너 생성 시점에 생성, DI도 발생
  1. 주입시점에 스프링 컨테이너에 프로토타입 빈 요청
  2. 스프링 컨테이너는 프로토타입 빈 생성 후 clientBean에 반환<br>
  -> 이 때 프로토타입 빈의 count는 0
- clientBean 내부에 프로토타입 빈의 참조값을 보관

user1이 요청
- clientBean을 스프링 컨테이너에 요청해서 받음, 싱글톤이므로 항상 같은 clientBean 반환
  3. user1 clientBean.logic() 호출
  4. clientBean은 prototypeBean의 addCount() 호출 후 프로토타이 빈의 count 증가

user2가 요청
- clientBean을 스프링 컨테이너에 요청해서 받음, 싱글톤이므로 항상 같은 clientBean 반환
- clientBean이 가지고 있는 프로토타입 빈은 이미 전에 주입이 끝난 빈이다. 주입 시점에 스프링 컨테이너에 요청해 프로토타입 빈이 생성된 것, 사용할 때마다 생성x
  5. user2 clientBean.logic() 호출
  6. clientBean은 prototypeBean의 addCount() 호출해 프로토타입 빈의 count 증가
  -> 1에서 2로 증가
  
원했던 결과: 사용할 때마다 새로 생성해서 사용하는 것

## 프로토타입 스코프 - 싱글톤 빈과 함께 사용 시 Provider로 문제 해결
~~~java
public class PrototypeProviderTest {

    @Test
    void providerTest() {

        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        assertThat(count2).isEqualTo(1);
    }

    static class ClientBean {

        @Autowired
        private ApplicationContext ac;

        public int logic() {
            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init " + this);

        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");

        }
    }
}
~~~

- logic()에서 ac.getBean()을 통해 새로운 프로토타입 빈을 생성
- 이렇게 직접 필요한 의존관계를 찾은 것을 Dependency Lookup (DL) 의존관계 조회라 한다.
- 이런식으로 ApplicationContext 전체를 주입받을 시, 스프링 컨테이너에 종속적인 코드가 되고, 단위 테스트도 어려워짐

### ObjectFactory, ObjectProvider
~~~java
static class ClientBean {

        @Autowired
        private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        public int logic() {
            PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }
~~~
- prototypeBeanProvider.getObject()를 통해 항상 새로운 프로토타입 빈이 생성
- getObject() 호출 시 내부에서는 스프링 컨테이너를 통해 해당 빈을 찾아 반환

## 웹 스코프
- 웹 환경에서만 동작
- 스프링이 해당 스코프의 종료시점까지 관리 -> 종료 메서드가 호출

### 웹 스코프 종류
- request: HTTP 요청 하나가 들어오고 나갈 때 까지 유지되는 스코프, 각각의 HTTP 요청마다 별도의 빈 인스턴스개 생성되고 관리
- session: HTTP Session과 동일한 생명주기를 가짐
- application: ServletContext와 동일한 생명주기를 가짐
- websocket: 웹 소켓과 동일한 생명주기를 가짐