package programmers.intro.day21;

public class Day21_3 {

    /**
     * 선분 세 개로 삼각형을 만들기 위해서는 다음과 같은 조건을 만족해야 합니다.
     * <p>
     * 가장 긴 변의 길이는 다른 두 변의 길이의 합보다 작아야 합니다.
     * 삼각형의 두 변의 길이가 담긴 배열 sides이 매개변수로 주어집니다.
     * 나머지 한 변이 될 수 있는 정수의 개수를 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(int[] sides) {
        // 변이 될 수 있는 개수
        int answer = 0;

        // 가장 긴 변
        int max = Math.max(sides[0], sides[1]);
        int min = Math.min(sides[0], sides[1]);

        for (int i = 1; i <= max; i++) {
            if (max < i + min) {
                answer++;
            }
        }

        for (int i = max + 1; i < max + min; i++) {
            answer++;
        }


        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1, 2}));
        System.out.println(solution(new int[]{3, 6}));
    }
}
