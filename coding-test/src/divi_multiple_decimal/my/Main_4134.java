package divi_multiple_decimal.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_4134 {

    /*
    문제
    정수 n(0 ≤ n ≤ 4*109)가 주어졌을 때,
    n보다 크거나 같은 소수 중 가장 작은 소수 찾는 프로그램을 작성하시오.

    입력
    첫째 줄에 테스트 케이스의 개수가 주어진다.
    각 테스트 케이스는 한 줄로 이루어져 있고, 정수 n이 주어진다.

    출력
    각각의 테스트 케이스에 대해서 n보다 크거나 같은 소수 중 가장 작은 소수를 한 줄에 하나씩 출력한다.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트 케이스 개수
        int t = Integer.parseInt(br.readLine());

        // 정수 n을 보관하는 배열
        long[] nArr = new long[t];

        // 정수 n 입력
        for (int i = 0; i < t; i++) {
            nArr[i] = Long.parseLong(br.readLine());
        }

        StringBuilder sb = new StringBuilder();
        // 소수 판별
        for (int i = 0; i < nArr.length; i++) {

            long num = nArr[i];

            while (true) {
                if (!isPrime(num)) {
                    num++;
                } else {
                    sb.append(num);
                    break;
                }
            }
            sb.append("\n");

        }

        System.out.println(sb);
    }

    private static boolean isPrime(long num) {

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
