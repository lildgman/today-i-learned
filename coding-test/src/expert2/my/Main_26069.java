package expert2.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_26069 {

    /*
    문제
    사람들이 만난 기록이 시간 순서대로 N개 주어진다.
    무지개 댄스를 추지 않고 있던 사람이 무지개 댄스를 추고 있던 사람을 만나게 된다면, 만난 시점 이후로 무지개 댄스를 추게 된다.
    기록이 시작되기 이전 무지개 댄스를 추고 있는 사람은 총총이 뿐이라고 할 때, 마지막 기록 이후 무지개 댄스를 추는 사람이 몇 명인지 구해보자!

    입력
    첫번째 줄에 사람들이 만난 기록 수 (1<= N <= 100)
    두번째 줄 부터 n개의 줄에 걸쳐 사람들이 만난 기록이 주어진다.
    i+1번째 줄부터 i번째로 만난 사람들의 이름 Ai와 Bi가 공백을 사이에 두고 주어진다.
    Ai와 Bi는 숫자, 영문 대소문자로 이루어진 최대 길이 20의 문자열이고 서로 같지 않다.
    총총이 이름은 "ChongChong", 기록에서 1회 이상 주어진다.
    동명이인 없으며, 사람의 이름은 대소문자로 구분, (ChongChong과 chongchong은 다름)

    출력
    마지막 기록 이후 무지개 댄스를 추는 사람의 수를 출력
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // ChongChong을 dancers에 미리 넣어두기
        HashSet<String> dancers = new HashSet<>();
        dancers.add("ChongChong"); // 총총이는 춤을 추고 있고, 마주하는 사람은 춤을 추게 된다.

        StringTokenizer st;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            String user1 = st.nextToken();
            String user2 = st.nextToken();

            // user1과 user2가 입력으로 "ChongChong"이 올 수 있음
            // "ChongChong"은 처음부터 춤을 추고 있음
            // dancers에 미리 "ChongChong"을 넣어뒀기 때문에
            // user1나 user2 둘 중 한명이라도 춤을 추고 있으면 두 사람 모두 춤을 추게 된다.
            if (dancers.contains(user1) || dancers.contains(user2)) {
                dancers.add(user1);
                dancers.add(user2);
            }

        }

        System.out.println(dancers.size());

    }
}
