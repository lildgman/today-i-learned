package dp.dongbin;
import java.io.*;
import java.util.*;

public class Ex2 {

    // 정수 X가 주어졌을 때, 정수 X에 사용할 수 있는 연산은 다음 4가지이다.
    /**
     * 1. X가 5로 나누어 떨어지면 5로 나눈다.
     * 2. X가 3으로 나누어 떨어지면 3으로 나눈다.
     * 3. X가 2로 나누어 떨어지면 2로 나눈다.
     * 4. X에서 1을 뺀다
     */
    // 연산 4개를 사용하여 X를 1로 만들고자 한다. 연산을 사용하는 횟수의 최솟값을 출력해라

    // 입력 조건
    // 첫째 줄에 정수 X가 주어진다.
    // 출력 조건
    // 첫째 줄에 연산을 하는 횟수의 최솟값을 출력한다.

    // 연산 횟수를 담기 위한 dp 테이블
    public static int[] d = new int[30001];

    public static void main(String[] args) throws IOException {

        // 입력을 받기 위한 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 정수 x
        int x = Integer.parseInt(br.readLine());

        // DP 진행
        // d[0]과 d[1]일 때는 연산 횟수가 없으므로 for문을 돌 필요가 없음
        for (int i = 2; i <= x; i++) {

            // 현재 수에서 1을 빼는 경우
            d[i] = d[i - 1] + 1;

            // 현재 수가 2로 나누어 떨어지는 경우
            if (i % 2 == 0) {
                d[i] = Math.min(d[i], d[i / 2] + 1);
            }
            // 현재 수가 3으로 나누어 떨어지는 경우
            if (i % 3 == 0) {
                d[i] = Math.min(d[i], d[i / 3] + 1);
            }
            // 현재 수가 5로 나누어 떨어지는 경우
            if (i % 5 == 0) {
                d[i] = Math.min(d[i], d[i / 5] + 1);
            }
        }

        System.out.println(d[x]);
    }
}
