package bark.ch3.resolve;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_3273 {

    /*
    문제
    n개의 서로 다른 양의 정수 a1, a2, ..., an으로 이루어진 수열이 있다.
    ai의 값은 1보다 크거나 같고, 1000000보다 작거나 같은 자연수이다.
    자연수 x가 주어졌을 때, ai + aj = x (1 ≤ i < j ≤ n)을 만족하는 (ai, aj)쌍의 수를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 수열의 크기 n이 주어진다.
    다음 줄에는 수열에 포함되는 수가 주어진다.
    셋째 줄에는 x가 주어진다. (1 ≤ n ≤ 100000, 1 ≤ x ≤ 2000000)

    출력
    문제의 조건을 만족하는 쌍의 개수를 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());
        int[] arr = new int[n];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Set<Integer> set = new HashSet<>();

        int x = Integer.parseInt(br.readLine());
        int count = 0;

        for (int i = 0; i < n; i++) {
            // 합이 x인 수를 찾을 것이다.
            // 타겟을 x에서 수열의 값을 뺀 값으로 설정
            int target = x - arr[i];

            // set에 이미 타겟이 존재한다면 한 쌍이 가능함
            if (set.contains(target)) {
                count++;
            }

            // 현재 숫자를 set에 추가
            set.add(arr[i]);
        }

        System.out.println(count);
    }
}
