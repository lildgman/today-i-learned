package programmers.intro.day23;

public class Day23_3 {

    /**
     * 머쓱이는 태어난 지 6개월 된 조카를 돌보고 있습니다.
     * 조카는 아직 "aya", "ye", "woo", "ma" 네 가지 발음을 최대 한 번씩 사용해 조합한(이어 붙인) 발음밖에 하지 못합니다.
     * 문자열 배열 babbling이 매개변수로 주어질 때, 머쓱이의 조카가 발음할 수 있는 단어의 개수를 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(String[] babbling) {

        int answer = 0;

        String[] pronunciationArr = {"aya", "ye", "woo", "ma"};

        for (String word : babbling) {

            for (String pronunciation : pronunciationArr) {
                word = word.replaceAll(pronunciation, " ");
            }

            if (word.replace(" ","").isEmpty()) {
                answer++;
            }
        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"aya", "yee", "u", "maa", "wyeoo"}));
        System.out.println(solution(new String[]{"ayaye", "uuuma", "ye", "yemawoo", "ayaa"}));
    }

}
