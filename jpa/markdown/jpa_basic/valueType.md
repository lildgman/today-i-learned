# 값 타입
## 기본 값 타입
### JPA의 데이터 타입 분류
- **엔티티 타입**
  - `@Entity`로 정의하는 객체
  - 데이터가 변해도 식별자로 지속해서 추적 가능
  - ex) 회원 엔티티의 키나 나이 값을 변경해도 식별자를 통해 인식 가능
- **값 타입**
  - int, Integer, String과 같이 단순히 값으로 사용하는 자바 기본 타입이나 객체
  - 식별자가 없고 값만 있으므로 변경 시 추적 불가

### 값 타입 분류
- 기본값 타입
  - 자바 기본 타입(int, double)
  - 래퍼 클래스(Integer, Long)
  - String
- 임베디드 타입
- 컬렉션 값 타입

### 기본 값 타입
- ex) String name, int age
- 생명주기를 엔티티의 의존
  - ex) 회원을 삭제하면 이름, 나이 필드도 함께 삭제
- 값 타입은 공유하면 안된다.
  - ex) 회원 이름 변경 시 다른 회원 이름도 함께 변경되면 안된다.

## 임베디드 타입(복합 값 타입)
- 새로운 값 타입을 직접 정의 가능
- JPA는 임베디드 타입이라 함
- 주로 기본 값 타입을 모아 만들어서 복합 값 타입이라고도 함
- int, String과 같은 값 타입

### 임베디드 타입
- ex) 회원 엔티티는 이름, 근무 시작일, 근무 종료일, 주소 도시, 주소 번지, 주소 우편번호를 가진다.
- 위 예시를 이와 같이 함축시킬 수 있다.
  - 회원 엔티티는 이름, 근무 기간, 집 주소를 가진다.

### 임베디드 타입 사용법
- `@Embeddable`: 값 타입을 정의하는 곳에 표시
- `@Embedded`: 값 타입을 사용하는 곳에 표시
- 기본 생성자 필수

~~~java
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;
}

@Embeddable
public class Period {

    // Period
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
~~~

~~~java
// Period
@Embedded
private Period workPeriod;

@Embedded
private Address homeAddress;
~~~

### 임베디드 타입의 장점
- 재사용
- 높은 응집도
- Period.isWork() 같이 해당 값 타임만 사용하는 의미 있는 메서드를 만들 수 있음
- 임베디드 타입을 포함한 모든 값 타입은 값 타입을 소유한 엔티티에 생명주기를 의존한다.

### 임베디드 타입과 테이블 매핑
- 임베디드 타입은 엔티티의 값일 뿐이다.
- 임베디드 타입을 사용하기 전과 후에 `매핑하는 테이블은 같다.`
- 객체와 테이블을 세밀하게 매핑하는 것이 가능해짐

### @AttributeOverride: 속성 재정의
- 한 엔티티에서 같은 값 타입을 사용하면 컬럼 명이 중복이 된다.
- `@AttributeOverides`, `@AttributeOverride`를 사용해 컬럼 명 속성을 재정의하면 된다.

~~~java
@Embedded
private Address homeAddress;

// address
@Embedded
@AttributeOverrides({
        @AttributeOverride(name="city",
                column = @Column(name = "WORK_CITY")),
        @AttributeOverride(name = "street",
                column = @Column(name = "WORK_STREET")),
        @AttributeOverride(name = "zipcode",
                column = @Column(name = "WORK_ZIPCODE"))
})
private Address workAddress;
~~~

## 값 타입과 불변 객체
값 타입은 복잡한 객체 세상을 조금이라도 단순화하려고 만든 개념이다. 따라서 값 타입은 단순하고 안전하게 다룰 수 있어야 한다.

### 값 타입 공유 참조
- 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유하면 위험하다. -> 사이드 이펙트 발생
- ex)
  - 회원1과 회원2가 같은 값 타입인 주소를 보고 있다.
  - 이 경우 시티의 값을 oldCity에서 newCity로 바꾸면 회원1과 회원2가 들고있던 테이블에서 주소값이 변경된다.

### 값 타입 복사
- 값 타입의 실제 인스턴스 값을 공유하는 것은 위험하다.
- 인스턴스(값)을 복사해서 사용하자

### 객체 타입의 한계
- 항상 값을 복사해 사용하면 공유 참조로 인해 발생하는 부작용을 피할 수 있다.
- 문제는 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본타입이 아니라 객체 타입이다.
- 자바 기본 타입에 값을 대입하면 값을 복사한다.
- 객체 타입은 참조 값을 직접 대입하는 것을 막을 방법이 없다.
- 객체의 공유 참조는 피할 수 없다.

### 불변 객체
- 객체 타입을 수정할 수 없게 만들면 부작용을 원찬 차단할 수 있다.
- 값 타입은 불변 객체로 설계해야한다.
- 불변 객체: 생성 시점 이후 절대 값을 변경할 수 없는 객체
- 생성자로만 값을 설정하고 수정자를 만들지 않으면 된다.
- Integer와 String은 자바가 제공하는 대표적인 불변 객체

## 값 타입의 비교
값 타입: 인스턴스가 달라도 그 안에 값이 같으면 같은 것으로 봐야한다.
- `동일성(identity) 비교`: 인스턴스의 참조 값을 비교, == 사용
- `동등성(equivalence) 비교`: 인스턴스의 값을 비교, equals() 사용
- 값 타입은 a.equals(b)를 사용해 동등성 비교를 해야한다.
- 값 타입의 equals()를 적절하게 재정의(주로 모든 필드 사용)

## 값 타입 컬렉션
- 값 타입을 하나 이상 저장할 때 사용
- `@ElementCollection`, `@CollectionTable` 사용
- 데이터베이스는 컬렉션을 같은 테이블에 저장할 수 없다.
- 컬렉션 저장하기 위한 별도의 테이블이 필요하다.
~~~java
@ElementCollection
@CollectionTable(name = "FAVORITE_FOOD",
    joinColumns = @JoinColumn(name = "MEMBER_ID"))
@Column(name = "FOOD_NAME")
private Set<String> favoriteFoods = new HashSet<>();

@ElementCollection
@CollectionTable(name = "ADDRESS",
        joinColumns = @JoinColumn(name = "MEMBER_ID"))
private List<Address> addressHistory = new ArrayList<>();
~~~

### 값 타입 컬렉션 사용
- 값 타입 저장
~~~java
Member member = new Member();
member.setUsername("member1");
member.setHomeAddress(new Address("homecity", "street", "10000"));

member.getFavoriteFoods().add("치킨");
member.getFavoriteFoods().add("족발");
member.getFavoriteFoods().add("피자");

member.getAddressHistory().add(new Address("city2", "street2", "20000"));
member.getAddressHistory().add(new Address("city3", "street3", "30000"));

em.persist(member);
~~~
- 값 타입 조회
~~~java
Address findMemberAddress = findMember.getHomeAddress();
~~~
~~~
select
    m1_0.MEMBER_ID,
    m1_0.city,
    m1_0.street,
    m1_0.zipcode,
    m1_0.name,
    m1_0.endDate,
    m1_0.startDate 
from
    Member m1_0 
where
    m1_0.MEMBER_ID=?
~~~
  - 조회 결과로 addressHistory와 favoriteFoods가 보이지 않는다.
  - 값 타입 컬렉션도 지연 로딩 전략을 사용하기 때문이다.

- 값 타입 수정
~~~java
Member findMember = em.find(Member.class, member.getId());

Address findMemberAddress = findMember.getHomeAddress();
findMember.setHomeAddress(new Address("newCity", findMemberAddress.getStreet(), findMemberAddress.getZipcode()));

findMember.getFavoriteFoods().remove("치킨");
findMember.getFavoriteFoods().add("한식");

System.out.println("===============address==============");
findMember.getAddressHistory().remove(new Address("city2", "street2", "20000"));
findMember.getAddressHistory().add(new Address("newCity2", "street2", "20000"));
~~~

### 값 타입 컬렉션의 제약사항
- 값 타입은 엔티티와 다르게 식별자 개념이 없다.
- 값은 변경하면 추적이 어렵다.
- 값 타입 컬렉션에 변경 사항이 발생하면 주인 엔티티와 연관된 모든 데이터를 삭제하고 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어 기본키를 구성해야 한다. -> null 입력X, 중복 저장X

### 값 타입 컬렉션 대안
- 상황에 따라 `값 타입 컬렉션 대신 일대다 관계를 고려하자`
- 일대다 관계를 위한 엔티티를 만들고 여기에서 값 타입을 사용
- 영속선 전이(cascade) + 고아 객체 제거를 사용해 값 타입 컬렉션 처럼 사용

### 정리
- 엔티티 타입 특징
  - 식별자 O
  - 생명 주기 관리
  - 공유
- 값 타입 특징
  - 식별자 X
  - 생명 주기를 엔티티에 의존
  - 공유하지 않는 것이 안전(복사해서 사용)
  - 불변 객체로 만드는 것이 안전
   