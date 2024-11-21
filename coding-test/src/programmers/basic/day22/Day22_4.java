package programmers.basic.day22;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

public class Day22_4 {

    /**
     * 정수 배열 arr과 delete_list가 있습니다.
     * arr의 원소 중 delete_list의 원소를 모두 삭제하고
     * 남은 원소들은 기존의 arr에 있던 순서를 유지한 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[] solution(int[] arr, int[] delete_list) {

        List<Integer> list = Arrays.stream(arr)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        for (int delete : delete_list) {

            list.removeIf(n -> n == delete);
        }


        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{293, 1000, 395, 678, 94}, new int[]{94, 777, 104, 1000, 1, 12})));
    }
}
