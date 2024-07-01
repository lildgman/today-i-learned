package sort.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main2587 {

    // 입력: 첫째 줄부터 다섯 번째 줄까지 한 줄에 하나씩 자연수가 주어진다. 주어지는 자연수는 100 보다 작은 10의 배수이다.
    // 출력: 첫째 줄에는 평균을 출력하고, 둘째 줄에는 중앙값을 출력한다. 평균과 중앙값은 모두 자연수이다.

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 다섯 번째줄까지 자연수가 주어진다 하였으므로 길이가 5인 배열 생성
        int[] arr = new int[5];

        for (int i = 0; i < 5; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // 중앙값을 알아내기 위해 정렬
        Arrays.sort(arr);

        int arrSum = 0;
        for (int i : arr) {
            arrSum += i;
        }

        System.out.println(arrSum / arr.length);
        System.out.println(arr[2]);
    }
}
