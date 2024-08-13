package dfs_bfs.ex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainEx1 {

    public static int n;
    public static int m;
    public static int[][] graph = new int[1000][1000];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        // 2차원 리스트에 입력값 넣어주기
        for (int i = 0; i < n; i++) {
            String row = br.readLine();
            for (int j = 0; j < m; j++) {
                graph[i][j] = row.charAt(j) - '0';
            }
        }

        // 음료수 채우기
        int result = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // dfs 수행

                if (dfs(i, j)) {
                    result += 1;
                }
            }
        }

        System.out.println(result);


    }

    public static boolean dfs(int i, int j) {
        // 범위를 벗어났을 때 종료
        if (i <= -1 || i >= n || j <= -1 || j >= m) {
            return false;
        }

        // 현재 위치를 방문하지 않았다면
        if (graph[i][j] == 0) {
            // 방문처리
            graph[i][j] = 1;

            // 이 위치의 상, 하, 좌, 우 재귀적으로 호출
            dfs(i, j - 1);
            dfs(i, j + 1);
            dfs(i - 1, j);
            dfs(i + 1, j);

            return true;
        }

        return false;
    }

}
