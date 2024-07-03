# Comparable VS Comparator
`Comparable`과 `Comparator`는 모두 인터페이스이다. 즉 인터페이스 내에 선언된 메서드를 모두 **구현** 해야 한다.

`Comparable` 인터페이스에는 `compareTo(T o)` 메서드 하나가 선언되어있다. `Comparable`을 사용하고자 한다면 `compareTo()` 메서드를 오버라이드 해줘야 한다.

`Comparator` 인터페이스에는 수많은 메서드가 있지만 실질적으로 구현해야하는 메서드는 `compare(T o1, T o2)` 메서드이다.

여기서 위에 설명했던 `인터페이스 내 선언된 메서드를 구현한다.` 라는 설명과 모순된다. 왜 compare 메서드만 구현하는가?

~~~java
@FunctionalInterface
public interface Comparator<T> {
    
    int compare(T o1, T o2);

    boolean equals(Object obj);

    default Comparator<T> reversed() {
        return Collections.reverseOrder(this);
    }

    default Comparator<T> thenComparing(Comparator<? super T> other) {
        Objects.requireNonNull(other);
        return (Comparator<T> & Serializable) (c1, c2) -> {
            int res = compare(c1, c2);
            return (res != 0) ? res : other.compare(c1, c2);
        };
    }

    default <U> Comparator<T> thenComparing(
            Function<? super T, ? extends U> keyExtractor,
            Comparator<? super U> keyComparator)
    {
        return thenComparing(comparing(keyExtractor, keyComparator));
    }
  ...
}
~~~
Comparator 인터페이스를 보면 일반 메서드처럼 구현되있는 메서드들이 있는 것을 확인할 수 있다.

Java8부터는 인터페이스에서도 일반 메서드를 구현할 수 있도록 변경되었다. 공통점을 보자면 `default`나 `static`으로 선언된 메서드들이다.
즉, `default`나 `static`이 붙은 메서드가 아니라면 모두 재정의를 해줘야 한다는 것이다.

여기서 `default`로 선언된 메서드는 재정의 할 수 있고, `static`으로 선언된 메서드는 재정의 할 수 없다.

---

Comparable과 Comparator 인터페이스는 **객체를 비교할 수 있도록 만든다.**
일반적인 int나 double 같은 변수는 부등호로 비교를 할 수 있지만, 객체를 비교할 때는 비교기준이 없어 누가 더 높은지 비교할 수 없다.

이때 Comparable과 Comparator를 사용한다.

Comparable의 compareTo(T o)는 파라미터가 1개, Comparator의 compare(T o1, T o2)는 파라미터가 2개이다.
즉, Comparable은 자기 자신과 파라미터로 넘어온 객체를 비교하는 것이고, Comparator는 파라미터로 넘어온 두개의 객체를 비교한다.

## Comparable
~~~java
public class Member implements Comparable<Member>{
    
    int age;
    int height;

    public Member(int age, int height) {
        this.age = age;
        this.height = height;
    }

    @Override
    public int compareTo(Member o) {
        return 0;
    }
}
~~~
Member라는 객체를 height를 기준으로 비교하고자 한다면 compareTo() 메서드의 파라미터로 넘어온 o의 height를 가지고 비교를 하면된다.

~~~java
@Override
public int compareTo(Member o) {

    if (this.age > o.age) {
        return 1;
    } else if (this.age == o.age) {
        return 0;
    } else {
        return -1;
    }
}
~~~
compareTo는 정수값을 반환하도록 되어있다. 그러면 어떤 기준으로 양수,0,음수를 반환하는 것인가?

compareTo()는 자기자신을 기준으로 다른 객체와 비교를 하는 메서드이다.
양수를 반환할 때는 나의 age가 o의 age보다 크다, 0일때는 둘의 age가 같다, 음수일때는 상대의 age가 더 크다라는 의미이다.

## Comparator
Comparator는 파라미터로 넘어온 두 개의 객체를 비교한다.

~~~java
import java.util.Comparator;

public class Member2 implements Comparator<Member2> {
    
    int age;
    int weight;
    
    @Override
    public int compare(Member2 o1, Member2 o2) {

        if (o1.weight > o2.weight) {
            return 1;
        } else if (o1.weight == o2.weight) {
            return 0;
        } else {
            return -1;
        } 
    }
}
~~~

compareTo()는 자기자신을 기준으로 파라미터로 넘어온 객체와 비교하는것에 반해 compare()는 o1와 o2를 비교하므로 자기 자신은 두 객체 비교에 영향이 없다.

결국 compare()를 사용하려면 어떠한 객체가 필요하다.
그럼 굳이 왜 이걸 사용할까?

진가는 익명클래스를 사용할 때 나타난다.
익명클래스는 말그대로 이름이 정의되지 않은 객체이다. 이름이 없어 특정 타입이 존재하는 것이 아니기 때문에 상속할 대상이 있어야한다.

~~~java
public class Member3 {

    int age;
    int height;
}
~~~
~~~java
public class CompareTest {

    public static void main(String[] args) {

        Comparator<Member3> memberComp = new Comparator<Member3>() {
            @Override
            public int compare(Member3 o1, Member3 o2) {
                return o1.height - o2.height;
            }
        };
    }

    public static Comparator<Member3> memberComp2 = new Comparator<Member3>() {
        @Override
        public int compare(Member3 o1, Member3 o2) {
            return o1.height - o2.height;
        }
    };
}
~~~

## Comparable과 Comparator의 정렬관계
- Java에서 정렬은 기본적으로 오름차순을 기준으로 정렬한다.
- 객체 비교 시 음수가 나오면 두 원소 위치를 바꾸지 않는다. 
- 양수가 나오면 두 원소의 위치를 바꾼다.

### Comparable 사용
~~~java
package temp;

import java.util.Arrays;

public class CompareTest2 {

    public static void main(String[] args) {

        TestInteger[] arr = new TestInteger[10];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TestInteger((int) (Math.random() * 100));
        }

        System.out.println("정렬 전 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr);

        System.out.println("정렬 후 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }
}

class TestInteger implements Comparable<TestInteger> {
    int value;

    public TestInteger(int value) {
        this.value = value;
    }


    @Override
    public int compareTo(TestInteger o) {
        return this.value - o.value;
    }
}
~~~

### Comparator
~~~java
public class CompareTest2 {

    public static void main(String[] args) {

        TestInteger2[] arr = new TestInteger2[10];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TestInteger2((int) (Math.random() * 100));
        }

        System.out.println("정렬 전 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr);

        System.out.println("정렬 후 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }
}

class TestInteger2 implements Comparable<TestInteger2> {
    int value;

    public TestInteger2(int value) {
        this.value = value;
    }


    @Override
    public int compareTo(TestInteger2 o) {
        return this.value - o.value;
    }
}
~~~

내림차순으로 정렬하려면 어떻게 해야할까?

선행원소가 후행원소보다 작으면 -> 음수 -> 오름차순
선행원소가 후행원소보다 크면 -> 양수 -> 내림차순

~~~java
public int compareTo(TestInteger o) {
  // return -(this.value - o.value)
  return o.value - this.value;

}

public int compare(TestInteger2 o1, TestInteger2 o2) {
  // return -(o1.value - o2.value);
  return o2.value - o1.value;

}
~~~

