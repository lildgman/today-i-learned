package programmers.intro.day21;

public class Day21_4 {

    /**
     * 알파벳이 담긴 배열 spell과 외계어 사전 dic이 매개변수로 주어집니다.
     * spell에 담긴 알파벳을 한번씩만 모두 사용한 단어가 dic에 존재한다면 1, 존재하지 않는다면 2를 return하도록 solution 함수를 완성해주세요.
     */

    static int solution(String[] spell, String[] dic) {

//        int answer = 0;
//
//        for (String word : dic) {
//            int[] spellCount = new int[spell.length];
//
//            for (int i = 0; i < spell.length; i++) {
//
//                String[] split = word.split("");
//
//                for (String s : split) {
//                    if (spell[i].equals(s)) {
//                        spellCount[i]++;
//                    }
//                }
//            }
//
//            String result = "";
//            for (int i : spellCount) {
//                result += i + "";
//            }
//
//            if (result.equals("1".repeat(word.length()))) {
//                answer = 1;
//                break;
//            } else {
//                answer = 2;
//            }
//
//        }
//
//        return answer;

        for (String word : dic) {
            int answer = 0;

            for (String s : spell) {
                if (word.contains(s)) {
                    answer++;
                }
            }

            if (answer == spell.length) {
                return 1;
            }

        }

        return 2;

    }

    public static void main(String[] args) {
        System.out.println(solution(new String[]{"p","o","s"},new String[]{"sod", "eocd", "qixm", "adio", "soo"}));
        System.out.println(solution(new String[]{"z","d","x"},new String[]{"def", "dww", "dzx", "loveaw"}));
        System.out.println(solution(new String[]{"s","o","m", "d"},new String[]{"moos", "dzx", "smm", "sunmmo", "som"}));
    }
}
