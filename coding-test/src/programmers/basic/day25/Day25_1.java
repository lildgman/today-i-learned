package programmers.basic.day25;

public class Day25_1 {

    /**
     * 양의 정수 n이 매개변수로 주어집니다.
     * n × n 배열에 1부터 n2 까지 정수를 인덱스 [0][0]부터
     * 시계방향 나선형으로 배치한 이차원 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[][] solution(int n) {

        int[][] answer = new int[n][n];

        // 시계방향으로 이동하기 위한 배열
        int[] dx = {0, 1, 0, -1};// 우, 하, 좌, 상
        int[] dy = {1, 0, -1, 0};

        // 시작 위치
        int x = 0;
        int y = 0;

        // 시작 방향
        int dir = 0;

        for (int i = 1; i <= n * n; i++) {
            // 지금 위치에 i 채우기
            answer[x][y] = i;

            // 다음 위치
            int nextX = x + dx[dir];
            int nextY = y + dy[dir];

            // 방향전환하는 조건
            // 배열 크기 벗어날 경우
            // 숫자가 채워진 경우
            if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= n || answer[nextX][nextY] != 0) {

                dir = (dir + 1) % 4;
                nextX = x + dx[dir];
                nextY = y + dy[dir];
            }

            x = nextX;
            y = nextY;

        }

        return answer;
    }

    public static void main(String[] args) {
        int[][] arr = solution(4);

        for (int[] ints : arr) {
            for (int anInt : ints) {
                System.out.print(anInt);
            }
            System.out.println();
        }

    }
}
