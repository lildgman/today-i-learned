package programmers.basic.day24;

public class Day24_4 {

    /**
     * 알파벳 소문자로 이루어진 문자열 myString이 주어집니다.
     * 알파벳 순서에서 "l"보다 앞서는 모든 문자를 "l"로 바꾼 문자열을 return 하는 solution 함수를 완성해 주세요.
     */

    public static String solution(String myString) {

//        String[] split = myString.split("");
//
//        for (int i = 0; i < split.length; i++) {
//            if (split[i].charAt(0) < 'l') {
//                split[i] = "l";
//            }
//        }
//
//        return String.join("", split);

        return myString.replaceAll("[^l-z]", "l");
    }

    public static void main(String[] args) {

        System.out.println(solution("abcdevwxyz"));
        System.out.println(solution("jjnnllkkmm"));
    }

}
