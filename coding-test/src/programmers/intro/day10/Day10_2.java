package programmers.intro.day10;

import java.util.Arrays;

public class Day10_2 {

    /**
     * 정수 배열 num_list와 정수 n이 매개변수로 주어집니다.
     * num_list를 다음 설명과 같이 2차원 배열로 바꿔 return하도록 solution 함수를 완성해주세요.
     * <p>
     * num_list가 [1, 2, 3, 4, 5, 6, 7, 8] 로 길이가 8이고 n이 2이므로
     * num_list를 2 * 4 배열로 다음과 같이 변경합니다.
     * 2차원으로 바꿀 때에는 num_list의 원소들을 앞에서부터 n개씩 나눠 2차원 배열로 변경합니다.
     */

    static int[][] solution(int[] num_list, int n) {
        int[][] answer = new int[num_list.length / n][n];

        int idx = 0;
        for (int i = 0; i < answer.length; i++) {
            for (int j = 0; j < answer[i].length; j++) {
                answer[i][j] = num_list[idx++];
            }
        }


        return answer;
    }

    public static void main(String[] args) {

        int[][] arr = solution(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 2);
        int[][] arr2 = solution(new int[]{100, 95, 2, 4, 5, 6, 18, 33, 948}, 3);

        for (int[] ints : arr) {
            System.out.println(Arrays.toString(ints));
        }

        for (int[] ints : arr2) {
            System.out.println(Arrays.toString(ints));

        }
    }
}
