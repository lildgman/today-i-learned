package programmers.intro.day11;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Day11_3 {

    /**
     * 정수 배열 numbers가 매개변수로 주어집니다.
     * numbers의 원소 중 두 개를 곱해 만들 수 있는 최댓값을 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(int[] numbers) {

        Arrays.sort(numbers);

        return numbers[numbers.length - 1] * numbers[numbers.length - 2];
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2, 3, 4, 5}));
        System.out.println(solution(new int[]{0, 31, 24, 10, 1, 9}));
    }
}
