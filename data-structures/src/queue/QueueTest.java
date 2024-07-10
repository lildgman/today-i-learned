package queue;

import java.util.NoSuchElementException;

public class QueueTest {

    public static void main(String[] args) {
        Queue<Integer> queue = new Queue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);

        System.out.println(queue.remove());
        System.out.println(queue.remove());
        System.out.println(queue.peek());
        System.out.println(queue.remove());
        System.out.println(queue.isEmpty());
        System.out.println(queue.remove());
        System.out.println(queue.isEmpty());

    }
}

// 객체 생성 시 데이터 타입을 명시하도록 하고,
class Queue<T> {

    // 같은 타입을 받는 Node 선언
    class Node<T> {
        // 데이터 선언
        private T data;
        // 다음 노드
        private Node<T> next;

        // 생성자에서 해당 타입의 데이터를 받아 내부변수에 저장
        public Node(T data) {
            this.data = data;
        }
    }

    // 큐는 앞뒤로 주소를 알고 있어야 한다.
    private Node<T> first;
    private Node<T> last;

    // 추가
    public void add(T item) {
        // 추가할 아이템을 받아 노드 생성
        Node<T> t = new Node<>(item);

        // 만약 마지막 노드가 있다면
        if (last != null) {
            // 그 뒤에 새로 생성한 노드를 붙이고
            last.next = t;
        }
        // t는 마지막 노드
        last = t;

        // 데이터가 없으면 (first도 null)
        if (first == null) {
            // last와 같은 값을 할당
            first = last;
        }
    }

    // 삭제
    public T remove() {
        // 만약 first가 비어있으면
        if (first == null) {
            throw new NoSuchElementException();
        }

        // 맨 앞에 있는 데이터를 반환하기 전에 그 다음주소에 있는 데이터를 first를 만들어줘야함
        T data = first.data;
        first = first.next;

        if (first == null) {
            last = null;
        }

        return data;
    }

    public T peek() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        // 첫번째 값 반환
        return first.data;
    }

    public boolean isEmpty() {
        return first == null;
    }
}

