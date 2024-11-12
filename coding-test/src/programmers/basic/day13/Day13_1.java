package programmers.basic.day13;

import java.util.Arrays;

public class Day13_1 {

    /**
     * 정수 리스트 num_list와 정수 n이 주어질 때,
     * n 번째 원소부터 마지막 원소까지의 모든 원소를 담은 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int[] num_list, int n) {

        return Arrays.copyOfRange(num_list, n-1, num_list.length);
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{2, 1, 6}, 3);
        int[] arr2 = solution(new int[]{5,2,1,7,5}, 2);

        for (int i : arr1) {
            System.out.print(i + " ");
        }


        System.out.println("================");
        for (int i : arr2) {
            System.out.print(i + " ");
        }
    }
}
