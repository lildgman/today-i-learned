package binarysearch.dongbin;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class BinarySearch_Loop {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("원소의 개수와 찾고하자 하는 값을 입력하세요. 구분은 띄어쓰기로 합니다.");
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 원소 개수
        int n = Integer.parseInt(st.nextToken());

        // 찾고자 하는 문자열
        int target = Integer.parseInt(st.nextToken());

        // 원소의 값들 입력받기
        System.out.println("입력한 원소의 개수만큼 문자열을 입력해주세요. 구분은 띄어쓰기로 합니다.");
        int[] arr = new int[n];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진 탐색 수행 결과 출력
        int result = binarySearch(arr, target, 0, n - 1);
        if (result == -1) {
            System.out.println("찾고자 하는 값이 존재하지 않습니다.");
        } else {
            System.out.println(result + 1);
        }
    }

    private static int binarySearch(int[] arr, int target, int start, int end) {
        while (start <= end) {
            int mid = (start + end) / 2;
            // 찾은 경우 중간점 인덱스 반환
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                // 중간점의 값보다 타겟의 값이 작을 경우 왼쪽부분 확인
                end = mid - 1;
            } else {
                // 중간점의 값보다 타겟의 값이 큰 경우 오른쪽 부분 확인
                start = mid + 1;
            }
        }
        return -1;
    }
}
