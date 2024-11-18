package programmers.basic.day19;

import java.util.*;

public class Day19_4 {

    /**
     * 0과 1로만 이루어진 정수 배열 arr가 주어집니다. arr를 이용해 새로운 배열 stk을 만드려고 합니다.
     * <p>
     * i의 초기값을 0으로 설정하고 i가 arr의 길이보다 작으면 다음을 반복합니다.
     * <p>
     * 만약 stk이 빈 배열이라면 arr[i]를 stk에 추가하고 i에 1을 더합니다.
     * stk에 원소가 있고, stk의 마지막 원소가 arr[i]와 같으면 stk의 마지막 원소를 stk에서 제거하고 i에 1을 더합니다.
     * stk에 원소가 있는데 stk의 마지막 원소가 arr[i]와 다르면 stk의 맨 마지막에 arr[i]를 추가하고 i에 1을 더합니다.
     * 위 작업을 마친 후 만들어진 stk을 return 하는 solution 함수를 완성해 주세요.
     * <p>
     * 단, 만약 빈 배열을 return 해야한다면 [-1]을 return 합니다.
     */

    public static int[] solution(int[] arr) {
//        int i = 0;
//        List<Integer> list = new ArrayList<>();
//        while (i < arr.length) {
//
//            if (list.isEmpty()) {
//                list.add(arr[i]);
//                i++;
//            } else {
//                if (!list.isEmpty() && list.get(list.size() - 1) == arr[i]) {
//                    list.remove(list.size() - 1);
//                    i++;
//                } else if (!list.isEmpty() && list.get(list.size() - 1) != arr[i]) {
//                    list.add(arr[i]);
//                    i++;
//                }
//            }
//        }
//
//        return !list.isEmpty() ? list.stream().mapToInt(j -> j).toArray() : new int[]{-1};

        Deque<Integer> stk = new ArrayDeque<>();

        for (int i : arr) {
            if (!stk.isEmpty() && i == stk.peek()) {
                stk.pop();
            } else {
                stk.push(i);
            }
        }

        return stk.isEmpty() ? new int[]{-1} : stk.stream().mapToInt(i -> i).toArray();


    }

    public static void main(String[] args) {

        int[] arr1 = solution(new int[]{0, 1, 1, 1, 0});
        int[] arr2 = solution(new int[]{0, 1, 0, 1, 0});
        int[] arr3 = solution(new int[]{0, 1, 1, 0});

        for (int i : arr1) {
            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("=============");

        for (int i : arr2) {

            System.out.print(i + " ");
        }

        System.out.println();
        System.out.println("=============");

        for (int i : arr3) {
            System.out.print(i + " ");
        }
    }
}
