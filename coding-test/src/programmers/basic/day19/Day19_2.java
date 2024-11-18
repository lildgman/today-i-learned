package programmers.basic.day19;

import java.util.ArrayList;
import java.util.List;

public class Day19_2 {

    /**
     * 아무 원소도 들어있지 않은 빈 배열 X가 있습니다.
     * 양의 정수 배열 arr가 매개변수로 주어질 때,
     * arr의 앞에서부터 차례대로 원소를 보면서
     * 원소가 a라면 X의 맨 뒤에 a를 a번 추가하는 일을 반복한 뒤의 배열 X를 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[] solution(int[] arr) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            int count = 0;
            while (count != arr[i]) {
                list.add(arr[i]);
                count++;
            }
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{5, 1, 4});
        int[] arr2 = solution(new int[]{6,6});
        int[] arr3 = solution(new int[]{1});

        for (Integer s : arr1) {

            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("===================");

        for (Integer s : arr2) {
            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("===================");

        for (Integer s : arr3) {
            System.out.print(s + " ");
        }
    }
}
