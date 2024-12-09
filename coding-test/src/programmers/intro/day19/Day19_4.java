package programmers.intro.day19;

public class Day19_4 {

    /**
     * 머쓱이네 반 친구들의 키가 담긴 정수 배열 array와 머쓱이의 키 height가 매개변수로 주어질 때,
     * 머쓱이보다 키 큰 사람 수를 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(int[] array, int height) {

        int answer = 0;

        for (int i : array) {

            if (i > height) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{149, 180, 192, 170}, 167));
        System.out.println(solution(new int[]{180, 120, 140}, 190));
    }
}
