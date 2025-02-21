package bruteforce.boj.mysolved;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main_19532 {

    /*
    https://www.acmicpc.net/problem/19532
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());
        int d = Integer.parseInt(st.nextToken());
        int e = Integer.parseInt(st.nextToken());
        int f = Integer.parseInt(st.nextToken());

        // ax + by = c
        // dx + ey = f

        StringBuilder sb = new StringBuilder();
        for (int x = -999; x <= 999; x++) {

            for (int y = -999; y <= 999; y++) {

                if (a * x + b * y == c) {
                    if (d * x + e * y == f) {
                        sb.append(x).append(" ").append(y);
                    }
                }
            }
        }

        System.out.println(sb);


    }
}
