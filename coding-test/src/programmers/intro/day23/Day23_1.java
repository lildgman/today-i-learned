package programmers.intro.day23;

import java.util.*;

public class Day23_1 {

    /**
     * 정수 n을 기준으로 n과 가까운 수부터 정렬하려고 합니다.
     * 이때 n으로부터의 거리가 같다면 더 큰 수를 앞에 오도록 배치합니다.
     * 정수가 담긴 배열 numlist와 정수 n이 주어질 때
     * numlist의 원소를 n으로부터 가까운 순서대로 정렬한 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(int[] numlist, int n) {

//        numlist = Arrays.stream(numlist).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();
//
//        List<Integer> list = new ArrayList<>();
//
//        int diff = 0;
//        while (diff != 10000) {
//
//            for (int num : numlist) {
//                if (Math.abs(num - n) == diff) {
//                    list.add(num);
//                }
//            }
//            diff++;
//        }
//
//        return list.stream().mapToInt(Integer::intValue).toArray();

        return Arrays.stream(numlist)
                .boxed()
                .sorted((a, b) -> Math.abs(n - a) == Math.abs(n - b) ? b.compareTo(a) : Integer.compare(Math.abs(n - a), Math.abs(n - b)))
                .mapToInt(Integer::intValue)
                .toArray();

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1, 2, 3, 4, 5, 6}, 4)));
    }
}
