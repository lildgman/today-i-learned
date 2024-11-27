package programmers.intro.day6;

public class Day6_1 {

    /**
     * 문자열 my_string이 매개변수로 주어집니다.
     * my_string을 거꾸로 뒤집은 문자열을 return하도록 solution 함수를 완성해주세요.
     */

    public static String solution(String my_string) {
        return new StringBuilder(my_string).reverse().toString();
    }

    public static void main(String[] args) {
        System.out.println(solution("jaron"));
        System.out.println(solution("bread"));
    }

}
