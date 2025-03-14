package recursion.boj.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_27433 {

    /*
    문제
    0보다 크거나 같은 정수 N이 주어진다. 이때, N!을 출력하는 프로그램을 작성하시오.

    입력
    첫째 줄에 정수 N(0 ≤ N ≤ 20)이 주어진다.

    출력
    첫째 줄에 N!을 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        System.out.println(recursion(n));
    }

    private static long recursion(int n) {
        if (n == 0) {
            return 1;
        }

        return n * recursion(n - 1);
    }
}
