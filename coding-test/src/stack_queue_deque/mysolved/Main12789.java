package stack_queue_deque.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main12789 {

    // 사람들은 현재 1열로 줄을 서있고, 맨 앞의 사람만 이동이 가능하다.
    // 인규는 번호표 순서대로만 통과할 수 있는 라인을 만들어 두었다.
    // 이 라인과 대기열의 맨 앞 사람 사이에는 한 사람씩 1열이 들어갈 수 있는 공간이 있다.
    // 현재 대기열의 사람들은 이 공간으로 올 수 있지만 반대는 불가능하다. 승환이를 도와 프로그램을 완성하라.

    // 입력
    // 입력의 첫째 줄에는 현재 승환이의 앞에 서 있는 학생들의 수 N(1 ≤ N ≤ 1,000,자연수)이 주어진다.
    // 다음 줄에는 승환이 앞에 서있는 모든 학생들의 번호표(1,2,...,N) 순서가 앞에서부터 뒤 순서로 주어진다.

    // 승환이가 무사히 간식을 받을 수 있으면 "Nice"(따옴표는 제외)를 출력하고 그렇지 않다면 "Sad"(따옴표는 제외)를 출력한다.
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Queue<Integer> queue = new LinkedList<>(); // 대기열
        Stack<Integer> stack = new Stack<>(); // 다른 대기열

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            queue.add(Integer.parseInt(st.nextToken()));
        }

        // 간식 배분 번호
        int num = 1;

        while (!queue.isEmpty()) {
            // 대기열 순번과 배분 번호가 같을 때
            if (queue.peek() == num) {
                queue.remove();
                num++;
            }
            // 다른 대기열이 비어있지 않고 다른 대기열의 번호와 배분 번호가 같을 때
            else if (!stack.isEmpty() && stack.peek() == num) {
                stack.pop();
                num++;
            }

            // 대기열과 다른 대기열의 번호가 배분 번호와 같지 않을 때
            else {
                stack.push(queue.remove());
            }
        }

        while (!stack.isEmpty()) { // 다른 대기열에 사람이 있을 경우
            if (stack.peek() == num) { // 배분번호와 대기열 번호가 같을 때
                stack.pop();
                num++;
            } else {
                System.out.println("Sad");
                return;
            }
        }

        System.out.println("Nice");


    }
}
