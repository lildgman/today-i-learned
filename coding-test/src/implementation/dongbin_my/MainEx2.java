package implementation.dongbin_my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainEx2 {

    // 정수 N이 입력되면 00시 00분 00초부터 N시 59분 59초까지의 모든 시각 중 3이 하나라도 포함되는 모든 경우의 수를 구하는 프로그램 작성

    public static void main(String[] args) throws IOException {
        // 시는 1~23까지 가능하다. 즉 일의 자리에서만 3이 올 수 있다. -> h % 10 == 3
        // 분, 초는 십의 자리와 일의 자리 모두 3이 올 수 있다 -> m / 10 == 3, m % 10 == 3, s / 10 == 3, s % 10 == 3
        // 3이 하나라도 포함되는 경우의 수를 구하는 것이므로 or를 사용하자

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine()); // 시간

        int count = 0;

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j < 60; j++) {
                for (int k = 0; k < 60; k++) {

                    if (i % 10 == 3 || j / 10 == 3 || j % 10 == 3 || k / 10 == 3 || k % 10 == 3) {
                        count++;
                    }
                }
            }
        }

        System.out.println(count);


    }
}
