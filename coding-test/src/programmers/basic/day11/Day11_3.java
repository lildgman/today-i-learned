package programmers.basic.day11;

public class Day11_3 {

    /**
     * 문자열 my_string과 정수 배열 indices가 주어질 때,
     * my_string에서 indices의 원소에 해당하는 인덱스의 글자를 지우고
     * 이어 붙인 문자열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static String solution(String my_string, int[] indices) {

        String answer = "";

        String[] split = my_string.split("");

        for (int i = 0; i < indices.length; i++) {
            split[indices[i]] = "";
        }

        for (String s : split) {
            answer += s;
        }

        return answer;
    }

    public static void main(String[] args) {

        System.out.println(solution("apporoograpemmemprs", new int[]{1, 16, 6, 15, 0, 10, 11, 3}));
    }
}
