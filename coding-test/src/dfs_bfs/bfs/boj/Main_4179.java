package dfs_bfs.bfs.boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main_4179 {

    /**
     * 지훈이는 미로에서 일을 한다. 지훈이를 미로에서 탈출하도록 도와주자!
     * 미로에서의 지훈이의 위치와 불이 붙은 위치를 감안해서 지훈이가 불에 타기전에 탈출할 수 있는지의 여부, 그리고 얼마나 빨리 탈출할 수 있는지를 결정해야한다.
     * 지훈이와 불은 매 분마다 한칸씩 수평또는 수직으로(비스듬하게 이동하지 않는다) 이동한다.
     * 불은 각 지점에서 네 방향으로 확산된다.
     * 지훈이는 미로의 가장자리에 접한 공간에서 탈출할 수 있다.
     * 지훈이와 불은 벽이 있는 공간은 통과하지 못한다.
     * <p>
     * 입력
     * 입력의 첫째 줄에는 공백으로 구분된 두 정수 R과 C가 주어진다.
     * 단, 1 ≤ R, C ≤ 1000 이다. R은 미로 행의 개수, C는 열의 개수이다.
     * 다음 입력으로 R줄동안 각각의 미로 행이 주어진다.
     * 각각의 문자들은 다음을 뜻한다.
     * #: 벽
     * .: 지나갈 수 있는 공간
     * J: 지훈이의 미로에서의 초기위치 (지나갈 수 있는 공간)
     * F: 불이 난 공간
     * <p>
     * J는 입력에서 하나만 주어진다.
     * <p>
     * 출력
     * 지훈이가 불이 도달하기 전에 미로를 탈출 할 수 없는 경우 IMPOSSIBLE 을 출력한다.
     * 지훈이가 미로를 탈출할 수 있는 경우에는 가장 빠른 탈출시간을 출력한다.
     */

    // 행
    static int r;
    // 열
    static int c;

    static String[][] maze;
    // 지훈이가 지나온 자리
    static int[][] jihunRoute;
    // 불이 지나온 자리
    static int[][] fireRoute;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());

        maze = new String[r][c];
        jihunRoute = new int[r][c];
        fireRoute = new int[r][c];

        for (int i = 0; i < r; i++) {
            Arrays.fill(fireRoute[i], -1);
            Arrays.fill(jihunRoute[i], -1);
        }

        Queue<Point> fire = new LinkedList<>();
        Queue<Point> jihun = new LinkedList<>();

        for (int i = 0; i < r; i++) {
            String[] split = br.readLine().split("");
            for (int j = 0; j < c; j++) {
                maze[i][j] = split[j];

                if (maze[i][j].equals("F")) {
                    fire.offer(new Point(i, j));
                    fireRoute[i][j] = 0;
                }

                if (maze[i][j].equals("J")) {
                    jihun.offer(new Point(i, j));
                    jihunRoute[i][j] = 0;
                }
            }
        }

        while (!fire.isEmpty()) {

            Point currentFireLocation = fire.poll();
            int x = currentFireLocation.getX();
            int y = currentFireLocation.getY();

            for (int i = 0; i < 4; i++) {

                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if (nextX >= 0 && nextX < r && nextY >= 0 && nextY < c
                        && fireRoute[nextX][nextY] == -1
                        && !maze[nextX][nextY].equals("#")) {
                    fireRoute[nextX][nextY] = fireRoute[x][y] + 1;
                    fire.offer(new Point(nextX, nextY));
                }

            }
        }

        boolean escaped = false;
        int escapeTime = -1;

        while (!jihun.isEmpty() && !escaped) {

            Point currentJihunLocation = jihun.poll();
            int x = currentJihunLocation.getX();
            int y = currentJihunLocation.getY();

            if (x == 0 || x == r - 1 || y == 0 || y == c - 1) {
                escapeTime = jihunRoute[x][y] + 1;
                escaped = true;
                break;
            }

            for (int i = 0; i < 4; i++) {
                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if (nextX >= 0 && nextX < r && nextY >= 0 && nextY < c
                        && fireRoute[nextX][nextY] == -1
                        && !maze[nextX][nextY].equals("#")
                        && (fireRoute[nextX][nextY] != -1
                        || jihunRoute[nextX][nextY] + 1 < fireRoute[nextX][nextY])) {
                    jihunRoute[nextX][nextY] = jihunRoute[x][y] + 1;
                    jihun.offer(new Point(nextX, nextY));
                }
            }
        }

        System.out.println(escaped ? escapeTime : "IMPOSSIBLE");

    }

    static class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }
    }

}
