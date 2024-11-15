package programmers.basic.day16;

public class Day16_1 {

    /**
     * 알파벳으로 이루어진 문자열 myString이 주어집니다.
     * 모든 알파벳을 대문자로 변환하여 return 하는 solution 함수를 완성해 주세요.
     */

    public static String solution(String myString) {
        return myString.toUpperCase();
    }

    public static void main(String[] args) {
        System.out.println(solution("aBcDeFg"));
        System.out.println(solution("AAA"));
    }
}
