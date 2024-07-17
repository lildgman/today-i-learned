package stack_queue_deque.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main2346 {

    // 1번부터 N번 까지 N개의 풍선이 원형으로 놓여 있고. i번 풍선의 오른쪽에는 i+1번 풍선이 있고, 왼쪽에는 i-1번 풍선이 있다.
    // 단, 1번 풍선의 왼쪽에 N번 풍선이 있고, N번 풍선의 오른쪽에 1번 풍선이 있다.
    // 각 풍선 안에는 종이가 하나 들어있고, 종이에는 -N보다 크거나 같고, N보다 작거나 같은 정수가 하나 적혀있다.
    // 이 풍선들을 다음과 같은 규칙으로 터뜨린다.

    // 제일 처음에는 1번 풍선을 터뜨린다.
    // 다음에는 풍선 안에 있는 종이를 꺼내어 그 종이에 적혀있는 값만큼 이동하여 다음 풍선을 터뜨린다.
    // 양수가 적혀 있을 경우에는 오른쪽으로, 음수가 적혀 있을 때는 왼쪽으로 이동한다. 이동할 때에는 이미 터진 풍선은 빼고 이동한다.

    // 입력
    // 첫째 줄에 자연수 N(1 ≤ N ≤ 1,000)이 주어진다.
    // 다음 줄에는 차례로 각 풍선 안의 종이에 적혀 있는 수가 주어진다.
    // 종이에 0은 적혀있지 않다.
    // 출력
    // 첫째 줄에 터진 풍선의 번호를 차례로 나열한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 풍선 개수
        int n = Integer.parseInt(br.readLine());

        Deque<int[]> deque = new ArrayDeque<>();

        // 풍선 안에 있는 숫자 입력
        StringTokenizer st = new StringTokenizer(br.readLine());

        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        sb.append("1 ");
        int nextTarget = arr[0];

        for (int i = 1; i < n; i++) {
            deque.add(new int[]{i + 1, arr[i]});
        }

        while (!deque.isEmpty()) {

            if (nextTarget > 0) {
                for (int i = 1; i < nextTarget; i++) {
                    deque.add(deque.poll());
                }

                int[] nextBallon = deque.poll();
                nextTarget = nextBallon[1];
                sb.append(nextBallon[0] + " ");
            } else {

                for (int i = 1; i < -nextTarget; i++) {
                    deque.addFirst(deque.pollLast());
                }

                int[] nextBalloon = deque.pollLast();
                nextTarget = nextBalloon[1];
                sb.append(nextBalloon[0] + " ");
            }
        }

        System.out.println(sb);

    }
}
