package set.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_10815_bs {

    /*

    문제
    숫자 카드는 정수 하나가 적혀져 있는 카드이다.
    상근이는 숫자 카드 N개를 가지고 있다.
    정수 M개가 주어졌을 때, 이 수가 적혀있는 숫자 카드를 상근이가 가지고 있는지 아닌지를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 상근이가 가지고 있는 숫자 카드의 개수 N(1 ≤ N ≤ 500,000)이 주어진다.
    둘째 줄에는 숫자 카드에 적혀있는 정수가 주어진다.
    숫자 카드에 적혀있는 수는 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다.
    두 숫자 카드에 같은 수가 적혀있는 경우는 없다.

    셋째 줄에는 M(1 ≤ M ≤ 500,000)이 주어진다.
    넷째 줄에는 상근이가 가지고 있는 숫자 카드인지 아닌지를 구해야 할 M개의 정수가 주어지며,
    이 수는 공백으로 구분되어져 있다. 이 수도 -10,000,000보다 크거나 같고, 10,000,000보다 작거나 같다

    출력
    첫째 줄에 입력으로 주어진 M개의 수에 대해서, 각 수가 적힌 숫자 카드를 상근이가 가지고 있으면 1을, 아니면 0을 공백으로 구분해 출력한다.
     */

    public static void main(String[] args) throws IOException {

        // 카드의 개수 n 입력 받기
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 카드의 개수
        int n = Integer.parseInt(br.readLine());

        // 가지고 있는 숫자 카드의 배열
        int[] numArr = new int[n];

        // 카드의 개수 만큼 숫자 카드 입력 받기
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            numArr[i] = Integer.parseInt(st.nextToken());
        }

        // 이진탐색을 위해 정렬하기
        Arrays.sort(numArr);

        // 가지고 있는 숫자인지 아닌지를 구해야할 개수 m
        int m = Integer.parseInt(br.readLine());

        // 판별할 숫자의 배열
        int[] validNumArr = new int[m];

        // 판별할 숫자들 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            validNumArr[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < m; i++) {
            if (binarySearch(numArr, validNumArr[i])) {
                sb.append(1);
            } else {
                sb.append(0);
            }
            sb.append(" ");
        }

        System.out.println(sb);

    }

    private static boolean binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {

            int mid = (left + right) / 2;

            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
}
