# 데이터 접근 기술 - 테스트
## 테스트 - 데이터베이스 연동

테스트 케이스는 `src/test`에 있기 때문에 `src/test`에 있는 `application.properties`파일이 우선순위를 갖고 실행된다.

이 파일도 데이터베이스에 접속할 수 있게 수정하자.
~~~
spring.profiles.active=test

spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
spring.datasource.password=

logging.level.org.springframework.jdbc=debug
~~~

~~~java
@SpringBootTest
class ItemRepositoryTest {}
~~~
- `@SpringBootTest`: @SpringBootTest는 `@SpringBootApplication`을 찾아 설정으로 사용한다.

~~~java
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {}
~~~

로컬에서 사용하는 애플리케이션 서버와 테스트에서 같은 데이터베이스를 사용하고 있어 테스트에서 문제가 생긴다. 테스트를 다른 환경과 분리를 해주도록 하자.

## 테스트 - 데이터베이스 분리
데이터베이스를 용도에 따라 2가지로 분류하자
- local에서 접근하는 데이터베이스(`jdbc:h2:tcp://localhost/~/test`)
- test케이스에서 사용하는 데이터베이스(`jdbc:h2:tcp://localhost/~/testcase`)

`src/test/application.properties` 파일도 수정해주자
~~~
spring.profiles.active=test

spring.datasource.url=jdbc:h2:tcp://localhost/~/testcase
spring.datasource.username=sa
spring.datasource.password=

logging.level.org.springframework.jdbc=debug
~~~

테스트에서 매우 중요한 원칙
- **테스트는 다른 테스트와 격리해야 한다.**
- **테스트는 반복해서 실행할 수 있어야 한다.**

테스트가 끝날 때 마다 `DELETE` SQL을 해도 좋지만 이렇게 되면 테스트 도중 예외가 발생해버리면 애플리케이션이 종료되어 `DELETE` SQL을 호출하지 못할 수도 있다. 결국 데이터가 남아있게 된다.

이런 문제를 해결하기 위해 `rollback`, `commit` 전략을 사용할 수 있다.

## 테스트 - 데이터 롤백
테스트가 끝나고 나서 트랜잭션을 강제로 롤백해버리면 데이터가 깔끔하게 제거된다. 테스트 도중 데이터를 이미 저장했는데 중간에 테스트가 실패해서 롤백을 호출하지 못해도 트랜잭션을 커밋하지 않았기 때문에 데이터베이스에 해당 데이터가 반영되지 않는다.

~~~java
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    // 트랜잭션 관련 코드
    @Autowired
    PlatformTransactionManager transactionManager;
    TransactionStatus status;

    @BeforeEach
    void beforeEach() {
        // 트랜잭션 시작
        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
    }

    @AfterEach
    void afterEach() {
        //MemoryItemRepository 의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }

        // 트랜잭션 롤백
        transactionManager.rollback(status);

    }

}
~~~
- `PlatformTransactionManager`를 주입받아 사용하면 된다. 스프링 부트는 자동으로 적절한 트랜잭션 매니저를 스프링 빈으로 등록해준다.
- `@BeforeEach`: 각각의 테스트 케이스를 실행하기 직전에 호출, 여기서 트랜잭션을 시작하면 된다. 
- `@AfterEach`: 각각의 테스트 케이스가 완료된 직후에 호출, 여기서 트랜잭션을 롤백하면 된다.

## 테스트 - @Transactional
스프링은 트랜잭션을 적용하고 롤백하는 방식을 `@Transactional` 애노테이션 하나로 깔끔하게 해결해준다.

~~~java
@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

//    @Autowired
//    PlatformTransactionManager transactionManager;
//    TransactionStatus status;
//
//    @BeforeEach
//    void beforeEach() {
//        // 트랜잭션 시작
//        status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//    }

    @AfterEach
    void afterEach() {
        //MemoryItemRepository 의 경우 제한적으로 사용
        if (itemRepository instanceof MemoryItemRepository) {
            ((MemoryItemRepository) itemRepository).clearStore();
        }

        // 트랜잭션 롤백
//        transactionManager.rollback(status);

    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId()).get();
        assertThat(findItem).isEqualTo(savedItem);
    }

}
~~~

- `@Transactional` 애노테이션은 로직이 성공적으로 수행되면 커밋하도록 동작한다. 테스트에서는 트랜잭션 안에서 실행하고, 테스트나 끝나면 트랜잭션을 롤백시켜 버린다.

- 테스트 케이스의 메서드나 클래스에 `@Transactional` 애노테이션을 직접 붙여 사용할 때만 이렇게 동작한다.

- 테스트가 끝난 뒤 개발자가 직접 데이터를 삭제하지 않아도 된다.
- 테스트 도중 강제로 종료되어도 괜찮다. 트랜잭션을 커밋하지 않고 롤백해버린다.
- 트랜잭션 범위 안에서 테스트를 진행하기 때문에 다른 테스트가 동시에 진행되어도 서로 영향을 주지 않는다.
- 가끔 데이터베이스에 데이터가 잘 보관되었는지 확인하고 싶을 때가 있다. 이때는 `@Commit`을 붙여주자.

## 테스트 - 임베디드 모드 DB
테스트 케이스를 실행하기 위해 별도의 데이터베이스를 설치하고 운영하는 것은 상당히 번잡한 작업이다. 

H2는 자바로 개발되어 있고 JVM 안에서 메모리 모드로 동작하는 특별한 기능을 제공한다. 그래서 애플리케이션을 실행할 때 H2 데이터베이스도 해당 JVM 메모리에 포함해서 함께 실행할 수 있다. DB를 애플리케이션에 내장해서 함께 실행한다고 해서 임베디드 모드라 한다.

물론 애플리케이션이 종료가 되면 임베디드 모드로 동작하는 데이터베이스도 함께 종료된다.

~~~java
@Slf4j
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

	@Bean
	@Profile("test")
	public DataSource dataSource() {
		log.info("메모리 데이터베이스 초기화");
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
		dataSource.setUsername("sa");
		dataSource.setPassword("");

		return dataSource;
	}

}
~~~
-`jdbc:h2:mem:db`: 데이터소스 생성 시 이렇게 적으면 임베디드 모드로 동작하는 H2 데이터베이스를 사용할 수 있다.
- `DB_CLOSE_DELAY=-1`: 데이터베이스 커넥션 연결이 모두 끊어지면 데이터베이스도 종료

처음 테스트 실행 시 오류가 날텐데 DB에 아직 테이블이 없기 때문이다. 스프링 부트는 이 문제를 해결해줄 기능을 제공한다.

스프링 부트는 SQL 스크립트를 실행해서 애플리케이션 로딩 시점에 데이터베이스를 초기화하는 기능을 제공한다.


`src/test/resources/schema.sql`
위치와 이름을 주의하자.
~~~
drop table if exists item CASCADE;
 create table item
 (
     id        bigint generated by default as identity,
     item_name varchar(10),
     price     integer,
     quantity  integer,
     primary key (id)
 );
~~~

## 테스트 - 스프링 부트와 임베디드 모드
스프링 부트는 데이터베이스에 대한 별다른 설정이 없으면 임베디드 데이터베이스를 사용한다.

~~~java
@Slf4j
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
@Import(JdbcTemplateV3Config.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

//	@Bean
//	@Profile("test")
//	public DataSource dataSource() {
//		log.info("메모리 데이터베이스 초기화");
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//
//		return dataSource;
//	}

}
~~~
- 메모리 DB용 dataSource 주석처리

~~~
#spring.datasource.url=jdbc:h2:tcp://localhost/~/testcase
#spring.datasource.username=sa
#spring.datasource.password=
~~~
- DB 접속 설정 정보 주석처리

이렇게 별다른 정보가 없으면 스프링 부트는 임베디드 모드로 접근하는 데이터소스를 만들어 제공한다.

~~~
conn0: url=jdbc:h2:mem:3e80e6f3-f7a2-4bf8-9e3d-19a5430586e7 user=SA
~~~
url을 보면 `jdbc:h2:mem`을 사용해 임베디드 DB를 사용하는 것을 볼 수 있다.