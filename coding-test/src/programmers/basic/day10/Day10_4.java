package programmers.basic.day10;

public class Day10_4 {

    /**
     * 문자열 my_string과 두 정수 m, c가 주어집니다.
     * my_string을 한 줄에 m 글자씩 가로로 적었을 때
     * 왼쪽부터 세로로 c번째 열에 적힌 글자들을 문자열로 return 하는 solution 함수를 작성해 주세요.
     */

    public static String solution(String my_string, int m, int c) {

//        StringBuilder sb = new StringBuilder();
//        List<String> list = new ArrayList<>();
//
//        for (int i = 0; i < my_string.length(); i += m) {
//            list.add(my_string.substring(i, i + m));
//        }
//
//        for (String s : list) {
//            sb.append(s.charAt(c - 1));
//        }
//
//        return sb.toString();

        StringBuilder sb = new StringBuilder();

        for (int i = c - 1; i < my_string.length(); i += m) {
            sb.append(my_string.charAt(i));
        }

        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(solution("ihrhbakrfpndopljhygc",4,2));
        System.out.println(solution("programmers",1,1));
    }
}
