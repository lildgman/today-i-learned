package implementation.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainEx3 {

    // 8 * 8 좌표 평면인 체스판이 존재한다. 체스판의 특정 칸에 나이트가 서 있다.
    // 이 때 좌표 평면상 나이트의 위치가 주어졌을 때 나이트가 이동할 수 있는 경우의 수
    // 가로로 a b c d e f g h
    // 세로로 1 2 3 4 5 6 7 8
    public static void main(String[] args) throws IOException {
        // 나이트는 8방향으로 이동이 가능하다
        // 숫자로 표현하자면
        // (-2, -1), (-1,-2), (1, -2) ,(2, -1), (2,1), (1,2), (-1,2), (-2, 1)
        // 현재 나이트의 위치에서 8방향 모두 갈 수 있는지 확인

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String place = br.readLine();

        int[] dx = {-2, -1, 1, 2, 2, 1, -1, -2};
        int[] dy = {-1, -2, -2, -1, 1, 2, 2, 1};

        // 나이트의 x 좌표
        int row = place.charAt(0) - 'a' + 1;
        // 나이트의 y 좌표
        int col = place.charAt(1) - '0';

        // 나이트의 좌표를 기준으로 이동할 수 있는 위치가 체스판 내에 위치 할 수 있는지 확인

        int count = 0;
        for (int i = 0; i < 8; i++) {
            int movedRow = row + dx[i];
            int movedCol = col + dy[i];

            if (movedRow >= 1 && movedRow <= 8 && movedCol >= 1 && movedCol <= 8) {
                count++;
            }
        }

        System.out.println(count);


    }
}
