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

        // 원소 개수
        int n = Integer.parseInt(st.nextToken());
        // 개수를 찾을 원소
        int x = Integer.parseInt(st.nextToken());

        // 수열 값 입력
        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int maxValue = arr[n - 1];

        // 값의 개수를 카운팅할 배열
        int[] cArr = new int[maxValue];

        for (int i = 0; i < arr.length; i++) {
            cArr[arr[i]-1] += 1;
        }

        System.out.println(cArr[x-1]);
    }
}
