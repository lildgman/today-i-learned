package programmers.basic.day11;

import java.util.ArrayList;
import java.util.List;

public class Day11_2 {

    /**
     * 정수 n과 k가 주어졌을 때,
     * 1 이상 n이하의 정수 중에서 k의 배수를 오름차순으로 저장한 배열을 return 하는 solution 함수를 완성해 주세요.
     */

    public static int[] solution(int n, int k) {
        int[] answer = {};

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % k == 0) {
                list.add(i);
            }
        }

        answer = list.stream().mapToInt(i -> i).toArray();

        return answer;
    }

    public static void main(String[] args) {
        int[] arr = solution(10, 3);

        for (int i : arr) {
            System.out.println("i = " + i);
        }
    }
}
