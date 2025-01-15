package dfs_bfs.dfs;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class DfsExample {

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

    static int[][] board = {
            {1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    static int n = 7;
    static int m = 10;

    private static final boolean[][] visited = new boolean[n][m];

    // 상,하,좌,우
    private static final int[] dx = {-1, 1, 0, 0};
    private static final int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) {

        Deque<Point> stack = new ArrayDeque<>();
        Point start = new Point(0, 0);
        stack.push(start);

        // 방문처리
        visited[start.getX()][start.getX()] = true;

        // dfs
        while (!stack.isEmpty()) {
            Point currentPoint = stack.pop();
            System.out.printf("(%d, %d) -> ", currentPoint.getX(), currentPoint.getY());

            // 4방향 탐색
            for (int i = 0; i < 4; i++) {
                int nextX = currentPoint.getX() + dx[i];
                int nextY = currentPoint.getY() + dy[i];

                if (nextX >= 0 && nextX < n && nextY >= 0 && nextY < m
                        && !visited[nextX][nextY] && board[nextX][nextY] == 1) {
                    visited[nextX][nextY] = true;
                    stack.push(new Point(nextX, nextY));
                }
            }
        }


    }
}
