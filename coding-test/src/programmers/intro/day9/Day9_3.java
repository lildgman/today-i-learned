package programmers.intro.day9;

public class Day9_3 {

    /**
     * 가위는 2 바위는 0 보는 5로 표현합니다.
     * 가위 바위 보를 내는 순서대로 나타낸 문자열 rsp가 매개변수로 주어질 때,
     * rsp에 저장된 가위 바위 보를 모두 이기는 경우를 순서대로 나타낸 문자열을 return하도록 solution 함수를 완성해보세요.
     */

    public static String solution(String rsp) {

        String answer = "";

        String[] split = rsp.split("");

        for (String s : split) {

            if (s.equals("2")) {
                answer += "0";
            } else if (s.equals("0")) {
                answer += "5";
            } else if (s.equals("5")) {
                answer += "2";
            }
        }

        return answer;

    }

    public static void main(String[] args) {

        System.out.println(solution("2"));
        System.out.println(solution("205"));
    }
}
