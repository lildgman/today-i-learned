package programmers.basic.day20;

import java.util.Arrays;

public class Day20_3 {

    /**
     * 문자열 배열 strArr이 주어집니다.
     * strArr의 원소들을 길이가 같은 문자열들끼리 그룹으로 묶었을 때
     * 가장 개수가 많은 그룹의 크기를 return 하는 solution 함수를 완성해 주세요.
     */

    public static int solution(String[] strArr) {
        int[] arr = new int[31];

        for (int i = 0; i < strArr.length; i++) {

            arr[strArr[i].length()]++;
        }

        return Arrays.stream(arr).max().orElse(0);
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"a","bc","d","efg","hi"}));
    }
}
