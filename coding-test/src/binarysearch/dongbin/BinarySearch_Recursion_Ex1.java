package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinarySearch_Recursion_Ex1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 원소의 개수
        System.out.println("원소의 개수를 입력하세요");
        int n = Integer.parseInt(br.readLine());
        // 찾고자 하는 값
        System.out.println("찾고자 하는 값을 입력해주세요");
        int target = Integer.parseInt(br.readLine());

        // 전체 원소 입력 받기
        int[] arr = new int[n];
        System.out.println("전체 원소들을 입력해주세요. 띄어쓰기 한 칸으로 구분해주세요");
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색 수행
        int result = binarySearch(arr, target, 0, n - 1);
        if (result == -1) {
            System.out.println("해당 원소가 존재하지 않습니다.");
        } else {
            System.out.println(result + 1);
        }

    }

    // 재귀함수를 이용한 이진탐색
    private static int binarySearch(int[] arr, int target, int start, int end) {

        if (start > end) {
            return -1;
        }

        // 중간점
        int mid = (start + end) / 2;
        // 찾은 경우 중간점 인덱스를 반환
        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] > target) {
            // 중간점의 값보다 타켓의 값이 더 작은 경우
            return binarySearch(arr, target, start, mid - 1);
        } else {
            // 중간점의 값보다 타겟의 값이 더 큰 경우
            return binarySearch(arr, target, mid + 1, end);
        }
    }
}
