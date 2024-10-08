package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class SequantialSearch {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("생성할 원소 개수를 입력한 다음 한 칸 띄고 찾을 문자열을 입력하세요.");
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        String target = st.nextToken();

        System.out.println("앞서 적은 원소 개수만큼 문자열을 입력하세요. 구분은 띄어쓰기 한 칸으로 합니다.");
        String[] arr = new String[n];

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = st.nextToken();
        }

        System.out.println(sequantialSearch(n, target, arr));


    }

    // 순차 탐색 소스코드 구현
    private static int sequantialSearch(int n, String target, String[] arr) {

        // 각 원소를 하나씩 확인
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
            // 현재 원소가 찾고자하는 원소와 동일한 경우
            if (arr[i].equals(target)) {
                return i + 1; // 현재 위치 반환
            }

        }

        return -1;
    }
}
