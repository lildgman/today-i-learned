package programmers.basic;

public class Day10_1 {

    /**
     *  문자열 my_string과 정수 n이 매개변수로 주어질 때,
     *  my_string의 앞의 n글자로 이루어진 문자열을 return 하는 solution 함수를 작성해 주세요.
     */


    public static String solution(String my_string, int n) {
        return my_string.substring(0, n);
    }

    public static void main(String[] args) {
        System.out.println(solution("ProgrammerS123", 11));
    }
}
