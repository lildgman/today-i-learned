# Call by Value & Call by reference

## 기본형과 참조형의 변수 대입
먼저 대원칙을 기억하도록 하자. <br>
**자바는 항상 변수의 값을 복사해서 대입한다**. <Br>
기본형과 참조형 모두 변수에 있는 값을 복사해서 대입한다. 기본형이면 실제 사용하는 값을 복사해서 대입하고 참조형이면 참조값을 복사해 대입한다.

### 기본형 변수 대입
코드를 통해 이해해보자
~~~java
public class Ex1 {

    public static void main(String[] args) {

        int a = 10;
        int b = a;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // a를 20으로 변경
        a = 20;
        System.out.println("a를 20으로 변경");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // b를 30으로 변경
        b = 30;
        System.out.println("b를 30으로 변경");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
~~~
~~~java
int a = 10;
int b = a;

System.out.println("a = " + a);
System.out.println("b = " + b);
~~~
~~~
실행 결과
a = 10
b = 10
~~~
대원칙을 기억하자. **자바는 항상 변수의 값을 복사해 대입한다.**
- a에 들어있는 값 10을 복사해서 b에 대입한다. 변수 a 자체를 b에 대입하는 것이 아니다.

~~~java
// a를 20으로 변경
a = 20;
System.out.println("a를 20으로 변경");
System.out.println("a = " + a);
System.out.println("b = " + b);
~~~
~~~
실행 결과
a = 20
b = 10
~~~
a에 20을 대입하였다. a의 값이 20으로 변경되었다. b에는 아무런 영향을 주지 않는다.

~~~java
// b를 30으로 변경
b = 30;
System.out.println("b를 30으로 변경");
System.out.println("a = " + a);
System.out.println("b = " + b);
~~~
b에 30을 대입하였다. b가 10에서 30으로 변경되었다. a에는 아무런 영향을 주지 않는다.


### 참조형 변수 대입
~~~java
public class Ex2 {

    public static void main(String[] args) {

        Data data1 = new Data();
        data1.value = 10;
        Data data2 = data1;

        System.out.println("data1의 참조값= " + data1);
        System.out.println("data2의 참조값 = " + data2);
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
        System.out.println();

        // data1 변경
        data1.value = 20;
        System.out.println("data1 변경 data1.value = 20");
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
        System.out.println();
        
        // data2 변경
        data2.value = 30;
        System.out.println("data2 변경 data2.value = 30");
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
    }
}

class Data {
    int value;
}
~~~
~~~java
Data data1 = new Data();
data1.value = 10;
Data data2 = data1;

System.out.println("data1의 참조값= " + data1);
System.out.println("data2의 참조값 = " + data2);
System.out.println("data1.value = " + data1.value);
System.out.println("data2.value = " + data2.value);
~~~
~~~
실행 결과
data1의 참조값= variable_type.Data@5ca881b5
data2의 참조값 = variable_type.Data@5ca881b5
data1.value = 10
data2.value = 10
~~~
- data1은 Data 클래스를 통해 만든 참조형 변수이다. data1에 Data 객체의 참조값을 저장한다. 
- `data1.value = 10` Data 객체의 value에 10을 대입한다.
- data1에는 `variable_type.Data@5ca881b5`과 같은 참조값이 들어있다.
- 이 참조값을 복사에 data2에 대입한다. 이 때 data1이 가리키는 인스턴스를 복사하는 것이 아닌 참조값만 복사해서 대입한다.
- data2에도 `variable_type.Data@5ca881b5`이 들어가있다.
- data1과 data2는 같은 Data 인스턴스를 가리킨다.

~~~java
// data1 변경
data1.value = 20;
System.out.println("data1 변경 data1.value = 20");
System.out.println("data1.value = " + data1.value);
System.out.println("data2.value = " + data2.value);
~~~
~~~
실행 결과
data1 변경 data1.value = 20
data1.value = 20
data2.value = 20
~~~
- `data1.value = 20`: data1이 가리키는 Data 인스턴스의 value 값을 20으로 변경했다.
- data2도 data1이 가리키는 Data 인스턴스를 참조하고 있다.
- 따라서 `data2.value = 20`, 둘 다 같은 값을 출력한다.

~~~java
// data2 변경
data2.value = 30;
System.out.println("data2 변경 data2.value = 30");
System.out.println("data1.value = " + data1.value);
System.out.println("data2.value = " + data2.value);
~~~
~~~
실행결과
data2 변경 data2.value = 30
data1.value = 30
data2.value = 30
~~~
- `data2.value = 30`: data2가 가리키는 Data 인스턴스의 value를 30으로 변경한다.
- data1도 data2가 가리키는 Data 인스턴스를 참조하고 있다.
- 따라서 `data1.value = 30`, 둘 다 같은 값을 출력한다.

## 기본형과 참조형, 메서드 호출
이제 메서드 호출에 따라 어떻게 달라지는지 확인해보자.<Br>
위에서 언급했던 대원칙을 다시 한번 상기하고 가자<br>
**자바는 항상 변수의 값을 복사해서 대입한다.**

코드로 확인해보자
### 기본형과 메서드 호출
~~~java
public class Ex3 {

    public static void main(String[] args) {
        int a = 10;
        System.out.println("메서드 호출 전 : a = " + a);
        change(a);
        System.out.println("메서드 호출 후 : a = " + a);
    }

    private static void change(int x) {
        x = 20;
    }
}
~~~
~~~
실행 결과

메서드 호출 전 : a = 10
메서드 호출 후 : a = 10
~~~
- `change(a)` 메서드를 호출할 때 매개변수 x에 변수 a의 값을 전달한다
- `int x = a`로 해석할 수 있다
- 자바는 변수의 값을 복사해서 대입한다. 따라서 a와 x는 10이라는 값을 갖고 있다.

~~~java
private static void change(int x) {
    x = 20;
}
~~~
- `change()` 메서드 안에서 `x = 20` 으로 새로운 값을 대입한다.
- 결과적으로 x의 값만 20으로 변경되고, a의 값은 10으로 유지된다.
- 메서드가 종료되면 매개변수 x는 제거된다.

### 참조형과 메서드 호출
~~~java
public class Ex4 {

    public static void main(String[] args) {
        Data data1 = new Data();
        data1.value = 10;
        System.out.println("메서드 호출 전: data1.value = " + data1.value);
        change(data1);
        System.out.println("메서드 호출 후: data1.value = " + data1.value);
    }

    private static void change(Data dataX) {
        dataX.value = 20;
    }
}
~~~
~~~
실행 결과

메서드 호출 전: data1.value = 10
메서드 호출 후: data1.value = 20
~~~
- Data 인스턴스를 생성하고 참조값은 data1에 담고, value에 10을 할당하였다.
- 메서드 호출 시 매개변수 dataX에 변수 data1을 전달한다.
- `Data dataX = data1`로 해석할 수 있다.
- 자바에서는 항상 값을 복사하여 변수에 대입한다. data1의 참조값을 복사하여 dataX에 대입한다. 따라서 data1과 dataX는 같은 참조값을 갖게 된다.

~~~java
private static void change(Data dataX) {
    dataX.value = 20;
}
~~~
- 메서드 안에서 `dataX.value = 20`으로 value에 20을 할당한다.
- 참조값을 통해 dataX에 담겨있는 참조값의 인스턴스가 갖고있는 value를 20으로 변경했다.
- data1, dataX 같은 인스턴스를 참조하기 때문에 data1.value와 data2.value는 같은 값인 20을 갖는다.

