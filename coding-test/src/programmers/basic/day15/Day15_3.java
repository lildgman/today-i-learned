package programmers.basic.day15;

public class Day15_3 {

    /**
     * 정수가 있을 때, 짝수라면 반으로 나누고,
     * 홀수라면 1을 뺀 뒤 반으로 나누면, 마지막엔 1이 됩니다.
     * 예를 들어 10이 있다면 다음과 같은 과정으로 1이 됩니다.
     *
     * 정수들이 담긴 리스트 num_list가 주어질 때,
     * num_list의 모든 원소를 1로 만들기 위해서 필요한 나누기 연산의 횟수를 return하도록 solution 함수를 완성해주세요.
     */

    public static int solution(int[] num_list) {

        int answer = 0;

        for (int i = 0; i < num_list.length; i++) {

            int num = num_list[i];

            while (num != 1) {
                if (num % 2 == 0) {
                    num /= 2;
                } else {
                    num = (num - 1) / 2;
                }

                answer++;
            }
        }


        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{12, 4, 15, 1, 14}));
    }
}
