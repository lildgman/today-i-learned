package programmers.intro.day17;

import java.util.Arrays;

public class Day17_4 {

    /**
     * 덧셈, 뺄셈 수식들이 'X [연산자] Y = Z' 형태로 들어있는 문자열 배열 quiz가 매개변수로 주어집니다.
     * 수식이 옳다면 "O"를 틀리다면 "X"를 순서대로 담은 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static String[] solution(String[] quiz) {
//        String[] answer = new String[quiz.length];
//
//        for (int i = 0; i < quiz.length; i++) {
//            String[] quizSplit = quiz[i].split(" ");
//
//            int num1 = Integer.parseInt(quizSplit[0]);
//            String oper = quizSplit[1];
//            int num2 = Integer.parseInt(quizSplit[2]);
//            int quizResult = Integer.parseInt(quizSplit[4]);
//
//            if (oper.equals("+")) {
//                if (num1 + num2 == quizResult) {
//                    answer[i] = "O";
//                } else {
//                    answer[i] = "X";
//                }
//
//            } else if (oper.equals("-")) {
//                if (num1 - num2 == quizResult) {
//                    answer[i] = "O";
//                } else {
//                    answer[i] = "X";
//                }
//            }
//        }
//
//        return answer;

        for (int i = 0; i < quiz.length; i++) {

            String[] quizSplit = quiz[i].split(" ");
            int result = Integer.parseInt(quizSplit[0]) + (Integer.parseInt(quizSplit[2]) * (quizSplit[1].equals("+") ? 1 : -1));
            quiz[i] = result == Integer.parseInt(quizSplit[4]) ? "O" : "X";

        }

        return quiz;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[]{"3 - 4 = -3", "5 + 6 = 11"})));
        System.out.println(Arrays.toString(solution(new String[]{"19 - 6 = 13", "5 + 66 = 71", "5 - 15 = 63", "3 - 1 = 2"})));
    }
}
