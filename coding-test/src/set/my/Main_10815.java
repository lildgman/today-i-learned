package set.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.StringTokenizer;

public class Main_10815 {

    public static void main(String[] args) throws IOException {

        // 카드의 개수 n 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // HashSet을 사용해 가지고 있는 카드를 저장
        HashSet<Integer> set = new HashSet<>();

        // 카드 개수만큼 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            set.add(Integer.parseInt(st.nextToken()));
        }

        // 판별해야할 숫자의 개수 m
        int m = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        // 숫자 판별
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {

            int num = Integer.parseInt(st.nextToken());

            if (set.contains(num)) { // 가지고 있으면 1
                sb.append(1);
            } else { // 없으면 0
                sb.append(0);
            }

            sb.append(" ");
        }

        System.out.println(sb);

    }
}
