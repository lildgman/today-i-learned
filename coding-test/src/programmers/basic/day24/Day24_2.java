package programmers.basic.day24;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day24_2 {

    /**
     * 직사각형 형태의 그림 파일이 있고,
     * 이 그림 파일은 1 × 1 크기의 정사각형 크기의 픽셀로 이루어져 있습니다.
     * 이 그림 파일을 나타낸 문자열 배열 picture과 정수 k가 매개변수로 주어질 때,
     * 이 그림 파일을 가로 세로로 k배 늘린 그림 파일을 나타내도록
     * 문자열 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static String[] solution(String[] picture, int k) {

        List<String> answer = new ArrayList<>();

        for (int i = 0; i < picture.length; i++) {
            String str = "";

            for (int j = 0; j < picture[i].length(); j++) {

                for (int x = 0; x < k; x++) {
                    str += picture[i].charAt(j);
                }
            }

            for (int j = 0; j < k; j++) {
                answer.add(str);

            }

        }

        return answer.toArray(String[]::new);
    }

    public static void main(String[] args) {
        String[] arr = solution(new String[]{".xx...xx.", "x..x.x..x", "x...x...x", ".x.....x.", "..x...x..", "...x.x...", "....x...."}, 2);

        for (String s : arr) {
            System.out.println(s);
        }

        String[] arr2 = solution(new String[]{"x.x", ".x.", "x.x"}, 3);

        for (String s : arr2) {
            System.out.println(s);
        }
    }
}
