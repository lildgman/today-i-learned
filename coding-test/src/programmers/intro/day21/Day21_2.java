package programmers.intro.day21;

public class Day21_2 {

    /**
     * 지뢰는 2차원 배열 board에 1로 표시되어 있고 board에는 지뢰가 매설 된 지역 1과, 지뢰가 없는 지역 0만 존재합니다.
     * 지뢰가 매설된 지역의 지도 board가 매개변수로 주어질 때, 안전한 지역의 칸 수를 return하도록 solution 함수를 완성해주세요.
     */

    static final int[] dx = {-1, 1, 0, 0, -1, -1, 1, 1};
    static final int[] dy = {0, 0, -1, 1, -1, 1, -1, 1};
    static int solution(int[][] board) {

        int answer = 0;
        boolean[][] dangerArea = new boolean[board.length][board.length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {

                    dangerArea[i][j] = true;

                    for (int k = 0; k < 8; k++) {

                        int nextX = i + dx[k];
                        int nextY = j + dy[k];

                        if (nextX >= 0 && nextX < board.length && nextY >= 0 && nextY < board[i].length) {
                            dangerArea[nextX][nextY] = true;
                        }
                    }

                }
            }
        }

        for (boolean[] booleans : dangerArea) {
            for (boolean danger : booleans) {
                if (!danger) {
                    answer++;
                }
            }
        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0,0,0,0,0},{0,0,0,0,0},{0,0,0,0,0},{0,0,1,0,0},{0,0,0,0,0}}));
    }


}
