package dijkstra;

import java.util.*;



public class Ex1 {

    public static final int INF = (int) 1e9; // 무한대

    // 노드의 개수
    public static int n;

    // 간선의 개수
    public static int m;

    // 시작 노드의 번호
    public static int start;

    // 각 노드에 연결되어 있는 노드에 대한 정보를 담는 배열
    public static ArrayList<ArrayList<Node>> graph = new ArrayList<>();

    // 방문한 적이 있는지 체크하는 목적의 배열
    public static boolean[] visited = new boolean[100001];

    // 최단 거리 테이블
    public static int[] d = new int[100001];

    // 방문하지 않은 노드 중, 가장 최단 거리가 짧은 노드의 번호 반환
    public static int getSmallestNode(){
        int min_value = INF;
        int index = 0; // 가장 최단 거리가 짧은 인덱스
        for (int i = 1; i <=n; i++) {
            if(d[i] < min_value && !visited[i]) {
                min_value = d[i];
                index = i;
            }
        }

        return index;
    }

    public static void dijkstra(int start) {
        // 시작 노드에 대해 초기화
        d[start] = 0; // 자기 자신에 대해서는 최단거리가 0
        visited[start] = true; // 시작지점을 방문처리

        for(int j = 0; j < graph.get(start).size(); j++) {
            d[graph.get(start).get(j).getIndex()] = graph.get(start).get(j).getDistance();
        }

        // 시작 노드를 제외한 n-1개의 노드에 대해 반복
        for(int i = 0; i<n-1; i++) {
            // 현재 최단 거리가 가장 짧은 노드를 꺼내 방문 처리
            int now = getSmallestNode();
            visited[now] = true;

            for(int j = 0; j < graph.get(now).size(); j++) {
                // 현재 노드를 거쳐가는 비용
                int cost = d[now] + graph.get(now).get(j).getDistance();
                // 현재 노드를 거쳐서 다른 노드로 이동하는 거리가 더 짧은 경우
                if (cost < d[graph.get(now).get(j).getIndex()]) {
                    d[graph.get(now).get(j).getIndex()] = cost;
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

        // 모든 간선 정보 입력
        for (int i = 0; i < m; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();

            // a번 노드에서 b번 노드로 가는 비용이 c이다.
            graph.get(a).add(new Node(b, c));
        }

        // 최단 거리 테이블을 모두 무한으로 초기화
        Arrays.fill(d,INF);

        // 다익스트라 알고리즘 수행
        dijkstra(start);

        // 모든 노드로 가기 위한 최단 거리 출력
        for (int i = 1; i <= n; i++) {
            // 도달할 수 없는 경우, 무한(INFINITY)라고 출력
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
