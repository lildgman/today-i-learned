package programmers.intro.day13;

import java.util.Arrays;

public class Day13_2 {

    /**
     * 문자열 배열 strlist가 매개변수로 주어집니다.
     * strlist 각 원소의 길이를 담은 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(String[] strList) {

//        int[] answer = new int[strList.length];
//
//        for (int i = 0; i < strList.length; i++) {
//            answer[i] = strList[i].length();
//        }
//
//        return answer;

        return Arrays.stream(strList).mapToInt(String::length).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new String[]{"We", "are", "the", "world!"})));
        System.out.println(Arrays.toString(solution(new String[]{"I", "Love", "Programmers."})));
    }

}
