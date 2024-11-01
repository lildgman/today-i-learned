package etc;

import java.util.Scanner;

public class DisjointSet {

    // v: 노드 개수
    public static int v;
    // e: 간선(union 연산)의 개수
    public static int e;

    // 부모 테이블
    // 노드의 개수를 100,000개 있다고 가정
    public static int[] parent = new int[100001];

    // 특정 원소가 속한 집합 찾기
    public static int findParent(int x) {

        // 루트 노드가 아니면 루트 노드를 찾을 때 까지 재귀적으로 호출
        if (x == parent[x]) {
            return x;
        }

        return findParent(parent[x]);
    }

    // 두 원소가 속한 집합을 합치기
    public static void unionParent(int a, int b) {
        a = findParent(a);
        b = findParent(b);

        if (a < b) {
            parent[b] = a;
        } else {
            parent[a] = b;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        v = sc.nextInt();
        e = sc.nextInt();

        // 부모 테이블에서 부모를 자기 자신으로 초기화
        for (int i = 1; i <= v; i++) {
            parent[i] = i;
        }

        // Union 연산 각각 수행
        for (int i = 0; i < e; i++) {
            int a = sc.nextInt();
            int b = sc.nextInt();

            unionParent(a, b);
        }
    }

}
