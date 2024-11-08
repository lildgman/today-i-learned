package array;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;

public class Main_10808 {

    // 소문자로 이루어진 단어 S, 각 알파벳 단어가 몇 개 포함되어 있는지

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        int[] alphabet = new int[26];

        for (int i = 0; i < s.length(); i++) {
            alphabet[s.charAt(i)-97]++;
        }

        StringBuilder sb = new StringBuilder();
        for (int c : alphabet) {
            sb.append(c).append(" ");
        }

        System.out.println(sb.toString());
    }
}
