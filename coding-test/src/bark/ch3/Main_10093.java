package bark.ch3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_10093 {

    /*
    문제
    두 양의 정수가 주어졌을 때, 두 수 사이에 있는 정수를 모두 출력하는 프로그램을 작성하시오.

    입력
    두 정수 A와 B가 주어진다.

    출력
    첫째 줄에 두 수 사이에 있는 수의 개수를 출력한다.
    둘째 줄에는 두 수 사이에 있는 수를 오름차순으로 출력한다.

    배점 30
    제한 1 <= A,B <= 1000

    배점 70
    제한 1 <= A,B <= 10^15, A와 B 차이 최대는 100,000
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());

        long max = Math.max(a, b);
        long min = Math.min(a, b);

        if (max == min || max - min == 1) {
            System.out.println(0);
            return;
        }

        long count = max - min - 1;
        System.out.println(count);

        for (long i = min + 1; i < max; i++) {
            System.out.print(i + " ");
        }
    }
}
