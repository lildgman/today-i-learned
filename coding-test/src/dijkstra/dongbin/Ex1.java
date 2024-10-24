package dijkstra.dongbin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Scanner;

class Node implements Comparable<Node> {

    private int index;
    private int distance;

    public Node(int index, int distance) {
        this.index = index;
        this.distance = distance;
    }

    public int getIndex() {
        return this.index;
    }

    public int getDistance() {
        return this.distance;
    }

    // 비용이 짧은 것이 높은 우선순위를 가짐
    @Override
    public int compareTo(Node o) {

        if(this.distance < o.distance) {
            return -1;
        }

        return 1;
    }
}

public class Ex1 {
    /**
     * N개의 도시가 있다. 그리고 도시로 보내고자 하는 메시지가 있는 경우 다른 도시로 전보를 보내서 다른 도시로 해당 메시지를 전송할 수 있다.
     * 하지만 X라는 도시에서 Y라는 도시로 전보를 보내고자 한다면 X에서 Y로 향하는 통로가 설치되어 있어야 한다.
     * 또한 통로를 거쳐 메시지를 보낼때는 일정 시간이 소요된다.
     */

    /**
     * 메시지는 도시 C에서 출발해 각 도시 사이에 설치된 통로를 거쳐 최대한 많이 퍼져나갈 것이다.
     * 각 도시의 번호와 통로가 설치되어 있는 정보가 주어졌을 때,
     * 도시 C에서 보낸 메시지를 받게 되는 도시의 개수는 몇 개이며, 도시들이 모두 메시지를 받는 데까지 걸리는 시간은 얼마인지 계산
     */

    // 입력 조건
    // 첫째 줄에 도시 개수 N, 통로 개수 M, 메시지를 보내고자 하는 도시 C
    // (1<=N<=30,000, 1<=M<=200,000, 1<=C<=N),

    // 둘째 줄부터 M+1번째 줄에 걸쳐 통로에 대한 정보 X,Y,Z가 주어진다.
    // 특정도시 X에서 다른 특정 도시 Y로 이어지는 통로가 있고, 메시지가 전달되는 시간은 Z

    // 출력 조건
    // C에서 보낸 메시지를 받는 도시의 총 개수와 총 걸리는 시간을 공백 구분하여 출력

    public static int INF = (int) 1e9; // 무한대

    // 노드의 개수
    public static int n;
    // 간선의 개수
    public static int m;
    // 시작 노드 번호
    public static int start;
    // 각 노드에 연결되어 있는 노드에 대한 정보를 담는 배열
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<>();
    // 최단 거리 테이블
    public static int[] d = new int[30001];

    public static void dijkstra(int start) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        // 시작 노드로 가기 위한 최단 경로는 0으로 설정하고 우선순위 큐에 삽입
        pq.offer(new Node(start, 0));
        d[start] = 0;

        while (!pq.isEmpty()) {
            // 우선순위 큐에서 노드 정보 추출
            Node node = pq.poll();
            int distance = node.getDistance(); // 현재 노드까지의 비용
            int now = node.getIndex(); // 현재 노드

            // 현재 노드가 이미 처리된 적이 있다면 무시
            if (d[now] < distance) {
                continue;
            }

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

        n = sc.nextInt();
        m = sc.nextInt();
        start = sc.nextInt();

        // 그래프 초기화
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<Node>());
        }

        // 간선 정보 입력
        for (int i = 0; i < m; i++) {
            int x = sc.nextInt();
            int y = sc.nextInt();
            int z = sc.nextInt();

            graph.get(x).add(new Node(y, z));
        }

        // 최단거리를 무한으로 초기화
        Arrays.fill(d, INF);

        // 다익스트라 알고리즘 수행
        dijkstra(start);

        // 도달할 수 있는 노드 개수
        int count = 0;
        // 도달할 수 있는 노드 중, 가장 멀리 있는 노드와의 최단 거리
        int maxDistance = 0;
        for (int i = 1; i <= n; i++) {
            if (d[i] != INF) {
                count += 1;
                maxDistance = Math.max(maxDistance, d[i]);
            }
        }

        System.out.println((count - 1) + " " + maxDistance);
    }

}


