package programmers.basic.day16;

public class Day16_5 {

    public static String solution(String my_string, String alp) {

        return my_string.replaceAll(alp, alp.toUpperCase());
    }

    public static void main(String[] args) {
        System.out.println(solution("programmers", "p"));
        System.out.println(solution("lowercase", "x"));
    }
}
