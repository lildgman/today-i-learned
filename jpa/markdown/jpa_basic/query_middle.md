# 객체지향 쿼링 언어2 - 중급 문법
## 경로 표현식
- `.`을 찍어 객체 그래프를 탐색하는 것
~~~ java
select m.username -> 상태필드
  from Member m
    join m.team t -> 단일 값 연관 필드
    join m.orders o -> 컬렉션 값 연관 필드
where t.name = '팀A'
~~~

- 상태 필드(state field): 단순히 값을 저장하기 위한 필드
- 연관 필드(association field): 연관관계를 위한 필드
  - 단일 값 연관 필드:
    @ManyToOne, @OneToOne, 대상이 엔티티
  - 컬렉션 값 연관 필드:
    @OneToMany, @ManyToMany, 대상이 컬렉션

### 경로 표현식 특징
- 상태 필드: 경로 탐색의 끝 -> 더 이상 탐색이 안됨
- 단일 값 연관 경로: 묵시적 내부 조인(inner join)발생, 탐색 더 가능
- 컬렉션 값 연관 경로: 묵시적 내부 조인 발생, 탐색이 더이상 안된다.
  - FROM절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색이 가능
~~~java
select m.username From Team t join t.members m
~~~
-> 묵시적 조인 대신 `명시적 조인을 사용하자`
-> 쿼리 튜닝이 용이하다.

### 명시적 조인, 묵시적 조인
- 명시적 조인: join 키워드 직접 사용
  - `select m from Member m join m.team t`
- 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생(내부 조인만 가능)
  - `select m.team from Member m`

### 경로 탐색을 사용한 묵시적 조인 시 주의사항
- 항상 내부 조인
- 컬렉션은 경로 탐색의 끝이다. 따라서 명시적 조인을 통해 별칭을 얻어야한다.
- 경로 탐색은 주로 SELECT, WHERE절에서 사용하지만 묵시적 조인으로 인해 SQL의 FROM절에 영향을 준다.

### 실무적인 조언!
- 가급적 묵시적 조인 대신 `명시적 조인을 사용하자.`
- 조인은 SQL 튜닝에 중요 포인트이다.
- 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기가 어렵다.

## 페치 조인(fetch join)
- 실무에서 엄청 중요
- SQL 조인 종류X
- JPQL에서 성능 최적화를 위해 제공
- 연관된 엔티티나 컬렉션을 SQL 한 번에 함께 조회
- join fetch 명령어 사용
- 페치 조인 ::=[LEFT [OUTER] | INNER] JOIN FETCH 조인경로

### 엔티티 페치 조인
- 회원을 조회하면서 연관된 팀도 함께 조회(SQL 한 번에)
- SQL을 보면 회원 뿐만 아니라 팀(T.*)도 함께 SELECT
- JPQL: select m from Member m join fetch m.team
- SQL: SELECT M.\*, T.\* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID = T.ID

~~~java
String query = "select m From Member m";

List<Member> resultList = em.createQuery(query, Member.class).getResultList();

for (Member m : resultList) {
    System.out.println("member = " + m.getUsername() + ", " + m.getTeam().getName());
    // 회원1, 팀A -> 팀A는 SQL문으로 조회
    // 회원2, 팀A -> 1차 캐시에서 조회
    // 회원3, 팀B -> 팀B는 SQL문으로 조회
}
~~~
~~~
Hibernate: 
    /* select
        m 
    From
        Member m */ select
            m1_0.id,
            m1_0.age,
            m1_0.memberType,
            m1_0.TEAM_ID,
            m1_0.username 
        from
            Member m1_0
Hibernate: 
    select
        t1_0.id,
        t1_0.name 
    from
        Team t1_0 
    where
        t1_0.id=?
m. = 회원1, teamA
m. = 회원2, teamA
Hibernate: 
    select
        t1_0.id,
        t1_0.name 
    from
        Team t1_0 
    where
        t1_0.id=?
m. = 회원3, teamB
~~~
~~~java
String query = "select m From Member m join fetch m.team";

List<Member> resultList = em.createQuery(query, Member.class).getResultList();

for (Member m : resultList) {
    System.out.println("m. = " + m.getUsername() + ", " + m.getTeam().getName());
    // 회원1, 팀A -> 팀A는 SQL문으로 조회
    // 회원2, 팀A -> 1차 캐시에서 조회
    // 회원3, 팀B -> 팀B는 SQL문으로 조회
}
~~~
~~~
Hibernate: 
    /* select
        m 
    From
        Member m 
    join
        
    fetch
        m.team */ select
            m1_0.id,
            m1_0.age,
            m1_0.memberType,
            t1_0.id,
            t1_0.name,
            m1_0.username 
        from
            Member m1_0 
        join
            Team t1_0 
                on t1_0.id=m1_0.TEAM_ID
m. = 회원1, teamA
m. = 회원2, teamA
m. = 회원3, teamB
~~~
지연로딩으로 세팅을 해도 페치조인이 항상 우선이 된다.

### 컬렉션 페치 조인
- 일대다 관계, 컬렉션 페치 조인
~~~java
String query = "select t From Team t join fetch t.members";

List<Team> resultList = em.createQuery(query, Team.class).getResultList();

for (Team t : resultList) {
    System.out.println("team = " + t.getName() +", members= "+ t.getMembers().size());
    for (Member member : t.getMembers()) {
        System.out.println("-> member = " + member);
    }
}
~~~
~~~
Hibernate: 
    /* select
        distinct t 
    From
        Team t 
    join
        
    fetch
        t.members */ select
            distinct t1_0.id,
            m1_0.TEAM_ID,
            m1_0.id,
            m1_0.age,
            m1_0.memberType,
            m1_0.username,
            t1_0.name 
        from
            Team t1_0 
        join
            Member m1_0 
                on t1_0.id=m1_0.TEAM_ID
team = teamA, members= 2
-> member = Member{id=1, username='회원1', age=0, team=jpql.Team@6c995c5d}
-> member = Member{id=2, username='회원2', age=0, team=jpql.Team@6c995c5d}
team = teamB, members= 1
-> member = Member{id=3, username='회원3', age=0, team=jpql.Team@51fe7f15}
~~~

### 페치 조인과 DISTINCT
- SQL의 DISTINCT는 중복된 결과를 제거하는 명령
- JPQL의 DISTINCT는 2가지 기능을 제공
  - SQL에 DISTINCT를 추가
  - 애플리케이션에서 엔티티 중복 제거
    - 하이버네이트6부터 DISTINCT 명령어를 사용하지 않아도 애플리케이션에서 중복 제거가 자동으로 적용

### 일반조인과 페치조인의 차이
- 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않는다.
~~~java
String query = "select t From Team t join t.members m";
~~~
~~~
Hibernate: 
    /* select
        t 
    From
        Team t 
    join
        t.members m */ select
            t1_0.id,
            t1_0.name 
        from
            Team t1_0 
        join
            Member m1_0 
                on t1_0.id=m1_0.TEAM_ID
Hibernate: 
    select
        m1_0.TEAM_ID,
        m1_0.id,
        m1_0.age,
        m1_0.memberType,
        m1_0.username 
    from
        Member m1_0 
    where
        m1_0.TEAM_ID=?
team = teamA, members= 2
-> member = Member{id=1, username='회원1', age=0, team=jpql.Team@179ee36b}
-> member = Member{id=2, username='회원2', age=0, team=jpql.Team@179ee36b}
Hibernate: 
    select
        m1_0.TEAM_ID,
        m1_0.id,
        m1_0.age,
        m1_0.memberType,
        m1_0.username 
    from
        Member m1_0 
    where
        m1_0.TEAM_ID=?
team = teamB, members= 1
-> member = Member{id=3, username='회원3', age=0, team=jpql.Team@383cdd4d}
~~~

~~~java
String query = "select t From Team t join fetch t.members";
~~~
~~~
Hibernate: 
    /* select
        t 
    From
        Team t 
    join
        
    fetch
        t.members */ select
            t1_0.id,
            m1_0.TEAM_ID,
            m1_0.id,
            m1_0.age,
            m1_0.memberType,
            m1_0.username,
            t1_0.name 
        from
            Team t1_0 
        join
            Member m1_0 
                on t1_0.id=m1_0.TEAM_ID
team = teamA, members= 2
-> member = Member{id=1, username='회원1', age=0, team=jpql.Team@161d95c6}
-> member = Member{id=2, username='회원2', age=0, team=jpql.Team@161d95c6}
team = teamB, members= 1
-> member = Member{id=3, username='회원3', age=0, team=jpql.Team@4cae66a8}
~~~
- JPQL은 결과를 반환할 때 연관관계를 고려하지 않는다.
- SELECT절에 지정한 엔티티만 조회한다.
- 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회(즉시 로딩)
- `페치 조인은 객체 그래프를 SQL 한번에 조회하는 개념`

### 페치 조인의 특징과 한계
- 페치 조인 대상에는 별칭을 줄 수 없다.
  - 하이버네이트는 가능하지만 가급적이면 사용하지 말자
- 둘 이상의 컬렉션은 페치 조인 할 수 없다.
- 컬렉션을 페치 조인하면 페이징 API(setFirstResult, setMaxResults)를 사용할 수 없다.
  - 단일 값 연관 필드들은 페치 조인해도 페이징 가능
  - 하이버네이트는 경로 로그를 남기고 메모리에서 페이징(매우 위험하다)
  - BatchSize를 주도록 하자
- 연관된 엔티티들은 SQL 한 번으로 조회 - 성능 최적화
- 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선시 함
- 실무에서 글로벌 로딩 전략은 모두 지연 로딩
- 최적화가 필요한 곳은 페치 조인을 적용

### 페치 조인 - 정리
- 모든 것을 페치 조인으로 해결할 수는 없다.
- 객체 그래프를 유지할 때 사용하면 효과적이다.
- 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야 하면 페치 조인보다는 일반조인을 사용, 필요한 데이터들만 조회한 뒤 DTO로 반환하는 것이 효과적이다.

## 다형성 쿼리
### TYPE
- 조회 대상을 특정 자식으로 한정
- ex) Item 중 Book, Movie를 조회해라
- JPQL: select i from Item I where type(i) IN (Book, Movie)
- SQL: select i from i where i.DTYPE in ('B', 'M')
### TREAT
- 부모인 Item과 자식 Book이 있다.
- JPQL: select i from Item i where treat(i as Book).auther = 'kim'
- SQL: select i from Item i where i.DTYPE = 'B' and i.auther = 'kim'

## JPQL - 엔티티 직접 사용
### 엔티티 직접 사용 - 기본 키 값
- JPQL에서 엔티티를 직접 사용하면 SQL에서 해당 엔티티 기본 키 값을 사용한다.
- JPQL
  - select count(m.id) from Member m // 엔티티의 아이디를 사용
  - select count(m) from Member m // 엔티티를 직접 사용
- SQL
  - select count(m.id) as cnt from Member m

~~~java
String query = "select m from Member m where m = :member";
Member findMember = em.createQuery(query)
                      .setParameter("member", member1)
                      .getSingleResult();
~~~
~~~java
String query = "select m from Member m where m.id = :memberId";
Member findMember = em.createQuery(query)
                      .setParameter("memberId", member.getId())
                      .getSingleResult();
~~~

### 엔티티 직접 사용 - 외래 키 값
~~~java
String query = "select m from Member m where m.team = :team";

List<Member> resultList = em.createQuery(query, Member.class)
        .setParameter("team", teamA)
        .getResultList();
~~~
~~~java
String query = "select m from Member m where m.team.id = :teamId";

List<Member> resultList = em.createQuery(query, Member.class)
        .setParameter("teamId", teamA.getId())
        .getResultList();
~~~

## Named 쿼리
