package set.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_10816 {

    /*
    문제
    숫자 카드는 정수 하나가 적혀져 있는 카드이다.
    상근이는 숫자 카드 N개를 가지고 있다.
    정수 M개가 주어졌을 때, 이 수가 적혀있는 숫자 카드를 상근이가 몇 개 가지고 있는지 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 상근이가 가지고 있는 숫자 카드의 개수 N(1 ≤ N ≤ 500,000)이 주어진다.

    둘째 줄에는 숫자 카드에 적혀있는 정수가 주어진다.
    숫자 카드에 적혀있는 수는 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다.

    셋째 줄에는 M(1 ≤ M ≤ 500,000)이 주어진다.

    넷째 줄에는 상근이가 몇 개 가지고 있는 숫자 카드인지 구해야 할 M개의 정수가 주어지며, 이 수는 공백으로 구분되어져 있다.
    이 수도 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다.

    출력
    첫째 줄에 입력으로 주어진 M개의 수에 대해서, 각 수가 적힌 숫자 카드를 상근이가 몇 개 가지고 있는지를 공백으로 구분해 출력한다.
     */

    public static void main(String[] args) throws IOException {

        // 가지고 있는 숫자 카드 개수 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 가지고 있는 숫자 카드 개수 N
        int n = Integer.parseInt(br.readLine());

        HashMap<Integer, Integer> cardCount = new HashMap<>();

        // 가지고 있는 숫자 카드 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            int num = Integer.parseInt(st.nextToken());
            cardCount.put(num, cardCount.getOrDefault(num, 0) + 1);
        }

        // 찾아야할 숫자카드 개수 입력
        int m = Integer.parseInt(br.readLine());

        // 숫자카드에 대한 개수 출력
        st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            int num = Integer.parseInt(st.nextToken());
            sb.append(cardCount.getOrDefault(num, 0)).append(" ");
        }

        System.out.println(sb);
    }
}
