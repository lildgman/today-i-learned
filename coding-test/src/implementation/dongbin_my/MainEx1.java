package implementation.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class MainEx1 {

    // 여행가 A는 N x N 크기의 정사각형 공간 위에 서 있다
    // 이 공간은 1 x 1 크기의 정사각형으로 나누어져 있다.
    // 가장 왼쪽 위 좌표는 (1,1)이며, 가장 오른쪽 아래 좌표는 (N, N)에 해당된다.
    // 여행가 A는 상, 하, 좌, 우 방향으로 이동가능하며 시작좌표는 항상 (1,1)이다
    // 계획서에는 하나의 줄에 띄어쓰기를 기준으로 L, R, U, D중 하나의 문자가 반복적으로 적혀있다.
    // L: 왼쪽으로 한칸, R: 오른쪽으로 한칸, U: 위쪽으로 한칸, D: 아래로 한칸

    // 이때 여행가 A가 N x N 크기의 정사각형 공간을 벗어나는 움직임은 무시된다.
    // 예를 들면 (1,1)의 위치에서 L 혹은 U를 만나면 무시된다.

    // 입력 조건
    // 첫째 줄에는 공간의 크기를 나타내는 N이 주어진다.
    // 둘째 줄에는 여행가 A가 이동할 계획서 내용이 주어진다

    // 출력 조건
    // 여행가 A가 최종적으로 도착할 지점의 좌표를 공백을 기준으로 구분하여 출력한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 시작 지점
        int x = 1;
        int y = 1;

        // 계획서에 입력된 문자들
        String[] plans = br.readLine().split(" ");

        // L,R,U,D에 따른 움직임
        int[] mx = {0, 0, -1, 1};
        int[] my = {-1, 1, 0, 0};
        char[] commands = {'L', 'R', 'U', 'D'};

        for (int i = 0; i < plans.length; i++) {
            char plan = plans[i].charAt(0);

            int nx = 0;
            int ny = 0;
            // 이동 후 좌표 구하기
            for (int j = 0; j < commands.length; j++) {
                if (plan == commands[j]) {
                    nx = x + mx[j];
                    ny = y + my[j];
                }
            }

            if (nx < 1 || ny < 1 || nx > n || ny > n) {
                continue;
            }

            x = nx;
            y = ny;
        }

        System.out.println(x + " " + y);


    }
}
