# 객체지향 쿼리 언어1 - 기본 문법
JPA는 다음과 같은 다양한 쿼리 방법을 지원한다
- JPQL
- JPA Criteria
- QueryDSL
- 네이티브 SQL
- JDBC API 직접사용, MyBatis, SpringJdbcTemplate와 함께 사용

## JPQL
JPA를 사용하면 엔티티 객체를 중심으로 개발하게 된다. 여기서 문제가 되는게 검색 쿼리이다. 검색을 할 때도 `테이블이 아닌 엔티티 객체를 대상으로 검색을 하게 된다`. 모든 DB 데이터를 객체로 변환해 검색하는 것은 불가능하다. 애플리케이션이 필요한 데이터만 DB에서 불러오려면 결국 검색 조건이 포함된 SQL이 필요하다.

JPA는 SQL을 추상화한 `JPQL`이라는 객체 지향 쿼리 언어를 제공한다. <br>
ANSI 표준 SQL이 지원하는 문법은 다 지원한다.<br>
`JPQL`은 엔티티 객체를 대상으로 쿼리를 하는 것이고 `SQL`은 데이터베이스 테이블을 대상으로 쿼리하는 것이다.

- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
- SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
- 한마디로 정의하자면 객체 지향 SQL

## Criteria
- 문자가 아닌 자바코드로 JPQL을 작성할 수 있음
- JPQL 빌더 역할
- JPA 공식 기능
- `너무 복잡하고 실용성이 없다.`
- Criteria 대신 `QueryDSL`을 사용하자. 

## QueryDSL
- 자바코드로 JPQL 작성 가능
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있다.
- 동적쿼리 작성이 편리하다.
- 단순하고 쉬움
- 실무 에서 사용 권장

## 정리
- JPQL은 객체지향 쿼리 언어이다. 따라서 테이블을 대상으로 쿼리하는 것이 아닌 **엔티티 객체를 대상으로 쿼리**한다.
- JPQL은 SQL을 추상화해 특정 데이터베이스 SQL에 의존하지 않는다.
- JPQL은 결국 SQL로 변환된다.

## JPQL 문법
~~~java
Member singleResult = em.createQuery("select m from Member m where m.username =:username", Member.class)
                    .setParameter("username", "hi")
                    .getSingleResult();
~~~
- 엔티티와 속성은 대소문자 구분 O
- JPQL 키워드는 대소문자 구분 X
- 엔티티 이름 사용, 테이블 이름이 아니다.
- **별칭 필수!**
- 결과 조회 API
  - getResultList(): 결과가 하나 이상일 때 리스트 반환, 결과가 없으면 빈 리스트 반환
  - getSingleResult(): 결과가 정확히 하나, 단일 객체 반환
    - 결과가 없으면 `NoResultException`
    - 결과가 둘 이상이면 `NoUniqueResultException`

## 프로젝션
- SELECT절에 조회할 대상을 지정하는 것
- 프로잭션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
- SELECT m FROM Member m -> 엔티티 프로젝션
- SELECT m.team FROM Member m -> 엔티티 프로젝션
- SELECT m.address FROM Member m -> 임베디드 타입 프로젝션
- SELECT m.username, m.age FROM Member m -> 스칼라 타입 프로젝션
- DISTINICT 중복 제거

여기서 나온 결과들은 영속성 컨텍스트에서 관리된다.

### 프로젝션 - 여러 값 조회
- SELECT m.username, m.age FROM Member m
- 1. Query 타입으로 조회
- 2. Object[] 타입으로 조회
- 3. new 명령어로 조회
  - 단순 값을 DTO로 바로 조회
    - SELECT new jpql.MemberDTO(m.username, m.age) from Member m
    - 패키지 명을 포함한 전체 클래스 명 입력
    - 순서와 타입이 일치하는 생성자가 필요

## 페이징 API
- setFirstResult(int startPosition): 조회 시작 위치(0부터 시작)
- setMaxResults(int maxResult): 조회할 데이터 수
~~~java
List<Member> resultList = em.createQuery("select m from Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();
~~~

## 조인
- 내부 조인
  - SELECT m FROM Member m [INNER] JOIN m.team t
- 외부 조인
  - SELECT m FROM Member m LEFT [OUTER] JOIN m.team t
- 세터 조인
  - SELECT count(m) from Member m, Team t where m.username = t.name

### 조인 - ON 절
- 조인 대상 필터링
- 연관관계 없는 엔티티 외부 조인

### 조인 대상 필터링
ex) 회원과 팀을 조인하면서 팀 이름이 A인 팀만 조인
- JPQL
  - SELECT m, t FROM Member m LEFT JOIN m.team t on t.name = 'A'
- SQL
  - SELECT m.\*, t.\* FROM Member m LEFT JOIN Team t ON m.TEAM_ID=t.id and t.name='A'

### 연관관계 없는 엔티티 외부 조인
ex) 회원의 이름과 팀 이름이 같은 대상 외부 조인
- JPQL
  - SELECT m, t FROM Member m LEFT JOIN Team t on m.username = t.name
- SQL
  - SELECT m.\*, t.\* FROM Member m LEFT JOIN Team t ON m.username = t.name

## 서브쿼리
- 나이가 평균보다 많은 회원
  - select m from Member m where m.age > (select avg(m2.age) from Member m2)
- 한 건이라도 주문한 고객
  - select m from Member m where (select count(o) from Order o where m = o.member) > 0

### 서브 쿼리 지원 함수
- [NOT] EXISTS (subquery): 서브쿼리에 존재하면 참
  - {ALL | ANY | SOME} (subquery)
  - ALL 모두 만족하면 참
  - ANY, SOME: 같은 의미, 조건을 하나라도 만족하면 참
- [NOT] IN (subquery): 서브쿼리의 결과 중 하나라도 같은 것이 있으면 참

### 서브 쿼리 - 예제
- 팀A 소속인 회원
  - select m from Member m where exists(select t from m.team t where t.name = '팀A')
- 전체 상품 각각의 재고보다 주문량이 많은 주문들
  - select o from Order o where o.orderAmount > ALL (select p.stockAmount from Product p)
- 어떤 팀이든 팀에 소속된 회원
  - select m from Member m where m.team = ANY (select t from Team t)

### JPA 서브 쿼리 한계
- JPA 표준 스펙에서는 WHERE, HAVING 절에서만 서브쿼리 사용 가능
- 구현체인 하이버네이트에서 SELECT 절도 가능하다.
- FROM절의 서브 쿼리는 현재 JPQL에서 불가능
  - 조인으로 풀 수 있으면 풀어서 해결 가능
  - 하이버네이트6 부터 FROM절의 서브쿼리 지원

## JPQL 타입 표현과 기타식
### JPQL 타입 표현
- 문자: 'HELLO', 'she`s'
- 숫자: 10L, 10D, 10F
- Boolean: TRUE, FALSE
- ENUM: jpql.MemberType.Admin(패키지명 포함)
- 엔티티 타입: TYPE(m) = Member(상속관계에서 사용)

### JPQL 기타
- SQL과 문법이 같다
- EXISTS, IN
- AND, OR, IN
- =, >, >=, <, <=, <>
- BETWEEN, LIKE, IS NULL