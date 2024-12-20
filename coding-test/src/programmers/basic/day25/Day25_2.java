package programmers.basic.day25;

public class Day25_2 {

    /**
     * n × n 크기의 이차원 배열 arr이 매개변수로 주어질 때,
     * arr이 다음을 만족하면 1을 아니라면 0을 return 하는 solution 함수를 작성해 주세요.
     * <p>
     * 0 ≤ i, j < n인 정수 i, j에 대하여 arr[i][j] = arr[j][i]
     */

    public static int solution(int[][] arr) {

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                if (arr[i][j] != arr[j][i]) {
                    return 0;
                }
            }
        }

        return 1;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{5, 192, 33}, {192, 72, 95}, {33, 95, 999}}));
    }
}
