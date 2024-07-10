package stack_queue_deque.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main18258 {

    // push X: 정수 X를 큐에 넣는 연산이다.
    // pop: 큐에서 가장 앞에 있는 정수를 빼고, 그 수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
    // size: 큐에 들어있는 정수의 개수를 출력한다.
    // empty: 큐가 비어있으면 1, 아니면 0을 출력한다.
    // front: 큐의 가장 앞에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
    // back: 큐의 가장 뒤에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.

    // 입력
    // 첫째 줄에 주어지는 명령의 수 N (1 ≤ N ≤ 2,000,000)이 주어진다.
    // 둘째 줄부터 N개의 줄에는 명령이 하나씩 주어진다.
    // 주어지는 정수는 1보다 크거나 같고, 100,000보다 작거나 같다.
    // 문제에 나와있지 않은 명령이 주어지는 경우는 없다.

    // 출력
    // 출력해야하는 명령이 주어질 때마다, 한 줄에 하나씩 출력한다.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            String order = st.nextToken();

            if (order.equals("push")) {
                queue.add(Integer.parseInt(st.nextToken()));
            } else if (order.equals("pop")) {
                if (!queue.isEmpty()) {
                    sb.append(queue.remove()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            } else if (order.equals("size")) {
                sb.append(queue.size()).append('\n');
            } else if (order.equals("empty")) {
                if (queue.isEmpty()) {
                    sb.append(1).append('\n');
                } else {
                    sb.append(0).append('\n');
                }
            } else if (order.equals("front")) {
                if (!queue.isEmpty()) {
                    sb.append(queue.peek()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            } else if (order.equals("back")) {
                if (!queue.isEmpty()) {
                    sb.append(((LinkedList<Integer>) queue).getLast()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            }
        }

        System.out.println(sb);

    }
}
