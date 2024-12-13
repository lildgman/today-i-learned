package programmers.intro.day22;

import java.util.Arrays;
import java.util.Comparator;

public class Day22_3 {

    /**
     * 선분 3개가 평행하게 놓여 있습니다.
     * 세 선분의 시작과 끝 좌표가 [[start, end], [start, end], [start, end]] 형태로 들어있는 2차원 배열 lines가 매개변수로 주어질 때,
     * 두 개 이상의 선분이 겹치는 부분의 길이를 return 하도록 solution 함수를 완성해보세요.
     *
     * 제한사항
     * lines의 길이 = 3
     * lines의 원소의 길이 = 2
     * 모든 선분은 길이가 1 이상입니다.
     * lines의 원소는 [a, b] 형태이며, a, b는 각각 선분의 양 끝점 입니다.
     * -100 ≤ a < b ≤ 100
     */

    static int solution(int[][] lines) {

        // 좌표를 이용하자
        // -100<= a < b <= 100
        int[] coordinate = new int[202];

        // 좌표에 길이 표시
        for (int[] line : lines) {

            int start = line[0] + 100;
            int end = line[1] + 100;

            for (int i = start; i < end; i++) {
                coordinate[i]++;
            }
        }

        // coordinate의 원소값이 2 이상이면 겹치는 부분
        int answer = 0;
        for (int count : coordinate) {
            if (count >= 2) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[][]{{0,1},{2,5},{3,9}}));
        System.out.println(solution(new int[][]{{-1,1},{1,3},{3,9}}));
        System.out.println(solution(new int[][]{{0,5},{3,9},{1,10}}));
    }
}
