package programmers.basic.day12;

import java.util.ArrayList;
import java.util.List;

public class Day12_4 {

    /**
     * 정수 배열 arr가 주어집니다.
     * 배열 안의 2가 모두 포함된 가장 작은 연속된 부분 배열을 return 하는 solution 함수를 완성해 주세요.
     *
     * 단, arr에 2가 없는 경우 [-1]을 return 합니다.
     */

    public static int[] solution(int[] arr) {

        List<Integer> list = new ArrayList<>();

        for (int i : arr) {
            list.add(i);
        }

        int firstIdx = list.indexOf(2);
        int lastIdx = list.lastIndexOf(2);

        List<Integer> answerList = new ArrayList<>();

        if (firstIdx == -1 || lastIdx == -1) {
            answerList.add(-1);
        } else {
            for (int i = firstIdx; i <= lastIdx; i++) {
                answerList.add(list.get(i));
            }
        }


        return answerList.stream().mapToInt(i -> i).toArray();

    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{1,2,1,4,5,2,9}));
        System.out.println(solution(new int[]{1,2,1}));
        System.out.println(solution(new int[]{1,1,1}));
        System.out.println(solution(new int[]{1,2,1,2,1,10,2,1}));

    }


}
