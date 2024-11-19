package programmers.basic.day20;

import java.util.Arrays;

public class Day20_2 {

    /**
     * 이 문제에서 두 정수 배열의 대소관계를 다음과 같이 정의합니다.
     * <p>
     * 두 배열의 길이가 다르다면, 배열의 길이가 긴 쪽이 더 큽니다.
     * 배열의 길이가 같다면 각 배열에 있는 모든 원소의 합을 비교하여 다르다면 더 큰 쪽이 크고, 같다면 같습니다.
     * 두 정수 배열 arr1과 arr2가 주어질 때, 위에서 정의한 배열의 대소관계에 대하여 arr2가 크다면 -1, arr1이 크다면 1, 두 배열이 같다면 0을 return 하는 solution 함수를 작성해 주세요.
     */

    public static int solution(int[] arr1, int[] arr2) {

//        if (arr1.length != arr2.length) {
//            int max = Math.max(arr1.length, arr2.length);
//            return max == arr1.length ? 1 : -1;
//        } else {
//            int arr1Sum = Arrays.stream(arr1).sum();
//            int arr2Sum = Arrays.stream(arr2).sum();
//
//            if (arr1Sum == arr2Sum) {
//                return 0;
//            } else {
//                int max = Math.max(arr1Sum, arr2Sum);
//                return max == arr1Sum ? 1 : -1;
//            }
//        }

        int answer = Integer.compare(arr1.length, arr2.length);

        if (answer == 0) {
            answer = Integer.compare(Arrays.stream(arr1).sum(), Arrays.stream(arr2).sum());
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{49, 13}, new int[]{70, 11, 2}));
        System.out.println(solution(new int[]{100, 17, 84, 1}, new int[]{55, 12, 65, 36}));
        System.out.println(solution(new int[]{1, 2, 3, 4, 5}, new int[]{3, 3, 3, 3, 3}));
    }
}
