package programmers.intro.day14;

public class Day14_4 {

    /**
     * 문자열 my_string이 매개변수로 주어질 때,
     * 대문자는 소문자로 소문자는 대문자로 변환한 문자열을 return하도록 solution 함수를 완성해주세요.
     */

    static String solution(String my_string) {

        String answer = "";

        char[] charArray = my_string.toCharArray();

        for (char c : charArray) {

            if (Character.isUpperCase(c)) {
                answer += Character.toLowerCase(c);
            } else if (Character.isLowerCase(c)) {
                answer += Character.toUpperCase(c);
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution("cccCCC"));
        System.out.println(solution("abCdEfghIJ"));
    }
}
