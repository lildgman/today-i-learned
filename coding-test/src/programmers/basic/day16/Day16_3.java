package programmers.basic.day16;

public class Day16_3 {

    /**
     * 문자열 배열 strArr가 주어집니다.
     * 모든 원소가 알파벳으로만 이루어져 있을 때,
     * 배열에서 홀수번째 인덱스의 문자열은 모든 문자를 대문자로,
     * 짝수번째 인덱스의 문자열은 모든 문자를 소문자로 바꿔서 반환하는 solution 함수를 완성해 주세요.
     */

    public static String[] solution(String[] strArr) {

        for (int i = 0; i < strArr.length; i++) {
            if (i % 2 != 0) {
                strArr[i] = strArr[i].toUpperCase();
            } else {
                strArr[i] = strArr[i].toLowerCase();

            }
        }

        return strArr;
    }

    public static void main(String[] args) {
        String[] arr1 = solution(new String[]{"AAA", "BBB", "CCC", "DDD"});
        String[] arr2 = solution(new String[]{"aBc", "AbC"});

        for (String s : arr1) {
            System.out.print(s + " ");
        }

        System.out.println();
        System.out.println("=================");

        for (String s : arr2) {
            System.out.print(s + " ");

        }
    }
}
