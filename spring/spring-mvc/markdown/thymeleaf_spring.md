# 타임리프 - 스프링 통합

- [기본 매뉴얼](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- [스프링 통합 매뉴얼](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)

스프링 부트를 사용하기 전에는 타임리프 템플릿 엔진과 타임리프용 뷰 리졸버를 스프링 빈으로 등록해야 한다.

스프링 부트는 `	implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'` build.gradle에 추가하면 타임리프와 관련된 라이브러리를 다운받고 스프링 빈을 자동으로 등록해준다.

## 입력 폼 수정

### 상품 추가 폼
~~~java
@GetMapping("/add")
public String addForm(Model model) {
    model.addAttribute("item", new Item());

    return "form/addForm";
}
~~~
~~~html
<form action="item.html" th:action th:object="${item}" method="post">
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" th:field="*{itemName}" class="form-control" placeholder="이름을 입력하세요">
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price" th:field="*{price}" class="form-control" placeholder="가격을 입력하세요">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" th:field="*{quantity}" class="form-control" placeholder="수량을 입력하세요">
        </div>

        <hr class="my-4">

        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit">상품 등록</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='items.html'"
                        th:onclick="|location.href='@{/form/items}'|"
                        type="button">취소</button>
            </div>
        </div>

    </form>
~~~
- `th:object="${item}"`: `<form></form>`에서 사용할 객체 지정한다. 선택 변수 식 `*{...}` 사용 가능
- `th.field=*{itemName}`
  - `th.field`: `id`, `name`, `value` 속성을 자동으로 만들어준다.
  - `*{itemName}`: `th:object="${item}"`로 지정한 객체(여기선 item)의 `itemName`에 접근한다.
    - `id="itemName"`
    - `name="itemName"`
    - `value=""`

### 상품 수정 폼
~~~java
@GetMapping("/{itemId}/edit")
public String editForm(@PathVariable Long itemId, Model model) {
    Item item = itemRepository.findById(itemId);
    model.addAttribute("item", item);
    return "form/editForm";
}
~~~
~~~html
<form action="item.html" th:action th:object="${item}" method="post">
        <div>
            <label for="id">상품 ID</label>
            <input type="text" id="id" class="form-control" th:field="*{id}" readonly>
        </div>
        <div>
            <label for="itemName">상품명</label>
            <input type="text" id="itemName" class="form-control" th:field="*{itemName}">
        </div>
        <div>
            <label for="price">가격</label>
            <input type="text" id="price" class="form-control" th:field="*{price}">
        </div>
        <div>
            <label for="quantity">수량</label>
            <input type="text" id="quantity" class="form-control" th:field="*{quantity}">
        </div>

        <hr class="my-4">

        <div class="row">
            <div class="col">
                <button class="w-100 btn btn-primary btn-lg" type="submit">저장</button>
            </div>
            <div class="col">
                <button class="w-100 btn btn-secondary btn-lg"
                        onclick="location.href='item.html'"
                        th:onclick="|location.href='@{/form/items/{itemId}(itemId=${item.id})}'|"
                        type="button">취소</button>
            </div>
        </div>

    </form>
~~~
- 상품 수정 폼에서는 `id`, `name`, `value`를 신경써야 했었는데 `th:object`, `th:field` 덕에 신경 쓸 부분이 줄어들었다.

## 체크박스 - 단일 1
~~~Html
<form action="item.html" th:action th:object="${item}" method="post">
<div>판매 여부</div>
<div>
    <div class="form-check">
        <input type="checkbox" id="open" name="open" class="form-check-input">
        <label for="open" class="form-check-label">판매 오픈</label>
    </div>
</div>
</form>
~~~
- 체크박스 체크 시 HTML Form에서 `open=on`이라는 값이 넘어간다. 스프링은 `on` 이라는 문자를 `true` 타입으로 변경해준다.
- 하지만 체크박스를 체크하지 않았을 때는 `open`이라는 필드 자체가 서버로 전송되지 않는다.

~~~html
<div>판매 여부</div>
<div>
    <div class="form-check">
        <input type="checkbox" id="open" name="open" class="form-check-input">
        <input type="hidden" name="_open" value="on"/>
        <label for="open" class="form-check-label">판매 오픈</label>
    </div>
</div>
~~~
스프링에서는 `_open` 처럼 체크박스 name 앞에 `_` 를 붙여서 전송하면 체크를 해제했다고 인식할 수 있다. hidden 필드는 항상 전송된다. 즉, 체크를 안했을 때는 `_open`만 전송되고 스프링 MVC는 이를 체크가 되지 않았다라고 판단한다.

- 체크 시 : `open`에 값이 있으므로 이를 사용해서 `true`
- 미체크시 : `_open`만 있으므로 미체크로 인식 -> `false`

## 체크박스 - 단일 2
~~~html
<div>판매 여부</div>
<div>
    <div class="form-check">
        <input type="checkbox" id="open" th:field="*{open}" class="form-check-input">
        <label for="open" class="form-check-label">판매 오픈</label>
    </div>
</div>
~~~
- input에 `th:field`를 추가하면 히든 필드와 관련된 부분도 같이 해결해줌

## 체크박스 - 다중
~~~java
@ModelAttribute("regions")
public Map<String, String> regions() {
    Map<String, String> regions = new LinkedHashMap<>();
    regions.put("SEOUL", "서울");
    regions.put("BUSAN", "부산");
    regions.put("DAEGU", "대구");
    return regions;
}
~~~
- 상품 등록폼, 상세화면, 수정 폼에서 이 지역을 선택하는 체크박스들을 보여줘야한다.
- 각 컨트롤러에서 `model.attribute()를 해줘야한다.
- 하지만 반복된 코드들이 생기게 된다.
- `@ModelAttribute`는 별도의 메서드에 적용이 가능하다
- 컨트롤러 요청 시 `regions`에 반환된 값이 자동으로 모델에 담기게 된다.

~~~html
<div>
  <div>등록 지역</div>
  <div th:each="region : ${regions}" class="form-check form-check-inline">
      <input type="checkbox" th:field="*{regions}" th:value="${region.key}"
              class="form-check-input">
      <label th:for="${#ids.prev('regions')}"
              th:text="${region.value}" class="form-check-label">서울</label>
  </div>
</div>
~~~
- 반복을 이용해 태그를 생성해주려면 `id`를 모두 다르게 해줘야한다.
- 타임리프는 체크박스를 `each` 안에서 반복해서 만들 때 임의로 1,2,3 숫자를 붙여준다.
- 체크박스의 `id`가 동적으로 생성되므로 `label`의 `id`값을 임의로 지정하기도 어렵다.
- 타임리프는 `th:for="${#ids.prev('regions')}"`를 제공해서 동적으로 생성되는 id 값을 사용할 수 있도록 한다.

## 라디오 박스
~~~java
public enum ItemType {

    BOOK("도서"), FOOD("식품"), ETC("기타");

    private final String description;
    ItemType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
~~~
~~~java
@ModelAttribute("itemTypes")
public ItemType[] itemTypes() {
    return ItemType.values();
}
~~~
-ItemType은 ENUM을 사용하였다
-`ItemType.values()` 사용 시 ENUM의 모든 정보를 배열로 반환

~~~html
<div>
    <div>상품 종류</div>
    <div th:each="type : ${itemTypes}" class="form-check form-check-inline">
        <input type="radio" th:field="*{itemType}" th:value="${type.name()}"
                class="form-check-input">
        <label th:for="${#ids.prev('itemType')}" th:text="${type.description}"
                class="form-check-label">
            BOOK
        </label>
    </div>
</div>
~~~

- 라디오 버튼은 미선택시 null이 넘어감
- 이미 선택 되있다면 항상 하나는 선택해야하므로 체크박스때 처럼 히든필드가 필요없다

## 셀렉트 박스
~~~java
@Data
@AllArgsConstructor
public class DeliveryCode {

    private String code;
    private String displayName;
}
~~~

~~~java
@ModelAttribute("deliveryCodes")
public List<DeliveryCode> deliveryCodes() {
    List<DeliveryCode> deliveryCodes = new ArrayList<>();
    deliveryCodes.add(new DeliveryCode("FAST", "빠른배송"));
    deliveryCodes.add(new DeliveryCode("NORMAL", "일반배송"));
    deliveryCodes.add(new DeliveryCode("SLOW", "느린배송"));
    return deliveryCodes;
}
~~~
- 위 코드는 컨트롤러 호출때마다 계속 호출된다.
- 이런 부분은 미리 생성해두고 재사용하는 것이 좋다.

~~~html
<div>
    <div>배송 방식</div>
    <select th:field="*{deliveryCode}" class="form-select">
        <option value="">==배송 방식 선택==</option>
        <option th:each="deliveryCode : ${deliveryCodes}" th:value="${deliveryCode.code}"
                th:text="${deliveryCode.displayName}">FAST</option>
    </select>
</div>
~~~