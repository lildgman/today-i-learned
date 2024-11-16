package programmers.basic.day17;

public class Day17_2 {

    /**
     * 문자열 myString과 pat이 주어집니다.
     * myString에서 pat이 등장하는 횟수를 return 하는 solution 함수를 완성해 주세요.
     */

    public static int solution(String myString, String pat) {
        int answer = 0;

//        for (int i = 0; i <= myString.length() - pat.length(); i++) {
//
//            if (myString.substring(i, i + pat.length()).equals(pat)) {
//                answer++;
//            }
//        }

        for (int i = 0; i < myString.length(); i++) {
            if (myString.substring(i).startsWith(pat)) {
                answer++;
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution("banana", "ana"));
        System.out.println(solution("aaaa", "aa"));
    }
}
