package programmers.intro.day13;

import java.util.Stack;

public class Day13_1 {

    /**
     * 숫자와 "Z"가 공백으로 구분되어 담긴 문자열이 주어집니다.
     * 문자열에 있는 숫자를 차례대로 더하려고 합니다.
     * 이 때 "Z"가 나오면 바로 전에 더했던 숫자를 뺀다는 뜻입니다.
     * 숫자와 "Z"로 이루어진 문자열 s가 주어질 때, 머쓱이가 구한 값을 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(String s) {

//        int answer = 0;
//        String[] split = s.split(" ");
//
//        for (int i = 0; i < split.length; i++) {
//
//            if (!split[i].equals("Z")) {
//                answer += Integer.parseInt(split[i]);
//            } else {
//                answer -= Integer.parseInt(split[i - 1]);
//            }
//        }
//
//        return answer;

        int answer = 0;
        Stack<Integer> stack = new Stack<>();

        for (String str : s.split(" ")) {

            if (str.equals("Z")) {
                stack.pop();
            } else {
                stack.push(Integer.parseInt(str));
            }
        }

        for (Integer i : stack) {
            answer += i;
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution("1 2 Z 3"));
        System.out.println(solution("10 20 30 40"));
    }
}
