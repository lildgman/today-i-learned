package programmers.basic.day13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day13_2 {

    /**
     * 정수 리스트 num_list와 정수 n이 주어질 때,
     * num_list를 n 번째 원소 이후의 원소들과 n 번째까지의 원소들로 나눠
     * n 번째 원소 이후의 원소들을 n 번째까지의 원소들 앞에 붙인 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int[] num_list, int n) {

        List<Integer> list = new ArrayList<>();

        for (int i = n; i < num_list.length; i++) {
            list.add(num_list[i]);
        }

        for (int i = 0; i < n; i++) {
            list.add(num_list[i]);
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{2, 1, 6}, 1);
        int[] arr2 = solution(new int[]{5, 2, 1,7, 5}, 3);

        for (int i : arr1) {
            System.out.print(i + " ");
        }


        System.out.println("----------------");
        for (int i : arr2) {
            System.out.print(i + " ");
        }

    }
}
