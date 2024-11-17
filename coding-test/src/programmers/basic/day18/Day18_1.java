package programmers.basic.day18;

public class Day18_1 {

    /**
     * 문자열 myString이 주어집니다.
     * myString을 문자 "x"를 기준으로 나눴을 때
     * 나눠진 문자열 각각의 길이를 순서대로 저장한 배열을 return 하는 solution 함수를 완성해 주세요.
     */

    public static int[] solution(String myString) {
        String[] split = myString.split("x", myString.length());
        int[] answer = new int[split.length];

        for (int i = 0; i < answer.length; i++) {
            answer[i] = split[i].length();
        }

        return answer;
    }

    public static void main(String[] args) {
        int[] arr1 = solution("oxooxoxxox");
        int[] arr2 = solution("xabcxdefxghi");

        for (int i : arr1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("=================");

        for (int i : arr2) {
            System.out.print(i + " ");
        }
    }

}
