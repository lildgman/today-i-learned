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