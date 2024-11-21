package programmers.basic.day22;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day22_1 {

    /**
     * 정수로 이루어진 문자열 n_str이 주어질 때,
     * n_str의 가장 왼쪽에 처음으로 등장하는 0들을 뗀 문자열을 return하도록 solution 함수를 완성해주세요.
     */

    public static String solution(String n_str) {

//        for (int i = 0; i < n_str.length(); i++) {
//
//            if (n_str.charAt(i) != '0') {
//                return n_str.substring(i);
//            }
//        }
//
//        return "0";

        return "" + Integer.parseInt(n_str);

    }

    public static void main(String[] args) {
        System.out.println(solution("0010"));
        System.out.println(solution("854020"));
    }
}
