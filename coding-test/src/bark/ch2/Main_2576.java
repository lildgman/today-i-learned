package bark.ch2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2576 {

    /*
    문제
    7개의 자연수가 주어질 때, 이들 중 홀수인 자연수들을 모두 골라 그 합을 구하고,
    고른 홀수들 중 최솟값을 찾는 프로그램을 작성하시오.

    예를 들어,
    7개의 자연수 12, 77, 38, 41, 53, 92, 85가 주어지면
    이들 중 홀수는 77, 41, 53, 85이므로 그 합은
    77 + 41 + 53 + 85 = 256이 되고,

    41 < 53 < 77 < 85
    이므로 홀수들 중 최솟값은 41이 된다.

    입력
    입력의 첫째 줄부터 일곱 번째 줄까지 한 줄에 하나의 자연수가 주어진다. 주어지는 자연수는 100보다 작다.

    출력
    홀수가 존재하지 않는 경우에는 첫째 줄에 -1을 출력한다.
    홀수가 존재하는 경우 첫째 줄에 홀수들의 합을 출력하고, 둘째 줄에 홀수들 중 최솟값을 출력한다.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 최소값
        int oddMin = Integer.MAX_VALUE;
        // 홀수의 합
        int oddSum = 0;

        for (int i = 0; i < 7; i++) {
            int input = Integer.parseInt(br.readLine());

            if (input % 2 != 0) {
                oddMin = Math.min(oddMin, input);
                oddSum += input;
            }
        }

        if (oddSum == 0) {
            System.out.println(-1);
        } else {
            System.out.println(oddSum);
            System.out.println(oddMin);
        }
    }
}
