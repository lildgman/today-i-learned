package sort.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main1427 {

    // 입력: 첫째 줄에 정렬하려고 하는 수 N이 주어진다. N은 1,000,000,000보다 작거나 같은 자연수이다.
    // 출력: 첫째 줄에 자리수를 내림차순으로 정렬한 수 출력

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String n = br.readLine();

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < n.length(); i++) {
            list.add(Integer.parseInt(String.valueOf(n.charAt(i))));
        }

        Collections.sort(list);
        Collections.reverse(list);

        for (Integer i : list) {
            sb.append(i);
        }
        System.out.println(sb);
    }
}
