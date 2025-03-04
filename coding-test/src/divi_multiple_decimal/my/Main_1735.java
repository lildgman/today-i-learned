package divi_multiple_decimal.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1735 {

    /*
    문제
    분수 A/B는 분자가 A, 분모가 B인 분수를 의미한다. A와 B는 모두 자연수라고 하자.

    두 분수의 합 또한 분수로 표현할 수 있다.
    두 분수가 주어졌을 때, 그 합을 기약분수의 형태로 구하는 프로그램을 작성하시오.
    기약분수란 더 이상 약분되지 않는 분수를 의미한다.

    입력
    첫째 줄과 둘째 줄에, 각 분수의 분자와 분모를 뜻하는 두 개의 자연수가 순서대로 주어진다.
    입력되는 네 자연수는 모두 30,000 이하이다.

    출력
    첫째 줄에 구하고자 하는 기약분수의 분자와 분모를 뜻하는 두 개의 자연수를 빈 칸을 사이에 두고 순서대로 출력한다.
     */

    public static void main(String[] args) throws IOException {

        // 2/3
        // 5/6

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] aFractions = new int[2];
        aFractions[0] = Integer.parseInt(st.nextToken());
        aFractions[1] = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] bFractions = new int[2];
        bFractions[0] = Integer.parseInt(st.nextToken());
        bFractions[1] = Integer.parseInt(st.nextToken());

        int numerator = aFractions[0] * bFractions[1] + bFractions[0] * aFractions[1];
        int denominator = aFractions[1] * bFractions[1];

        int gcdVal = getGCD(numerator, denominator);

        System.out.println((numerator / gcdVal) + " " + (denominator / gcdVal));

    }

    private static int getGCD(int a, int b) {

        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }

        return a;
    }
}
