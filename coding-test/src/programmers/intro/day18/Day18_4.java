package programmers.intro.day18;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Day18_4 {

    /**
     * 영어 대소문자로 이루어진 문자열 my_string이 매개변수로 주어질 때,
     * my_string을 모두 소문자로 바꾸고 알파벳 순서대로 정렬한 문자열을 return 하도록 solution 함수를 완성해보세요.
     */

    static String solution(String my_string) {
//        String[] strSplit = my_string.split("");
//
//        for (int i = 0; i < strSplit.length; i++) {
//
//            strSplit[i] = strSplit[i].toLowerCase();
//        }
//
//        Arrays.sort(strSplit);
//
//        return Arrays.stream(strSplit).collect(Collectors.joining());

        char[] c = my_string.toLowerCase().toCharArray();
        Arrays.sort(c);

        return new String(c);
    }

    public static void main(String[] args) {
        System.out.println(solution("Bcad"));
        System.out.println(solution("heLLo"));
        System.out.println(solution("Python"));
    }
}
