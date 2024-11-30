package programmers.intro.day10;

import java.util.LinkedList;
import java.util.Queue;

public class Day10_3 {

    /**
     * 머쓱이는 친구들과 동그랗게 서서 공 던지기 게임을 하고 있습니다.
     * 공은 1번부터 던지며 오른쪽으로 한 명을 건너뛰고 그다음 사람에게만 던질 수 있습니다.
     * 친구들의 번호가 들어있는 정수 배열 numbers와 정수 K가 주어질 때, k번째로 공을 던지는 사람의 번호는 무엇인지 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(int[] numbers, int k) {

//        int idx = 0;
//
//        for (int i = 1; i < k; i++) {
//            idx = (idx + 2) % numbers.length;
//        }
//
//        return numbers[idx];

        int answer = 0;

        Queue<Integer> queue = new LinkedList<>();

        for (int number : numbers) {

            queue.add(number);
        }

        for (int i = 1; i < k; i++) {
            queue.add(queue.poll());
            queue.add(queue.poll());
        }

        answer = queue.poll();

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2, 3, 4}, 2));
        System.out.println(solution(new int[]{1, 2, 3, 4, 5, 6}, 5));
        System.out.println(solution(new int[]{1, 2, 3}, 3));
    }
}
