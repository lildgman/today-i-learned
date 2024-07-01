package sort.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

public class Main2751 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        int n = Integer.parseInt(br.readLine());

        ArrayList<Integer> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            list.add(Integer.parseInt(br.readLine()));
        }

        Collections.sort(list);
        // Collections.sort는 Timsort 알고리즘 사용
        // Timsort는 합병 및 삽입정렬 알고리즘 사용
        // 합병정렬은 최선, 최악 모두 O(nlogn)
        // 삽입정렬은 최선의 경우 O(n), 최악의 경우 O(n^2)


        for (int i : list) {
            sb.append(i).append('\n');
        }

        System.out.println(sb);
    }
}
