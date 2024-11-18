package deque;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main_10866 {

    /**
     * 정수를 저장하는 덱(Deque)를 구현한 다음, 입력으로 주어지는 명령을 처리하는 프로그램을 작성하시오.
     * <p>
     * 명령은 총 여덟 가지이다.
     * <p>
     * push_front X: 정수 X를 덱의 앞에 넣는다.
     * push_back X: 정수 X를 덱의 뒤에 넣는다.
     * pop_front: 덱의 가장 앞에 있는 수를 빼고, 그 수를 출력한다. 만약, 덱에 들어있는 정수가 없는 경우에는 -1을 출력한다.
     * pop_back: 덱의 가장 뒤에 있는 수를 빼고, 그 수를 출력한다. 만약, 덱에 들어있는 정수가 없는 경우에는 -1을 출력한다.
     * size: 덱에 들어있는 정수의 개수를 출력한다.
     * empty: 덱이 비어있으면 1을, 아니면 0을 출력한다.
     * front: 덱의 가장 앞에 있는 정수를 출력한다. 만약 덱에 들어있는 정수가 없는 경우에는 -1을 출력한다.
     * back: 덱의 가장 뒤에 있는 정수를 출력한다. 만약 덱에 들어있는 정수가 없는 경우에는 -1을 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());
        Deque<Integer> dq = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            String[] strArr = br.readLine().split(" ");

            switch (strArr[0]) {
                case "push_front":
                    dq.addFirst(Integer.parseInt(strArr[1]));
                    break;
                case "push_back":
                    dq.addLast(Integer.parseInt(strArr[1]));
                    break;
                case "pop_front":
                    if (dq.isEmpty()) {
                        sb.append(-1).append("\n");
                    } else {
                        sb.append(dq.pollFirst()).append("\n");
                    }
                    break;
                case "pop_back":
                    if (dq.isEmpty()) {
                        sb.append(-1).append("\n");
                    } else {
                        sb.append(dq.pollLast()).append("\n");
                    }
                    break;
                case "size":
                    sb.append(dq.size()).append("\n");
                    break;
                case "empty":
                    if (dq.isEmpty()) {
                        sb.append(1).append("\n");
                    } else {
                        sb.append(0).append("\n");
                    }
                    break;
                case "front":
                    if (dq.isEmpty()) {
                        sb.append(-1).append("\n");
                    } else {
                        sb.append(dq.peekFirst()).append("\n");
                    }
                    break;
                case "back":
                    if (dq.isEmpty()) {
                        sb.append(-1).append("\n");
                    } else {
                        sb.append(dq.peekLast()).append("\n");
                    }
                    break;
            }
        }

        System.out.println(sb);

    }
}
