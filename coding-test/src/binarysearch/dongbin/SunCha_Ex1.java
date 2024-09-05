package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class SunCha_Ex1 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("생성할 원소 개수를 입력한 다음 한 칸을 띄고 찾을 문자열을 입력하세요");
        // 원소 개수
        int n = Integer.parseInt(br.readLine());
        // 찾고자 하는 문자열
        String target = br.readLine();

        System.out.println("앞서 적은 원소 개수만큼 문자열을 입력하세요. 띄어쓰기 한칸으로 구분해주세요");
        String[] arr = new String[n];
        StringTokenizer st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            arr[i] = st.nextToken();
        }

        System.out.println(sequantialSearch(n, target, arr));



        

    }

    // 순차 탐색
    private static int sequantialSearch(int n, String target, String[] arr) {
        // 각 원소를 하나씩 확인하면서
        for (int i = 0; i < n; i++) {
            System.out.println(arr[i]);

            // 현재 원소가 찾고자 하는 원소와 동일한 경우
            if (arr[i].equals(target)) {
                return i + 1;
            }

        }
        return -1; // 원소를 찾지 못했을 때 -1 반환
    }
}
