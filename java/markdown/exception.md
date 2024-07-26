# 예외
## 예외 계층
![alt text](image.png)
- `Throwable`: 최상위 예외
- `Error`: 애플리케이션에서 복구 불가능한 시스템 예외, 메모리 부족 등
- `Exception`: 체크 예외
  - 애플리케이션 로직에서 사용할 수 있는 실질적인 최상위 예외
  - `Exception`과 그 하위 예외는 모두 컴파일러가 체크하는 체크예외
  - `RuntimeException`은 예외
- `RuntimeException`: 언체크 예외, 런타임 예외
  - 컴파일러가 체크하지 않는 언체크 예외
  - `RuntimeException`과 그 자식 예외는 모두 언체크 예외

## 예외 기본 규칙
예외는 폭탄 돌리기라 생각하면 되는데 잡아서 처리하거나 밖으로 던져야 한다.

![alt text](image-1.png)
- 서비스에서 예외를 처리하면 이후에는 로직이 정상 흐름으로 동작한다.

![alt text](image-2.png)
- 예외를 처리하지 못하면 호출한 곳으로 예외를 계속 던진다.

1. 예외는 잡아서 처리하거나 던져야한다.
2. 예외를 잡거나 던질 때 지정한 예외뿐만 아니라 그 예외의 자식들도 함께 처리된다
  - `Exception`을 `catch`로 잡으면 하위 예외들도 모두 잡을 수 있다.
  - `Exception`을 `throws`로 던지면 하위 예외들도 모두 던질 수 있다.

예외를 처리하지 못하고 계속 던지게 되면
- main() 쓰레드의 경우, 예외 로그를 출력하면서 시스템이 종료된다.
- 웹 애플리케이션의 경우, 하나의 예외 때문에 시스템이 종료되면 안된다. WAS가 해당 예외를 받아 처리해주는데 주로 개발자가 지정한 오류페이지를 보여준다.

## 체크 예외 기본 이해
- `Exception`과 그 하위 예외는 모두 컴파일러가 체크하는 체크 예외이다 (`RuntimeException` 제외)
- 체크예외는 잡아서 처리하거나 밖으로 던지도록 해야한다. 그렇지 않으면 컴파일 오류가 발생한다.

~~~java
@Slf4j
public class CheckedTest {

    @Test
    void checked_catch() {
        Service service = new Service();
        service.callCatch();
    }

    @Test
    void checked_throw() {
        Service service = new Service();

        assertThatThrownBy(() -> service.callThrow())
                .isInstanceOf(MyCheckedException.class);
    }

    /**
     * Exception을 상속받은 예외는 체크 예외가 된다.
     */
    static class MyCheckedException extends Exception {
        public MyCheckedException(String message) {
            super(message);
        }
    }

    /**
     * Checked 예외는
     * 예외를 잡아서 처리하거나 던지거나 둘 중 하나를 필수로 해야한다.
     */
    static class Service {
        Repository repository = new Repository();

        /**
         * 예외를 잡아 처리하는 코드
         */
        public void callCatch() {
            try {
                repository.call();
            } catch (MyCheckedException e) {
                log.info("예외 처리, message={}", e.getMessage(), e);
            }
        }

        /**
         * 체크 예외를 밖으로 던지는 코드
         * 체크 예외는 예외를 잡지 않고 밖으로 던지려면 throws 예외를 메서드에 선언해줘야한다.
         * @throws MyCheckedException
         */
        public void callThrow() throws MyCheckedException {
            repository.call();
        }
    }

    static class Repository {
        public void call() throws MyCheckedException {
            throw new MyCheckedException("ex");
        }
    }
}
~~~
- `Exception`을 상속받은 예외는 체크 예외가 된다.
- 체크 예외를 잡아 처리하려면 `catch(..)`를 사용하면 된다.
  - 해당 타입과 하위 타입 모두 잡을 수 있다.
- 체크 예외를 처리할 수 없을 때는 `method() throws 예외`를 사용해 밖으로 던질 예외를 필수로 지정해줘야한다.
  - 체크 예외를 밖으로 던지는 경우에도 해당 타입과 하위 타입 모두 던질 수 있다.