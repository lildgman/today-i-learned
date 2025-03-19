package backtracking.boj.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringJoiner;
import java.util.StringTokenizer;

public class Main_15649 {

    /*
    문제
    자연수 N과 M이 주어졌을 때,
    아래 조건을 만족하는 길이가 M인 수열을 모두 구하는 프로그램을 작성하시오.
        - 1부터 N까지 자연수 중에서 중복 없이 M개를 고른 수열

    입력
    첫째 줄에 자연수 N과 M이 주어진다. (1 ≤ M ≤ N ≤ 8)

    출력
    한 줄에 하나씩 문제의 조건을 만족하는 수열을 출력한다.
    중복되는 수열을 여러 번 출력하면 안되며, 각 수열은 공백으로 구분해서 출력해야 한다.
    수열은 사전 순으로 증가하는 순서로 출력해야 한다.
     */

    private static int n;
    private static int m;
    private static boolean[] visited;
    private static int[] sequence;
    private static StringBuilder result = new StringBuilder();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        visited = new boolean[n + 1];
        sequence = new int[m];

        backtrack(0);
        System.out.println(result);

    }

    private static void backtrack(int depth) {

        if (depth == m) {
            StringJoiner joiner = new StringJoiner(" ");
            for (int num : sequence) {
                joiner.add(Integer.toString(num));
            }
            result.append(joiner).append("\n");
        }

        for (int i = 0; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                sequence[depth] = i;
                backtrack(depth + 1);
                visited[i] = false;
            }
        }
    }
}
