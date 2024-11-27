package programmers.intro.day7;

import java.util.stream.IntStream;

public class Day7_4 {

    /**
     * 정수 n이 주어질 때,
     * n이하의 짝수를 모두 더한 값을 return 하도록 solution 함수를 작성해주세요.
     */

    public static int solution(int n) {

        int answer = 0;

//        for (int i = 1; i <= n; i++) {
//            if (i % 2 == 0) {
//                answer += i;
//            }
//        }

//        for (int i = 2; i <= n; i += 2) {
//            answer += i;
//        }
//
//        return answer;

        return IntStream.rangeClosed(0, n)
                .filter(i -> i % 2 == 0)
                .sum();
    }

    public static void main(String[] args) {

        System.out.println(solution(10));
        System.out.println(solution(4));
    }
}
