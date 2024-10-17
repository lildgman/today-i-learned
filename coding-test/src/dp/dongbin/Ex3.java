package dp.dongbin;

import java.io.*;
import java.util.*;

public class Ex3 {

    // 효율적인 화폐 구성
    /**
     * N가지 화폐 종류가 있습니다. 이 화폐들의 개수를 최소한으로 이용해서 그 가치의 합이 M원이 되도록 하려고 합니다.
     * 이때 각 종류의 화폐는 몇개라도 사용할 수 있습니다.
     * M원을 만들기 위한 최소한의 화폐개수를 출력하는 프로그램을 작성하세요
     */

    // 입력조건
    /**
     * 첫째줄에 N,M이 주어진다.
     * 이후 N개의 줄에 각 화폐의 가치가 주어진다.
     * 화폐의 가치는 10,000보다 작거나 같은 자연수
     */

    // 출력 조건
    /**
     * 첫째 줄에는 최소 화폐 개수 출력
     * 불가능할 때는 -1 출력
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 화폐 종류의 개수
        int n = Integer.parseInt(st.nextToken());
        // 화폐 합
        int m = Integer.parseInt(st.nextToken());

        // 화폐 종류
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 계산된 결과를 담기 위한 dp 테이블
        int[] d = new int[m + 1];
        Arrays.fill(d, 10001);

        // dp 진행
        d[0] = 0;

        for (int i = 0; i < n; i++) {
            for (int j = arr[i]; j <= m; j++) {

                if (d[j - arr[i]] != 10001) {
                    d[j] = Math.min(d[j], d[j - arr[i]] + 1);
                }
            }
        }

        if (d[m] == 10001) {
            System.out.println(-1);
        } else {
            System.out.println(d[m]);
        }

    }




}
