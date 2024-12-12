package programmers.intro.day21;

public class Day21_1 {

    /**
     * 문자열 my_string이 매개변수로 주어집니다.
     * my_string은 소문자, 대문자, 자연수로만 구성되어있습니다.
     * my_string안의 자연수들의 합을 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(String my_string) {

//        int answer = 0;
//
//        StringBuilder sb = new StringBuilder();
//
//        for (char c : my_string.toCharArray()) {
//
//            // c가 숫자이면
//            if (Character.isDigit(c)) {
//                sb.append(c);
//            }
//            // c가 문자이면
//            else {
//                if (sb.length() > 0) {
//                    answer += Integer.parseInt(sb.toString());
//                    sb.setLength(0);
//                }
//
//            }
//        }
//
//        if (sb.length() > 0) {
//            answer += Integer.parseInt(sb.toString());
//        }
//
//
//        return answer;

        int answer = 0;

        String[] split = my_string.replaceAll("[a-zA-Z]", " ").split(" ");

        for (String str : split) {
            if (!str.isEmpty()) {
                answer += Integer.parseInt(str);
            }
        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(solution("aAb1B2cC34oOp"));
    }
}
