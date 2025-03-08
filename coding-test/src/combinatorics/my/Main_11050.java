package combinatorics.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_11050 {

    /*
    문제
    자연수
    N과 정수
    K가 주어졌을 때 이항 계수
    이항계수 N K를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에
    N과
    K가 주어진다.

    출력
    N, K를 출력한다.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        // 이항계수 구하는 방법
        // n! / k! *(n-k)!

        System.out.println(factorial(n) / (factorial(k) * factorial(n - k)));
    }

    private static int factorial(int n) {
        if (n == 0) {
            return 1;
        }

        return n * factorial(n - 1);
    }
}
