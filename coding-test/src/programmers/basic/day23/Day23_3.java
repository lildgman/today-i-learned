package programmers.basic.day23;

import java.util.Arrays;

public class Day23_3 {

    /**
     * 정수 리스트 num_list와 찾으려는 정수 n이 주어질 때,
     * num_list안에 n이 있으면 1을 없으면 0을 return하도록 solution 함수를 완성해주세요.
     */

    public static int solution(int[] num_list, int n) {

//        for (int i = 0; i < num_list.length; i++) {
//            if (num_list[i] == n) {
//                return 1;
//            }
//        }
//
//        return 0;

        return Arrays.stream(num_list)
                .anyMatch(i -> i == n) ? 1 : 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1,2,3,4,5},3));
        System.out.println(solution(new int[]{15, 98, 23, 2, 15},20));
    }
}
