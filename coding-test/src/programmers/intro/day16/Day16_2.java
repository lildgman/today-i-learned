package programmers.intro.day16;

import java.util.Arrays;

public class Day16_2 {

    /**
     * 정수 배열 array가 매개변수로 주어질 때,
     * 가장 큰 수와 그 수의 인덱스를 담은 배열을 return 하도록 solution 함수를 완성해보세요.
     */

    static int[] solution(int[] array) {

        int[] answer = new int[2];

        for (int i = 0; i < array.length; i++) {

            if (answer[0] < array[i]) {
                answer[0] = array[i];
                answer[1] = i;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1,8,3})));
        System.out.println(Arrays.toString(solution(new int[]{9,10,11,8})));
    }
}
