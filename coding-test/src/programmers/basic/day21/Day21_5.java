package programmers.basic.day21;

public class Day21_5 {

    /**
     * 숫자로만 이루어진 문자열 n_str이 주어질 때,
     * n_str을 정수로 변환하여 return하도록 solution 함수를 완성해주세요.
     */

    public static int solution(String n_str) {
        return Integer.parseInt(n_str);
    }

    public static void main(String[] args) {

        System.out.println(solution("10"));
        System.out.println(solution("8542"));
    }
}
