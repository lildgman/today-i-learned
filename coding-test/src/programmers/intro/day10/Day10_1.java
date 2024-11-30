package programmers.intro.day10;

public class Day10_1 {

    /**
     * x 좌표 (x, y)를 차례대로 담은 정수 배열 dot이 매개변수로 주어집니다.
     * 좌표 dot이 사분면 중 어디에 속하는지 1, 2, 3, 4 중 하나를 return 하도록 solution 함수를 완성해주세요.
     */

    static int solution(int[] dot) {

        int x = dot[0];
        int y = dot[1];

        if (x > 0) {
            if (y > 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (y > 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{2,4}));
        System.out.println(solution(new int[]{-7,9}));
    }
}
