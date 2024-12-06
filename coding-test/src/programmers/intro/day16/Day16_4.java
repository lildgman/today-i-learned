package programmers.intro.day16;

public class Day16_4 {

    /**
     * 두 배열이 얼마나 유사한지 확인해보려고 합니다.
     * 문자열 배열 s1과 s2가 주어질 때 같은 원소의 개수를 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(String[] s1, String[] s2) {
        int answer = 0;

        for (String str1 : s1) {
            for (String str2 : s2) {
                if (str1.equals(str2)) {
                    answer++;
                }
            }
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"a", "b", "c"}, new String[]{"com", "b", "d", "p", "c"}));
        System.out.println(solution(new String[]{"n", "omg"}, new String[]{"m", "dot"}));
    }

}
