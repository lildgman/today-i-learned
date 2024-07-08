# 메시지, 국제화

- 메시지 기능: 다양한 메시지를 한 곳에서 관리하도록 하는 기능
- 국제화 기능: 각 나라별로 메시지를 관리하여 서비스를 국제화 할 수 있음

언어를 인식하는 방법: HTTP `accept-language` 헤더값을 사용하거나 사용자가 직접 언어로 선택하도록하고 쿠키등으로 처리하면 된다.

## 스프링 메시지 소스 설정
스프링에서 제공하는 `MessageSource` 스프링 빈을 등록하면 된다. `MessageSource`는 인터페이스 -> 구현체인 ResourceBundleMessageSource를 스프링 빈으로 등록하면 된다.

~~~java
@Bean
public MessageSource messageSource() {
  ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
  messageSource.setBasenames("messages", "errors");
  messageSource.setDefaultEncoding("utf-8");
  return messageSource;
}
~~~

- basenames: 설정파일의 이름을 지정
  - 여기서는 `messages.properties` 파일을 읽어서 사용
  - 국제화 기능 적용하려면 `messages_en.properties`와 같이 파일명 마지막에 언어 정보를 주면 된다. 국제화 파일 없을 시 `messages.properties`를 기본으로 사용
  - 파일 위치는 `/resoureces/messages.properties`

### 스프링 부트 메시지 소스 설정
스프링 부트 사용 시 `MessageSource`를 자동으로 스프링 빈에 등록해줌

## 웹에 메시지 적용
`message.properties`
~~~
label.item=상품
label.item.id=상품 ID
label.item.itemName=상품명
label.item.price=가격
label.item.quantity=수량
~~~
타임리프에서 `#{...}`를 사용하면 스프링의 메시지를 조회할 수 있다.

## 웹에 국제화 적용
~~~
label.item=Item
label.item.id=Item ID
label.item.itemName=Item Name
label.item.price=price
label.item.quantity=quantity
~~~
- 브라우저에서 언어 우선순위를 바꿔서 보면 위의 설정한 바와 같이 화면에 나오게 된다.

메시지 기능은 `Locale` 정보를 알아야 언어를 선택할 수 있다. -> 스프링은 언어선택시 `Accept-Language` 헤더의 값을 사용한다.

스프링은 LocaleResolver라는 인터페이스를 제공, 스프링 부트는 기본으로 `AcceptHeaderLocaleResolver`를 사용하는데 이는 `Accept-Language`를 활용한다. 
