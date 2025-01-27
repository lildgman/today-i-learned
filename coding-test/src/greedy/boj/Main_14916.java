package greedy.boj;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class Main_14916 {

    /**
     * https://www.acmicpc.net/problem/14916
     * <p>
     * 춘향이는 편의점 카운터에서 일한다.
     * <p>
     * 손님이 2원짜리와 5원짜리로만 거스름돈을 달라고 한다.
     * 2원짜리 동전과 5원짜리 동전은 무한정 많이 가지고 있다.
     * 동전의 개수가 최소가 되도록 거슬러 주어야 한다.
     * 거스름돈이 n인 경우,
     * 최소 동전의 개수가 몇 개인지 알려주는 프로그램을 작성하시오.
     * <p>
     * 예를 들어,
     * 거스름돈이 15원이면 5원짜리 3개를,
     * 거스름돈이 14원이면 5원짜리 2개와 2원짜리 2개로 총 4개를,
     * 거스름돈이 13원이면 5원짜리 1개와 2원짜리 4개로 총 5개를 주어야
     * 동전의 개수가 최소가 된다.
     * <p>
     * 입력
     * 첫째 줄에 거스름돈 액수 n(1 ≤ n ≤ 100,000)이 주어진다.
     * <p>
     * 출력
     * 거스름돈 동전의 최소 개수를 출력한다.
     * 만약 거슬러 줄 수 없으면 -1을 출력한다.
     */


    public static void main(String[] args) throws IOException {

        // 거스름돈 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 거스름돈
        int n = Integer.parseInt(br.readLine());

        // 동전개수
        int coinCount = 0;

        while (n > 0) {
            if (n % 5 == 0) {
                coinCount += n / 5;
                n = 0;
                break;
            } else {
                n -= 2;
                coinCount++;
            }
        }

        if (n < 0) {
            System.out.println(-1);
        } else {
            System.out.println(coinCount);
        }

    }
}
