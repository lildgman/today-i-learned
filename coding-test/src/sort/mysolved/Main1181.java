package sort.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main1181 {

    // 첫째 줄에 단어의 개수 N이 주어진다. (1 ≤ N ≤ 20,000)
    // 둘째 줄부터 N개의 줄에 걸쳐 알파벳 소문자로 이루어진 단어가 한 줄에 하나씩 주어진다.
    // 주어지는 문자열의 길이는 50을 넘지 않는다.

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 단어 개수 입력
        int n = Integer.parseInt(br.readLine());

        ArrayList<String> list = new ArrayList<>();

        // 개수만큼 반복문 돌면서 입력한 단어를 list에 추가
        for (int i = 0; i < n; i++) {
            list.add(br.readLine());
        }

        // list 정렬
        list.sort((s1, s2) -> {
            // 단어 길이가 같을 때
            if (s1.length() == s2.length()) {
                // 사전 순으로 정렬
                return s1.compareTo(s2);
            } else {
                return s1.length() - s2.length();
            }
        });

        StringBuilder sb = new StringBuilder();
        sb.append(list.get(0)).append('\n');

        for (int i = 1; i < n; i++) {
            if (!list.get(i).equals(list.get(i - 1))) {
                sb.append(list.get(i)).append('\n');
            }
        }

        System.out.println(sb);
    }
}
