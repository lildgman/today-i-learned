package programmers.intro.day6;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day6_3 {

    /**
     * 정수가 담긴 리스트 num_list가 주어질 때,
     * num_list의 원소 중 짝수와 홀수의 개수를 담은 배열을 return 하도록 solution 함수를 완성해보세요.
     */

    public static int[] solution(int[] num_list) {

//        int odd = 0;
//        int even = 0;
//
//        for (int i = 0; i < num_list.length; i++) {
//            if (num_list[i] % 2 != 0) {
//                odd++;
//            } else {
//                even++;
//            }
//        }
//
//        return new int[]{even, odd};

//        int[] answer = new int[2];
//
//        for (int i = 0; i < num_list.length; i++) {
//            answer[num_list[i] % 2]++;
//        }
//
//        return answer;

        return IntStream.of((int) Arrays.stream(num_list)
                                .filter(i -> i % 2 == 0)
                                .count(),
                        (int) Arrays.stream(num_list)
                                .filter(i -> i % 2 == 1)
                                .count())
                .toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 4, 5})));
        System.out.println(Arrays.toString(solution(new int[]{1, 3, 5, 7})));
    }
}
