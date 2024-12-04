package programmers.intro.day14;

public class Day14_1 {

    /**
     * 정수 배열 array와 정수 n이 매개변수로 주어질 때,
     * array에 들어있는 정수 중 n과 가장 가까운 수를 return 하도록 solution 함수를 완성해주세요.
     */

    static int solution(int[] array, int n) {

        int answer = array[0];
        int diff = Math.abs(n - array[0]);

        for (int i : array) {

            int currDiff = Math.abs(n - i);

            if (currDiff < diff || (currDiff == diff && i < answer)) {
                answer = i;
                diff = currDiff;
            }
        }

        return answer;
    }

    public static void main(String[] args) {

        System.out.println(solution(new int[]{3, 10, 28}, 20));
        System.out.println(solution(new int[]{10, 11, 12}, 13));
    }
}
