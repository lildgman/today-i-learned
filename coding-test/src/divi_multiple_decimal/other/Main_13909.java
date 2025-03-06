package divi_multiple_decimal.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_13909 {

    /*
    문제
    서강대학교 컴퓨터공학과 실습실 R912호에는 현재 N개의 창문이 있고 또 N명의 사람이 있다.
    1번째 사람은 1의 배수 번째 창문을 열려 있으면 닫고 닫혀 있으면 연다.
    2번째 사람은 2의 배수 번째 창문을 열려 있으면 닫고 닫혀 있으면 연다.
    이러한 행동을 N번째 사람까지 진행한 후 열려 있는 창문의 개수를 구하라.
    단, 처음에 모든 창문은 닫혀 있다.

    예를 들어 현재 3개의 창문이 있고 3명의 사람이 있을 때,
    1번째 사람은 1의 배수인 1,2,3번 창문을 연다. (1, 1, 1)
    2번째 사람은 2의 배수인 2번 창문을 닫는다. (1, 0, 1)
    3번째 사람은 3의 배수인 3번 창문을 닫는다. (1, 0, 0)
    결과적으로 마지막에 열려 있는 창문의 개수는 1개 이다.

    입력
    첫 번째 줄에는 창문의 개수와 사람의 수 N(1 ≤ N ≤ 2,100,000,000)이 주어진다.

    출력
    마지막에 열려 있는 창문의 개수를 출력한다.
     */

    public static void main(String[] args) throws IOException {

        /*
        n이 10일 경우
        먼저 모든 창문은 닫혀있다. -> x,x,x,x,x,x,x,x,x,x
        1번 사람 -> o,o,o,o,o,o,o,o,o,o
        2번 사람 -> o,x,o,x,o,x,o,x,o,x
        3번 사람 -> o,x,x,x,o,o,o,x,x,x
        4번 사람 -> o,x,x,o,o,o,o,o,x,x
        5번 사람 -> o,x,x,o,x,o,o,o,x,o
        6번 사람 -> o,x,x,o,x,x,o,o,x,o
        7번 사람 -> o,x,x,o,x,x,x,o,x,o
        8번 사람 -> o,x,x,o,x,x,x,x,x,o
        9번 사람 -> o,x,x,o,x,x,x,x,o,o
        10번 사람 -> o,x,x,o,x,x,x,x,o,x
        1,4,9번 창문 열려 있음
        완전제곱수의 개수를 구하면 된다.

         */

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        System.out.println((int)Math.sqrt(n));
    }
}
