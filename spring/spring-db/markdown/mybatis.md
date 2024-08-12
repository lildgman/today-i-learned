# 데이터 접근 기술 - MyBatis

## MyBatis 소개
MyBatis는 JdbcTemplate보다 더 많은 기능을 제공하는 SQL Mapper 이다.

가장 매력적인 부분은 SQL을 XML에 편리하게 작성 가능하고 동적 쿼리를 매우 편리하게 작성할 수 있다.

## MyBatis 설정
의존성 추가
~~~
implementation 'org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3'
~~~

`application.properties`에 추가 <br>
`main`과 `test`각 위치에 있는 파일 모두 수정

~~~
mybatis.type-aliases-package=hello.itemservice.domain
mybatis.configuration.map-underscore-to-camel-case=true
logging.level.hello.itemservice.repository.mybatis=trace
~~~
- `mybatis.type-aliases-package`
  - 마이바티스에서 타입 정보 사용 시 패키지 이름을 적어주어야 한다. 여기에 명시하게되면 패키지 이름을 생략할 수 있다.
  - 지정한 패키지와 그 하위 패키지가 자동으로 인식된다.
  - `,`나 `;`로 구분
- `map-underscore-to-camel-case`
  - 언더바를 카멜로 자동 변경해주는 기능을 활성화

## MyBatis 적용1 - 기본
XML에 SQL을 작성한다는 점을 제외하고는 JDBC 반복을 줄여준다는 점에서 JdbcTemplate와 거의 유사하다

~~~java
@Mapper
public interface ItemMapper {

    void save(Item item);

    void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);

    Optional<Item> findById(Long id);

    List<Item> findAll(ItemSearchCond itemSearchCond);

}
~~~
- 마이바티스 매핑 XML을 호출해주는 매퍼 인터페이스이다.
- 이 인터페이스에는 `@Mapper`를 붙여주어야한다. 그래야 MyBatis가 인식을 할 수 있다.
- 이 인터페이스의 메서드를 호출하면 xml의 해당 SQL을 실행하고 결과를 돌려준다.

같은 위치에 실행할 SQL이 있는 XML 매핑 파일을 만들어주자.<br>
자바 코드가 아니기 때문에 `src/main/resources` 하위에 만들되 패키지 위치는 맞추어 주어야 한다.

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="hello.itemservice.repository.mybatis.ItemMapper">

    <insert id="save" useGeneratedKeys="true" keyProperty="id">
        insert into item(item_name, price, quantity)
        values(#{itemName}, #{price}, #{quantity})
    </insert>

    <update id="update">
        update item
        set item_name=#{updateParam.itemName},
            price=#{updateParam.price},
            quantity=#{updateParam.quantity}
        where id = #{id}
    </update>

    <select id="findById" resultType="Item">
        select id, item_name, price, quantity
        from item
        where id = #{id}
    </select>

    <select id="findAll" resultType="Item">
        select id, item_name, price, quantity
        from item
        <where>
            <if test="itemName != null and itemName != ' '">
                and item_name like concat('%', #{itemName},'%')
            </if>
            <if test="maxPrice != null">
                and price $lt;= #{maxPrice}
            </if>
        </where>
    </select>

</mapper>
~~~
- `namespace`: 매퍼 인터페이스를 지정하면 된다.
  - 경로와 파일 이름 주의!

**XML 파일 경로 수정하기** <br>
XML파일을 원하는 위치에 두고 싶다면 `application.properties`에 다음과 같이 설정하자.
`mybatis.mapper-locations=classpath:mapper/**/*.xml`

이렇게 하면 `resources/mapper`를 포함한 그 하위 폴더에 있는 XML을 XML 매핑 파일로 인식한다.

### insert - save
~~~java
void save(Item item);
~~~
~~~xml
<insert id="save" useGeneratedKeys="true" keyProperty="id">
    insert into item(item_name, price, quantity)
    values(#{itemName}, #{price}, #{quantity})
</insert>
~~~
- `<insert>`사용
- id에는 매퍼 인터페이스에 설정한 `메서드 이름`을 지정하면 된다. 
- 파라미터는 `#{}` 문법을 사용한다. 매퍼에서 넘긴 객체의 프로퍼티 이름을 적어주면 된다.
- `#{}` 사용 시 `PreparedStatement`를 사용한다. JDBC의 ?를 치환한다 생각하자.
- `useGeneratedKeys`: 데이터베이스가 키를 생성해주는 IDENTITY 전략일 때 사용, `keyProperty`는 생성되는 키의 속성 이름을 지정한다. Insert가 끝나면 item 객체의 id 속성에 생성된 값이 입력된다.

### update - update
~~~java
void update(@Param("id") Long id, @Param("updateParam") ItemUpdateDto updateParam);
~~~
~~~xml
<update id="update">
    update item
    set item_name=#{updateParam.itemName},
        price=#{updateParam.price},
        quantity=#{updateParam.quantity}
    where id = #{id}
</update>
~~~
- `<update>` 사용
- 여기서는 파라미터가 `Long id`, `ItemUpdateDto updateParam` 두 개가 있다. 파라미터가 1개만 있으면 `@Param`을 지정하지 않아도 되지만 파라미터가 2개 이상이면 `@Param`으로 이름을 지정해서 파라미터를 구분해야 한다.

### select - findById
~~~java
Optional<Item> findById(Long id);
~~~
~~~xml
<select id="findById" resultType="Item">
    select id, item_name, price, quantity
    from item
    where id = #{id}
</select>
~~~
- `<select>` 사용
- `resultType`: 반환 타입을 명시하면 된다.
  - `application.properties`에 `mybatis.type-aliases-package=hello.itemservice.domain` 속성을 지정해줬기 때문에 모든 패키지를 적어주지 않아도 됐다.
  - JdbcTemplate의 `BeanPropertyRowMapper`처럼 `SELECT` SQL의 결과를 편리하게 객체로 바로 변환해준다.
  - `mybatis.configuration.map-underscore-to-camel-case=true` 속성을 지정해준 덕분에 카멜 표기법으로 자동으로 처리해준다.
- 반환 객체가 하나면 `Item`, `Optional<Item>` 처럼 사용하면 되고 하나 이상이면 컬렉션을 사용하면 된다. `List`

### select - findAll
~~~java
List<Item> findAll(ItemSearchCond itemSearchCond);
~~~
~~~xml
<select id="findAll" resultType="Item">
    select id, item_name, price, quantity
    from item
    <where>
        <if test="itemName != null and itemName != ' '">
            and item_name like concat('%', #{itemName},'%')
        </if>
        <if test="maxPrice != null">
            and price $lt;= #{maxPrice}
        </if>
    </where>
</select>
~~~
- 마이바티스는 `<where>`, `<if>` 같은 동적 쿼리 문법을 통해 편리한 동적 쿼리를 지원한다.
- `<if>`는 해당 조건이 만족했을 때 구문을 추가한다.
- `<where>`는 적절하게 where문장을 만들어준다.
  - 위에서 `if`가 모두 실패하게되면 SQL WHERE을 만들지 않는다.
  - 하나라도 성공한 경우 처음 나타나는 and를 where로 변환해준다.