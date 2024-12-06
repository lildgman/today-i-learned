package programmers.intro.day16;

public class Day16_3 {

    /**
     * my_string은 "3 + 5"처럼 문자열로 된 수식입니다.
     * 문자열 my_string이 매개변수로 주어질 때,
     * 수식을 계산한 값을 return 하는 solution 함수를 완성해주세요.
     */

    static int solution(String my_string) {
        String[] split = my_string.split(" ");

        int answer = Integer.parseInt(split[0]);

        for (int i = 1; i < split.length; i += 2) {

            String op = split[i];
            int num = Integer.parseInt(split[i + 1]);

            if (op.equals("+")) {
                answer += num;
            } else if (op.equals("-")) {
                answer -= num;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution("3 + 4"));
    }
}
