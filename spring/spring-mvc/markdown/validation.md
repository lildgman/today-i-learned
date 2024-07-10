# 검증
- 타입 검증
  - 가격, 수량에 문자가 들어가면 검증 오류 처리 필드 검증
- 상품명: 필수, 공백X
  - 가격: 1000원 이상, 1백만원 이하 수량: 최대 9999
- 특정 필드의 범위를 넘어서는 검증
  - 가격 * 수량의 합은 10,000원 이상

## 검증 직접 처리
- 사용자가 상품 등록을 범위 내의 데이터를 입력하면 서버에서 검증 로직이 통과되고 상품을 저장하고 상품 상세 화면으로 리다이렉트한다.
- 사용자가 범위 밖의 데이터를 가지고 상품 등록을 하려고 할 때 검증 로직이 실패해야 한다. 검증이 실패한 경우 다시 상품 등록 폼이 보여지고 어떤 값이 잘못됐는지 알려줘야 한다.

~~~java
@PostMapping("/add")
public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes, Model model) {

    // 검증 오류 결과를 보관
    Map<String, String> errors = new HashMap<>();

    // 검증 로직
    if (!StringUtils.hasText(item.getItemName())) {
        errors.put("itemName", "상품 이름은 필수입니다.");
    }

    if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 10000000) {
        errors.put("price", "가격은 1,000원에서 ~ 10,000,000원까지 허용합니다.");
    }

    if (item.getQuantity() == null || item.getQuantity() >= 999) {
        errors.put("quantity", "수량은 최대 999개 까지 허용합니다");
    }

    // 특정 필드가 아닌 복합 룰 검증
    if (item.getPrice() != null && item.getQuantity() != null) {
        int resultPrice = item.getPrice() * item.getQuantity();
        if (resultPrice < 10000) {
            errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice);
        }
    }

    // 검증에 실패 시 다시 입력 폼으로 이동
    if (!errors.isEmpty()) {
        log.info("errors={}", errors);
        model.addAttribute("errors", errors);
        return "validation/v1/addForm";
    }

    // 성공 로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v1/items/{itemId}";
}
~~~
- `Map<String, String> errors = new HashMap<>()`: 검증 오류 시 어떤 검증에서 오류가 났는지 정보를 담아준다.
  - 검증 오류 시 `errors`에 담아둔다. 어떤 오류가 발생했는지 구분하기 위해 오류가 발생한 필드값을 `key`로 사용한다.
  - 필드값이 없는 경우에는 `globalError`라는 `key`를 사용한다.

~~~java
// 검증에 실패 시 다시 입력 폼으로 이동
if (!errors.isEmpty()) {
    log.info("errors={}", errors);
    model.addAttribute("errors", errors);
    return "validation/v1/addForm";
}
~~~
- `errors`에 오류가 하나라도 있으면 `model`에 `errors`를 담고 입력 폼으로 보낸다.

~~~html
<div th:if="${errors?.containsKey('globalError')}">
    <p class="field-error" th:text="${errors['globalError']}">전체 오류 메시지</p>
</div>

<label for="itemName" th:text="#{label.item.itemName}">상품명</label>
    <input type="text" id="itemName" th:field="*{itemName}"
            th:classappend="${errors?.containsKey('itemName')} ? 'field-error' : _"
            class="form-control" placeholder="이름을 입력하세요">
    <div class="field-error" th:if="${errors?.containsKey('itemName')}">
        <p class="field-error" th:text="${errors['itemName']}">상품명 오류 메시지</p>
    </div>
~~~
오류 메시지는 `errors`에 내용이 있을 때만 출력한다.

여기서 `errors`가 null 일 경우 (상품 등록 폼 진입 시점) `NullPointException`이 발생한다. `errors?.`는 `errors`가 null 일 때 `NullPointException`이 발생하지 않고 null을 반환하는 문법이다.

## BindingResult1
~~~java
@PostMapping("/add")
public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult,RedirectAttributes redirectAttributes, Model model) {

    // 검증 로직
    if (!StringUtils.hasText(item.getItemName())) {
        bindingResult.addError(new FieldError("item", "itemName","상품이름은 필수입니다."));
    }

    if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
        bindingResult.addError(new FieldError("item", "price","가격은 1,000원 ~ 1,000,000원까지 허용합니다.."));
    }

    if (item.getQuantity() == null || item.getQuantity() >= 9999) {
        bindingResult.addError(new FieldError("item", "quantity","수량은 최대 9,999개 까지 허용합니다"));

    }

    // 특정 필드가 아닌 복합 룰 검증
    if (item.getPrice() != null && item.getQuantity() != null) {
        int resultPrice = item.getPrice() * item.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice));
        }
    }

    // 검증에 실패 시 다시 입력 폼으로 이동
    if (bindingResult.hasErrors()) {
        log.info("bindingResult={}", bindingResult);
        return "validation/v2/addForm";
    }

    // 성공 로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v2/items/{itemId}";
}
~~~

- 반드시 `@ModelAttribute Item item` 뒤에 `BindingResult bindingResult`가 와야함

### 필드 오류
- `new FieldError("item", "itemName","상품이름은 필수입니다.")`
  - 첫번째 인자: `@ModelAttribute` 의 이름
  - 두번째 인자: 오류가 발생한 필드
  - 세번째 인자: 오류 기본 메시지

### 글로벌 오류
- `new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재 값 = " + resultPrice)`
- 특정 필드를 넘어서는 오류가 있을 때 `ObjectError`
를 사용하면 된다.
  - 첫번째 인자: `@ModelAttribute` 의 이름
  - 두번째 인자: 오류 기본 메시지

~~~html
<div th:if="${#fields.hasGlobalErrors()}">
    <p class="field-error" th:each="err : ${#fields.globalErrors()}" th:text="${err}">전체 오류 메시지</p>
</div>

<div>
    <label for="itemName" th:text="#{label.item.itemName}">상품명</label>
    <input type="text" id="itemName" th:field="*{itemName}"
            th:errorclass="field-error"
            class="form-control" placeholder="이름을 입력하세요">
    <div class="field-error" th:errors="*{itemName}">
        상품명 오류 메시지
    </div>

</div>
~~~
타임리프에서는 BindingResult를 활용해 검증 오류를 표현하는 기능 제공
  - `#fields`: BindingResult가 제공하는 검증 오류에 접근 가능
  - `th:errors`: 해당 필드에 오류가 있는 경우 태그 출력
  - `th:errorclass`: `th:field`에서 지정한 필드에 오류가 있으면 class 정보 추가

## BindingResult2
BindingResult
- 스프링이 제공하는 검증 로류를 보관하는 객체
- BindingResult가 있으면 @ModelAttribute에 데이터 바인딩 시 오류가 발생해도 컨트롤러가 호출됨
  - BindingResult가 없을 때 -> 400오류 발생, 컨트롤러 호출x, 오류 페이지로 이동
  - BindingResult가 있을 때 -> 오류정보를 BindingResult에 담아 컨트롤러 정상 호출

### BindingResult에 검증 오류를 적용하는 방법
- @ModelAttribute의 객체에 타입 오류 등으로 바인딩이 실패하는 경우 `FieldError` 생성해서 BindingResult에 넣어준다
- 개발자가 직접 넣어준다
- `Validator` 사용한다.

### BindingResult와 Errors
`BindingResult`는 인터페이스이고 `Errors` 인터페이스를 상속받고 있다.
- 실제 넘어오는 구현체는 `BeanPropertyBindingResult`인데 둘 다 구현하고 있음
- `Errors`는 단순한 오류 저장과 조회 기능 제공
- `BindingResult`는 추가기능 더 제공

## FieldError, ObjectError
### FieldError
~~~java
public FieldError(String objectName, String field, String defaultMessage);

public FieldError(String objectName, String field, @Nullable Object
rejectedValue, boolean bindingFailure, @Nullable String[] codes, @Nullable
Object[] arguments, @Nullable String defaultMessage)
~~~
파라미터 목록
- `field`: 오류 필드
- `rejectedValue`: 사용자가 입력한 값(거절된 값)
- `bindingFailure`: 타입 오류 같은 바인딩 실패인지, 검증 실패인지 구분 값
- `codes`: 메시지 코드
- `arguments`: 메시지에서 사용하는 인자
- `defaultMessage`: 기본 오류 메시지

`ObjectError`도 유사하게 두 가지 생성자를 제공

~~~java
new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품이름은 필수입니다.")
~~~
- 사용자의 입력 데이터가 컨트롤러의 `@ModelAttribute`에 바인딩되는 시점에 오류가 발생하면 모델 객체에 사용자 입력 값을 유지하기 어렵다.
- 가격에 숫자가 아닌 문자가 입력됐을 때 가격은 Integer라 문자를 보관할 수가 없다. 그래서 오류 발생 시 사용자 입력 값을 보관하는 방법이 필요하다.
- `FieldError`는 오류 발생 시 사용자 입력 값을 저장하는 기능을 제공한다.

- `rejectedValue`가 바로 오류 발생 시 사용자 입력값을 저장하는 필드이다.

타임리프의 사용자 입력 값 유지
`th:field="*{price}"`: 정상 상황에는 모델 객체의 값을 사용, 오류 발생시 `FieldError`에서 보관한 값을 사용해 값을 출력

스프링의 바인딩 오류 처리
타입 오류로 바인딩에 실패하면 스프링은 `FieldError`를 생성하고 사용자가 입력한 값을 넣어둔다. 그 후 해당 오류를 `BindingResult`에 담아 컨트롤러를 호출한다. 

## 오류 코드와 메시지 처리1
`FieldError, ObjectError` 생성자는 `codes`, `arguments`를 제공하는데 오류코드로 메시지를 찾기 위해 사용한다.

오류메시지를 관리할 수 있도록 `errors.properties`를 만들어서 관리하도록 하자. 스프링 부트가 이 파일을 인식할 수 있게 `application.properties` 에 있는
`spring.messages.basename`에 `errors`를 추가하자.

~~~java
// range.item.price=가격은 {0} ~ {1} 까지 허용합니다.
new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null)
~~~
- `codes`: `range.item.price`를 사용해 메시지 코드를 지정, 배열로 여러값을 전달할 수 있는데 순서대로 매칭해서 처음 매칭되는 메시지가 사용된다.
- `arguments`: `Object[]{1000,1000000}`를 사용해 {0}, {1}로 치환할 값을 전달한다.

## 오류 코드와 메시지 처리 2
`BindingResult`는 검증해야 할 객체 `target` 바로 다음에 온다. `BingdingResult`는 검증해야 할 객체인 `target`을 알고 있다.

### rejectValue(), reject()
- `rejectValue()`
~~~java
void rejectValue(@Nullable String field, String errorCode,
 @Nullable Object[] errorArgs, @Nullable String defaultMessage);
~~~
~~~java
bindingResult.rejectValue("price", "range",new Object[]{1000,1000000}, null);
~~~
- `field`: 오류 필드명
- `errorCode`: 오류 코드(메시지에 등록된 코드 아님)
- `errorArgs`: 오류 메시지에서 {0} 을 치환하기 위한 값
- `defaultMessage`: 오류 메시지를 찾을 수 없을 때 사용하는 기본 메시지

- `reject()`
~~~java
void reject(String errorCode, @Nullable Object[] errorArgs, @Nullable String
 defaultMessage);
~~~
~~~java
bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null );
~~~

## 오류 코드와 메시지 처리 3
오류코드를 단순하게 만들게 되면 범용성이 좋아져 여러곳에서 사용할 수 있지만 세밀하게 메시지를 작성하기 어렵다. 반대로 메시지를 세밀하게 작성했을 때는 범용성이 떨어진다. 

가장 좋은방법은 범용성으로 사용하다 세밀하게 사용해야할 때에는 세밀한 내용이 적용되도록 메시지에 단계를 두는 것이다.

~~~
#Level1
required.item.itemName: 상품 이름은 필수 입니다.

#Level2
required: 필수 값 입니다. 
~~~
위 처럼 `required`라는 메시지만 있을 때는 이 메시지를 선택해서 사용하고, `required.item.itemName`처럼 객체명과 필드명을 조합한 세밀한 코드가 있으면 이 메시지를 더 높은 우선순위로 둔다.

## 오류 코드와 메시지 처리 4
스프링은 `MessageCodesResolver`라는 것을 지원한다.

~~~java
public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item");
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required", "item", "itemName", String.class);
        assertThat(messageCodes).containsExactly(
                "required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required"
        );
    }
}
~~~
### MessageCodesResolver
- 검증 오류 코드로 메시지 코드를 생성
- `MessageCodesResolver`는 인터페이스, `DefaultMessageCodesResolver`는 기본 구현체
- 주로 `ObjectError`, `FieldError`와 함께 사용

DefaultMessageCodesResolver 기본 메시지 생성 규칙

#### 객체 오류
~~~
다음 순서로 2가지 생성
1.: code + "." + object name
2.: code

ex) 오류코드: required, object name: item
1.: required.item
2.: required
~~~

#### 필드오류
~~~
다음 순서로 4가지 메시지 코드 생성
1.: code + "." + object name + "." + field
2.: code + "." + field
3.: code + "." + field type
4.: code

ex) 오류코드: type, object name: "user", filed: "age", field type: int

1.: "type.user.age"
2.: "type.age"
3.: "type.int"
4.: "type"
~~~

동작 방식
- `rejectValue()`, `reject()`는 내부에서 `MessageCodesResolver`를 사용
- `FieldError`, `ObjectError`는 오류 코드를 여러개 가질 수 있다. `MessageCodesResolver`를 통해 생성된 순서대로 오류 코드를 보관

`rejectValue("itemName", "required")` <br>
4가지 오류 코드를 자동 생성
- `required.item.itemName`
- `required.itemName`
- `required.java.lang.String`
- `required`

`reject("totalPriceMin")`<br>
2가지 오류 코드 자동으로 생성
- `totalPriceMin.item`
- `totalPriceMin`

타임리프 화면 렌더링 시 `th:errors` 실행, 이때 오류가 있으면 생성된 오류 메시지 코드를 순서대로 돌아가면서 찾고 없으면 디폴트 메시지를 출력한다.

## 오류 코드와 메시지 처리 5

### 오류 코드 관리 전략
구체적인 것에서 덜 구체적인 것으로

`MessageCodesResolver`는 `required.item.itemName`처럼 구체적인 것을 먼저 만들어주고 `required`처럼 덜 구체적인 것을 나중에 만든다.

1. `rejectValue()` 호출
2. `MessageCodesResolver`를 사용해 검증 오류 코드로 메시지 코드 생성
3. `new FieldError()` 생성하면서 메시지 코드 보관
4. `th:errors`에서 메시지 코드들로 메시지를 순서대로 찾고 보여줌

## 오류 코드와 메시지 처리 6
### 스프링이 직접 만든 오류 메시지 처리
검증 오류 코드는 2가지로 나눌 수 있다.
- 개발자가 직접 설정
- 스프링이 직접 검증 오류에 추가(주로 타입 불일치)

타입 검증오류 발생 시 `BindingResult`안에`FieldError()`가 담겨있고 메시지 코드들이 생성된다.

- `typeMismatch.item.price` 
- `typeMismatch.price` 
- `typeMismatch.java.lang.Integer` 
- `typeMismatch`

## Validator 분리 1
~~~java
@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return Item.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        Item item = (Item) o;

        // 검증 로직
        if (!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }

        if (item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range",new Object[]{1000,1000000}, null);
        }

        if (item.getQuantity() == null || item.getQuantity() >= 9999) {
            errors.rejectValue("quantity","max", new Object[]{9999}, null);
        }

        // 특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null );
            }
        }

    }
}
~~~
- `supports()`: 해당 검증기를 지원하는 여부 확인
- `validate(Object target, Errors errors)`: 검증 대상 객체와 BindingResult

## Validator 분리 2
스프링에서 Validator를 제공하는 이유는 체계적으로 검증 기능을 도입하기 위함이다. 위와 같이 검증기를 직접 불러 사용해도되지만 스프링의 도움을 받아 사용할 수도 있다

~~~java
@InitBinder
public void init(WebDataBinder dataBinder) {
    dataBinder.addValidators(itemValidator);
}
~~~
`WebDataBinder`에 검증기를 추가하면 해당 컨트롤러에서는 검증기를 자동으로 적용할 수 있다. `@InitBinder`는 해당 컨트롤러에만 영향을 준다.

~~~java
@PostMapping("/add")
    public String addItemV6(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        // 검증에 실패 시 다시 입력 폼으로 이동
        if (bindingResult.hasErrors()) {
            log.info("bindingResult={}", bindingResult);
            return "validation/v2/addForm";
        }

        // 성공 로직
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v2/items/{itemId}";
    }
~~~

`@ModelAttribute`(검증 대상) 앞에 `@Validate` 추가

`@Validate`는 검증기를 실행하라는 애노테이션이다. 이 애노테이션이 붙으면 `WebDataBinder`에 등록한 검증기를 찾아서 실행한다. 이때 여러 검증기가 등록되어있다면 어떤 검증기를 실행해야할지에 대한 기준이 필요하다. 이때 사용하는게 `supports()` 이다. 

`supports()`가 호출되고 결과가 true일 때 `validate()`가 호출된다.