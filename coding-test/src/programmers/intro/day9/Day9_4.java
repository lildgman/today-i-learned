package programmers.intro.day9;

public class Day9_4 {

    /**
     * 머쓱이는 구슬을 친구들에게 나누어주려고 합니다.
     * 구슬은 모두 다르게 생겼습니다.
     * 머쓱이가 갖고 있는 구슬의 개수 balls와
     * 친구들에게 나누어 줄 구슬 개수 share이 매개변수로 주어질 때,
     * balls개의 구슬 중 share개의 구슬을 고르는 가능한 모든 경우의 수를 return 하는 solution 함수를 완성해주세요.
     */

    static int solution(int balls, int share) {

        share = Math.min(share, balls - share);

        if (share == 0) {
            return 1;
        }

        long result = solution(balls - 1, share - 1);
        result *= balls;
        result /= share;

        return (int)result;

    }

    public static void main(String[] args) {
        System.out.println(solution(3,2));
        System.out.println(solution(5,3));
    }
}
