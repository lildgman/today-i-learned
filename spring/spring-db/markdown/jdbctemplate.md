# 데이터 접근 기술 - 스프링 JdbcTemplate

## JdbcTemplate
### 장점
- 설정의 편리함
  - JdbcTemplate는 spring-jdbc 라이브러리에 포함되어있다.
  - 스프링으로 JDBC를 사용할 때 기본으로 사용되는 라이브러리
  - 별도의 복잡한 설정 없이 바로 사용 가능
- 반복 문제 해결
  - JdbcTemplate는 템플릿 콜백 패턴을 사용해 JDBC를 직접 사용할 때 발생하는 반복 문제를 해결해준다.
    - 커넥션 획득
    - statement 준비, 실행
    - 결과를 반복하도록 루프 실행
    - 커넥션 종료, statment, resultset 종료
    - 트랜잭션을 다루기 위한 커넥션 동기화
    - 예외 발생 시 스프링 예외 변환기 실행
  - 개발자는 SQL을 작성하고 전달할 파라미터를 정의하고 응답 값을 매핑하기만 하면 된다.

### 단점
- 동적 SQL을 해결하기 어렵다.

## JdbcTemplate 적용
~~~java
@Slf4j
public class JdbcTemplateItemRepositoryV1 implements ItemRepository {

    private final JdbcTemplate template;

    public JdbcTemplateItemRepositoryV1(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            // 자동 증가 키
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, item.getItemName());
            ps.setInt(2, item.getPrice());
            ps.setInt(3, item.getQuantity());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);

        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item set item_name=?, price=?, quantity=? where id=?";
        template.update(sql,
                updateParam.getItemName(),
                updateParam.getPrice(),
                updateParam.getQuantity(),
                itemId);
    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = ?";
        try {
            Item item = template.queryForObject(sql, itemRowMapper(), id);
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        String sql = "select id, item_name, price, quantity from item";

        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        List<Object> param = new ArrayList<>();
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',?,'%')";
            param.add(itemName);
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= ?";
            param.add(maxPrice);
        }
        
        log.info("sql={}", sql);
        return template.query(sql, itemRowMapper(), param.toArray());
    }

    private RowMapper<Item> itemRowMapper() {
        return (((rs, rowNum) -> {
            Item item = new Item();
            item.setId(rs.getLong("id"));
            item.setItemName(rs.getString("item_name"));
            item.setPrice(rs.getInt("price"));
            item.setQuantity(rs.getInt("quantity"));
            return item;
        }));
    }
}
~~~
- this.template = new JdbcTemplate(dataSource)
  - JdbcTemplate는 데이터소스(dataSource)가 필요하
  - JdbcTemplateRepositortV1() 생성자에서 dataSoruce를 의존 관계 주입을 받고 생성자 내부에서 JdbcTemplate를 생성한다.
  - JdbcTemplate을 스프링 빈으로 직접 등록하고 주입 받아도 된다.

**save()** <br>
- `template.update()`: 데이터 변경 시 update() 사용
  - INSERT, UPDATE, DELETE SQL에 사용
  - int를 반환하는 데 영향 받은 로우 수 반환
- 데이터 저장 시 pk 생성에 identity(auto increment)방식을 사용했다. 그로 인해 pk인 ID값을 개발자가 정하는 것이 아니라 비워두고 저장해야 한다. 그러면 데이터베이스가 PK인 ID를 대신 생성해준다.
- 데이터베이스가 PK ID값을 대신 생성해주기 때문에 데이터베이스에 INSERT가 완료되어야 생성된 PK ID 값을 확인할 수 있다.
- `KeyHolder`와 `connection.prepareStatement(sql, new String[]{"id"})`를 사용해 id를 지정해주면 INSERT 쿼리 실행 이후에 데이터베이스에서 생성된 ID 값을 조회할 수 있다.

**update()** <br>
- `template.update()`: 데이터 변경 시 update() 사용
  - ? 에 바인딩할 파라미터를 순서대로 전달하면 된다.
  - 반환 값은 영향 받은 로우 수

**findById()** <br>
데이터 하나 조회
- template.queryForObject()
  - 결과 로우가 하나일 때 사용
  - RowMapper는 데이터베이스 반환 결과인 ResultSet을 객체로 변환
  - 결과가 없으면 `EmptyResultDataAccessException` 예외 발생
  - 결과가 둘 이상이면 `IncorrectResultDataAccessException` 예외 발생

**findAll()** <br>
데이터를 리스트로 조회, 검색 조건으로 적절한 데이터를 찾는다.
- template.query()
  - 결과가 하나 이상일 때 사용
  - RowMapper는 데이터베이스 반환 결과인 ResultSet을 객체로 변환
  - 결과가 없으면 빈 컬렉션을 반환

**itemRowMapper()** <br>
데이터베이스 조회 결과를 변환할 때 사용

## JdbcTemplate - 동적 쿼리 문제
findAll()에서 사용자가 검색하는 값에 따라 실행하는 SQL이 동적으로 달라져야 한다.
~~~
검색조건이 없음, 상품명으로 검색, 최대 가격으로 검색, 상품명, 최대가격으로 검색
~~~
4가지 상황에 따른 SQL을 동적으로 생성해야 한다. 

## JdbcTemplate 적용3 - 구성과 실행
~~~java
@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV1Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV1(dataSource);
    }
}
~~~
- `itemRepository` 구현체로 `JdbcTemplateItemRepositoryV1`이 사용되도록 했다. 

~~~java
//@Import(MemoryConfig.class)
@Import(JdbcTemplateV1Config.class)
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

}
~~~

**application.properties**
~~~
spring.profiles.active=local
spring.datasource.url=jdbc:h2:tcp://localhost/~/test
spring.datasource.username=sa
spring.datasource.password=
~~~
- 이렇게만 설정하면 스프링 부트가 해당 설정을 사용해 커넥션 풀과 DataSource, 트랜잭션 매니저를 스프링 빈으로 등록한다.

## JdbcTemplate - 이름 지정 파라미터 1
JdbcTemplate를 기본으로 사용하면 파라미터를 순서대로 바인딩한다. 

`NamedParameterJdbcTemplate`를 사용하면 이름을 지정해서 파라미터를 바인딩 할 수 있다.

~~~java
@Slf4j
public class JdbcTemplateItemRepositoryV2 implements ItemRepository {

    //    private final JdbcTemplate template;
    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateItemRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Item save(Item item) {
        String sql = "insert into item(item_name, price, quantity) " +
                "values (:itemName,:price,:quantity)";

        SqlParameterSource param = new BeanPropertySqlParameterSource(item);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        item.setId(key);

        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item " +
                "set item_name=:itemName, price=:price, quantity=:quantity " +
                "where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);

        template.update(sql, param);

    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try {

            Map<String, Object> param = Map.of("id", id);

            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";

        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);
        return template.query(sql, param, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class); // camel 지원
    }
}
~~~
- `NamedParameterJdbcTemplate`도 내부에 dataSource가 필요하다.
- 생성자를 보면 dataSource를 받고 내부에서 NamedParameterJdbcTemplate를 생성해서 가지고 있다.

**save()** <br>
~~~java
insert into item (item_name, price, quantity) " +
              "values (:itemName, :price, :quantity)"
~~~
SQL에서 ? 대신 `:파라미터이름`을 받고 있다.

## JdbcTemplate - 이름 지정 파라미터 2
### 이름 지정 파라미터
파라미터를 전달하려면 Map과 같이 key, value 데이터 구조를 만들어 전달해야 한다. key는 `:파라미터이름`으로 지정한 파라미터 이름, value는 해당 파라미터 값

이름 지정 바인딩에서 자주 사용하는 파라미터 종류
- Map
- SqlParameterSource
  - MapSqlParameterSource
  - BeanPropertySqlParameterSource

#### Map
~~~java
Map<String, Object> param = Map.of("id", id);
Item item = template.queryForObject(sql, param, itemRowMapper());


~~~

#### MapSqlParameterSource
SQL 타입을 지정할 수 있는 등 SQL에 좀 더 특화된 기능 제공한다. `SqlParameterSource` 인터페이스의 구현체이다.

~~~java
SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);
template.update(sql, param);

~~~

#### BeanPropertySqlParameterSource
`SqlParameterSource` 인터페이스의 구현체로 자바빈 프로퍼티 규약을 통해 자동으로 파라미터 객체 생성
~~~
getXXX -> xxx, getItemName() -> itemName
~~~
예를 들어 `getItemName(), getPrice()`가 있다했을 때,
- key=itemName, value=상품명 값
- key=price, value=가격 값
처럼 데이터가 자동으로 만들어진다.
~~~java
SqlParameterSource param = new BeanPropertySqlParameterSource(item);
KeyHolder keyHolder = new GeneratedKeyHolder();
template.update(sql, param, keyHolder);
~~~

~~~java
SqlParameterSource param = new BeanPropertySqlParameterSource(cond);
~~~

### BeanPropertyRowMapper
~~~java
private RowMapper<Item> itemRowMapper() {
    return (((rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getLong("id"));
        item.setItemName(rs.getString("item_name"));
        item.setPrice(rs.getInt("price"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    }));
}
~~~
위 코드가
~~~java
private RowMapper<Item> itemRowMapper() {
    return BeanPropertyRowMapper.newInstance(Item.class); // camel 지원
}
~~~
이 처럼 변경되었다.

`BeanPropertyRowMapper`는 ResultSet의 결과를 받아 자바빈 규약에 맞춰 데이터를 변환한다.<br>
예를 들어, 조회 결과가 `select id, price` 라고 한다면
~~~java
Item item = new Item();
item.setId(rs.getLong("id"));
item.setPrice(rs.getInt("price"));
~~~
이와 같은 코드를 작성해준다.

그런데 `select item_name`의 경우 `setItem_name()이라는 메서드가 없기 때문에 곤란하다. 이때 SQL문에 이와 같이 별칭을 주어 해결할 수 있다.
~~~sql
select item_name as itemName
~~~

데이터베이스 컬럼 이름과 객체 이름이 다를 때 이를 이용해서 문제를 해결할 수 있다.

그리고 자바 객체는 카멜(camelCase) 표기법을 사용하고, 데이터베이스에서는 언더스코어를 사용하는 snake_case 표기법을 주로 사용한다.

이런 관례로 인해서 `BeanPropertyRowMapper`는 언더스코어 표기법을 카멜로 자동으로 변환해준다.
`select item_name` 으로 조회하더라도 `setItemName()`에 문제 없이 값이 들어간다.

snake_case 문제는 자동으로 해결되니 객체 이름이 다른 경우에 SQL문에 별칭을 사용하자.

## JdbcTemplate - 이름 지정 파라미터 3
~~~java
@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV2Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV2(dataSource);
    }
}
~~~
~~~java
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
@Import(JdbcTemplateV2Config.class)
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

}
~~~

## JdbcTemplate - SimpleJdbcInsert
JdbcTemplate는 INSERT SQL를 작성하지 않아도 되도록 `SimpleJdbcInsert`라는 편의 기능을 제공한다.

~~~java

@Slf4j
public class JdbcTemplateItemRepositoryV3 implements ItemRepository {

    private final NamedParameterJdbcTemplate template;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateItemRepositoryV3(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
//                .usingColumns("item_name", "price", "quantity"); // 생략 가능
    }

    @Override
    public Item save(Item item) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        Number key = jdbcInsert.executeAndReturnKey(param);

        item.setId(key.longValue());
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        String sql = "update item " +
                "set item_name=:itemName, price=:price, quantity=:quantity " +
                "where id=:id";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("itemName", updateParam.getItemName())
                .addValue("price", updateParam.getPrice())
                .addValue("quantity", updateParam.getQuantity())
                .addValue("id", itemId);

        template.update(sql, param);

    }

    @Override
    public Optional<Item> findById(Long id) {
        String sql = "select id, item_name, price, quantity from item where id = :id";
        try {

            Map<String, Object> param = Map.of("id", id);

            Item item = template.queryForObject(sql, param, itemRowMapper());
            return Optional.of(item);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Item> findAll(ItemSearchCond cond) {

        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        SqlParameterSource param = new BeanPropertySqlParameterSource(cond);

        String sql = "select id, item_name, price, quantity from item";

        // 동적 쿼리
        if (StringUtils.hasText(itemName) || maxPrice != null) {
            sql += " where";
        }
        boolean andFlag = false;
        if (StringUtils.hasText(itemName)) {
            sql += " item_name like concat('%',:itemName,'%')";
            andFlag = true;
        }

        if (maxPrice != null) {
            if (andFlag) {
                sql += " and";
            }
            sql += " price <= :maxPrice";
        }

        log.info("sql={}", sql);
        return template.query(sql, param, itemRowMapper());
    }

    private RowMapper<Item> itemRowMapper() {
        return BeanPropertyRowMapper.newInstance(Item.class); // camel 지원
    }
}
~~~

- `this.jdbcInsert = new SimpleJdbcInsert(dataSource)`: 생성자에서 의존관계 주입은 dataSource를 주입 받고 내부에서 `SimpleJdbcInsert`를 생성해서 가지고 있다.

~~~java
public JdbcTemplateItemRepositoryV3(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("item")
                .usingGeneratedKeyColumns("id");
//                .usingColumns("item_name", "price", "quantity"); // 생략 가능
    }
~~~
- withTableName: 데이터를 저장할 테이블 명
- usingGeneratedKeyColumns: key를 생성하는 PK 컬럼명 지정
- usingColumns: INSERT SQL에 사용할 컬럼을 지정, 특정한 값만 저장하고 싶을 때 사용한다. 생략 가능 

`SimpleJdbcInsert`는 생성 시점에 데이터베이스 테이블의 메타 데이터를 조회한다. 따라서 어떤 컬럼이 있는지 확인을 할 수 있으므로 usingColumns를 생략할 수 있다.

~~~java
@Override
public Item save(Item item) {
    SqlParameterSource param = new BeanPropertySqlParameterSource(item);
    Number key = jdbcInsert.executeAndReturnKey(param);

    item.setId(key.longValue());
    return item;
}
~~~
`jdbcInsert.executeAndReturnKey(param)`를 사용해 INSERT SQL을 실행하고 생성된 키 값도 편리하게 조회가 가능하다.

~~~java
@Configuration
@RequiredArgsConstructor
public class JdbcTemplateV3Config {

    private final DataSource dataSource;

    @Bean
    public ItemService itemService() {
        return new ItemServiceV1(itemRepository());
    }

    @Bean
    public ItemRepository itemRepository() {
        return new JdbcTemplateItemRepositoryV3(dataSource);
    }
}
~~~

~~~java
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

}
~~~

## JdbcTemplate 사용법 정리
https://docs.spring.io/spring-framework/reference/data-access/jdbc/core.html#jdbc-JdbcTemplate