package greedy.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GreedyEx1 {
    // 카운터에는 거스름돈으로 사용할 500원, 100원, 50원, 10원 동전이 무한히 있다고 가정
    // 손님에게 거슬러줘야할 돈이 N원일 때 거슬러줘야 할 동전의 최소 개수

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 손님에게 거슬러줘야할 금액
        int n = Integer.parseInt(br.readLine());

        // 현재 내가 갖고있는 동전
        int[] coins = {500, 100, 50, 10};

        int count = 0; // 거슬러줘야 할 동전 개수

        for (int i = 0; i < coins.length; i++) {
            count += n / coins[i];
            n %= coins[i];
        }

        System.out.println(count);


    }
}
