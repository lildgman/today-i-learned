package programmers.intro.day20;

import java.util.Arrays;

public class Day20_3 {

    /**
     * 정수 배열 numbers가 매개변수로 주어집니다.
     * numbers의 원소 중 두 개를 곱해 만들 수 있는 최댓값을 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(int[] numbers) {

//        int max1 = Integer.MIN_VALUE;
//        int max2 = Integer.MIN_VALUE;
//        int min1 = Integer.MAX_VALUE;
//        int min2 = Integer.MAX_VALUE;
//
//        for (int num : numbers) {
//
//            // 최대값 찾기
//            if (num > max1) {
//                max2 = max1;
//                max1 = num;
//            } else if (num > max2) {
//                max2 = num;
//            }
//
//            // 음수의 경우
//            if (num < min1) {
//                min2 = min1;
//                min1 = num;
//            } else if (num < min2) {
//                min2 = num;
//            }
//        }
//
//        return Math.max(max1 * max2, min1 * min2);

        Arrays.sort(numbers);

        return Math.max(numbers[0] * numbers[1], numbers[numbers.length - 1] * numbers[numbers.length - 2]);
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2, -3, 4, -5}));
        System.out.println(solution(new int[]{0, -31, 24, 10, 1, 9}));
        System.out.println(solution(new int[]{10, 20, 30, 5, 5, 20, 5}));

    }
}
