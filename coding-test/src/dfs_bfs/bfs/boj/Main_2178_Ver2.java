package dfs_bfs.bfs.boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;


public class Main_2178_Ver2 {

    /**
     * N×M크기의 배열로 표현되는 미로가 있다.
     * 미로에서 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다.
     * 이러한 미로가 주어졌을 때, (1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오.
     * 한 칸에서 다른 칸으로 이동할 때, 서로 인접한 칸으로만 이동할 수 있다.
     * <p>
     * 입력
     * 첫째 줄에 두 정수 N, M(2 ≤ N, M ≤ 100)이 주어진다.
     * 다음 N개의 줄에는 M개의 정수로 미로가 주어진다.
     * 각각의 수들은 붙어서 입력으로 주어진다.
     * <p>
     * 출력
     * 첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.
     */

    // 좌표를 표현하기 위한 클래스
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

    // 미로
    static int[][] maze;
    // 방문기록
    static boolean[][] visit;

    // 방향 벡터 (상, 하, 좌, 우)
    static int[] dx = {0, 0, -1, 1};
    static int[] dy = {-1, 1, 0, 0};

    static int n;
    static int m;

    public static void main(String[] args) throws IOException {
        // 입력을 위한 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력한 값을 나누기 위한 StringTokenizer
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        maze = new int[n][m];
        visit = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            String[] split = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                maze[i][j] = Integer.parseInt(split[j]);
            }
        }

        // 최소거리 찾기
        System.out.println(getMinDistance(new Point(0, 0)));
    }

    private static int getMinDistance(Point point) {

        // bfs 알고리즘을 위한 queue
        Queue<Point> queue = new LinkedList<>();
        queue.offer(point);

        // 방문처리
        visit[point.getX()][point.getY()] = true;

        // bfs 수행
        while (!queue.isEmpty()) {

            Point currentPoint = queue.poll();

            // 현재 좌표
            int x = currentPoint.getX();
            int y = currentPoint.getY();

            if (x == n - 1 && y == m - 1) {
                return maze[x][y];
            }

            // 상, 하, 좌, 우 탐색
            for (int i = 0; i < 4; i++) {

                int nextX = x + dx[i];
                int nextY = y + dy[i];

                // nextX와 nextY가 미로 범위안에 있고 방문하지 않은 길이라면
                if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m
                        && maze[nextX][nextY] == 1
                        && !visit[nextX][nextY]) {

                    queue.offer(new Point(nextX, nextY));
                    visit[nextX][nextY] = true;
                    maze[nextX][nextY] += maze[x][y];
                }

            }
        }

        return -1;
    }

}
