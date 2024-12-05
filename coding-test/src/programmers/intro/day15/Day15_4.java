package programmers.intro.day15;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day15_4 {

    /**
     * 정수 n이 매개변수로 주어질 때,
     * n의 약수를 오름차순으로 담은 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(int n) {

        List<Integer> list = new ArrayList<>();

        for (int i = 1; i <= n; i++) {

            if (n % i == 0) {
                list.add(n / i);
            }
        }

        return list.stream().sorted().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(24)));
        System.out.println(Arrays.toString(solution(29)));
    }
}
