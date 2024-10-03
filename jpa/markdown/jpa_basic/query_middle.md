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