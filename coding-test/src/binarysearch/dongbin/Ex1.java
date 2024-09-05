package binarysearch.dongbin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Ex1 {
    // 절단기에 높이(h)를 지정하면 줄지어진 떡을 한 번에 절단한다.
    // 높이가 H보다 긴 떡은 H 위의 부분이 H 위의 부분이 잘리고
    // 낮은 떡은 잘리지 않는다.

    // 입력 조건
    // 첫째 줄에 떡의 개수 N과 요청한 떡의 길이 M
    // 둘째 줄에 떡의 개별 높이
    // 떡 높이의 총합은 항상 M 이상이므로 손님은 필요한 양 만큼 떡을 사갈 수 있다.

    // 출력 조건
    // 적어도 M만큼의 떡을 집에 가져가기 위해 절단기에 설정할 수 있는 높이의 최댓값

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 떡의 개수
        int n = Integer.parseInt(st.nextToken());
        // 요청한 떡의 길이
        int m = Integer.parseInt(st.nextToken());

        // 떡의 길이
        int[] arr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        int result = giveMeDDuck(arr, n, m);
        System.out.println(result);
    }

    private static int giveMeDDuck(int[] arr, int amount ,int orderRiceCakeLength) {
        for (int i = 0; i < amount - 1; i++) {

            int RiceCakeSettingLength = arr[i];
            int cuttedRiceCakeLength = RiceCakeSettingLength - arr[i];
            int totalCuttedRiceCakeLength = 0;


            if (cuttedRiceCakeLength >= 0) {
                totalCuttedRiceCakeLength += cuttedRiceCakeLength;
            }

            if (totalCuttedRiceCakeLength == orderRiceCakeLength) {
                return arr[i];
            }
        }
        return -1;
    }
}
