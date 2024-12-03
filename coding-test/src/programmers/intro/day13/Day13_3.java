package programmers.intro.day13;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day13_3 {

    /**
     * 문자열 my_string이 매개변수로 주어집니다.
     * my_string에서 중복된 문자를 제거하고
     * 하나의 문자만 남긴 문자열을 return하도록 solution 함수를 완성해주세요.
     */

    static String solution(String my_string) {

        return Arrays.stream(my_string.split("")).distinct().collect(Collectors.joining());
    }

    public static void main(String[] args) {
        System.out.println(solution("people"));
        System.out.println(solution("We are the world"));
    }
}
