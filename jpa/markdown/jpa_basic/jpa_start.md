# JPA 시작하기
## JPA 구동방식
`Persistence`라는 클래스가 `META-INF/persistance.xml`이라는 설정정보를 읽어 `EntityManagerFactory`라는 곳에서 `EntityManager`를 생성해서 이 엔티티 매니저를 사용하면 된다.

## 실습
### 객체와 테이블을 생성하고 매핑
~~~java
@Entity
public class Member {

    @Id
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
~~~
- `@Id`를 통해 PK가 무엇인지 JPA에 알려주도록 하자.
- 클래스명을 가지고 테이블을 생성한다.
- DB의 테이블 이름과 다르다면 클래스에 `@Table(name = "USER")`과 같이 DB에 있는 테이블명과 맞춰주면 된다.
- 컬럼명 또한 `@Column(name = "username")`처럼 컬럼명을 맞춰주면 된다.

~~~java
public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        //code
        tx.begin();

        try {
//            createMember(em);
//            findMember(em);
//            deleteMember(em);
//            updateMember(em);
            findMembers(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();


    }

    /**
     * 회원 조회
     * JPQL 사용
     */
    private static void findMembers(EntityManager em) {
        List<Member> result = em.createQuery("select m from Member m", Member.class)
                .getResultList();
        for (Member member : result) {
            System.out.println("member.getName() = " + member.getName());
        }
    }

    /**
     * 회원 변경
     */
    private static void updateMember(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        findMember.setName("Kang");
    }

    /**
     * 회원 삭제
     */
    private static void deleteMember(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);

        em.remove(findMember);
    }

    /**
     * 회원 단건 조회
     */
    private static void findMember(EntityManager em) {
        Member findMember = em.find(Member.class, 1L);
        System.out.println("findMember.getId() = " + findMember.getId());
        System.out.println("findMember.getName() = " + findMember.getName());
    }

    /**
     * 회원 등록
     */
    private static void createMember(EntityManager em) {
        Member member = new Member();
        member.setId(2L);
        member.setName("Oh");

        em.persist(member);
    }
}
~~~
- 엔티티 매니저 팩토리는 하나만 생성해 애플리케이션 전체에서 공유해야 한다.
- JPA에서 데이터를 변경하는 모든 작업은 꼭 트랜잭션 안에서 작업해야 한다.

회원 변경 부분이 정말 신기한데 jpa를 통해 엔티티를 가져오면 jpa에서 해당 엔티티를 관리한다. jpa가 엔티티가 변경되는 것을 커밋되는 시점 직전에 체크를 해서 변경된 부분이 있다면 업데이트 쿼리를 db에 날리게된다.

### JPQL
- JPA를 사용하면 엔티티 객체를 중심으로 개발하게 된다.
- 이때 문제가 되는 부분이 검색하는 부분이다.
- 검색할 때도 엔티티 객체를 대상으로 검색하는데
- 모든 DB 데이터를 객체로 변환해 검색하는 것은 무리이다
- 애플리케이션이 필요한 데이터만 불러오려면 검색 조건이 포함된 SQL이 필요하다.
- JPQL은 SQL을 추상화한 객체 지향 쿼리 언어
