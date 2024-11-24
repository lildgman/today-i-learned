package programmers.basic.day25;

public class Day25_3 {

    /**
     * 이차원 정수 배열 arr이 매개변수로 주어집니다.
     * arr의 행의 수가 더 많다면
     * 열의 수가 행의 수와 같아지도록 각 행의 끝에 0을 추가하고,
     * 열의 수가 더 많다면 행의 수가 열의 수와 같아지도록
     * 각 열의 끝에 0을 추가한 이차원 배열을 return 하는 solution 함수를 작성해 주세요.
     */

    public static int[][] solution(int[][] arr) {

        int rowLength = arr.length;
        int colLength = arr[0].length;
        int maxLength = Math.max(rowLength, colLength);

        int[][] answer = new int[maxLength][maxLength];

        for (int i = 0; i < maxLength; i++) {
            for (int j = 0; j < maxLength; j++) {

                if (i < rowLength && j < colLength) {
                    answer[i][j] = arr[i][j];
                } else {
                    answer[i][j] = 0;
                }
            }
        }

        return answer;

    }
}
