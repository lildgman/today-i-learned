package programmers.intro.day17;

public class Day17_3 {

    /**
     * 정수 n이 매개변수로 주어질 때 n의 각 자리 숫자의 합을 return하도록 solution 함수를 완성해주세요
     */

    static int solution(int n) {

        int answer = 0;
        for (String s : String.valueOf(n).split("")) {
            answer += Integer.parseInt(s);
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(1234));
        System.out.println(solution(930211));

    }
}
