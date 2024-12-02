package programmers.intro.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day12_2 {

    /**
     * 문자열 my_string이 매개변수로 주어질 때,
     * my_string 안에 있는 숫자만 골라 오름차순 정렬한 리스트를 return 하도록 solution 함수를 작성해보세요.
     */

    static int[] solution(String my_string) {

        List<Integer> list = new ArrayList<>();

        String[] split = my_string.split("");

        for (String str : split) {
            if (str.charAt(0) < 97) {
                list.add(Integer.parseInt(str));
            }
        }

        Collections.sort(list);

        return list.stream().mapToInt(i -> i).toArray();
    }


    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution("hi12392")));
        System.out.println(Arrays.toString(solution("p2o4i8gj2")));
        System.out.println(Arrays.toString(solution("abcde0")));
    }
}
