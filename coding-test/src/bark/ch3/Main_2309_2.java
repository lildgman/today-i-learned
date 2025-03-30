package bark.ch3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main_2309_2 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] heights = new int[9];
        int[] result = new int[7];

        for (int i = 0; i < 9; i++) {
            heights[i] = Integer.parseInt(br.readLine());
        }

        boolean found = false; // 7명의 난쟁이를 찾았는지 여부를 체크하는 플래그

        for (int i = 0; i < 8 && !found; i++) {  // 9명 중 첫 번째 제외할 난쟁이 선택
            for (int j = i + 1; j < 9 && !found; j++) {  // 두 번째 제외할 난쟁이 선택
                int total = 0;
                int index = 0;

                // 두 명을 제외한 나머지 7명을 result 배열에 저장
                for (int k = 0; k < 9; k++) {
                    if (k != i && k != j) {
                        result[index++] = heights[k];
                    }
                }

                // 7명의 키 합이 100인지 확인
                for (int a = 0; a < 7; a++) {
                    total += result[a];
                }

                if (total == 100) {
                    found = true; // 100이 맞으면 찾았다고 설정
                }
            }
        }

        Arrays.sort(result);
        for (int i : result) {
            System.out.println(i);
        }

    }
}
