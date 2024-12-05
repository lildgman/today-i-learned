package programmers.intro.day15;

import java.util.Stack;

public class Day15_3 {

    /**
     * 문자열 s가 매개변수로 주어집니다.
     * s에서 한 번만 등장하는 문자를 사전 순으로 정렬한 문자열을 return 하도록 solution 함수를 완성해보세요.
     * <p>
     * 한 번만 등장하는 문자가 없을 경우 빈 문자열을 return 합니다.
     */

    static String solution(String s) {

        String answer = "";

        int[] alphabetCount = new int[26];

        for (char c : s.toCharArray()) {
            alphabetCount[c - 'a']++;
        }

        for (int i = 0; i < alphabetCount.length; i++) {

            if (alphabetCount[i] == 1) {
                answer += (char)(i + 'a');
            }
        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(solution("abcabcadc"));
    }
}
