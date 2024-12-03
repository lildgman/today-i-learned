package dfs_bfs.bfs.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_2178 {

    // https://www.acmicpc.net/problem/2178

    /**
     * N×M크기의 배열로 표현되는 미로가 있다.
     *
     * 미로에서 1은 이동할 수 있는 칸을 나타내고, 0은 이동할 수 없는 칸을 나타낸다.
     * 이러한 미로가 주어졌을 때, (1, 1)에서 출발하여 (N, M)의 위치로 이동할 때 지나야 하는 최소의 칸 수를 구하는 프로그램을 작성하시오.
     * 한 칸에서 다른 칸으로 이동할 때, 서로 인접한 칸으로만 이동할 수 있다.
     *
     * 위의 예에서는 15칸을 지나야 (N, M)의 위치로 이동할 수 있다. 칸을 셀 때에는 시작 위치와 도착 위치도 포함한다.
     *
     * 입력
     * 첫째 줄에 두 정수 N, M(2 ≤ N, M ≤ 100)이 주어진다. 다음 N개의 줄에는 M개의 정수로 미로가 주어진다. 각각의 수들은 붙어서 입력으로 주어진다.
     *
     * 출력
     * 첫째 줄에 지나야 하는 최소의 칸 수를 출력한다. 항상 도착위치로 이동할 수 있는 경우만 입력으로 주어진다.
     */

    static int n;
    static int m;

    static int[][] board;
    static boolean[][] visit;

    static int[] dx = {-1, 1, 0, 0};
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n][m];
        visit = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            String[] split = br.readLine().split("");
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(split[j]);
            }
        }

        System.out.println(bfs_2178(new Node(0, 0)));
    }

    private static int bfs_2178(Node startNode) {
        Queue<Node> queue = new LinkedList<>();
        queue.offer(startNode);

        visit[startNode.getX()][startNode.getY()] = true;

        while (!queue.isEmpty()) {

            Node currentNode = queue.poll();

            int x = currentNode.getX();
            int y = currentNode.getY();

            // 네 방향 탐색
            for (int i = 0; i < 4; i++) {

                int nextX = x + dx[i];
                int nextY = y + dy[i];

                if (x == n - 1 && y == m - 1) {
                    return board[x][y];
                }

                // nextX와 nextY가 미로범위 내에 있을 경우
                // 다음 좌표의 board값을 현재 값과 더하기
                if (nextX >= 0 && nextX < n
                        && nextY >= 0 && nextY < m
                        && board[nextX][nextY] == 1
                        && !visit[nextX][nextY]) {

                    queue.offer(new Node(nextX, nextY));
                    visit[nextX][nextY] = true;
                    board[nextX][nextY] += board[x][y];

                }
            }

        }

        return -1;
    }

    static class Node {
        private int x;
        private int y;

        public Node(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}


