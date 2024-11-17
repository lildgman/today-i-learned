package programmers.basic.day18;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day18_2 {

    /**
     * 문자열 myString이 주어집니다.
     * "x"를 기준으로 해당 문자열을 잘라내 배열을 만든 후
     * 사전순으로 정렬한 배열을 return 하는 solution 함수를 완성해 주세요.
     * <p>
     * 단, 빈 문자열은 반환할 배열에 넣지 않습니다.
     */

    public static String[] solution(String myString) {

//        String[] split = myString.split("x");
//        List<String> list = new ArrayList<>();
//
//        for (String s : split) {
//            if (!s.isEmpty()) {
//                list.add(s);
//            }
//        }
//
//        Collections.sort(list);
//
//        return list.toArray(String[]::new);

        return Arrays.stream(myString.split("x"))
                .filter(s -> !s.isEmpty())
                .sorted()
                .toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] arr1 = solution("axbxcxdx");
        String[] arr2 = solution("dxccxbbbxaaaa");

        for (String i : arr1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("=================");

        for (String i : arr2) {
            System.out.print(i + " ");
        }
    }
}
