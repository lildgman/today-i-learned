package programmers.intro.day19;

import java.util.Arrays;

public class Day19_2 {

    /**
     * 문자열 my_str과 n이 매개변수로 주어질 때,
     * my_str을 길이 n씩 잘라서 저장한 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static String[] solution(String my_str, int n) {
        String[] answer = new String[(int) Math.ceil((double) my_str.length() / n)];

        for (int i = 0; i < answer.length; i++) {

            int start = i * n;
            int end = Math.min(start + n, my_str.length());

            answer[i] = my_str.substring(start, end);
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution("abc1Addfggg4556b", 6)));
        System.out.println(Arrays.toString(solution("abcdef123", 3)));
    }
}
