package sort.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main2750 {

    // N개의 수가 주어졌을 때, 이를 오름차순으로 정렬하는 프로그램을 작성하시오.
    // 입력: 첫째 줄에 수의 개수 N(1 ≤ N ≤ 1,000)이 주어진다. 둘째 줄부터 N개의 줄에는 수가 주어진다.
    // 이 수는 절댓값이 1,000보다 작거나 같은 정수이다. 수는 중복되지 않는다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        // 크기가 n인 배열 생성
        int[] arr = new int[n];

        // arr 크기만큼 반복문을 돌면서 arr 배열에 요소 입력받아 채워넣음
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // 정렬
        Arrays.sort(arr);

        // 출력
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
