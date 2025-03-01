package programmers.basic.day20;

import java.util.Arrays;

public class Day20_5 {

    /**
     * 정수로 이루어진 리스트 num_list가 주어집니다.
     * num_list에서 가장 작은 5개의 수를
     * 오름차순으로 담은 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int[] num_list) {

//        return Arrays.stream(num_list)
//                .sorted()
//                .limit(5)
//                .toArray();

        Arrays.sort(num_list);

        return Arrays.copyOfRange(num_list, 0, 5);
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{12, 4, 15, 46, 38, 1, 14})));
    }
}
