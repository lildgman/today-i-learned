package programmers.basic.day22;

import java.math.BigInteger;

public class Day22_2 {

    /**
     * 0 이상의 두 정수가 문자열 a, b로 주어질 때,
     * a + b의 값을 문자열로 return 하는 solution 함수를 작성해 주세요.
     */

    public static String solution(String a, String b) {

        BigInteger numA = new BigInteger(a);
        BigInteger numB = new BigInteger(b);

        return numA.add(numB).toString();
    }

    public static void main(String[] args) {
        System.out.println(solution("582","734"));
        System.out.println(solution("18446744073709551615","287346502836570928366"));
        System.out.println(solution("0","0"));
    }
}
