package dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Ex2 {

    public static final int INF = (int) 1e9; // 무한

    // 노드 개수
    public static int n;

    // 간선 개수
    public static int m;

    // 시작 노드 번호
    public static int start;

    // 각 노드에 연결되어 있는 노드에 대한 정보를 담는 배열
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<>();

    // 최단거리 테이블
    public static int[] d = new int[100001];

    public static void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>(); // 우선순위 큐
        // 시작 노드로 가기 위한 최단 경로는 0으로 설정하고 큐에 삽입
        pq.offer(new Node(start, 0));
        d[start] = 0;

        while (!pq.isEmpty()) {
            // 큐가 비어있지 않다면
            // 가장 최단거리가 짧은 노드에 대한 정보 꺼내기
            Node node = pq.poll();
            int distance = node.getDistance(); // 현재 노드까지의 비용
            int now = node.getIndex();// 현재 노드

            // 현재 노드가 이미 처리된 적 있다면 무시
            if (d[now] < distance) {
                continue;
            }
            // 현재 노드와 연결된 인접 노드 확인
            for (int i = 0; i < graph.get(now).size(); i++) {

                int cost = d[now] + graph.get(now).get(i).getDistance();
                // 현재 노드를 거쳐 다른 노드로 이동하는 거리가 더 짧은 경우
                if (cost < d[graph.get(now).get(i).getIndex()]) {
                    d[graph.get(now).get(i).getIndex()] = cost;
                    pq.offer(new Node(graph.get(now).get(i).getIndex(), cost));
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 노드 개수
        n = sc.nextInt();
        // 간선 개수
        m = sc.nextInt();
        // 시작 노드 번호
        start = sc.nextInt();

        // 그래프 초기화
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Node>());
        }

        // 모든 간선 정보 입력 받기
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            // a번 노드에 b번 노드로 가는 비용이 c
            graph.get(a).add(new Node(b, c));
        }

        // 최단 거리 테이블을 모두 무한으로 초기화
        Arrays.fill(d, INF);

        // 다익스트라 알고리즘 수행
        dijkstra(start);

        // 모든 노드로 가기 위한 최단 거리 출력
        for (int i = 1; i <= n; i++) {
            // 도달할 수 없는 경우, INFINITY 출력
            if (d[i] == INF) {
                System.out.println("INFINITY");
            }
            // 도달할 수 있는 경우 거리 출력
            else {
                System.out.println(d[i]);
            }
        }
    }

}


