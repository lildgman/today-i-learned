# DFS/BFS
- 탐색: 많은 양의 데이터 중 원하는 데이터를 찾는 과정
- 코딩 테스트에서 매우 자주 등장하는 유형

## 스택 자료구조
- 먼저 들어온 데이터가 나중에 나가는 형식(FILO)
- 입구와 출구가 동일한 형태
- 박스 쌓기를 생각하자

~~~java
public class StackEx {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();

        stack.push(5);
        stack.push(2);
        stack.push(3);
        stack.push(6);
        stack.pop();
        stack.push(1);
        stack.push(4);
        stack.pop();

        while (!stack.empty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
~~~
- push(): 요소 추가
- pop(): 요소 삭제
- empty(): 스택이 비어있는지 확인

[Stack](https://docs.oracle.com/javase/8/docs/api/java/util/Stack.html)

## 큐 자료구조
- 먼저 들어온 데이터가 먼저 나가는 형식의 자료구조 (FIFO)
- 입구와 출구가 모두 뚫려있는 터널과 같은 형태
- 대기열을 생각하자

~~~java
public class QueueEx {

    public static void main(String[] args) {

        Queue<Integer> q = new LinkedList<>();

        q.offer(5);
        q.offer(2);
        q.offer(3);
        q.offer(7);
        q.poll();
        q.offer(1);
        q.offer(4);
        q.poll();

        while (!q.isEmpty()) {
            System.out.println(q.poll());
        }
    }
}
~~~
- offer(): 요소 추가
- poll(): 요소 삭제
- isEmpty(): 큐가 비어있는지 확인

[Queue](https://docs.oracle.com/javase/8/docs/api/java/util/Queue.html)

## 재귀함수
`재귀함수(Recursive Function)`는 **자기 자신을 다시 호출** 하는 함수
- 재귀 함수를 문제 풀이에서 사용할 때 재귀 함수의 종료 조건을 반드시 명시해야 한다.

~~~java
public class RecursiveEx {

    // 반복문으로 구현한 n!
    public static int factorialIterative(int n) {
        int result = 1;

        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    // 재귀적으로 구현한 n!
    public static int factorialRecursive(int n) {

        // n이 1이하인 경우 1 반환
        if (n <= 1) {
            return 1;
        }

        return n * factorialRecursive(n - 1);
    }

    public static void main(String[] args) {

        System.out.println("반복적으로 구현한 n!: " + factorialIterative(5));
        System.out.println("재귀적으로 구현한 n!: "+ factorialRecursive(5));
    }
}
~~~

### 최대공약수 계산 (유클리드 호제법)
- 두 개의 자연수에 대한 최대공약수를 구하는 알고리즘
- 유클리드 호제법
  - 두 자연수 A,B에 대해 (A>B) A를 B로 나눈 나머지가 R이라고 할 때
  - A와 B의 최대공약수는 B와 R의 최대공약수와 같다.

### 재귀 함수 사용 유의사항
- 재귀함수 사용 시 복잡한 알고리즘을 간결하게 작성할 수 있다.
- 하지만, 다른 사람이 이해하기 어려운 형태가 나올 수 있으니 신중히 사용해야 한다.
- 재귀 함수는 반복문을 이용해 동일한 기능을 구현할 수 있다.
- 재귀함수가 유리한 경우도, 불리한 경우도 있다.
- 컴퓨터가 함수를 연속적으로 호출 시 컴퓨터 메모리 내부의 스택 프레임에 쌓인다.
  - 스택 구현 시 구현상 스택 라이브러리 대신 재귀 함수를 이용하는 경우가 많다.

## DFS(Depth-First-Search)
- DFS는 `깊이 우선 탐색`이라고 부르며 그래프에서 `깊은 부분을 우선적으로 탐색하는 알고리즘`
- `스택 자료구조 or 재귀 함수`를 이용
  - 1. 탐색 시작 노드를 스택에 삽입하고 방문 처리
  - 2. 스택의 최상단 노드에 방문하지 않은 인접한 노드가 하나라도 있으면 그 노드를 스택에 넣고 방문 처리, 방문하지 않은 인접 노드가 없으면 스택에서 최상단 노드를 꺼냄
  - 3. 2번 과정을 수행할 수 없을 때 까지 반복

~~~java
public class DfsEx {

    public static boolean[] visited = new boolean[9];
    // ArrayList를 2차원으로 생성
    public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

    // DFS 함수 정의
    public static void dfs(int x) {
        // 현재 노드를 방문 처리
        visited[x] = true;
        System.out.print(x + " ");

        // 현재 노드와 연결된 다른 노드를 재귀적으로 방문
        for (int i = 0; i < graph.get(x).size(); i++) {
            int y = graph.get(x).get(i);
            if (!visited[y]) {
                dfs(y);
            }
        }
    }

    public static void main(String[] args) {

        // 그래프 초기화
        for (int i = 0; i < 9; i++) {
            graph.add(new ArrayList<Integer>());
        }

        // 노드1에 연결된 노드 정보 저장
        graph.get(1).add(2);
        graph.get(1).add(3);
        graph.get(1).add(8);

        // 노드2에 연결된 노드 정보 저장
        graph.get(2).add(1);
        graph.get(2).add(7);

        // 노드3에 연결된 노드 정보 저장
        graph.get(3).add(1);
        graph.get(3).add(4);
        graph.get(3).add(5);

        // 노드4에 연결된 노드 정보 저장
        graph.get(4).add(3);
        graph.get(4).add(5);

        // 노드5에 연결된 노드 정보 저장
        graph.get(5).add(3);
        graph.get(5).add(4);

        // 노드6에 연결된 노드 정보 저장
        graph.get(6).add(7);

        // 노드7에 연결된 노드 정보 저장
        graph.get(7).add(2);
        graph.get(7).add(6);
        graph.get(7).add(8);

        // 노드 8에 연결된 노드 정보 저장
        graph.get(8).add(1);
        graph.get(8).add(7);

        dfs(1);
    }
}
~~~

## BFS(Breadth-First-Search)
- **너비 우선 탐색**이라하며, `가까운 노드부터 우선적으로 탐색하는 알고리즘`
- **큐 자료구조**를 이용
  - 1. 탐색 시작 노드를 큐에 삽입하고 방문처리
  - 2. 큐에 노드를 꺼낸 뒤 해당 노드의 인접 노드 중 방문하지 않은 노드를 모두 큐에 삽입하고 방문 처리
  - 3. 2번을 수행할 수 없을 때까지 반복

~~~java
public class BfsEx {

    public static boolean[] visited = new boolean[9];
    public static ArrayList<ArrayList<Integer>> graph = new ArrayList<>();

    // BFS 함수
    public static void bfs(int start) {
        Queue<Integer> q = new LinkedList<>();
        q.offer(start);

        // 현재 노드를 방문 처리
        visited[start] = true;

        // 큐가 빌 때까지 반복
        while (!q.isEmpty()) {
            // 큐에서 하나의 원소를 뽑아서 출력
            int x = q.poll();
            System.out.print(x + " ");

            // 해당 원소와 연결되고 방문하지 않은 원소들을 큐에 삽입
            for (int i = 0; i < graph.get(x).size(); i++) {
                int y = graph.get(x).get(i);
                if (!visited[y]) {
                    q.offer(y);
                    visited[y] = true;
                }
            }
        }
    }

    public static void main(String[] args) {

        // 그래프 초기화
        for (int i = 0; i < 9; i++) {
            graph.add(new ArrayList<>());
        }

        // 노드1에 연결된 노드 정보 저장
        graph.get(1).add(2);
        graph.get(1).add(3);
        graph.get(1).add(8);

        // 노드2에 연결된 노드 정보 저장
        graph.get(2).add(1);
        graph.get(2).add(7);

        // 노드3에 연결된 노드 정보 저장
        graph.get(3).add(1);
        graph.get(3).add(4);
        graph.get(3).add(5);

        // 노드4에 연결된 노드 정보 저장
        graph.get(4).add(3);
        graph.get(4).add(5);

        // 노드5에 연결된 노드 정보 저장
        graph.get(5).add(3);
        graph.get(5).add(4);

        // 노드6에 연결된 노드 정보 저장
        graph.get(6).add(7);

        // 노드7에 연결된 노드 정보 저장
        graph.get(7).add(2);
        graph.get(7).add(6);
        graph.get(7).add(8);

        // 노드 8에 연결된 노드 정보 저장
        graph.get(8).add(1);
        graph.get(8).add(7);

        bfs(1);
    }
}
~~~