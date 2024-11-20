package programmers.basic.day21;

public class Day21_3 {

    /**
     * 실수 flo가 매개 변수로 주어질 때,
     * flo의 정수 부분을 return하도록 solution 함수를 완성해주세요.
     */

    public static int solution(double flo) {
        return (int) flo;
    }

    public static void main(String[] args) {

        System.out.println(solution(1.42));
        System.out.println(solution(69.32));
    }
}
