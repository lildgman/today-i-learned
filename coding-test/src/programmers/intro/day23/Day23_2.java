package programmers.intro.day23;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Day23_2 {

    /**
     * 영어 점수와 수학 점수의 평균 점수를 기준으로 학생들의 등수를 매기려고 합니다.
     * 영어 점수와 수학 점수를 담은 2차원 정수 배열 score가 주어질 때,
     * 영어 점수와 수학 점수의 평균을 기준으로 매긴 등수를 담은 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(int[][] score) {

//        List<Double> avgScores = new ArrayList<>();
//
//        for (int[] scoreArr : score) {
//            double avg = (double) (scoreArr[0] + scoreArr[1]) / 2;
//            avgScores.add(avg);
//        }
//
//        int[] answer = new int[avgScores.size()];
//
//        for (int i = 0; i < avgScores.size(); i++) {
//
//            int rank = 1;
//
//            for (int j = 0; j < avgScores.size(); j++) {
//                if (avgScores.get(i) < avgScores.get(j)) {
//                    rank++;
//                }
//            }
//
//            answer[i] = rank;
//        }
//
//        return answer;

        List<Integer> scoreList = new ArrayList<>();

        for (int[] arr : score) {
            scoreList.add(arr[0] + arr[1]);
        }

        scoreList.sort(Comparator.reverseOrder());

        int[] answer = new int[score.length];
        for (int i = 0; i < score.length; i++) {
            answer[i] = scoreList.indexOf(score[i][0] + score[i][1]) + 1;
        }

        return answer;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[][]{{80, 70}, {90, 50}, {40, 70}, {50, 80}})));
        System.out.println(Arrays.toString(solution(new int[][]{{80, 70}, {70, 80}, {30, 50}, {90, 100}, {100, 90}, {100, 100}, {10, 30}})));
    }
}
