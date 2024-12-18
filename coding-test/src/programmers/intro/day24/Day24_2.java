package programmers.intro.day24;

public class Day24_2 {

    /**
     * 이진수를 의미하는 두 개의 문자열 bin1과 bin2가 매개변수로 주어질 때,
     * 두 이진수의 합을 return하도록 solution 함수를 완성해주세요.
     */

    static String solution(String bin1, String bin2) {
        return Integer.toBinaryString(binaryToDecimal(bin1) + binaryToDecimal(bin2));
    }

    static int binaryToDecimal(String binary) {

        return Integer.parseInt(binary,2);
    }

    public static void main(String[] args) {
        System.out.println(solution("10", "11"));
        System.out.println(solution("1001", "1111"));

    }
}
