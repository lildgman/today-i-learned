# JDBC 이해
JDBC(Java Database Connectivity)는 자바에서 데이터베이스에 접속할 수 있도록 하는 자바 API
## JDBC 등장 이유
클라이언트가 서버를 통해 데이터를 저장, 조회하면 서버는 다음과 같은 과정을 통해 DB를 사용한다.
1. 커넥션 연결: TCP/IP를 사용해 커넥션 연결
2. SQL 전달: 서버에서 DB가 이해할 수 SQL을 커넥션을 통해 DB에 전달
3. 결과 응답: DB는 전달받은 SQL을 수행 후 결과를 응답, 서버는 응답 결과를 활용한다.

여기서 각 데이터베이스마다 커넥션을 연결하는 방법, SQL을 전달하는 방법, 결과를 응답 받는 방법이 모두 달랐다.
- 데이터베이스를 변경할 경우 서버에 개발된 데이터베이스 사용 코드도 변경해줘야 했다.
- 데이터베이스마다 커넥션 연결, SQL 전달, 응답 받는 방법을 새로 학습해야한다.

이런 문제를 해결하기 위해 JDBC가 등장했다.

## JDBC 표준 인터페이스

`Connection(연결)`, `Statement(SQL을 담은 내용)`, `ResultSet(SQL 요청 응답)` 의 기능을 표준 인터페이스로 정의해서 제공한다.

인터페이스만 있다고 기능이 동작하지 않는다. JDBC 인터페이스를 각각의 DB회사에서 DB에 맞춰 구현해서 라이브러리를 제공한다. 이것을 JDBC 드라이버라고 한다.

JDBC의 등장으로 아래와 같은 문제가 해결됐다.
- 데이터베이스를 교체할 경우 서버의 데이터베이스 사용 코드도 함께 변경해야했다
  - 이제 애플리케이션 로직은 JDBC 표준 인터페이스에만 의존한다. 다른 데이터베이스로 변경하고자 할 때 JDBC 구현 라이브러리만 변경하면 된다.
- 개발자가 각각의 데이터베이스마다 사용방법을 새로 학습해야한다.
  - 개발자는 JDBC 표준 인터페이스 사용방법만 학습하면 된다.

하지만 각각의 데이터베이스마나 SQL, 데이터타입 등 사용방법이 다른경우가 있다. 결국 데이터베이스를 변경할 경우 SQL은 변경할 데이터베이스에 맞도록 변경해야한다. JPA 사용 시 데이터베이스마다 다른 SQL을 정의해야 하는 문제를 해결할 수 있다.

## JDBC와 최신 데이터 접근 기술
최근에는 JDBC를 직접 사용하기 보단 JDBC를 편리하게 사용하는 기술들이 나와있다. 대표적으로 SQL Mapper, ORM 기술로 나눌 수 있다.

- SQL Mapper
  - 장점: JDBC를 편리하게 사용하도록 도와준다.
    - SQL 응답 결과를 객체로 편리하게 변환해준다.
    - JDBC의 반복코드를 제거해준다
  - 단점: 개발자가 SQL을 직접 작성해야한다.
  - 스프링 JdbcTemplate, MyBatis..

- ORM 기술
  - ORM: 객체를 관계형 데이터베이스 테이블과 매핑해주는 기술, 개발자는 반복적인 SQL작성하지 않고 ORM기술이 SQL을 동적으로 만들어 실행해준다. 데이터베이스들마다 다른 SQL을 사용하는 문제도 해결해준다.
  - JPA, 하이버네이트, 이클립스링크...
  - JPA는 자바진영 ORM 표준 인터페이스이고 이것을 구현한 것으로 하이버네이트와 이클립스 링크 등의 기술이 있다.

### SQL Mapper VS ORM 
SQL Mapper는 SQL만 직접 작성하면 번거로운 일은 SQL Mapper가 대신해준다. SQL만 작성할 줄 알면 금방 사용할 수 있다.

ORM기술은 SQL 자체를 작성하지 않아도 돼서 개발 생산성이 높아진다. 편리하지만 쉬운 기술이 아니라 깊이있게 학습해야한다. 

## 데이터베이스 연결
**데이터베이스에 접속하기위한 정보들**
~~~java
public abstract class ConnectionConst {

    public static final String URL = "jdbc:h2:tcp://localhost/~/test";
    public static final String USERNAME = "sa";
    public static final String PASSWORD = "";
}
~~~

~~~java
@Slf4j
public class DBConnectionUtil {

  public static Connection getConnection() {
      try {
          Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
          log.info("get connection={}, class={}", connection, connection.getClass());

          return connection;
      } catch (SQLException e) {
          throw new IllegalStateException(e);
      }
  }

}
~~~
데이터베이스에 연결하기 위해 JDBC가 제공하는 `DriverManager.getConnection()`을 사용한다. 라이브러리에 있는 데이터베이스 드라이버를 찾아 해당 드라이버가 제공하는 커넥션을 반환해준다.

`DriverManager`는 라이브러리에 등록된 DB드라이버를 관리하고 커넥션을 획득하는 기능을 제공한다.
1. `DriverManager.getConnection()` 호출
2. `DriverManager`가 라이브러리에 등록된 드라이버 목록을 인식, 드라이버들에게 순서대로 정보를 넘겨 커넥션을 획득할 수 있는지 확인
  - URL
  - 이름, 비밀번호 등...
  - 드라이버는 URL 정보를 체크해 처리할 수 있는 요청인지 확인
  - URL이 `jdbc:h2`로 시작 시 h2 데이터베이스에 접근하기 위한 규칙이다. H2 드라이버가 이를 처리할 수 있으므로 실제 데이터베이스에 연결해 커넥션을 획득하고 클라이언트에 반환한다.
  - `jdbc:h2`로 시작하긴하지만 MySQL 드라이버가 먼저 실행도면 본인이 처리할 수 없다는 결과를 반환하고 다음 드라이버에게 순서가 넘어가게된다.
3. 이런식으로 찾은 커넥션 구현첵 클라이언트에 반환된다.

## JDBC 개발 - 등록
~~~java
@Data
public class Member {

    private String memberId;
    private int money;

    public Member() {
    }

    public Member(String memberId, int money) {
        this.memberId = memberId;
        this.money = money;
    }
}
~~~

~~~java
@Slf4j
public class MemberRepositoryV0 {

  public Member save(Member member) throws SQLException {
      String sql = "insert into member(member_id, money) values (?,?)";

      Connection conn = null;
      PreparedStatement pstmt = null;

      try {
          conn = getConnection();
          pstmt = conn.prepareStatement(sql);
          pstmt.setString(1, member.getMemberId());
          pstmt.setInt(2, member.getMoney());
          pstmt.executeUpdate();

          return member;
      } catch (SQLException e) {

          log.error("db error, ", e);
          throw e;
      } finally {
          close(conn, pstmt, null);

      }
  }

  private void close(Connection conn, Statement stmt, ResultSet rs) {

      if (rs != null) {
          try {
              rs.close();
          } catch (SQLException e) {
              log.info("error", e);
          }
      }

      if (stmt != null) {

          try {
              stmt.close();
          } catch (SQLException e) {
              log.info("error", e);
          }

      }

      if (conn != null) {
          try {
              conn.close();
          } catch (SQLException e) {
              log.info("error", e);
          }

      }

  }

  private static Connection getConnection() {
      return DBConnectionUtil.getConnection();
  }
}
~~~
- `getConnection()`: DBConnectionUtil을 통해 데이터베이스 커넥션 획득
- `con.preparedStatement(sql)`: 데이터베이스에 전달할 sql과 파라미터로 전달할 데이터들을 준비
  - `pstmt.setString(1, member.getMemberId())`: sql 첫번째 ? 에 값 지정
  - `pstmt.setInt(2, member.getMoney())`: sql 두번째 ? 에 값 지정
- `pstmt.executeUpdate()`: `Statement`를 통해 SQL을 커넥션을 이용해 데이터베이스에 전달, 이때 int로 결과가 반환되는데 DB row 수를 반환한다.

쿼리 실행 후 리소스를 정리해야한다. 리소스 정리 시 항상 역순으로 해야한다.

리소스 정리는 반드시 해줘야한다. 이 부분을 놓치게 되면 커넥션이 끊어지지 않고 유지되는 문제가 발생할 수 있다. 이것을 리소스 누수라고하며 커넥션 부족으로 장애를 일으킬 수 있다.

`PreparedStatement`는 `Statement`의 자식 타입, ? 를 통한 파라미터 바인딩을 가능하게 해준다. SQL Injection을 예방할 수 있다.

## JDBC 개발 - 조회
~~~java
public Member findById(String memberId) throws SQLException {
    String sql = "select * from member where member_id = ?";

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = DBConnectionUtil.getConnection();
        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, memberId);

        rs = pstmt.executeQuery();

        if (rs.next()) {
            Member member = new Member();
            member.setMemberId(rs.getString("member_id"));
            member.setMoney(rs.getInt("money"));
            return member;
        } else {
            throw new NoSuchElementException("member not found. memberId = " + memberId);
        }
    }catch (SQLException e) {
        log.error("db error", e);
        throw e;
    } finally {
        close(conn, pstmt, rs);
    }
}
~~~
- `rs = pstmt.excuteQuery()`: 데이터를 변경할 때는 `executeUpdate()`를 사용하지만 조회할 때는 `executeQuery()`를 사용한다. 결과를 `ResultSet`에 담아 반환한다.

**ResultSet**
- `ResultSet`에는 select 쿼리의 결과가 순서대로 들어간다.
- `ResultSet` 내부에 있는 커서를 이동해 다음 데이터를 조회할 수 있다.
- `rs.next()`: 커서가 다음으로 이동, 최초 커서는 데이터를 가리키고 있지 않기 대문에 한번은 호출해야 데이터를 조회할 수 있다.
  - `rs.next()`의 결과가 true이면 이동 결과 데이터가 있다는 의미
- `rs.getString("member_id")`: 현재 커서가 가리키고 있는 위치의 `member_id` 데이터를 String 타입으로 반환
- `rs.getInt("money")`: 현재 커서가 가리키고 있는 위치의 money 데이터를 int로 반환

## JDBC 개발 - 수정, 삭제

**수정**
~~~java
public void update(String memberId, int money) throws SQLException {
  String sql = "update member set money = ? where member_id=?";

  Connection conn = null;
  PreparedStatement pstmt = null;

  try {

      conn = DBConnectionUtil.getConnection();
      pstmt = conn.prepareStatement(sql);

      pstmt.setInt(1, money);
      pstmt.setString(2, memberId);
      int resultSize = pstmt.executeUpdate();
      log.info("resultSize = {}", resultSize);
  } catch (SQLException e) {

      log.error("db error, ", e);
      throw e;
  } finally {
      close(conn, pstmt, null);

  }
}
~~~

**삭제**
~~~java
public void delete(String memberId) throws SQLException {
  String sql = "delete from member where member_id = ?";

  Connection conn = null;
  PreparedStatement pstmt = null;

  try {

      conn = DBConnectionUtil.getConnection();
      pstmt = conn.prepareStatement(sql);

      pstmt.setString(1, memberId);
      pstmt.executeUpdate();
  } catch (SQLException e) {

      log.error("db error, ", e);
      throw e;
  } finally {
      close(conn, pstmt, null);

  }
}
~~~


## DataSource 적용
~~~java
private final DataSource dataSource;

public MemberRepositoryV1(DataSource dataSource) {
    this.dataSource = dataSource;
}

...

private void close(Connection conn, Statement stmt, ResultSet rs) {

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(conn);

    }

    private Connection getConnection() throws SQLException{
        Connection conn = dataSource.getConnection();
        log.info("get connection = {}, class = {}", conn, conn.getClass());
        return conn;
    }
~~~
- DataSource 의존관계 주입
    - 외부에서 DataSource를 주입받아 사용, 직접 DBConnectionUtil 사용하지 않아도 됨
    - `DataSource`는 표준 인터페이스이기 때문에 `DriverManagerDataSource`에서 `HikariDataSource`로 변경되어도 코드를 변경해주지 않아도 된다.
- JdbcUtils 편의 메서드
    - `JdbUtils`를 사용하면 커넥션을 편리하게 닫을 수 있다.

`DriverManagerDataSource` 사용 시 `conn0~5`처럼 항상 새로운 커넥션이 생성되어서 사용된다.<br>
커넥션 풀 사용 시 커넥션이 재사용된다. 사용이 끝난 커넥션을 다시 돌려주는 것을 반복하게 된다. 웹 애플리케이션에 동시에 여러 요청이 들어오게 되면 여러 쓰레드에서 커넥션 풀의 커넥션을 가져가는 상황이 나오게 될 것이다