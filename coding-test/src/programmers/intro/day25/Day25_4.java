package programmers.intro.day25;

public class Day25_4 {

    /**
     * 등차수열 혹은 등비수열 common이 매개변수로 주어질 때,
     * 마지막 원소 다음으로 올 숫자를 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(int[] common) {
        if (common[1] - common[0] == common[2] - common[1]) {
            return common[common.length - 1] + (common[1] - common[0]);
        } else {
            return common[common.length - 1] * (common[1] / common[0]);
        }
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2, 3, 4}));
        System.out.println(solution(new int[]{2, 4, 8}));
    }
}
