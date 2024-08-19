package dfs_bfs.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class MainEx2 {

    // 동빈이는 N X M 크기의 직사강형 형태의 미로에 갇혀있다. 미로에는 여러 마리 괴물이 있어 피해서 탈출해야 한다
    // 동빈이는 (1,1) 위치에 있으면 출구는 (N,M)의 위치에 있다.
    // 한 번에 한 칸씩 이동 가능하다.
    // 괴물이 있는 부분은 0, 없는 부분은 1로 표시, 미로는 반드시 탈출할 수 있는 형태로 제시
    // 동빈이가 탈출하기 위해 움직여야 하는 최소 칸의 개수, 칸을 셀 때는 시작칸과 마지막 칸 모두 포함해 계산

    // 입력: 첫째 줄에 두 정수 N, M 주어지고 N개 줄에 각각 M개의 정수로 미로 정보가 주어진다.
    // 각각 수들은 공백 없이 붙어서 입력으로 제시 시작과 마지막 칸은 항상 1

    // 출력: 첫째 줄에 최소 이동 칸의 개수 출력

    public static int n;
    public static int m;
    public static int[][] graph = new int[201][201];

    // 네 가지 방향 정의
    public static int dx[] = {0, 0, -1, 1};
    public static int dy[] = {-1, 1, 0, 0};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                graph[i][j] = row.charAt(j) - '0';
            }
        }

        System.out.println(bfs(0,0));
    }

    private static int bfs(int x, int y) {

        Queue<Node> queue = new LinkedList<>();
        queue.offer(new Node(x, y));

        while (!queue.isEmpty()) {
            Node node = queue.poll();

            x = node.getX();
            y = node.getY();

            // 현재 위치에서 네 가지 방향으로 길 확인
            for (int i = 0; i < 4; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];

                // 미로 공간의 크기를 벗어나면 무시
                if (nx < 0 || nx >= n || ny < 0 || ny >= m) {
                    continue;
                }

                // 괴물이 있는 경우 무시
                if (graph[nx][ny] == 0) {
                    continue;
                }

                // 해당 노드를 처음 방문하는 경우, 최단거리를 기록
                if (graph[nx][ny] == 1) {
                    graph[nx][ny] = graph[x][y] + 1;
                    queue.offer(new Node(nx, ny));
                }
            }
        }
        return graph[n - 1][m - 1];
    }

}

class Node {
    private int x;
    private int y;

    public Node(int x, int y) {
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
