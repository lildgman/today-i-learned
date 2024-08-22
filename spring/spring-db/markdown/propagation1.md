# 스프링 트랜잭션 전파1 - 기본
## 스프링 트랜잭션 전파1 - 커밋, 롤백

~~~java
@Slf4j
@SpringBootTest
public class BasicTxTest {

    @Autowired
    PlatformTransactionManager txManager;

    @TestConfiguration
    static class Config {
        @Bean
        public PlatformTransactionManager transactionManager(DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    @Test
    void commit() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 커밋 시작");
        txManager.commit(status);
        log.info("트랜잭션 커밋 완료");

    }

    @Test
    void rollback() {
        log.info("트랜잭션 시작");
        TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

        log.info("트랜잭션 롤백 시작");
        txManager.rollback(status);
        log.info("트랜잭션 롤백 완료");

    }
}
~~~

### commit()
~~~java
@Test
void commit() {
    log.info("트랜잭션 시작");
    TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

    log.info("트랜잭션 커밋 시작");
    txManager.commit(status);
    log.info("트랜잭션 커밋 완료");

}
~~~
**실행 로그**
~~~
BasicTxTest   : 트랜잭션 시작
DataSourceTransactionManager     : Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
DataSourceTransactionManager     : Acquired Connection [HikariProxyConnection@1705558790 wrapping conn0: url=jdbc:h2:mem:5b634725-38eb-4199-81bb-562d7ae615ac user=SA] for JDBC transaction
DataSourceTransactionManager     : Switching JDBC Connection [HikariProxyConnection@1705558790 wrapping conn0: url=jdbc:h2:mem:5b634725-38eb-4199-81bb-562d7ae615ac user=SA] to manual commit
BasicTxTest   : 트랜잭션 커밋 시작
DataSourceTransactionManager     : Initiating transaction commit
DataSourceTransactionManager     : Committing JDBC transaction on Connection [HikariProxyConnection@1705558790 wrapping conn0: url=jdbc:h2:mem:5b634725-38eb-4199-81bb-562d7ae615ac user=SA]
DataSourceTransactionManager     : Releasing JDBC Connection [HikariProxyConnection@1705558790 wrapping conn0: url=jdbc:h2:mem:5b634725-38eb-4199-81bb-562d7ae615ac user=SA] after transaction
BasicTxTest   : 트랜잭션 커밋 완료
~~~

### rollback()
~~~java
@Test
void rollback() {
    log.info("트랜잭션 시작");
    TransactionStatus status = txManager.getTransaction(new DefaultTransactionAttribute());

    log.info("트랜잭션 롤백 시작");
    txManager.rollback(status);
    log.info("트랜잭션 롤백 완료");

}
~~~
~~~
BasicTxTest   : 트랜잭션 시작
DataSourceTransactionManager     : Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
DataSourceTransactionManager     : Acquired Connection [HikariProxyConnection@2021678579 wrapping conn0: url=jdbc:h2:mem:9aa35610-2a2b-4652-8bd7-b38e27bea53f user=SA] for JDBC transaction
DataSourceTransactionManager     : Switching JDBC Connection [HikariProxyConnection@2021678579 wrapping conn0: url=jdbc:h2:mem:9aa35610-2a2b-4652-8bd7-b38e27bea53f user=SA] to manual commit
BasicTxTest   : 트랜잭션 롤백 시작
DataSourceTransactionManager     : Initiating transaction rollback
DataSourceTransactionManager     : Rolling back JDBC transaction on Connection [HikariProxyConnection@2021678579 wrapping conn0: url=jdbc:h2:mem:9aa35610-2a2b-4652-8bd7-b38e27bea53f user=SA]
DataSourceTransactionManager     : Releasing JDBC Connection [HikariProxyConnection@2021678579 wrapping conn0: url=jdbc:h2:mem:9aa35610-2a2b-4652-8bd7-b38e27bea53f user=SA] after transaction
BasicTxTest   : 트랜잭션 롤백 완료
~~~

## 스프링 트랜잭션 전파2 - 트랜잭션 두 번 사용
이번에는 트래잭션이 각각 사용되는 경우를 보자
~~~java
@Test
void double_commit() {
    log.info("트랜잭션1  시작");
    TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("트랜잭션 커밋1 시작");
    txManager.commit(tx1);

    log.info("트랜잭션2 시작");
    TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("트랜잭션2 커밋 시작");
    txManager.commit(tx2);

}
~~~
~~~
트랜잭션1  시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] to manual commit
트랜잭션 커밋1 시작
Initiating transaction commit
Committing JDBC transaction on Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA]
Releasing JDBC Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] after transaction

트랜잭션2 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] to manual commit
트랜잭션2 커밋 시작
Initiating transaction commit
Committing JDBC transaction on Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA]
Releasing JDBC Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] after transaction
~~~
**트랜잭션1**
- `Acquired Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] for JDBC transaction`
  - 트랜잭션1을 시작하고 커넥션 풀에서 conn0 커넥션 획득
- `Releasing JDBC Connection [HikariProxyConnection@1909332462 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] after transaction`
  - 트랜잭션1을 커밋하고 커넥션 풀에 conn0 커넥션 반납

**트랜잭션2**
- `Acquired Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] for JDBC transaction`
  - 트랜잭션2를 시작하고 커넥션 풀에서 conn0 커넥션 획득
- `Releasing JDBC Connection [HikariProxyConnection@919473090 wrapping conn0: url=jdbc:h2:mem:d8d3c589-1872-43de-9b95-f884350974b4 user=SA] after transaction`
  - 트랜잭션2을 커밋하고 커넥션 풀에 conn0 커넥션 반납

위에서 확인할 수 있듯이 트랜잭션1과 트랜잭션2는 같은 conn0을 사용하고 있다. 커넥션 풀 때문에 그런 것이다. 트랜잭션1은 conn0을 모두 사용하고 커넥션 풀에 반납을 완료했다. 이후 트랜잭션2가 conn0을 획득하였다. 따라서 둘은 다른 커넥션으로 인지하는 것이 맞다.

히카리 커넥션 풀에서 커넥션을 획득하면 실제 커넥션을 반환하는 것이 아닌 내부 관리를 위해 히카리 프록시 커넥션이라는 객체를 생성해 반환한다. 내부에 실제 커넥션이 포함되어있다. 
- 트랜잭션1: `Acquired Connection [HikariProxyConnection@1909332462 wrapping conn0]`
- 트랜잭션2: `Acquired Connection [HikariProxyConnection@919473090 wrapping conn0]`

결과적으로 conn0을 통해 커넥션이 재사용 된 것을 볼 수 있고, 각각 커넥션 풀에서 커넥션을 조회한 것을 확인할 수 있다.

![alt text](image-26.png)
- 트랜잭션이 각각 수행되기 때문에 사용되는 DB 커넥션 또한 각각 다르다.
- 이 때 트랜잭션을 각자 관리하기 때문에 전체 트랜잭션을 묶을 수 없다.
  - 트랜잭션1이 커밋하고 트랜잭션2이 롤백하는 경우
    - 트랜잭션1에서 저장한 데이터는 커밋되고, 트랜잭션2에서 저장한 데이터는 롤백된다.

~~~java
@Test
void double_commit_rollback() {
    log.info("트랜잭션1  시작");
    TransactionStatus tx1 = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("트랜잭션 커밋1 시작");
    txManager.commit(tx1);

    log.info("트랜잭션2 시작");
    TransactionStatus tx2 = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("트랜잭션2 롤백 시작");
    txManager.rollback(tx2);

}
~~~
~~~
트랜잭션1  시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@1220163548 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@1220163548 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] to manual commit
트랜잭션 커밋1 시작
Initiating transaction commit
Committing JDBC transaction on Connection [HikariProxyConnection@1220163548 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA]
Releasing JDBC Connection [HikariProxyConnection@1220163548 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] after transaction

트랜잭션2 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@830449117 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@830449117 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] to manual commit
트랜잭션2 롤백 시작
Initiating transaction rollback
Rolling back JDBC transaction on Connection [HikariProxyConnection@830449117 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA]
Releasing JDBC Connection [HikariProxyConnection@830449117 wrapping conn0: url=jdbc:h2:mem:56011bc1-7e56-4852-9d4d-1f4132138c3a user=SA] after transaction
~~~
- 트랜잭션을 각각 관리했기 때문에 트랜잭션1은 커밋되지만, 트랜잭션2는 롤백된다.

## 스프링 트랜잭션 전파3 - 전파 기본
만약 트랜잭션이 진행 중인데 여기에 추가로 트랜잭션을 수행하면 어떻게 될까?<br>
이런 경우 어떻게 동작할지 결정하는 것을 트랜잭션 전파라고 한다.

예제를 통해 트랜잭션 전파를 알아보자
![alt text](image-27.png)
- 외부 트랜잭션이 수행 중이고, 끝나지 않았는데 내부 트랜잭션이 수행된다.

![alt text](image-28.png)
- 스프링에서 이 경우 외부 트랜잭션과 내부 트랜잭션을 묶어 하나의 트랜잭션을 만들어준다. 내부 트랜잭션이 외부 트랜잭션에 참여하는 것이다. 이것이 기본 동작이고 옵션을 통해 다른 방식으로 변경할 수 있다.

![alt text](image-29.png)
- 스프링에서는 논리 트랜잭션, 물리 트랜잭션이라는 개념을 나눈다.
- 논리 트랜잭션들은 하나의 물리 트랜잭션으로 묶인다.
- `물리 트랜잭션`은 실제 데이터베이스에 적용되는 트랜잭션을 뜻한다. 실제 트랜잭션을 시작하고 실제 트랜잭션을 커밋, 롤백하는 단위이다.
- `논리 트랜잭션`은 트랜잭션 매니저를 통해 트랜잭션을 사용하는 단위이다.
- 논리 트랜잭션 개념은 트랜잭션이 진행 중에 내부에 추가로 트랜잭션을 사용하는 경우에 나타난다. 트랜잭션이 하나인 경우는 둘을 구분하지 않는다.

트랜잭션이 사용 중일때 또 다른 트랜잭션이 내부에 사용되면 여러가지 복잡한 상황이 발생한다. 이때 논리 트랜잭션 개념을 도입하면 단순한 원칙을 만들 수 있다.

**원칙**<br>
- **모든 논리 트랜잭션이 커밋되어야 물리 트랜잭션이 커밋된다.**
- **하나의 논리 트랜잭션이라도 롤백되면 물리 트랜잭션은 롤백된다.**

## 스프링 데이터 전파4 - 예제
~~~java
@Test
void inner_commit() {
    log.info("외부 트랜잭션 시작");
    TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

    log.info("내부 트랜잭션 시작");
    TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("inner.isNewTransaction()={}", inner.isNewTransaction());
    log.info("내부 트랜잭션 커밋");
    txManager.commit(inner);

    log.info("외부 트랜잭션 커밋");
    txManager.commit(outer);
}
~~~
- 외부 트랜잭션 수행 중에 내부 트랜잭션을 추가로 수행했다.
- 외부 트랜잭션은 처음 수행된 트랜잭션이다. 이 경우 신규 트랜잭션이 된다.(isNewTransaction=true)
- 내부 트랜잭션을 시작하는 시점에는 이미 외부 트랜잭션이 진행중인 상태이다. 이 경우 내부 트랜잭션은 외부 트랜잭션에 참여한다.
- 트랜잭션 참여
  - 내부 트랜잭션이 외부 트랜잭션에 참여한다는 뜻
  - 이는 내부 트랜잭션이 외부 트랜잭션을 이어 받아 따른다는 의미이다.
  - 외부 트랜잭션의 범위가 내부 트랜잭션까지 넓어진다는 뜻이다.
  - 외부에서 시작된 물리적인 트랜잭션의 범위가 내부 트랜잭션까지 넓어진다는 뜻이다.
  - 즉, 외부 트랜잭션과 내부 트랜잭션이 하나의 물리 트랜잭션으로 묶이는 것이다.
- 내부 트랜잭션은 이미 진행중인 외부 트랜잭션에 참여한다. 이 때 신규 내부 트랜잭션은 신규 트랜잭션이 아니다.(isNewTransaction=false)

위 예제의 코드에서 커밋을 두 번 호출했다. 학습했던 내용을 떠올려보면 하나의 커넥션에 커밋은 한번만 호출할 수 있다.

스프링은 어떻게 외부 트랜잭션과 내부 트랜잭션을 묶어 하나의 물리 트랜잭션으로 동작하게 하는지 알아보자
**실행 로그**
~~~
외부 트랜잭션 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@161573617 wrapping conn0: url=jdbc:h2:mem:15dda021-fa0d-4492-bed0-b8c238441733 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@161573617 wrapping conn0: url=jdbc:h2:mem:15dda021-fa0d-4492-bed0-b8c238441733 user=SA] to manual commit
outer.isNewTransaction()=true

내부 트랜잭션 시작
Participating in existing transaction
inner.isNewTransaction()=false
내부 트랜잭션 커밋

외부 트랜잭션 커밋
Initiating transaction commit
Committing JDBC transaction on Connection [HikariProxyConnection@161573617 wrapping conn0: url=jdbc:h2:mem:15dda021-fa0d-4492-bed0-b8c238441733 user=SA]
Releasing JDBC Connection [HikariProxyConnection@161573617 wrapping conn0: url=jdbc:h2:mem:15dda021-fa0d-4492-bed0-b8c238441733 user=SA] after transaction
~~~
- 내부 트랜잭션 시작 시 `Participating in existing transaction` 메시지를 확인할 수 있다. 내부 트랜잭션이 기존에 존재하는 외부 트랜잭션에 참여한다는 뜻이다.
- 외부 트랜잭션을 시작하거나 커밋할 때는 DB 커넥션을 통한 물리 트랜잭션을 시작하고, DB 커넥션을 통해 커밋 하는 것을 볼 수 있다. (Switching JDBC Connection [HikariProxyConnection@161573617 wrapping conn0] to `manual commit`)
- 하지만 내부 트랜잭션을 시작하거나 커밋할 때는 DB 커넥션을 통해 커밋하는 로그를 확인할 수 없다. 
- `간단히 말해서 외부 트랜잭션만 물리 트랜잭션을 시작하고 커밋한다.`
- 만약 내부 트랜잭션이 실제 물리 트랜잭션을 커밋하면 트랜잭션이 끝나기 때문에 트랜잭션을 외부 트랜잭션까지 이어갈 수 없다.
- 따라서 내부 트랜잭션은 DB커넥션을 통한 물리 트랜잭션을 커밋하면 안된다.
- 스프링은 여러 트랜잭션이 함께 사용되는 경우, `처음 트랜잭션을 시작한 외부 트랜잭션이 실제 물리 트랜잭션을 관리`하도록 한다.

이제 트랜잭션 전파가 실제로 어떻게 동작하는지 알아보자

![alt text](image-30.png)
**요청 흐름 - 외부 트랜잭션**<br>
- 1. 외부 트랜잭션 시작
- 2. 트랜잭션 매니저는 데이터소스를 통해 커넥션을 생성
- 3. 생성한 커넥션을 수동 커밋 모드로 설정 - **물리 트랜잭션 시작**
- 4. 트랜잭션 매니저는 트랜잭션 동기화 매니저에 커넥션을 보관
- 5. 트랜잭션 매니저는 트랜잭션 생성 결과를 `TransactionStatus`에 담아 반환하는데 여기에 신규 트랜잭션 여부가 담겨 있다. 트랜잭션을 처음 시작했으므로 신규 트랜잭션이다.(isNewTransaction=true)
- 6. 로직1이 사용되고 커넥션이 필요한 경우 트랜잭션 동기화 매니저를 통해 트랜잭션이 적용된 커넥션을 획득해서 사용한다.

**요청 흐름 - 내부 트랜잭션**<br>
- 7. 내부 트랜잭션 시작
- 8. 트랜잭션 매니저는 트랜잭션 동기화 매니저를 통해 기존 트랜잭션이 존재하는지 확인
- 9. 기존 트랜잭션이 존재하므로 기존 트랜잭션에 참여한다. 이 말은 아무것도 하지 않는다는 뜻이다.
- 10. 기존 트랜잭션에 참여했기 때문에 신규 트랜잭션이 아니다.(isNewTransaction=false)
- 11. 로직2가 사용되고 커넥션이 필요한 경우 트랜잭션 동기화 매니저를 통해 외부 트랜잭션이 보관한 커넥션을 획득해 사용한다.

![alt text](image-31.png)
**응답 흐름 - 내부 트랜잭션**
- 12. 로직2가 끝나고 트랜잭션 매니저를 통해 내부 트랜잭션을 커밋한다.
- 13. 이 경우에는 신규 트랜잭션이 아니므로 커밋을 호출하지 않는다. 이 부분이 중요한데 실제 커넥션에 커밋이나 롤백을 호출해버리면 물리 트랜잭션이 끝나버린다. 트랜잭션이 끝난게 아니기 때문에 실제 커밋을 호출하면 안된다. 물리 트랜잭션은 외부 트랜잭션을 종료할 때 까지 이어져야 한다.

**응답 흐름 - 외부 트랜잭션**
- 14. 로직1이 끝나고 트랜잭션 매니저를 통해 외부 트랜잭션을 커밋
- 15. 외부 트랜잭션은 신규 트랜잭션이므로 DB 커넥션에 실제 커밋을 호출
- 16. 트랜잭션 매니저에 커밋하는 것이 논리적인 커밋이라면 실제 커넥션에 커밋하는 것을 물리 커밋이라 할 수 있다. 실제 데이터베이스에 커밋이 반영되고 물리 트랜잭션도 끝난다.

**핵심**
- 신규 트랜잭션인 경우에만 실제 커넥션을 사용해 물리 커밋과 롤백 수행


## 스프링 트랜잭션 전파5 - 외부 롤백
내부 트랜잭션은 커밋되는데 외부 트랜잭션이 롤백되는 상황
~~~java
@Test
void outer_rollback() {
    log.info("외부 트랜잭션 시작");
    TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
    
    log.info("내부 트랜잭션 시작");
    TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("내부 트랜잭션 커밋");
    txManager.commit(inner);

    log.info("외부 트랜잭션 롤백");
    txManager.rollback(outer);
}
~~~
~~~ 실행 로그
외부 트랜잭션 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:7dfae2f5-06f4-4c19-85c3-236e898b467f user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:7dfae2f5-06f4-4c19-85c3-236e898b467f user=SA] to manual commit

내부 트랜잭션 시작
Participating in existing transaction
내부 트랜잭션 커밋

외부 트랜잭션 롤백
Initiating transaction rollback
Rolling back JDBC transaction on Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:7dfae2f5-06f4-4c19-85c3-236e898b467f user=SA]
Releasing JDBC Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:7dfae2f5-06f4-4c19-85c3-236e898b467f user=SA] after transaction
~~~
- 외부 트랜잭션이 물리 트랜잭션을 시작하고 롤백하고 있다.
- 내부 트랜잭션은 직접 트랜잭션에 관여하지 않고 있다.
- 외부 트랜잭션에서 시작한 물리 트랜잭션의 범위가 내부 트랜잭션까지 사용된다.
- 외부 트랜잭션이 롤백되면서 전체 내용이 롤백된다.

![alt text](image-32.png)
**응답 흐름 - 내부 트랜잭션**
1. 로직2가 끝나고 트랜잭션 매니저를 통해 내부 트랜잭션을 커밋
2. 트랜잭션 매니저는 커밋 시점에 신규 트랜잭션 여부에 따라 다르게 동작한다. 위의 예제에서는 신규 트랜잭션이 아니기 때문에 커밋을 호출하지 않는다.

**응답 흐름 - 외부 트랜잭션**
3. 로직1이 끝나고 트랜잭션 매니저를 통해서 외부 트랜잭션을 롤백한다.
4. 트랜잭션 매니저는 롤백 시점에 신규 트랜잭션 여부에 따라 다르게 동작한다. 위의 예제에서 외부 트랜잭션은 신규트랜잭션이다. 따라서 커넥션에 실제로 롤백을 호출한다.
5. 트랜잭션 매니저에 롤백하는 것을 논리적인 롤백이라면 실제 커넥션에 롤백 하는 것을 물리 롤백이라 할 수 있다. 실제 데이터베이스에 롤백이 반영되고 물리 트랜잭션도 끝이 난다.

## 스프링 트랜잭션 전파6 - 내부 롤백
내부 트랜잭션은 롤백되고 외부 트랜잭션은 커밋되는 상황을 보자
~~~java
@Test
void inner_rollback() {
    log.info("외부 트랜잭션 시작");
    TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());

    log.info("내부 트랜잭션 시작");
    TransactionStatus inner = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("내부 트랜잭션 롤백");
    txManager.rollback(inner);

    log.info("외부 트랜잭션 롤백");
    assertThatThrownBy(() -> txManager.commit(outer))
            .isInstanceOf(UnexpectedRollbackException.class);
}
~~~
- 실행결과 `UnexpectedRollbackException`이 발생한다.

~~~
외부 트랜잭션 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:c8dbd7c1-7f39-41c4-9bd6-60f4148465b2 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:c8dbd7c1-7f39-41c4-9bd6-60f4148465b2 user=SA] to manual commit

내부 트랜잭션 시작
Participating in existing transaction
내부 트랜잭션 롤백
Participating transaction failed - marking existing transaction as rollback-only
Setting JDBC transaction [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:c8dbd7c1-7f39-41c4-9bd6-60f4148465b2 user=SA] rollback-only

외부 트랜잭션 커밋
Global transaction is marked as rollback-only but transactional code requested commit
Initiating transaction rollback
Rolling back JDBC transaction on Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:c8dbd7c1-7f39-41c4-9bd6-60f4148465b2 user=SA]
Releasing JDBC Connection [HikariProxyConnection@1204036307 wrapping conn0: url=jdbc:h2:mem:c8dbd7c1-7f39-41c4-9bd6-60f4148465b2 user=SA] after transaction
~~~
- 내부 트랜잭션 롤백
  - `Participating transaction failed - marking existing transaction as rollback-only`
  - 내부 트랜잭션을 롤백하면 물리 트랜잭션은 롤백하지 않는다. 대신 기존 트랜잭션을 `롤백 전용으로 표시`한다.
- 외부 트랜잭션 커밋
  - `Global transaction is marked as rollback-only but transactional code requested commit`
  - 커밋하려했지만 물리 트랜잭션이 롤백 전용으로 표시되어 있다. 따라서 물리 트랜잭션을 롤백한다.

![alt text](image-33.png)
**응답 흐름 - 내부 트랜잭션**
1. 로직2가 끝나고 트랜잭션 매니저를 통해 내부 트랜잭션을 롤백
2. 트랜잭션 매니저는 롤백 시점에 신규 트랜잭션 여부에 따라 다르게 동작한다. 이 경우에 신규 트랜잭션이 아니므로 롤백을 호출하지 않는다.
3. 내부 트랜잭션은 물리 트랜잭션을 롤백하지 않고 트랜잭션 동기화 매니저에 `rollbackOnly=true`라는 표시를 해둔다.

**응답 흐름 - 외부 트랜잭션**
4. 로직1이 끝나고 트랜잭션 매니저를 통해서 외부 트랜잭션을 커밋한다.
5. 외부 트랜잭션은 신규 트랜잭션이므로 실제 커밋을 해야한다. 이때 트랜잭션 동기화 매니저에 `rollbackOnly=true`가 있는지 확인하고 롤백 전용 표시가 있으면 물리 트랜잭션을 커밋하는 것이 아닌 롤백한다.
6. 실제 데이터베이스에 롤백이 반영되고 물리 트랜잭션도 끝이 난다.
7. 트랜잭션 매니저에 커밋을 한 개발자 입장에서는 커밋을 기대했지만 롤백이 되어버린 결과를 받게 되었다.
  - 시스템 입장에서는 커밋을 호출했지만 롤백되었다는 것을 알려야한다.
  - 스프링은 이 때 `UnexpectedRollbackException` 런타임 예외를 던진다.
  - 그래서 커밋을 하였지만, 기대하지 않았던 롤백이 발생했다는 것을 개발자에게 알려준다.

**정리**
- 논리 트랜잭션이 하나라도 롤백이 될 경우 물리 트랜잭션은 롤백된다.
- 내부 논리 트랜잭션이 롤백되면 롤백 전용(rollbackOnly=true) 마크를 표시한다.
- 외부 트랜잭션을 커밋할 때 롤백 전용 마크를 확인하고 롤백 전용 마크가 있다면 물리 트랜잭션을 롤백하고 UnexpectedRollbackException 예외를 던진다.


## 스프링 트랜잭션 전파7 - REQUIRES_NEW
외부 트랜잭션과 내부 트랜잭션을 완전히 분리해서 사용하는 방법
이렇게 하면 커밋과 롤백이 각각 별도로 이루어지게 된다.

~~~java
@Test
void inner_rollback_requires_new() {
    log.info("외부 트랜잭션 시작");
    TransactionStatus outer = txManager.getTransaction(new DefaultTransactionAttribute());
    log.info("outer.isNewTransaction()={}", outer.isNewTransaction());

    log.info("내부 트랜잭션 시작");
    DefaultTransactionAttribute definition = new DefaultTransactionAttribute();
    definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
    TransactionStatus inner = txManager.getTransaction(definition);
    log.info("inner.isNewTransaction()={}", inner.isNewTransaction()); // true

    log.info("내부 트랜잭션 롤백");
    txManager.rollback(inner);

    log.info("외부 트랜잭션 커밋");
    txManager.commit(outer);

}
~~~
- 내부 트랜잭션 시작 시 전파 옵션인 `propagationBehavior`에 `TransactionDefinition.PROPAGATION_REQUIRES_NEW`옵션을 주었다.
- 이 전파 옵션을 사용하면 내부 트랜잭션을 시작할 때 기존 트랜잭션에 참여하는 것이 아닌 새로운 물리 트랜잭션을 만들어 시작한다.

**실행 로그**
~~~
외부 트랜잭션 시작
Creating new transaction with name [null]: PROPAGATION_REQUIRED,ISOLATION_DEFAULT
Acquired Connection [HikariProxyConnection@1535276950 wrapping conn0: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@1535276950 wrapping conn0: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] to manual commit
outer.isNewTransaction()=true

내부 트랜잭션 시작
Suspending current transaction, creating new transaction with name [null]
Acquired Connection [HikariProxyConnection@927724363 wrapping conn1: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] for JDBC transaction
Switching JDBC Connection [HikariProxyConnection@927724363 wrapping conn1: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] to manual commit
inner.isNewTransaction()=true

내부 트랜잭션 롤백
Initiating transaction rollback
Rolling back JDBC transaction on Connection [HikariProxyConnection@927724363 wrapping conn1: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA]
Releasing JDBC Connection [HikariProxyConnection@927724363 wrapping conn1: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] after transaction
Resuming suspended transaction after completion of inner transaction

외부 트랜잭션 커밋
Initiating transaction commit
Committing JDBC transaction on Connection [HikariProxyConnection@1535276950 wrapping conn0: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA]
Releasing JDBC Connection [HikariProxyConnection@1535276950 wrapping conn0: url=jdbc:h2:mem:62c81737-8b0a-4f67-b622-b6d8ab126d51 user=SA] after transaction
~~~
**외부 트랜잭션 시작**
- 외부 트랜잭션을 시작하면서 conn0 커넥션을 획득하고 manual commit으로 변경해 물리 트랜잭션을 시작한다.
- 외부 트랜잭션은 신규 트랜잭션

**내부 트랜잭션 시작**
- 내부 트랜잭션을 시작하면서 conn1 커넥션을 획득하고 manual commit으로 변경해 물리 트랜잭션을 시작한다.
- 내부 트랜잭션은 외부 트랜잭션에 참여하는 것이 아니라 완전히 새로운 신규 트랜잭션으로 생성된다.

**내부 트랜잭션 롤백**
- 내부 트랜잭션을 롤백한다.
- 내부 트랜잭션은 신규 트랜잭션이기 때문에 실제 물리 트랜잭션을 롤백한다.
- 내부 트랜잭션은 conn1을 사용하므로 conn1에 물리 롤백을 수행한다.

**외부 트랜잭션 커밋**
- 외부 트랜잭션 커밋
- 외부 트랜잭션은 신규 트랜잭션이기 때문에 실제 물리 트랜잭션을 커밋
- 외부 트랜잭션은 conn0을 사용하므로 conn0에 물리 커밋을 수행한다.

![alt text](image-34.png)
**요청 흐름 - 외부 트랜잭션**
1. 외부 트랜잭션 시작
2. 트랜잭션 매니저는 데이터소스를 통해 커넥션 생성
3. 생성한 커넥션을 수동 커밋모드로 설정(setAutoCommit(false))로 설정 - **물리 트랜잭션 시작**
4. 트랜잭션 매니저는 트랜잭션 동기화 매니저에 커넥션을 보관
5. 트랜잭션 매니저는 트랜잭션을 생성활 결과를 `TransactionalStatus`에 담아 반환하는데 여기에 신규 트랜잭션 여부가 담겨있다(isNewTransaction). 트랜잭션을 처음 시작했기 때문이 신규 트랜잭션이다.
6. 로직1이 사용되고 커넥션이 필요한 경우 트랜잭션 동기화 매니저를 통해 트랜잭션이 적용된 커넥션을 획득해 사용한다.

**요청 흐름 - 내부 트랜잭션**
7. `REQUIRES_NEW`옵션과 함께 내부 트랜잭션을 시작
  - 이 때 트랜잭션 매니저는 `REQUIRES_NEW`옵션을 확인하고 기존 트랜잭션에 참여하는 것이 아닌 새로운 트랜잭션을 시작한다.
8. 트랜잭션 매니저는 데이터소스를 통해 커넥션을 생성
9. 생성한 커넥션을 수동 커밋 모드(setAutoCommit(false))로 설정 - **물리 트랜잭션 시작**
10. 트랜잭션 매니저는 트랜잭션 동기화 매니저에 커넥션을 보관
  - 이때 con1은 잠시 보류되고, con2가 사용된다.(내부 트랜잭션을 완료할 때까지 con2가 사용된다.)
11. 트랜잭션 매니저는 신규 트랜잭션의 생성 결과를 반환.
12. 로직2가 사용되고 커넥션이 필요한 경우 트랜잭션 동기화 매니저에 있는 con2 커넥션을 획득해 사용한다.

![alt text](image-35.png)
**응답 흐름 - 내부 트랜잭션**
1. 로직2가 끝나고 트랜잭션 매니저를 통해 내부 트랜잭션을 롤백
2. 트랜잭션 매니저는 롤백 시점에 신규 트랜잭션 여부에 따라 다르게 동작한다. 이 경우에는 신규 트랜잭션이기 때문에 실제 롤백을 호출하게된다.
3. 내부 트랜잭션이 con2 물리 트랜잭션을 롤백한다.
  - 트랜잭션이 종료되고 con2는 종료되거나 커넥션 풀에 반납된다.
  - 이후 con1의 보류가 끝나게 되고 다시 con1을 사용한다.

**응답 흐름 - 외부 트랜잭션**
4. 외부 트랜잭션에 커밋을 요청한다.
5. 외부 트랜잭션은 신규 트랜잭션이기 때문에 물리 트랜잭션을 커밋한다.
6. 이때 rollbackOnly 설정을 체크하고 rollbackOnly 설정이 없으므로 커밋한다.
7. con1 커넥션을 통해 물리 트랜잭션을 커밋한다.
  - 트랜잭션이 종료되고 con1은 종료되거나 커넥션 풀에 반납된다.

**정리**
- `REQUIRES_NEW` 옵션을 사용하면 물리 트랜잭션이 분리가 된다.
- `REQUIRES_NEW` 옵션을 사용하게 되면 커넥션이 동시에 2개가 사용된다는 점을 주의해야 한다.


## 스프링 트랜잭션 전파8 - 전파옵션
전파옵션에 별도 설정을 하지 않으면 REQUIRED가 기본으로 사용된다. 실무에서는 대부분 REQUIRED 옵션을 사용하고 가끔 REQUIRED_NEW를 사용한다. 나머지는 거의 사용하지 않으니 이런게 있다 정도만 보고 넘어가자

### REQUIRED
기존 트랜잭션이 존재하면 참여하고 없으면 생성한다. 트랜잭션이 필수라는 뜻으로 생각하자

### REQUIRED_NEW
항상 새로운 트랜잭션을 생성한다.