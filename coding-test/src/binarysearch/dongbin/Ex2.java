package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;



public class Ex2 {
    // N개 원소를 포함하고 있는 수열이 오름차순으로 정렬되어있다.
    // 이때 이 수열에서 x가 등장하는 횟수를 계산해라

    // 입력조건
    // 첫째줄에 N과 x가 정수 형태로 공백으로 구분되어 입력
    // 둘째 줄에 N개 원소가 정수 형태로 공백으로 구분되어 입력

    // 출력조건
    // 수열의 원소 중 값이 x인 원소의 개수 출력

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 원소 개수 N
        int n = Integer.parseInt(st.nextToken());

        // 타겟 x
        int x = Integer.parseInt(st.nextToken());

        // N개의 원소 개수 입력
        st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 개수 구하기
        int count = 0;
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] == x) {
//                count++;
//            }
//        }

//        System.out.println(count);

        // x와 같은 값의 첫번째 인덱스 구하기
        int firstIndex = getFirstIndex(arr, x,0, n - 1);

        // x와 같은 값의 마지막 인덱스 구하기
        int lastIndex = getLastIndex(arr, x, 0, n - 1);

        System.out.println(lastIndex - firstIndex == 0 ? -1 : lastIndex - firstIndex);

    }

    private static int getLastIndex(int[] arr, int target, int start, int end) {

        // 타겟보다 큰 인덱스를 찾는다.
        // 중간값이 타겟보다 큰 경우 끝값을 중간으로 이동
        while (start < end) {
            int mid = (start + end) / 2;
            if (arr[mid] > target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return end;
    }

    /**
     * 타겟과 같은 같의 첫번째 인덱스를 구하는 함수
      * @param arr
     * @param start
     * @param end
     * @return
     */
    private static int getFirstIndex(int[] arr, int target ,int start, int end) {
        // 이진탐색 진행
        // 타겟보다 크거나 같은 인덱스를 찾는다.
        // 중간값이 타겟보다 크거나 같으면 끝값을 중간으로 옮긴다.
        while (start < end) {

            int mid = (start + end) / 2;
            if (arr[mid] >= target) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        return end;
    }
}
