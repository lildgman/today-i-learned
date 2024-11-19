package programmers.basic.day20;

import java.util.Arrays;

public class Day20_1 {

    /**
     * 정수 배열 arr이 매개변수로 주어집니다.
     * arr의 길이가 2의 정수 거듭제곱이 되도록 arr 뒤에 정수 0을 추가하려고 합니다.
     * arr에 최소한의 개수로 0을 추가한 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[] solution(int[] arr) {
        int n = 1;

        while (n < arr.length) {
            n *= 2;
        }

        return Arrays.copyOf(arr, n);
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{1, 2, 3, 4, 5, 6});
        int[] arr2 = solution(new int[]{58, 172, 746, 89});

        for (int i : arr1) {

            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("=======================");

        for (int i : arr2) {
            System.out.print(i + " ");
        }
    }
}
