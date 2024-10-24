package dijkstra.dongbin;

import java.util.Arrays;
import java.util.Scanner;

public class Ex2 {
    // 미래 도시에는 1번부터 N번까지 회사가 있고 특정 회사끼리 서로 도로를 통해 연결되어있다.
    // 판매자 A는 현재 1번 회사에 있고 X번 회사에 방문해 물건을 판매하고자 한다

    // 특정 회사에 도착하기 위한 방법은 회사끼리 연결되어 있는 도로를 이용하는 방법뿐이다.
    // 또한 연결된 2개의 회사는 양방향으로 이동할 수 있다.
    // 특정회사와 다른 회사가 도로로 연결되어 있다면 정확히 1만큼 시간으로 이동 가능

    // 1번 회사에서 출발해 K번 회사를 방문한 뒤 X번 회사로 가는 것이 목표이다.
    // 이때 회사 사이를 이동하게 되는 최소 시간을 계산

    // 입력 조건
    // 첫째 줄에 전체 회사 개수 N, 경로 개수 M이 공백으로 구분되어 차례대로 주어진다.
    // (1<=n,m<=100)
    // 둘째줄 부터 M+1번째 줄에는 연결된 두 회사 번호가 공백으로 구분되어 주어진다.
    // M+2번째 줄에는 X와 K가 공백으로 구분되어 주어진다.

    // 출력 조건
    // 첫째 줄에 방문 판매원 A가 K번 회사를 거쳐 X번 회사로 가는 최소 이동 시간을 출력
    // 만약 도달할 수 없다면 -1 출력

    public static int INF = (int) 1e9; // 무한대

    // 노드 개수
    public static int n;
    // 간선 개수
    public static int m;
    // 거쳐갈 노드
    public static int x;
    // 목적지 노드
    public static int k;

    // 최단 거리 테이블
    public static int[][] graph = new int[101][101];

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        n = sc.nextInt();
        m = sc.nextInt();

        // 최단 거리 테이블 모두 무한으로 초기화
        for (int i = 0; i < 101; i++) {
            Arrays.fill(graph[i], INF);
        }

        // 자기 자신으로 가는 비용은 0으로 초기화
        for (int a = 1; a <= n; a++) {
            for (int b = 1; b <= n; b++) {
                if (a == b) {
                    graph[a][b] = 0;
                }
            }
        }

        // 간선 정보를 입력 받아 초기화
        for (int i = 0; i < m; i++) {
            // a와 b 서로에게 가는 비용은 1
            int a = sc.nextInt();
            int b = sc.nextInt();
            graph[a][b] = 1;
            graph[b][a] = 1;
        }

        x = sc.nextInt();
        k = sc.nextInt();

        // 점화식에 따라 플로이드 워셜 알고리즘 수행
        for (int k = 1; k <= n; k++) {
            for (int a = 1; a <= n; a++) {
                for (int b = 1; b <= n; b++) {
                    graph[a][b] = Math.min(graph[a][b], graph[a][k] + graph[k][b]);
                }
            }
        }

        // 수행 결과
        int distance = graph[1][k] + graph[k][x];

        if (distance >= INF) {
            System.out.println(-1);
        } else {
            System.out.println(distance);
        }
    }

}
