package sort.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main25305 {

    // 2022 연세대학교 미래캠퍼스 슬기로운 코딩생활에
    // N명의 학생들이 응시했다.
    //
    // 이들 중 점수가 가장 높은
    // k명은 상을 받을 것이다. 이 때, 상을 받는 커트라인이 몇 점인지 구하라.
    //
    // 커트라인이란 상을 받는 사람들 중 점수가 가장 가장 낮은 사람의 점수를 말한다.

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 첫번째 줄
        // 응시자 수
        int n = Integer.parseInt(st.nextToken());
        // 상을 받을 사람의 수
        int k = Integer.parseInt(st.nextToken());

        // 역순 정렬을 하기 위해 래퍼클래스로 배열 정의
        Integer[] arr = new Integer[n];

        st = new StringTokenizer(br.readLine());
        for (Integer i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 역순 정렬
        Arrays.sort(arr, Collections.reverseOrder());

        System.out.println(arr[k-1]);

    }
}
