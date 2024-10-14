package dp.dongbin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.io.IOException;

public class Ex1 {

    // 각 식량창고에는 정해진 수의 식량을 저장하고 있고,
    // 개미 전사는 식량창고를 선택적으료 약탈해 식량르 빼앗을 예정이다.
    // 이때 메뚜기 정찰병들은 일직선상에 존재하는 식량창고 중 서로 인접한 식량창고가 공격받으면 알아챌 수 있다.
    // 따라서 최소 한 칸 이상 떨어진 식량창고를 약탈해야 한다.

    // 식량창고 N개에 대한 정보가 주어졌을 때 얻을 수 있는 식량의 최댓값을 구하는 프로그램을 작성해라

    // 입력조건
    // 첫째줄: 식량창고의 개수 N
    // 둘째줄: 공백을 기준으로 각 식량창고에 저장된 식량 개수 K

    // 출력조건
    // 개미전사가 얻을 수 있는 식량의 최댓값

    // 계산된 결과를 저장하기 위한 DP 테이블
    // arr의 자리까지에서 계산된 최대값을 보관
    public static int[] d = new int[100];

    public static void main(String[] args) throws IOException{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 식량창고 개수
        int n = Integer.parseInt(br.readLine());

        // 식량창고에 저장된 식량 개수
        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // DP 진행
        d[0] = arr[0];
        d[1] = Math.max(arr[0], arr[1]);

        for (int i = 2; i < n; i++) {
            d[i] = Math.max(d[i - 1], d[i - 2] + arr[i]);
        }

        System.out.println(d[n - 1]);
    }
}
