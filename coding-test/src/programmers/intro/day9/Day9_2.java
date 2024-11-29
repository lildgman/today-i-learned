package programmers.intro.day9;

import java.util.HashMap;
import java.util.Map;

public class Day9_2 {

    /**
     * 머쓱이는 친구에게 모스부호를 이용한 편지를 받았습니다.
     * 그냥은 읽을 수 없어 이를 해독하는 프로그램을 만들려고 합니다.
     * 문자열 letter가 매개변수로 주어질 때, letter를 영어 소문자로 바꾼 문자열을 return 하도록 solution 함수를 완성해보세요.
     * 모스부호는 다음과 같습니다.
     * morse = {
     * '.-':'a','-...':'b','-.-.':'c','-..':'d','.':'e','..-.':'f',
     * '--.':'g','....':'h','..':'i','.---':'j','-.-':'k','.-..':'l',
     * '--':'m','-.':'n','---':'o','.--.':'p','--.-':'q','.-.':'r',
     * '...':'s','-':'t','..-':'u','...-':'v','.--':'w','-..-':'x',
     * '-.--':'y','--..':'z'
     * }
     */

    static final Map<String, Character> morseMap = new HashMap<>(){{
        put(".-", 'a');
        put("-...", 'b');
        put("-.-.", 'c');
        put("-..", 'd');
        put(".", 'e');
        put("..-.", 'f');
        put("--.", 'g');
        put("....", 'h');
        put("..", 'i');
        put(".---", 'j');
        put("-.-", 'k');
        put(".-..", 'l');
        put("--", 'm');
        put("-.", 'n');
        put("---", 'o');
        put(".--.", 'p');
        put("--.-", 'q');
        put(".-.", 'r');
        put("...", 's');
        put("-", 't');
        put("..-", 'u');
        put("...-", 'v');
        put(".--", 'w');
        put("-..-", 'x');
        put("-.--", 'y');
        put("--..", 'z');
    }};

    public static String solution(String letter) {

        String answer = "";
        String[] split = letter.split(" ");

        for (String morse : split) {
            answer += morseMap.get(morse);
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(solution(".... . .-.. .-.. ---"));
        System.out.println(solution(".--. -.-- - .... --- -."));
    }
}
