package programmers.basic.day15;

public class Day15_5 {

    /**
     * 알파벳으로 이루어진 문자열 myString과 pat이 주어집니다.
     * myString의 연속된 부분 문자열 중
     * pat이 존재하면 1을 그렇지 않으면 0을 return 하는 solution 함수를 완성해 주세요.
     *
     * 단, 알파벳 대문자와 소문자는 구분하지 않습니다.
     *
     */

    public static int solution(String myString, String pat) {
        String lowerCaseMyString = myString.toLowerCase();
        String lowerCasePat = pat.toLowerCase();

        return lowerCaseMyString.contains(lowerCasePat) ? 1 : 0;

    }

    public static void main(String[] args) {
        System.out.println(solution("aaAA", "aaaaa"));
        System.out.println(solution("AbCdEfG", "aBc"));
    }
}
