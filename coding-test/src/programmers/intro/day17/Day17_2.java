package programmers.intro.day17;

import java.util.Arrays;

public class Day17_2 {

    /**
     * 정수 n과 정수 배열 numlist가 매개변수로 주어질 때,
     * numlist에서 n의 배수가 아닌 수들을 제거한 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(int n, int[] numlist) {
//        List<Integer> answer = new ArrayList<>();
//
//        for (int i : numlist) {
//
//            if (i % n == 0) {
//                answer.add(i);
//            }
//        }
//
//        return answer.stream().mapToInt(Integer::intValue).toArray();

        return Arrays.stream(numlist).filter(value -> value % n == 0).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(3, new int[]{4, 5, 6, 7, 8, 9, 10, 11, 12})));
        System.out.println(Arrays.toString(solution(5, new int[]{1, 9, 3, 10, 13, 5})));
        System.out.println(Arrays.toString(solution(12, new int[]{2, 100, 120, 600, 12, 12})));

    }
}
