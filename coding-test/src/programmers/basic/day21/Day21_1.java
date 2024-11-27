package programmers.basic.day21;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Day21_1 {

    /**
     * 정수로 이루어진 리스트 num_list가 주어집니다.
     * num_list에서 가장 작은 5개의 수를 제외한 수들을 오름차순으로 담은 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int[] num_list) {

//        Arrays.sort(num_list);
//
//        return Arrays.copyOfRange(num_list, 5, num_list.length);

//        return Arrays.stream(num_list)
//                .sorted()
//                .skip(5)
//                .toArray();

        return IntStream.of(num_list)
                .sorted()
                .skip(5)
                .toArray();
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(solution(new int[]{12, 4, 15, 46, 38, 1, 14, 56, 32, 10})));
    }
}