package stack_queue_deque.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main28279 {

    // 정수를 저장하는 덱을 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.
    // 1 X: 정수 X를 덱의 앞에 넣는다. (1 ≤ X ≤ 100,000)
    // 2 X: 정수 X를 덱의 뒤에 넣는다. (1 ≤ X ≤ 100,000)
    // 3: 덱에 정수가 있다면 맨 앞의 정수를 빼고 출력한다. 없다면 -1을 대신 출력한다.
    // 4: 덱에 정수가 있다면 맨 뒤의 정수를 빼고 출력한다. 없다면 -1을 대신 출력한다.
    // 5: 덱에 들어있는 정수의 개수를 출력한다.
    // 6: 덱이 비어있으면 1, 아니면 0을 출력한다.
    // 7: 덱에 정수가 있다면 맨 앞의 정수를 출력한다. 없다면 -1을 대신 출력한다.
    // 8: 덱에 정수가 있다면 맨 뒤의 정수를 출력한다. 없다면 -1을 대신 출력한다.

    // 입력
    // 첫째 줄에 명령의 수 N이 주어진다. (1 ≤ N ≤ 1,000,000)
    // 둘째 줄부터 N개 줄에 명령이 하나씩 주어진다.
    // 출력을 요구하는 명령은 하나 이상 주어진다.

    // 출력
    // 출력을 요구하는 명령이 주어질 때마다 명령의 결과를 한 줄에 하나씩 출력한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int n = Integer.parseInt(br.readLine());

        Deque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int order = Integer.parseInt(st.nextToken());
            if (order == 1) {
                deque.addFirst(Integer.parseInt(st.nextToken()));
            } else if (order == 2) {
                deque.addLast(Integer.parseInt(st.nextToken()));
            } else if (order == 3) {
                if (!deque.isEmpty()) {
                    sb.append(deque.removeFirst()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            } else if (order == 4) {
                if (!deque.isEmpty()) {
                    sb.append(deque.removeLast()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            } else if (order == 5) {
                sb.append(deque.size()).append('\n');
            } else if (order == 6) {
                if (deque.isEmpty()) {
                    sb.append(1).append('\n');
                } else {
                    sb.append(0).append('\n');
                }
            } else if (order == 7) {
                if (!deque.isEmpty()) {
                    sb.append(deque.peekFirst()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            } else if (order == 8) {
                if (!deque.isEmpty()) {
                    sb.append(deque.peekLast()).append('\n');
                } else {
                    sb.append(-1).append('\n');
                }
            }
        }

        System.out.println(sb);
    }
}
