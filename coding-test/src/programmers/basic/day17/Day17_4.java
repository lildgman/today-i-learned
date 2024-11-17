package programmers.basic.day17;

public class Day17_4 {

    /**
     * 단어가 공백 한 개로 구분되어 있는 문자열 my_string이 매개변수로 주어질 때,
     * my_string에 나온 단어를 앞에서부터 순서대로 담은 문자열 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static String[] solution(String my_string) {
        return my_string.split(" ");
    }

    public static void main(String[] args) {
        String[] arr1 = solution("i love you");
        String[] arr2 = solution("programmers");

        for (String s : arr1) {
            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("================");

        for (String s : arr2) {
            System.out.print(s+ " ");
        }
    }

}
