package programmers.basic.day13;

import java.util.ArrayList;

public class Day13_5 {

    /**
     * 정수 리스트 num_list와 정수 n이 주어질 때,
     * num_list의 첫 번째 원소부터 마지막 원소까지 n개 간격으로 저장되어있는 원소들을
     * 차례로 담은 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int[] num_list, int n) {

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < num_list.length; i += n) {
            list.add(num_list[i]);
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{4, 2, 6, 1, 7, 6}, 2);
        int[] arr2 = solution(new int[]{4, 2, 6, 1, 7, 6}, 4);

        for (int i : arr1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("================");

        for (int i : arr2) {
            System.out.print(i + " ");
        }
    }
}
