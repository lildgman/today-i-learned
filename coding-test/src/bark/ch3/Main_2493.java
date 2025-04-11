package bark.ch3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main_2493 {

    /*
    문제
    KOI 통신연구소는 레이저를 이용한 새로운 비밀 통신 시스템 개발을 위한 실험을 하고 있다.
    실험을 위하여 일직선 위에 N개의 높이가 서로 다른 탑을 수평 직선의 왼쪽부터 오른쪽 방향으로 차례로 세우고,
    각 탑의 꼭대기에 레이저 송신기를 설치하였다.
    모든 탑의 레이저 송신기는 레이저 신호를 지표면과 평행하게 수평 직선의 왼쪽 방향으로 발사하고,
    탑의 기둥 모두에는 레이저 신호를 수신하는 장치가 설치되어 있다.
    하나의 탑에서 발사된 레이저 신호는 가장 먼저 만나는 단 하나의 탑에서만 수신이 가능하다.

    예를 들어 높이가 6, 9, 5, 7, 4인 다섯 개의 탑이 수평 직선에 일렬로 서 있고,
    모든 탑에서는 주어진 탑 순서의 반대 방향(왼쪽 방향)으로 동시에 레이저 신호를 발사한다고 하자.
    그러면, 높이가 4인 다섯 번째 탑에서 발사한 레이저 신호는 높이가 7인 네 번째 탑이 수신을 하고,
    높이가 7인 네 번째 탑의 신호는 높이가 9인 두 번째 탑이,
    높이가 5인 세 번째 탑의 신호도 높이가 9인 두 번째 탑이 수신을 한다.
    높이가 9인 두 번째 탑과 높이가 6인 첫 번째 탑이 보낸 레이저 신호는 어떤 탑에서도 수신을 하지 못한다.

    탑들의 개수 N과 탑들의 높이가 주어질 때,
    각각의 탑에서 발사한 레이저 신호를 어느 탑에서 수신하는지를 알아내는 프로그램을 작성하라.

    입력
    첫째 줄에 탑의 수를 나타내는 정수 N이 주어진다. N은 1 이상 500,000 이하이다.
    둘째 줄에는 N개의 탑들의 높이가 직선상에 놓인 순서대로 하나의 빈칸을 사이에 두고 주어진다. 탑들의 높이는 1 이상 100,000,000 이하의 정수이다.

    출력
    첫째 줄에 주어진 탑들의 순서대로 각각의 탑들에서 발사한 레이저 신호를 수신한 탑들의 번호를 하나의 빈칸을 사이에 두고 출력한다.
    만약 레이저 신호를 수신하는 탑이 존재하지 않으면 0을 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());
        int[] result = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        Stack<Tower> stack = new Stack<>();

        // 탑을 왼쪽 방향으로 탐색하면서
        // 현재 탑의 높이보다 낮은 탑들은 레이저를 수신할 수 없으므로 버린다.
        // 현재 탑의 높이보다 높은 탑이 현재 탑의 레이저를 수신할 수 있다.
        for (int i = 0; i < n; i++) {

            // 현재 탑의 높이
            int height = Integer.parseInt(st.nextToken());

            // 현재 탑의 높이보다 낮은 탑들은 수신할 수 없다.
            while (!stack.isEmpty() && stack.peek().getHeight() < height) {
                stack.pop();
            }

            // 스택이 비어있지 않다면 수신이 가능한 탑이 있다는 뜻
            if (!stack.isEmpty()) {
                result[i] = stack.peek().getIndex();
            } else {
                result[i] = 0;
            }

            // 스택에 현재 탑을 넣음
            stack.push(new Tower(i + 1, height));
        }

        StringBuilder sb = new StringBuilder();
        for (int index : result) {
            sb.append(index).append(" ");
        }

        System.out.println(sb);

    }

    static class Tower {
        private int index;
        private int height;

        public Tower(int index, int height) {
            this.index = index;
            this.height = height;
        }

        public int getIndex() {
            return this.index;
        }

        public int getHeight() {
            return this.height;
        }
    }
}
