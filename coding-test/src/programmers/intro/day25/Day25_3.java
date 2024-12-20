package programmers.intro.day25;

import java.util.Arrays;

public class Day25_3 {

    /**
     * 연속된 세 개의 정수를 더해 12가 되는 경우는 3, 4, 5입니다.
     * 두 정수 num과 total이 주어집니다.
     * 연속된 수 num개를 더한 값이 total이 될 때,
     * 정수 배열을 오름차순으로 담아 return하도록 solution함수를 완성해보세요.
     */

    static int[] solution(int num, int total) {

        int[] answer = new int[num];

        // 연속된 숫자(num개)의 합 = total
        // 첫번째 수를 x라 하였을 때
        // x + (x + 1) + (x + 2) + ... + (x + (num - 1))
        // x로 묶으면
        // num * x + (0 + 1 + 2 + ... + (num - 1)) = total
        // num * x + (num * (num - 1) / 2) = total
        // x = (total - (num * (num - 1) / 2)) / num

        int startNum = (total - (num * (num - 1) / 2)) / num;

        for (int i = 0; i < num; i++) {
            answer[i] = startNum + i;
        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(3, 12)));
        System.out.println(Arrays.toString(solution(5, 15)));
        System.out.println(Arrays.toString(solution(4, 14)));
        System.out.println(Arrays.toString(solution(5, 5)));
    }
}
