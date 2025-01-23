package greedy.thisiscodingtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Ex1 {

    /**
     * 어떠한 수 N이 1이 될 때까지 두 과정 중 하나를 반복적으로 선택하여 수행하려 한다.
     * 두 번째 연산은 N이 K로 나누어 떨어질 때만 선택이 가능하다
     * <p>
     * 1. N에서 1을 뺀다.
     * 2. N을 K로 나눈다.
     * <p>
     * N과 K가 주어질 때 N이 1이 될 때까지 1번 혹은 2번 과정을 수행해야 하는 최소 횟수를 구해라
     */


    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int operCount = 0;

        while (n != 1) {
            if (n % k == 0) {
                n /= k;
                operCount++;
            } else {
                n -= 1;
                operCount++;
            }
        }

        System.out.println(operCount);
    }
}
