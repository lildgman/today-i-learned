package expert2.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_2108 {

    /*
    문제
    수를 처리하는 것은 통계학에서 상당히 중요한 일이다.
    통계학에서 N개의 수를 대표하는 기본 통계값에는 다음과 같은 것들이 있다.
    단, N은 홀수라고 가정하자.

    산술평균 : N개의 수들의 합을 N으로 나눈 값
    중앙값 : N개의 수들을 증가하는 순서로 나열했을 경우 그 중앙에 위치하는 값
    최빈값 : N개의 수들 중 가장 많이 나타나는 값
    범위 : N개의 수들 중 최댓값과 최솟값의 차이
    N개의 수가 주어졌을 때, 네 가지 기본 통계값을 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 수의 개수 N(1 ≤ N ≤ 500,000)이 주어진다. 단, N은 홀수이다. 그 다음 N개의 줄에는 정수들이 주어진다. 입력되는 정수의 절댓값은 4,000을 넘지 않는다.

    출력
    첫째 줄에는 산술평균을 출력한다. 소수점 이하 첫째 자리에서 반올림한 값을 출력한다.
    둘째 줄에는 중앙값을 출력한다.
    셋째 줄에는 최빈값을 출력한다. 여러 개 있을 때에는 최빈값 중 두 번째로 작은 값을 출력한다.
    넷째 줄에는 범위를 출력한다.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 입력한 정수들을 보관할 배열
        int[] numbers = new int[n];
        // 빈도수를 저장하기 위한 Map, <입력한 정수, 빈도수>
        HashMap<Integer, Integer> map = new HashMap<>();

        // 산술평균을 구하기 위한 sum
        int sum = 0;

        for (int i = 0; i < n; i++) {
            numbers[i] = Integer.parseInt(br.readLine());
            sum += numbers[i];
            map.put(numbers[i], map.getOrDefault(numbers[i], 0) + 1);
        }

        // 산술평균
        // 소수점 이하 첫째자리에서 반올림
        int avg = (int) Math.round((double) sum / n);

        // 중앙값
        // 입력한 정수들을 정렬한 후 가운데 값을 찾기
        Arrays.sort(numbers);
        int midNum = numbers[n / 2];

        // 최빈값
        // 최반값이 여러개 있을 경우 두번 째 작은 값을 반환
        // 최빈값을 저장하기 위한 List
        ArrayList<Integer> list = new ArrayList<>();
        // 최빈값
        int max = Collections.max(map.values());

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == max) {
                list.add(entry.getKey());
            }
        }

        // 리스트를 정렬한 후 여러개 일 때 두번째로 작은 값을 출력
        Collections.sort(list);
        int most = list.size() > 1 ? list.get(1) : list.get(0);

        // 범위
        // 중앙값을 구하기 위해 배열을 정렬했으므로 끝값에서 첫번째값을 빼주면 된다.
        int range = numbers[n - 1] - numbers[0];

        System.out.println(avg);
        System.out.println(midNum);
        System.out.println(most);
        System.out.println(range);
    }
}
