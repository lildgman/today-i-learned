package programmers.basic.day11;

import java.util.ArrayList;
import java.util.List;

public class Day11_4 {

    /**
     * 정수 start_num와 end_num가 주어질 때,
     * start_num에서 end_num까지 1씩 감소하는 수들을
     * 차례로 담은 리스트를 return하도록 solution 함수를 완성해주세요.
     */

    public static int[] solution(int start_num, int end_num) {
        List<Integer> list = new ArrayList<>();

        for (int i = start_num; i >= end_num; i--) {
            list.add(i);
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] solution = solution(10, 3);

        for (int i : solution) {
            System.out.println(i);
        }

        System.out.println();
    }


}
