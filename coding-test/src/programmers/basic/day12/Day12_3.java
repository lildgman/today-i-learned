package programmers.basic.day12;

import java.util.ArrayList;

public class Day12_3 {

    /**
     * 정수 배열 arr와 2개의 구간이 담긴 배열 intervals가 주어집니다.
     * <p>
     * intervals는 항상 [[a1, b1], [a2, b2]]의 꼴로 주어지며 각 구간은 닫힌 구간입니다.
     * 닫힌 구간은 양 끝값과 그 사이의 값을 모두 포함하는 구간을 의미합니다.
     * <p>
     * 이때 배열 arr의 첫 번째 구간에 해당하는 배열과
     * 두 번째 구간에 해당하는 배열을 앞뒤로 붙여
     * 새로운 배열을 만들어 return 하는 solution 함수를 완성해 주세요.
     */

    public static int[] solution(int[] arr, int[][] intervals) {

        ArrayList<Integer> list = new ArrayList<>();

        // a1 ~ b1 를 인덱스로해서 arr 인덱스값들 list에 추가
        for (int i = intervals[0][0]; i <= intervals[0][1]; i++) {
            list.add(arr[i]);
        }

        // a2 ~ b2
        for (int i = intervals[1][0]; i <= intervals[1][1]; i++) {
            list.add(arr[i]);
        }

        return list.stream().mapToInt(i -> i).toArray();

    }

    public static void main(String[] args) {

        int[] arr = solution(new int[]{1, 2, 3, 4, 5}, new int[][]{{1, 3}, {0, 4}});

        for (int i : arr) {
            System.out.print(i + " ");

        }
    }
}
