package programmers.basic.day21;

import java.util.Arrays;

public class Day21_4 {

    /**
     * 한 자리 정수로 이루어진 문자열 num_str이 주어질 때,
     * 각 자리수의 합을 return하도록 solution 함수를 완성해주세요.
     */

    public static int solution(String num_str) {
//        int answer = 0;
//        String[] split = num_str.split("");
//
//        for (int i = 0; i < split.length; i++) {
//            answer += Integer.parseInt(split[i]);
//        }
//
//        return answer;

        return Arrays.stream(num_str.split(""))
                .mapToInt(Integer::parseInt)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println(solution("123456789"));
        System.out.println(solution("1000000"));
    }
}
