package sort.dongbin;

// 두 개의 배열 A와 B가 있다.
// 두 배열은 N개의 원소로 구성되어 있으며 배열의 원소는 모두 자연수
// 최대 K번의 바꿔치기 연산을 수행할 수 있다.
// 바꿔치기란 배열A에 있는 원소 하나와 배열B에 있는 원소 하나를 골라 두 원소를 서로 바꾸는 것을 말한다.
// 배열 A의 모든 원소 합이 최대가 되도록하자
// N,K, 배열 A와 배열 B의 정보가 주어졌을 때, 최대 K번의 바꿔치기 연산을 수행하여 만들 수 있는 배열 A의 모든 원소의 합의 최대값을 출력

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Ex1 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 배열의 길이
        int n = Integer.parseInt(st.nextToken());

        // 바꿔치기할 횟수
        int k = Integer.parseInt(st.nextToken());

        Integer[] arrA = new Integer[n];
        Integer[] arrB = new Integer[n];

        // 배열에 값 할당
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrA[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arrB[i] = Integer.parseInt(st.nextToken());
        }

        // 바꿔치기 진행
        // A의 가장 작은값과 B의 가장 큰 값들을 교환해주면 A의 합이 가장 큰 값을 가지게 될 것이다.
        // 배열 A 오름차순으로 정렬
        Arrays.sort(arrA);

        // 배열 B 내림차순 정렬
        Arrays.sort(arrB, Collections.reverseOrder());

        for (int i = 0; i < k; i++) {
            if (arrA[i] < arrB[i]) {
                int tmp = arrA[i];
                arrA[i] = arrB[i];
                arrB[i] = tmp;
            } else {
                break;
            }

        }

        int sum = 0;
        for (int i : arrA) {
            sum += i;
        }

        System.out.println(sum);
    }
}
