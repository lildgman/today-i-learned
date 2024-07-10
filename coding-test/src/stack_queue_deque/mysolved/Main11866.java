package stack_queue_deque.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main11866 {

    // 1번부터 N번까지 N명의 사람이 원을 이루면서 앉아있고, 양의 정수 K(≤ N)가 주어진다.
    // 이제 순서대로 K번째 사람을 제거한다.
    // 한 사람이 제거되면 남은 사람들로 이루어진 원을 따라 이 과정을 계속해 나간다.
    // 이 과정은 N명의 사람이 모두 제거될 때까지 계속된다.
    // 원에서 사람들이 제거되는 순서를 (N, K)-요세푸스 순열이라고 한다.
    // 예를 들어 (7, 3)-요세푸스 순열은 <3, 6, 2, 7, 5, 1, 4>이다.

    // 입력
    // 첫째 줄에 N과 K가 빈 칸을 사이에 두고 순서대로 주어진다. (1 ≤ K ≤ N ≤ 1,000)

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Queue<Integer> queue1 = new LinkedList<>();

        StringBuilder sb = new StringBuilder();
        sb.append("<");

        for (int i = 1; i <= n; i++) {
            queue1.add(i);
        }

        // 한 사람만 남을 때 까지 반복
        while (queue1.size() > 1) {

            for (int i = 0; i < k - 1; i++) {
                // 맨 앞에 사람을 뒤로 옮김
                queue1.add(queue1.remove());
            }
            // 맨 앞사람 제거 후 sb에 담아주기
            sb.append(queue1.poll()).append(", ");
        }

        // queue1에 남은 사람 sb에 담아주기
        sb.append(queue1.poll()).append(">");

        System.out.println(sb);
    }
}
