package greedy.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GreedyEx3 {

    // 각 자리가 숫자(0부터 9)로만 이루어진 문자열 S가 주어졌을 때, 왼쪽부터 오른쪽으로 하나씩 모든 숫자를 확인하며,
    // 숫자 사이에 '*' 혹은 '+' 연산자를 넣어 결과적으로 만들어질 수 있는 가장 큰 수를 구하는 프로그램을 작성하세요
    // 모든 연산은 왼쪽에서부터 순서대로 이루어진다고 가정

    // 숫자가 1이상일 때만 생각해보자
    // 1일 때는 곱하는 것보단 더해주는게 가장 큰 수를 구할 수 있다.
    // 2부터는 더하는 것 보단 곱해주는게 좋을 것이다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

//        int result = 1;
//        for (int i = 0; i < s.length(); i++) {
//            if (s.charAt(i)-'0' != 0) {
//                result *= s.charAt(i) - '0';
//            }
//
//        }
//
//        System.out.println(result);

        int result = s.charAt(0) - '0';
        for (int i = 1; i < s.length(); i++) {
            int num = s.charAt(i) - '0';

            if (num <= 1 || result <= 1) {
                result += num;
            } else {
                result *= num;
            }
        }

        System.out.println(result);
    }
}

