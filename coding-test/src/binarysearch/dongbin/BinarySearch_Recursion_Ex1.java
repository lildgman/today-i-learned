package binarysearch.dongbin;

import java.io.IOException;
import java.io.*;
import java.util.StringTokenizer;

public class BinarySearch_Recursion_Ex1 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("원소의 개수와 찾고자 하는 값을 입력해주세요. 구분은 띄어쓰기로 합니다.");

        StringTokenizer st = new StringTokenizer(br.readLine());

        // 원소의 개수
        int n = Integer.parseInt(st.nextToken());

        // 타켓
        int target = Integer.parseInt(st.nextToken());

        // 전체 원소 입력받기
        System.out.println("정렬된 원소를 입력해주세요. 구분은 띄어쓰기로 합니다.");
        st = new StringTokenizer(br.readLine());

        Integer[] arr = new Integer[n];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색 시작
        int result = binarySearch(arr, target, 0, n - 1);
        if (result == -1) {
            System.out.println("값이 존재하지 않습니다.");
        } else {
            System.out.println(result + 1);
        }
    }

    private static int binarySearch(Integer[] arr, int target, int start, int end) {
        if (start > end) {
            return -1;
        }

        // 중간 인덱스
        int mid = (start + end) / 2;

        if (arr[mid] == target) {
            return mid;
        } else if (arr[mid] > target) {
            // 찾고자하는 값이 중간 인덱스의 값보다 작을 경우
            return binarySearch(arr, target, start, mid - 1);
        } else {
            // 찾고자하는 값이 중간 인덱스의 값도가 클 경우
            return binarySearch(arr, target, mid + 1, end);
        }



    }
}
