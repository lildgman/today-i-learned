package recursion.boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Main_1629 {

    /**
     * 문제
     * 자연수 A를 B번 곱한 수를 알고 싶다.
     * 단 구하려는 수가 매우 커질 수 있으므로 이를 C로 나눈 나머지를 구하는 프로그램을 작성하시오.
     *
     * 입력
     * 첫째 줄에 A, B, C가 빈 칸을 사이에 두고 순서대로 주어진다.
     * A, B, C는 모두 2,147,483,647 이하의 자연수이다.
     *
     * 출력
     * 첫째 줄에 A를 B번 곱한 수를 C로 나눈 나머지를 출력한다.
     *
     */

    // 나눠줄 수
    public static long c;

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        long a = Long.parseLong(st.nextToken());
        long b = Long.parseLong(st.nextToken());
        c = Long.parseLong(st.nextToken());

        System.out.println(pow(a,b));
    }

    private static long pow(long a, long exponent) {

        // 지수가 1일 때, a^1
        if (exponent == 1) {
            return a % c;
        }

        // 지수의 반
        long tmp = pow(a, exponent / 2);

        if (exponent % 2 == 1) {
            return (tmp * tmp % c) * a % c;
        }

        return tmp * tmp % c;
    }
}
