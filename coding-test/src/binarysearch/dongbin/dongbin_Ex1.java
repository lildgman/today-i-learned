package binarysearch.dongbin;

import java.io.*;
import java.util.StringTokenizer;

public class dongbin_Ex1 {

    /**
     * 절단기 높이 H를 지정하면 줄지어진 떡을 한 번에 절단합니다.
     * 높이가 H보다 긴 떡은 H 위의 부분이 잘리고 낮은 떡은 잘리지 않을 것입니다.
     *
     * 손님이 왔을 때 요청한 총 길이가 M일 때 적어도 M만큼 떡을 얻기 위해
     * 절단기에 설정할 수 있는 높이의 최대값을 구하는 프로그램
     */

    /**
     * 입력 조건
     * 첫째 줄에 떡의 개수 N과 요청한 떡의 길이 M이 주어짐
     * 둘째 줄에 떡의 개별 높이가 주어짐
     * <p>
     * 출력 조건
     * 적어도 M만큼의 떡을 집에 가져가기 위해 절단기에 설정할 수 있는 높이 최대값
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 떡의 개수
        int n = Integer.parseInt(st.nextToken());

        // 요청한 떡의 길이
        int m = Integer.parseInt(st.nextToken());

        // 개별적인 떡의 길이 입력
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색을 위한 시작점과 끝점 설정
        int start = 0;
        int end = (int) 1e9;

        // 이진 탐색 수행
        int result = 0;

        while (start <= end) {
            long total = 0;
            int mid = (start + end) / 2;
            for (int i = 0; i < arr.length; i++) {
                // 잘랐을 때의 떡의 양 계산
                if (arr[i] > mid) {
                    total += arr[i] - mid;
                }
            }

            if (total < m) { // 떡의 양이 부족한 경우, 더 많이 자르기(왼쪽부분 탐색)
                end = mid - 1;
            } else {

                // 떡의 양이 충분한 경우 덜 자르기(오른쪽 부분 탐색)
                result = mid;
                start = mid + 1;
            }
        }

        System.out.println(result);
    }

}
