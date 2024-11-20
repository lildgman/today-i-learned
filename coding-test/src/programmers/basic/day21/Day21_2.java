package programmers.basic.day21;

import java.util.*;
import java.util.stream.Collector;

public class Day21_2 {

    /**
     * 0번부터 n - 1번까지 n명의 학생 중
     * 3명을 선발하는 전국 대회 선발 고사를 보았습니다.
     * 등수가 높은 3명을 선발해야 하지만,
     * 개인 사정으로 전국 대회에 참여하지 못하는 학생들이 있어
     * 참여가 가능한 학생 중 등수가 높은 3명을 선발하기로 했습니다.
     * <p>
     * 각 학생들의 선발 고사 등수를 담은 정수 배열 rank와
     * 전국 대회 참여 가능 여부가 담긴 boolean 배열 attendance가 매개변수로 주어집니다.
     * 전국 대회에 선발된 학생 번호들을 등수가 높은 순서대로 각각 a, b, c번이라고 할 때
     * 10000 × a + 100 × b + c를 return 하는 solution 함수를 작성해 주세요.
     */

    public static int solution(int[] rank, boolean[] attendance) {

        Map<Integer, Integer> studentRanks = new HashMap<>();

        for (int i = 0; i < rank.length; i++) {
            if (attendance[i]) {
                studentRanks.put(i, rank[i]);
            }
        }

        List<Map.Entry<Integer, Integer>> sortedStudents = new ArrayList<>(studentRanks.entrySet());
        sortedStudents.sort(Map.Entry.comparingByValue());

        int a = sortedStudents.get(0).getKey();  // 1등의 학생 번호
        int b = sortedStudents.get(1).getKey();  // 2등의 학생 번호
        int c = sortedStudents.get(2).getKey();  // 3등의 학생 번호

        return 10000 * a + 100 * b + c;

    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{3, 7, 2, 5, 4, 6, 1}, new boolean[]{false, true, true, true, true, false, false}));
        System.out.println(solution(new int[]{1,2,3}, new boolean[]{true, true, true}));
        System.out.println(solution(new int[]{6, 1, 5, 2, 3, 4}, new boolean[]{true, false, true, false, false, true}));
    }
}
