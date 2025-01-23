package greedy.thisiscodingtest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class GreedyExample {

    // 거스름돈으로 사용할 500원, 100원, 50원, 10원 짜리가 무한대 있다고 가정할 때,
    // 손님에게 거슬러줘야할 돈이 N원 일 때 거슬러줘야할 동전의 최소 개수를 구하라

    static final int[] coins = {500, 100, 50, 10};
    static int coinCount = 0;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 손님에게 거슬러 줘야할 금액
        int n = Integer.parseInt(br.readLine());

        for (int coinType : coins) {
            coinCount += n / coinType;
            n %= coinType;
        }

        System.out.println(coinCount);
    }
}
