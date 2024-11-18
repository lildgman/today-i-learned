package programmers.basic.day19;

import java.util.Arrays;

public class Day19_1 {

    /**
     * 임의의 문자열이 주어졌을 때 문자 "a", "b", "c"를 구분자로 사용해 문자열을 나누고자 합니다.
     * <p>
     * 예를 들어 주어진 문자열이 "baconlettucetomato"라면 나눠진 문자열 목록은 ["onlettu", "etom", "to"] 가 됩니다.
     * <p>
     * 문자열 myStr이 주어졌을 때 위 예시와 같이 "a", "b", "c"를 사용해 나눠진 문자열을 순서대로 저장한 배열을 return 하는 solution 함수를 완성해 주세요.
     * <p>
     * 단, 두 구분자 사이에 다른 문자가 없을 경우에는 아무것도 저장하지 않으며, return할 배열이 빈 배열이라면 ["EMPTY"]를 return 합니다
     */

    public static String[] solution(String myStr) {
//        String[] answer = {};
//
//        String replaced = myStr.replaceAll("[a-c]", " ");
//        if (replaced.trim().isEmpty()) {
//            answer = new String[]{"EMPTY"};
//        } else {
//            answer = replaced.trim().split("\\s+");
//        }
//
//        return answer;

        String[] arr = Arrays.stream(myStr.split("[a-c]+")).filter(s -> !s.isEmpty()).toArray(String[]::new);
        return arr.length == 0 ? new String[]{"EMPTY"} : arr;
    }

    public static void main(String[] args) {
        String[] arr1 = solution("baconlettucetomato");
        String[] arr2 = solution("abcd");
        String[] arr3 = solution("cabab");

        for (String s : arr1) {

            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("===================");

        for (String s : arr2) {
            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("===================");

        for (String s : arr3) {
            System.out.print(s + " ");
        }
    }
}
