package programmers.basic.day24;

import java.util.Arrays;

public class Day24_3 {

    /**
     * 정수 배열 arr와 자연수 k가 주어집니다.
     * 만약 k가 홀수라면 arr의 모든 원소에 k를 곱하고, k가 짝수라면 arr의 모든 원소에 k를 더합니다.
     * 이러한 변환을 마친 후의 arr를 return 하는 solution 함수를 완성해 주세요.
     */

    public static int[] solution(int[] arr, int k) {

//        if (k % 2 != 0) {
//
//            for (int i = 0; i < arr.length; i++) {
//                arr[i] *= k;
//            }
//        } else {
//            for (int i = 0; i < arr.length; i++) {
//                arr[i] += k;
//            }
//        }
//
//        return arr;

        return Arrays.stream(arr).map(num -> k % 2 == 0 ? num + k : num * k).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 100, 99, 98}, 3)));
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 100, 99, 98}, 2)));
    }
}
