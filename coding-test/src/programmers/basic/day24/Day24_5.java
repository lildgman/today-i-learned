package programmers.basic.day24;

import java.util.Arrays;

public class Day24_5 {

    /**
     * 정수 n이 매개변수로 주어질 때, 다음과 같은 n × n 크기의 이차원 배열 arr를 return 하는 solution 함수를 작성해 주세요.
     * <p>
     * arr[i][j] (0 ≤ i, j < n)의 값은 i = j라면 1, 아니라면 0입니다.
     */

    public static int[][] solution(int n) {
        int[][] answer = new int[n][n];

        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                if (i == j) {
                    answer[i][j] = 1;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(solution(3)));
        System.out.println(Arrays.toString(solution(6)));
        System.out.println(Arrays.toString(solution(1)));
    }
}
