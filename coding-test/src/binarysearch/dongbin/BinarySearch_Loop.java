package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinarySearch_Loop {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 원소의 개수
        int n = Integer.parseInt(br.readLine());
        // 찾는 원소
        int target = Integer.parseInt(br.readLine());

        // 전체 원소 입력 받기
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색 수행 결과 출력
        int result = binarySearchLoop(arr, target, 0, n - 1);
        if (result == -1) {
            System.out.println("해당 원소를 찾지 못했습니다.");
        } else {
            System.out.println(result + 1);
        }
    }

    private static int binarySearchLoop(int[] arr, int target, int start, int end) {
        while (start <= end) {

            int mid = (start + end) / 2;

            // 찾은 경우 중간점 인덱스 반환
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                // 중간점의 값보다 타겟의 값이 작은 경우 왼쪽 확인
                end = mid - 1;
            } else {
                // 중간점의 값보다 타겟의 값이 더 큰 경우 오른쪽 확인
                start = mid + 1;
            }

        }

        return -1;
    }
}
