package programmers.intro.day15;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day15_2 {

    /**
     * 문자열 my_string과 정수 num1, num2가 매개변수로 주어질 때,
     * my_string에서 인덱스 num1과 인덱스 num2에 해당하는 문자를 바꾼 문자열을 return 하도록 solution 함수를 완성해보세요.
     */

    static String solution(String my_string, int num1, int num2) {

        String[] split = my_string.split("");

        String tmp = split[num1];
        split[num1] = split[num2];
        split[num2] = tmp;

        return Arrays.stream(split).collect(Collectors.joining());
    }

    public static void main(String[] args) {
        System.out.println(solution("hello",1,2));
        System.out.println(solution("i love you",3,6));
    }
}
