package sort.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main10989 {

    // 카운팅정렬
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] counting = new int[10001];

        for (int i = 0; i < n; i++) {
            counting[Integer.parseInt(br.readLine())]++;
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < 10001; i++) {

            while (counting[i] > 0) {
                sb.append(i).append('\n');
                counting[i]--;
            }
        }

        System.out.println(sb);

    }
}
