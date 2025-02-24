package bruteforce.boj.othersolved;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main_1018 {

    /*
    https://www.acmicpc.net/problem/1018

    문제
    지민이는 자신의 저택에서 M,N개의 단위 정사각형으로 나누어져 있는 M×N 크기의 보드를 찾았다.
    어떤 정사각형은 검은색으로 칠해져 있고, 나머지는 흰색으로 칠해져 있다.
    지민이는 이 보드를 잘라서 8×8 크기의 체스판으로 만들려고 한다.

    체스판은 검은색과 흰색이 번갈아서 칠해져 있어야 한다.
    구체적으로, 각 칸이 검은색과 흰색 중 하나로 색칠되어 있고,
    변을 공유하는 두 개의 사각형은 다른 색으로 칠해져 있어야 한다.
    따라서 이 정의를 따르면 체스판을 색칠하는 경우는 두 가지뿐이다.
    하나는 맨 왼쪽 위 칸이 흰색인 경우, 하나는 검은색인 경우이다.

    보드가 체스판처럼 칠해져 있다는 보장이 없어서,
    지민이는 8×8 크기의 체스판으로 잘라낸 후에 몇 개의 정사각형을 다시 칠해야겠다고 생각했다.
    당연히 8*8 크기는 아무데서나 골라도 된다.
    지민이가 다시 칠해야 하는 정사각형의 최소 개수를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 N과 M이 주어진다. N과 M은 8보다 크거나 같고, 50보다 작거나 같은 자연수이다.
    둘째 줄부터 N개의 줄에는 보드의 각 행의 상태가 주어진다. B는 검은색이며, W는 흰색이다.

    출력
    첫째 줄에 지민이가 다시 칠해야 하는 정사각형 개수의 최솟값을 출력한다.
     */

    public static void main(String[] args) throws IOException {
        // 8 * 8 크기의 체스판이 필요함
        // 흑, 백이 번갈아 나와야함
        // 어디에서든 체스판을 자를 수 있음
        // 다시 색을 칠해야 하는 최소 개수를 구해라

        // 입력을 받기 위한 BufferedReader
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // N과 M을 구분해주기 위한 StringTokenizer
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        // 보드의 색 입력 받기
        char[][] board = new char[n][m];

        for (int i = 0; i < n; i++) {
            String str = br.readLine();

            for (int j = 0; j < m; j++) {
                board[i][j] = str.charAt(j);
            }
        }

        int min = Integer.MAX_VALUE;

        // 8*8로 자를 수 있는 모든 범위에 대해 검사
        for (int i = 0; i <= n - 8; i++) {
            for (int j = 0; j <= m - 8; j++) {

                // 흰색으로 시작하는 경우
                int wCount = 0;
                // 검은색으로 시작하는 경우
                int bCount = 0;

                // 자른 8*8 보드에 대해서 검사
                for (int x = 0; x < 8; x++) {
                    for (int y = 0; y < 8; y++) {

                        // 현재 위치의 보드 색상
                        char currentColor = board[i + x][j + y];

                        // x+y가 짝수일 때 시작색과 같고
                        // 홀수일 때 시작색과 달라야한다.
                        // 0,0 = > 흰, 0 + 0 = 0
                        // 0,1 = > 검, 0 + 1 = 1, 시작색과 반대
                        // 0,2 = > 흰, 0 + 2 = 2, 시작색과 일치
                        if ((x + y) % 2 == 0) {
                            if (currentColor != 'W') {
                                wCount++;
                            }
                            if (currentColor != 'B') {
                                bCount++;
                            }
                        } else {
                            if (currentColor != 'B') {
                                wCount++;
                            }

                            if (currentColor != 'W') {
                                bCount++;
                            }
                        }
                    }
                }

                min = Math.min(min, Math.min(wCount, bCount));
            }

        }

        System.out.println(min);
    }
}
