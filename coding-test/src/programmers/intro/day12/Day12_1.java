package programmers.intro.day12;

public class Day12_1 {

    /**
     * 영어에선 a, e, i, o, u 다섯 가지 알파벳을 모음으로 분류합니다.
     * 문자열 my_string이 매개변수로 주어질 때 모음을 제거한 문자열을 return하도록 solution 함수를 완성해주세요.
     */

    static String solution(String my_string) {
//        String[] split = my_string.split("");
//
//        for (int i = 0; i < split.length; i++) {
//
//            if (split[i].equals("a") || split[i].equals("e") || split[i].equals("i") || split[i].equals("o") || split[i].equals("u")) {
//                split[i] = "";
//            }
//        }
//
//        return String.join("", split);

        return my_string.replaceAll("[aeiou]", "");
    }

    public static void main(String[] args) {
        System.out.println(solution("bus"));
        System.out.println(solution("nice to meet you"));
    }
}
