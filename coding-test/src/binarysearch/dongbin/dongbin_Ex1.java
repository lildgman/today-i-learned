package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class dongbin_Ex1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 떡의 개수
        int n = Integer.parseInt(st.nextToken());
        // 요청한 떡의 길이
        int m = Integer.parseInt(st.nextToken());

        // 떡의 길이
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색을 위해 시작점과 끝점 설정
        int start = 0;
        int end = (int) 1e9;

        int result = 0;

        // 이진 탐색 시작
        while (start <= end) {

            long total = 0;

            int mid = (start + end) / 2;

            for (int i = 0; i < n; i++) {
                // 잘랐을 때 떡의 양 계산
                if (arr[i] > mid) {
                    total += arr[i] - mid;
                }
            }

            if (total < m) { // 잘린 떡의 양이 부족한 경우 더 많이 자르기 (왼쪽 부분 탐색)
                end = mid - 1;
            } else {
                // 떡의 양이 충분한 경우 덜 자르기 (오른쪽 부분 탐ㅋ)
                result = mid;
                start = mid + 1;
            }
        }

        System.out.println(result);

    }
}
