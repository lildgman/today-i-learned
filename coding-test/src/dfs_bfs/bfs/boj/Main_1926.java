package dfs_bfs.bfs.boj;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_1926 {

    // https://www.acmicpc.net/problem/1926

    /**
     * 어떤 큰 도화지에 그림이 그려져 있을 때, 그 그림의 개수와, 그 그림 중 넓이가 가장 넓은 것의 넓이를 출력하여라.
     * 단, 그림이라는 것은 1로 연결된 것을 한 그림이라고 정의하자.
     * 가로나 세로로 연결된 것은 연결이 된 것이고 대각선으로 연결이 된 것은 떨어진 그림이다.
     * 그림의 넓이란 그림에 포함된 1의 개수이다.
     * <p>
     * 입력
     * 첫째 줄에 도화지의 세로 크기 n(1 ≤ n ≤ 500)과 가로 크기 m(1 ≤ m ≤ 500)이 차례로 주어진다.
     * 두 번째 줄부터 n+1 줄 까지 그림의 정보가 주어진다. (단 그림의 정보는 0과 1이 공백을 두고 주어지며, 0은 색칠이 안된 부분, 1은 색칠이 된 부분을 의미한다)
     * <p>
     * 출력
     * 첫째 줄에는 그림의 개수, 둘째 줄에는 그 중 가장 넓은 그림의 넓이를 출력하여라.
     * 단, 그림이 하나도 없는 경우에는 가장 넓은 그림의 넓이는 0이다.
     */

    // 세로
    static int n;
    // 가로
    static int m;

    // 그림 2차원 배열
    static int[][] board;
    // 방문 테이블
    static boolean[][] visited;

    // 우, 좌, 하, 상 이동 벡터
    static int[] dx = {1, -1, 0, 0};
    static int[] dy = {0, 0, 1, -1};

    public static void main(String[] args) throws IOException {
        // 입력을 위한 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        board = new int[n][m];
        visited = new boolean[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 그림 개수
        int picCount = 0;
        // 가장 큰 그림의 크기
        int maxSize = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // 방문하지 않았고 그림이 그려져 있는 부분일 경우
                if (board[i][j] == 1 && !visited[i][j]) {

                    maxSize = Math.max(maxSize, bfs_1926(new Node(i,j)));
                    picCount++;
                }
            }
        }

        System.out.println(picCount);
        System.out.println(maxSize);
    }

    private static int bfs_1926(Node node) {

        Queue<Node> q = new LinkedList<>();
        q.offer(node);

        visited[node.getX()][node.getY()] = true;
        int size = 1;

        while (!q.isEmpty()) {
            Node currentNode = q.poll();

            int x = currentNode.getX();
            int y = currentNode.getY();

            // 우,좌,하,상 방향을 확인하기 위한 반복문
            for (int i = 0; i < 4; i++) {
                // 다음 방문할 x좌표
                int nextX = x + dx[i];
                // 다음 방문할 y좌표
                int nextY = y + dy[i];

                // 범위 안에 있으면
                if (nextX >= 0 && nextX < n && nextY >=0 && nextY < m
                        && board[nextX][nextY] == 1
                        && !visited[nextX][nextY]) {
                    q.offer(new Node(nextX, nextY));
                    visited[nextX][nextY] = true;
                    size++;
                }
            }
        }

        return size;
    }

    static class Node {
        private int x;
        private int y;

        Node(int x, int y) {
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
