package programmers.basic.day19;

import java.util.ArrayList;
import java.util.List;

public class Day19_3 {

    /**
     * 아무 원소도 들어있지 않은 빈 배열 X가 있습니다.
     * 길이가 같은 정수 배열 arr과 boolean 배열 flag가 매개변수로 주어질 때,
     * flag를 차례대로 순회하며 flag[i]가 true라면
     * X의 뒤에 arr[i]를 arr[i] × 2 번 추가하고,
     * flag[i]가 false라면 X에서 마지막 arr[i]개의 원소를 제거한 뒤 X를 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[] solution(int[] arr, boolean[] flag) {
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < flag.length; i++) {

            if (flag[i]) {
                int count = 0;
                while (count != arr[i] * 2) {
                    list.add(arr[i]);
                    count++;
                }
            } else {
                int count = 0;
                while (count != arr[i]) {
                    list.remove(list.size()-1);
                    count++;

                }
            }
        }

        return list.stream().mapToInt(i -> i).toArray();
    }

    public static void main(String[] args) {
        int[] arr1 = solution(new int[]{3, 2, 4, 1, 3},new boolean[]{true, false, true, false, false});


        for (Integer s : arr1) {

            System.out.print(s + " ");
        }


    }
}
