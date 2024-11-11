package programmers.basic.day12;

import java.util.ArrayList;
import java.util.List;

public class Day12_1 {

    /**
     * 정수 n과 정수 3개가 담긴 리스트 slicer
     * 그리고 정수 여러 개가 담긴 리스트 num_list가 주어집니다.
     * slicer에 담긴 정수를 차례대로 a, b, c라고 할 때,
     * n에 따라 다음과 같이 num_list를 슬라이싱 하려고 합니다.
     *
     * n = 1 : num_list의 0번 인덱스부터 b번 인덱스까지
     * n = 2 : num_list의 a번 인덱스부터 마지막 인덱스까지
     * n = 3 : num_list의 a번 인덱스부터 b번 인덱스까지
     * n = 4 : num_list의 a번 인덱스부터 b번 인덱스까지 c 간격으로
     *
     * 올바르게 슬라이싱한 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int n, int[] slicer, int[] num_list) {

        List<Integer> list = new ArrayList<>();

        // n이 1일 경우 -> num_list 0 ~ b 인덱스까지
        if (n == 1) {
            for (int i = 0; i <= slicer[1]; i++) {
                list.add(num_list[i]);
            }
        }
        // n이 2일 경우 -> num_list a ~ 마지막 인덱스까지
        else if (n == 2) {
            for (int i = slicer[0]; i < num_list.length; i++) {
                list.add(num_list[i]);
            }
        }
        // n이 3일 경우 -> num_list a ~ b 인덱스 까지
        else if (n == 3) {
            for (int i = slicer[0]; i <= slicer[1]; i++) {
                list.add(num_list[i]);
            }
        }
        // n이 4일 경우 -> num_list a ~ b 인덱스까지, c간격
        else if (n == 4) {
            for (int i = slicer[0]; i <= slicer[1]; i += slicer[2]) {
                list.add(num_list[i]);
            }
        }

        return list.stream().mapToInt(i -> i).toArray();

    }

    public static int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};

    public static void main(String[] args) {
        int[] arr1 = solution(3, new int[]{1, 5, 2}, arr);
        int[] arr2 = solution(4, new int[]{1, 5, 2}, arr);

        for (int i : arr1) {
            System.out.println(i);
        }

        System.out.println();

        for (int i : arr2) {
            System.out.println(i);
        }
    }
}
