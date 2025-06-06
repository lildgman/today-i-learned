package bark.ch6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_10845_18258 {

    /*
    문제
    정수를 저장하는 큐를 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.

    명령은 총 여섯 가지이다.

    push X: 정수 X를 큐에 넣는 연산이다.
    pop: 큐에서 가장 앞에 있는 정수를 빼고, 그 수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
    size: 큐에 들어있는 정수의 개수를 출력한다.
    empty: 큐가 비어있으면 1, 아니면 0을 출력한다.
    front: 큐의 가장 앞에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.
    back: 큐의 가장 뒤에 있는 정수를 출력한다. 만약 큐에 들어있는 정수가 없는 경우에는 -1을 출력한다.

    입력
    첫째 줄에 주어지는 명령의 수 N (1 ≤ N ≤ 10,000)이 주어진다. 둘째 줄부터 N개의 줄에는 명령이 하나씩 주어진다. 주어지는 정수는 1보다 크거나 같고, 100,000보다 작거나 같다. 문제에 나와있지 않은 명령이 주어지는 경우는 없다.

    출력
    출력해야하는 명령이 주어질 때마다, 한 줄에 하나씩 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 명령어 개수
        int n = Integer.parseInt(br.readLine());

        // 정수를 저장할 큐
        Deque<Integer> queue = new ArrayDeque<>();

        StringBuilder sb = new StringBuilder();
        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine(), " ");

            String input = st.nextToken();

            switch (input) {

                case "push": // 정수를 큐에 넣는다.
                    int num = Integer.parseInt(st.nextToken());
                    queue.offer(num);
                    break;
                case "pop": // 큐에서 가장 앞에 있는 정수 추출 후 그 수를 출력, 큐에 들어있는 수가 없으면 -1 출력
                    if (!queue.isEmpty()) {
                        int poped = queue.poll();
                        sb.append(poped).append('\n');
                    } else {
                        sb.append(-1).append('\n');
                    }
                    break;
                case "size": // 큐에 들어있는 정수개수 출력
                    sb.append(queue.size()).append('\n');
                    break;
                case "empty": // 큐가 비었으면 1, 아니면 0
                    if (queue.isEmpty()) {
                        sb.append(1).append('\n');
                    } else {
                        sb.append(0).append('\n');
                    }
                    break;
                case "front":
                    if (!queue.isEmpty()) {
                        int front = queue.getFirst();
                        sb.append(front).append('\n');
                    } else {
                        sb.append(-1).append('\n');
                    }
                    break;
                case "back":
                    if (!queue.isEmpty()) {
                        int back = queue.getLast();
                        sb.append(back).append('\n');
                    } else {
                        sb.append(-1).append('\n');
                    }
                    break;

            }

        }

        System.out.println(sb);
    }
}
