package divi_multiple_decimal.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_17103 {

    /*
    문제
    골드바흐의 추측: 2보다 큰 짝수는 두 소수의 합으로 나타낼 수 있다.
    짝수 N을 두 소수의 합으로 나타내는 표현을 골드바흐 파티션이라고 한다. 짝수 N이 주어졌을 때, 골드바흐 파티션의 개수를 구해보자. 두 소수의 순서만 다른 것은 같은 파티션이다.

    입력
    첫째 줄에 테스트 케이스의 개수 T (1 ≤ T ≤ 100)가 주어진다. 각 테스트 케이스는 한 줄로 이루어져 있고, 정수 N은 짝수이고, 2 < N ≤ 1,000,000을 만족한다.

    출력
    각각의 테스트 케이스마다 골드바흐 파티션의 수를 출력한다.
     */

    static final int MAX = 1000000;
    static boolean[] isPrime = new boolean[MAX + 1];

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        sieve();

        int t = Integer.parseInt(br.readLine());

        for (int i = 0; i < t; i++) {
            int n = Integer.parseInt(br.readLine());
            int count = 0;

            for (int a = 2; a <= n / 2; a++) {
                int b = n - a;
                if (isPrime[a] && isPrime[b]) {
                    count++;
                }
            }

            sb.append(count).append('\n');
        }

        System.out.println(sb);
    }

    private static void sieve() {

        for (int i = 2; i <= MAX; i++) {
            isPrime[i] = true;
        }

        for (int i = 2; i * i <= MAX; i++) {
            if (isPrime[i]) {
                for (int j = i * i; j <= MAX; j += i) {
                    isPrime[j] = false;
                }
            }
        }

    }

}
