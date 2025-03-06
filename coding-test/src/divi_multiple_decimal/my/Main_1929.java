package divi_multiple_decimal.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1929 {

    /*
    문제
    M 이상 N 이하의 소수를 모두 출력하는 프로그램을 작성하시오.

    입력
    첫째 줄에 자연수 M과 N이 빈 칸을 사이에 두고 주어진다. (1 ≤ M ≤ N ≤ 1,000,000)
    M 이상 N 이하의 소수가 하나 이상 있는 입력만 주어진다.

    출력
    한 줄에 하나씩, 증가하는 순서대로 소수를 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int m = Integer.parseInt(st.nextToken());
        int n = Integer.parseInt(st.nextToken());

        StringBuilder sb = new StringBuilder();
        for (int i = m; i <= n; i++) {
            if (isPrime(i)) {
                sb.append(i).append("\n");
            }
        }

        System.out.println(sb);
    }

    private static boolean isPrime(int num) {
        if (num <= 1) {
            return false;
        }

        for (int i = 2; i <= Math.sqrt(num); i++) {

            if (num % i == 0) {
                return false;
            }
        }

        return true;
    }
}
