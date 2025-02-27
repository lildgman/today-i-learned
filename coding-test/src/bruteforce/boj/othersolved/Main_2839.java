package bruteforce.boj.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_2839 {

    /*

    문제
    상근이는 요즘 설탕공장에서 설탕을 배달하고 있다.
    상근이는 지금 사탕가게에 설탕을 정확하게 N킬로그램을 배달해야 한다.
    설탕공장에서 만드는 설탕은 봉지에 담겨져 있다.
    봉지는 3킬로그램 봉지와 5킬로그램 봉지가 있다.

    상근이는 귀찮기 때문에, 최대한 적은 봉지를 들고 가려고 한다.
    예를 들어, 18킬로그램 설탕을 배달해야 할 때,
    3킬로그램 봉지 6개를 가져가도 되지만, 5킬로그램 3개와 3킬로그램 1개를 배달하면, 더 적은 개수의 봉지를 배달할 수 있다.

    상근이가 설탕을 정확하게 N킬로그램 배달해야 할 때,
    봉지 몇 개를 가져가면 되는지 그 수를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 N이 주어진다. (3 ≤ N ≤ 5000)

    출력
    상근이가 배달하는 봉지의 최소 개수를 출력한다. 만약, 정확하게 N킬로그램을 만들 수 없다면 -1을 출력한다.
     */

    public static void main(String[] args) throws IOException {

        // 옮겨야할 무게 n
        // 3키로, 5키로 존재
        // 옮겨야할 봉지 개수 count
        // 최소 개수의 봉지가 필요함
        // 5로 먼저 나누고 나머지를 3으로 나누기
        // 안나눠지면 -1출력


        // 그리디 알고리즘 사용
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        // 옮겨야할 무게
//        int n = Integer.parseInt(br.readLine());
//
//        // 봉지 개수
//        int count = 0;
//
//        while (n >= 0) {
//
//            // 무게가 5로 나눠떨어질 경우
//            if (n % 5 == 0) {
//                count += n / 5;
//                System.out.println(count);
//                return;
//            }
//
//            // 5로 나눠떨어지지 않을 경우 3kg 봉지를 하나씩 빼고
//            // 봉지 개수 추가
//            n -= 3;
//            count++;
//        }
//
//        System.out.println(-1);

        // 브루트 포스
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int minBags = Integer.MAX_VALUE;

        for (int i = 0; i <= n / 5; i++) { // 5kg 봉지를 i개 사용하는 경우

            // 남은 무게
            int remainKg = n - (i * 5);

            // 3kg 봉지로 나누어떨어지는 경우
            if (remainKg % 3 == 0) {
                int bags = i + (remainKg / 3);
                minBags = Math.min(minBags, bags);
            }
        }

        System.out.println(minBags == Integer.MAX_VALUE ? -1 : minBags);
        
    }
}
