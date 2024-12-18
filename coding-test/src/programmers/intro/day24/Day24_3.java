package programmers.intro.day24;

import java.util.Arrays;

public class Day24_3 {

    /**
     * 문자열 before와 after가 매개변수로 주어질 때,
     * before의 순서를 바꾸어 after를 만들 수 있으면 1을, 만들 수 없으면 0을 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(String before, String after) {

        char[] beforeCharArr = before.toCharArray();
        char[] afterCharArr = after.toCharArray();

        Arrays.sort(beforeCharArr);
        Arrays.sort(afterCharArr);

        return Arrays.equals(beforeCharArr, afterCharArr) ? 1 : 0;

    }

    public static void main(String[] args) {
        System.out.println(solution("olleh", "hello"));
        System.out.println(solution("allpe", "apple"));
    }
}
