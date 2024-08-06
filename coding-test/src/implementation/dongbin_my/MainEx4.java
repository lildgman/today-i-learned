package implementation.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainEx4 {

    // 알파벳 대문자와 숫자(0~9)로만 구성된 문자열이 입력으로 주어진다.
    // 이때 모든 알파벳을 오름차순으로 정렬하고 출력한 뒤,
    // 모든 숫자를 더한 값

    public static void main(String[] args) throws IOException {
        // 입력에서 알파벳 대문자와 숫자를 나눠준 뒤
        // 알파벳은 정렬, 숫자는 더해준 뒤 합해주자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        // 정렬을 하기 위한 ArrayList
        List<Character> list = new ArrayList<>();

        // 숫자들의 합
        int sum = 0;

        for (int i = 0; i < input.length(); i++) {
            int inputChar = input.charAt(i);

            if (inputChar >= 65 && inputChar <= 90) {
                list.add((char) inputChar);
            } else {
                sum += inputChar - '0';
            }
        }

        Collections.sort(list);

        StringBuilder sb = new StringBuilder();

        for (Character c : list) {
            sb.append(c);
        }

        sb.append(sum);

        System.out.println(sb);

    }
}
