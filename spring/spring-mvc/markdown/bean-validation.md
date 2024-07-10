# Bean Validation
검증 기능을 전과같이 매번 코드로 작성하기에는 번거로운 부분이 있다. 이런 검증 로직을 모든 프로젝트에 적용할 수 있게하고 표준화 한것이 Bean Validation이다.

Bean Validation은 검증 애노테이션과 여러 인터페이스의 모음이다.

build.gradle에 
~~~
implementation 'org.springframework.boot:spring-boot-starter-validation'
~~~
추가

~~~java
@Data
public class Item {

    private Long id;

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min = 1000, max=1000000)
    private Integer price;

    @NotNull
    @Max(9999)
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
~~~
필드값 위에 애노테이션 적용

~~~java
@Test
void beanValidation() {
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    Item item = new Item();
    item.setItemName(" ");
    item.setPrice(0);
    item.setQuantity(100000);

    Set<ConstraintViolation<Item>> violations = validator.validate(item);
    for (ConstraintViolation<Item> violation : violations) {
        System.out.println("violation = " + violation);
        System.out.println("violation.getMessage() = " + violation.getMessage());
    }
}
~~~

검증기 생성
~~~java
ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
Validator validator = factory.getValidator();
~~~

검증 실행
~~~java
Set<ConstraintViolation<Item>> violations = validator.validate(item);
~~~
검증 대상(여기서는 item)을 직접 검증기에 넣고 그 결과를 받는다. ConstraintVaiolation 이라는 검증 오류가 담기고 Set이 비어있으면 검증 오류가 없는 것이다.

## Bean Validation - 스프링 적용
스프링 부트가 `spring-boot-starter-validation` 라이브러리를 넣으면 자동으로 Bean Validator를 인지하고 스프링에 통합한다.

자동으로 글로번 Validator로 등록
`LocalValidatorFactoryBean`을 글로벌 Validator로 등록한다. 이 Validator가 `@NotNull` 같은 애노테이션을 보고 검증을 수행.

글로벌 `Validator`가 적용되어 있기 때문에 `@Valid`, `@Validated`만 적용하면 된다. 검증 오류 발생 시 `FieldError`, `ObjectError`를 생성해 `BindingResult`에 담아준다.

직접 글로벌 Validator를 등록하게 되면 스프링 부트가 Bean Validator를 글로벌 Validator로 등록하지 않는다.

검증시 `@Validated`, `@Valid` 둘 다 사용 가능하다.
`javax.validation.@Valid` 사용하려면 의존관계를 추가해야한다. 

`@Validated`는 스프링 전용 검증 애노테이션, `@Valid`는 자바 표준 검증 애노테이션이다. 

### 검증 순서
1. `@ModelAttribute` 각각 필드에 타입 변환 시도
  - 성공 시 다음으로
  - 실패 시 typeMismatch로 FieldError 추가
2. `Validator` 적용

**바인딩이 성공한 필드만 Bean Validation 적용**<br>
BeanValidator는 바인딩에 실패한 필드는 BeanValidation을 적용하지 않는다. 타입 변환이 성공해서 바인딩에 성공한 필드여야 BeanValidation 적용이 의미가 있다.

ex)
- `itemName`에 문자 입력 -> 타입 변환 성공 -> `itemName` 필드에 `Bean Validation` 적용
- `price`에 문자 입력 -> 문자를 숫자 타입으로 변환 실패 -> typeMismatch FieldError 추가 -> `price` 필드에 `Bean Validation` 적용 실패

## Bean Validation - 에러 코드
Bean Validation을 적용하고 bindingResult에 등록된 검증 오류 코드를 보면 오류코드가 애노테이션 이름으로 등록이 된다.

`NotBlank` 라는 오류 코드를 기반으로 `MessageCodesResolver`를 통해 메시지 코드가 생성된다.

**@NotBlank**
- NotBlank.item.itemName
- NotBlank.itemName
- NotBlank.java.lang.String
- NotBlank

**@Range**
- Range.item.price
- Range.price
- Range.java.lang.Integer
- Range

### BeanValidtaion 메시지 찾는 순서
1. 생성된 메시지 코드 순서대로 `messageSource`에서 메시지 찾기
2. 애노테이션의 message 속성 사용
3. 라이브러리가 제공하는 기본 값 사용

## Bean Validation - 오브젝트 오류
Bean Validation에서 오브젝트 오류(ObjectError)는 어떻게 처리할까?

`@ScriptAssert()`
~~~java
@ScriptAssert(lang = "javascript", script = "_this.price * _this.quantity >= 10000")
public class Item{
  ...
}
~~~
하지만 이는 제약도 많고 복잡하다. 오브젝트 오류의 경우 억지로 쓰기 보단 오브젝트 오류 관련 부분만 자바 코드로 작성하는 것을 추천

~~~java
@PostMapping("/add")
public String addItem(@Validated @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

    // 특정 필드가 아닌 복합 룰 검증
    if (item.getPrice() != null && item.getQuantity() != null) {
        int resultPrice = item.getPrice() * item.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null );
        }
    }

    // 검증에 실패 시 다시 입력 폼으로 이동
    if (bindingResult.hasErrors()) {
        log.info("bindingResult={}", bindingResult);
        return "validation/v3/addForm";
    }

    // 성공 로직
    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v3/items/{itemId}";
}
~~~

## Bean Validation - 한계
데이터 등록할 때와 수정할 때 요구사항이 있다

이때 다르게 검증할 수 있는 방법이 두가지 있다
- BeanValidationdml groups 기능 사용
- Item 직접 사용않고 폼 전송을 위한 별도의 모델 객체를 만들어서 사용

### Bean Validation - groups

**저장용 groups**
~~~java
public interface SaveCheck {
}
~~~

**수정용 groups**
~~~java
public interface UpdateCheck {
}
~~~

**Item**
~~~java
@Data
public class Item {

    @NotNull(groups = UpdateCheck.class)
    private Long id;

    @NotBlank(groups = {SaveCheck.class, UpdateCheck.class})
    private String itemName;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Range(min = 1000, max=1000000, groups={SaveCheck.class, UpdateCheck.class})
    private Integer price;

    @NotNull(groups = {SaveCheck.class, UpdateCheck.class})
    @Max(value = 9999, groups = {SaveCheck.class})
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
~~~

**저장 로직에 SaveCheck Groups 적용**
~~~java
@PostMapping("/add")
public String addItem2(@Validated(SaveCheck.class) @ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
  ...
}
~~~

**수정 로직에 UpdateCheck Groups 적용**
~~~java
@PostMapping("/{itemId}/edit")
public String edit2(@PathVariable Long itemId, @Validated(UpdateCheck.class) @ModelAttribute Item item, BindingResult bindingResult) {
  ...
}
~~~
상품 등록과 수정 시 다르게 검증이 가능하게 됐지만 복잡도가 올라갔다.

## Form 전송 객체 분리
groups를 사용하지 않는 이유: 등록 시 품에서 전달하는 데이터가 Item 도메인 객체와 딱 맞지 않다. 그래서 폼 데이터를 컨트롤러까지 전달할 별도의 객체를 만들어 전달한다. 수정 때에도 등록과 다른 데이터가 넘어온다.

그래서 폼 데이터 전달을 위한 별도 객체를 사용하고 등록, 수정용 폼 객체를 나누면 등록, 수정이 완전히 분리가 된다.

**Item 저장용 폼**
~~~java
@Data
public class ItemSaveForm {

    @NotNull
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    @Max(value=9999)
    private Integer quantity;

}
~~~

**Item 수정용 폼**
~~~java
@Data
public class ItemUpdateForm {

    @NotNull
    private Long id;

    @NotNull
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    private Integer quantity;

}
~~~

**상품 등록**
~~~java
@PostMapping("/add")
public String addItem(@Validated @ModelAttribute("item") ItemSaveForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

    // 특정 필드가 아닌 복합 룰 검증
    if (form.getPrice() != null && form.getQuantity() != null) {
        int resultPrice = form.getPrice() * form.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null );
        }
    }

    // 검증에 실패 시 다시 입력 폼으로 이동
    if (bindingResult.hasErrors()) {
        log.info("bindingResult={}", bindingResult);
        return "validation/v4/addForm";
    }

    // 성공 로직
    Item item = new Item();
    item.setItemName(form.getItemName());
    item.setPrice(form.getPrice());
    item.setQuantity(form.getQuantity());

    Item savedItem = itemRepository.save(item);
    redirectAttributes.addAttribute("itemId", savedItem.getId());
    redirectAttributes.addAttribute("status", true);
    return "redirect:/validation/v4/items/{itemId}";
}
~~~
- `@ModelAttribute`로 Item이 아닌 ItemSaveForm을 받는다. 이 때 `@ModelAttribute`에 item 이름을 넣어주자. 그렇지 않으면 명명 규칙에 의해서 `itemSaveForm`이라는 이름으로 model에 담기게 된다.


**상품 수정**
~~~java
@PostMapping("/{itemId}/edit")
public String edit(@PathVariable Long itemId, @Validated @ModelAttribute("item") ItemUpdateForm form, BindingResult bindingResult) {

    // 특정 필드가 아닌 복합 룰 검증
    if (form.getPrice() != null && form.getQuantity() != null) {
        int resultPrice = form.getPrice() * form.getQuantity();
        if (resultPrice < 10000) {
            bindingResult.reject("totalPriceMin", new Object[]{10000, resultPrice}, null );
        }
    }

    if (bindingResult.hasErrors()) {
        log.info("bindingResult={}", bindingResult);
        return "validation/v4/EditForm";
    }

    Item itemParam = new Item();
    itemParam.setItemName(form.getItemName());
    itemParam.setPrice(form.getPrice());
    itemParam.setQuantity(form.getQuantity());

    itemRepository.update(itemId, itemParam);
    return "redirect:/validation/v4/items/{itemId}";
}
~~~

## Bean Validation - HTTP 메시지 컨버터
`@Valid`, `@Validated`는 `HttpMessageConverter`(`@RequestBody`)에도 적용 가능

~~~java
@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class ValidationItemApiController {

  @PostMapping("/add")
  public Object addItem(@RequestBody @Validated ItemSaveForm form, BindingResult bindingResult) {
      log.info("Api 컨트롤러 호출");

      if (bindingResult.hasErrors()) {
          log.info("검증 오류 발생, errors={}", bindingResult);
          return bindingResult.getAllErrors();
      }

      log.info("성공 로직 실행");
      return form;
  }
}
~~~

API 경우 3가지의 경우를 생각해야 한다.
- 성공 요청: 성공
- 실패 요청: JSON 객체를 생성하는 것 자체가 실패
- 검증 오류 요청: JSON 객체 생성은 성공 했지만 검증에서 실패

Postman으로 확인해보자
**성공요청**
~~~json
http://localhost:8080/validation/api/items/add
{"itemName":"hello", "price" : 1000, "quantity" : 1000}
~~~
**요청결과**
~~~json
{
    "itemName": "hello",
    "price": 1000,
    "quantity": 1000
}
~~~

**실패요청**
~~~json
http://localhost:8080/validation/api/items/add
{"itemName":"hello", "price" : "ㅁㅁㅁ", "quantity" : 1000}
~~~
**요청결과**
~~~json
{
    "timestamp": "2024-07-10T06:40:31.501+00:00",
    "status": 400,
    "error": "Bad Request",
    "message": "",
    "path": "/validation/api/items/add"
}
~~~
요청 JSON을 ItemSaveItem 객체로 생성하는데 실패한다.
ItemSaveForm 객체를 만들지 못하기 때문에 컨트롤러 자체가 호출되지 않고 그 전에 예외가 발생한다. Validator도 실행되지 않는다.

**검증 오류 요청**
~~~json
http://localhost:8080/validation/api/items/add
{"itemName":"hello", "price" : 1000, "quantity" : 10000}
~~~
수량이 10000개 일 때 BeanValidation에 의해 걸리게 된다.
**요청결과**
~~~json
[
    {
        "codes": [
            "Max.itemSaveForm.quantity",
            "Max.quantity",
            "Max.java.lang.Integer",
            "Max"
        ],
        "arguments": [
            {
                "codes": [
                    "itemSaveForm.quantity",
                    "quantity"
                ],
                "arguments": null,
                "defaultMessage": "quantity",
                "code": "quantity"
            },
            9999
        ],
        "defaultMessage": "9999 이하여야 합니다",
        "objectName": "itemSaveForm",
        "field": "quantity",
        "rejectedValue": 10000,
        "bindingFailure": false,
        "code": "Max"
    }
]
~~~

**@ModelAttribute vs @RequestBody**
`@ModelAttribute`는 각각 필드 단위로 세밀하게 적용된다. 그래서 특정 필드에 타입이 맞지 않는 오류가 발생해도 다른 필드는 정상 처리가 가능했다.

`HttpMessageConvertor(@RequestBody)`는 필드 단위로 적용되는 것이 아닌 전체 객체 단위로 적용된다. 즉, 메시지 컨버터의 작동이 성공해서 ItemSaveForm 객체를 만들어야만 `@Validated`가 적용이 된다.