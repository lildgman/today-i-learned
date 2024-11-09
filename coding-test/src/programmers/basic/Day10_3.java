package programmers.basic;

public class Day10_3 {

    /**
     * 문자열 my_string과 정수 s, e가 매개변수로 주어질 때,
     * my_string에서 인덱스 s부터 인덱스 e까지를 뒤집은 문자열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static String solution(String my_string, int s, int e) {
        StringBuilder sb = new StringBuilder(my_string);
        StringBuilder substring = new StringBuilder(sb.substring(s, e+1));
        StringBuilder reverse = substring.reverse();

        sb.replace(s, e+1, reverse.toString());

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(solution("Progra21Sremm3",6,12));
        System.out.println(solution("Stanley1yelnatS",4,10));
    }
}
