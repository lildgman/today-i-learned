package sort.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main18870 {

    // 수직선 위에 N개의 좌표 X1, X2, ..., XN이 있다. 이 좌표에 좌표 압축을 적용하려고 한다.
    // Xi를 좌표 압축한 결과 X'i의 값은 Xi > Xj를 만족하는 서로 다른 좌표 Xj의 개수와 같아야 한다.
    // X1, X2, ..., XN에 좌표 압축을 적용한 결과 X'1, X'2, ..., X'N를 출력해보자.

    // 입력
    // 첫째 줄에 N이 주어진다.
    // 출력
    // 첫째 줄에 X'1, X'2, ..., X'N을 공백 한 칸으로 구분해서 출력한다.

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 원본배열
        int[] originArr = new int[n];
        // 정렬할 배열
        int[] sortedArr = new int[n];
        // rank를 매길 HashMap
        Map<Integer, Integer> rankingMap = new HashMap<>();

        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < n; i++) {
            // 정렬할 배열과 원본 배열에 값 넣어주기
            sortedArr[i] = originArr[i] = Integer.parseInt(st.nextToken());
        }

        // 정렬할 배열을 정렬해주자
        Arrays.sort(sortedArr);

        // 정렬된 배열을 순회하며 map에 넣어준다
        int rank = 0;
        for (int i : sortedArr) {
            // 원소가 중복되지 않을때만 rank를 넣어주자
            if (!rankingMap.containsKey(i)) {
                rankingMap.put(i, rank);
                rank++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int key : originArr) {
            int ranking = rankingMap.get(key);
            sb.append(ranking).append(' ');
        }

        System.out.println(sb);
    }
}
