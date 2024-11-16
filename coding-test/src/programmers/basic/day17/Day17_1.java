package programmers.basic.day17;

public class Day17_1 {

    /**
     * 문자열 myString과 pat가 주어집니다.
     * myString의 부분 문자열중 pat로 끝나는 가장 긴 부분 문자열을 찾아서 return 하는 solution 함수를 완성해 주세요.
     */

    public static String solution(String myString, String pat) {

        return myString.substring(0, myString.lastIndexOf(pat)) + pat;


    }

    public static void main(String[] args) {
        System.out.println(solution("AbCdEFG","dE"));
        System.out.println(solution("AAAAaaaa","a"));
    }
}
