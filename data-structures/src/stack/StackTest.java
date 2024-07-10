package stack;

import java.util.EmptyStackException;

public class StackTest {

    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.push(1);
        s.push(2);
        s.push(3);
        s.push(4);
        s.push(5);

        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.peek());
        System.out.println(s.pop());
        System.out.println(s.isEmpty());
        System.out.println(s.pop());
        System.out.println(s.pop());
        System.out.println(s.isEmpty());

    }

}

// 객체 생성 시 데이터 타입 명시하게 끔 선언
class Stack<T> {

    // 같은 타입을 받는 노드 선언
    class Node<T> {
        // 데이터 선언
        private T data;
        // 다음 노드
        private Node<T> next;

        // 생성자로 데이터를 받아 내부변수에 저장
        public Node(T data) {
            this.data = data;
        }
    }

    // 스택은 맨 위에 올라가있는 데이터의 주소만 알고 있으면 된다.
    private Node<T> top;

    // 가장 위에 있는 데이터를 가져오기
    public T pop() {
        if (top == null) {
            throw new EmptyStackException();
        }

        // 데이터를 반환하기 전에 다음 주소에 있는 데이터를 top으로 만들어 줘야 함
        T item = top.data;
        top = top.next;

        return item;
    }

    public void push(T item) {
        // push할 item을 받아 노드 생성
        Node<T> t = new Node<>(item);
        // top 앞에 생성한 노드를 배치시키
        t.next = top;
        // 생성한 노드가 top이 된다.
        top = t;
    }

    public T peek() {
        if (top == null) {
            throw new EmptyStackException();
        }

        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }
}
