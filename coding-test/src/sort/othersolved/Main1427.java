package sort.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main1427 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // 각 자릿수는 0~9까지 이므로 10칸의 배열만 있으면 된다.
        int[] counting = new int[10];

        String n = br.readLine();

        for (int i = 0; i < n.length(); i++) {
            counting[n.charAt(i) - '0']++;
        }

        for (int i = 9; i >= 0; i--) {
            while (counting[i]-- > 0) {
                sb.append(i);
            }
        }

        System.out.println(sb);
    }
}
