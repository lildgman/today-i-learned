package bruteforce.boj.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_1436 {

    /*

    종말의 수란 어떤 수에 6이 적어도 3개 이상 연속으로 들어가는 수를 말한다.
    제일 작은 종말의 수는 666이고, 그 다음으로 큰 수는 1666, 2666, 3666, .... 이다.
    따라서, 숌은 첫 번째 영화의 제목은 "세상의 종말 666", 두 번째 영화의 제목은 "세상의 종말 1666"와 같이 이름을 지을 것이다.
    일반화해서 생각하면, N번째 영화의 제목은 세상의 종말 (N번째로 작은 종말의 수) 와 같다.

    숌이 만든 N번째 영화의 제목에 들어간 수를 출력하는 프로그램을 작성하시오.
    숌은 이 시리즈를 항상 차례대로 만들고, 다른 영화는 만들지 않는다.

    입력
    첫째 줄에 N이 주어진다. N은 10,000보다 작거나 같은 자연수이다.

    출력
    첫째 줄에 N번째 영화의 제목에 들어간 수를 출력한다.
     */

    public static void main(String[] args) throws IOException {

        // 666,1666,2666,3666,4666,5666,6660,6661,6662,6663...
        // 규칙이 일정치 않다.
        // 666부터 차례대로 비교해보자
        // "666"이 존재하면 count++
        // n과 count가 같으면 출력


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int count = 0;
        int num = 666;

        while (true) {

            // 숫자에 666이 포함되어있으면 count++
            if (String.valueOf(num).contains("666")) {
                count++;
            }

            // 입력한 n과 count가 같으면 출력
            if (count == n) {
                System.out.println(num);
                break;
            }

            num++;

        }

    }
}
