package dfs_bfs.bfs;

import java.util.LinkedList;
import java.util.Queue;

public class BfsExample {

    public static void main(String[] args) {
        int[][] board = {
                {1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };

        // 행의 개수
        int n = 7;
        // 열의 개수
        int m = 10;

        // 방향 백터
        int[] dx = {1, 0, -1, 0};
        int[] dy = {0, 1, 0, -1};

        // 방문 여부를 체크할 배열
        boolean[][] visit = new boolean[502][502];

        // 큐 선언
        Queue<Point> q = new LinkedList<>();

        // 0,0 방문 처리 및 큐에 추가
        visit[0][0] = true;
        q.offer(new Point(0, 0));

        while (!q.isEmpty()) {
            Point current = q.poll();
            System.out.print("(" + current.getX() + ", " + current.getY() + ") -> ");

            // 4방향 탐색
            for (int dir = 0; dir < 4; dir++) {
                int nextX = current.getX() + dx[dir];
                int nextY = current.getY() + dy[dir];

                if (nextX < 0 || nextX >= n || nextY < 0 || nextY >= m) {
                    continue;
                }

                if (visit[nextX][nextY] || board[nextX][nextY] != 1) {
                    continue;
                }

                visit[nextX][nextY] = true;
                q.offer(new Point(nextX, nextY));


            }
        }
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
