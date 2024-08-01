package greedy.dongbin_other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class GreedyEx4 {
    // 한 마을에 모함가가 N명 있다. 모험가 길드에서는 N명의 모험가를 대상으로 '공포도'를 측정했는데,
    // '공포도'가 높은 모험가는 쉽게 공포를 느껴 위험 상황에서 제대로 대처할 능력이 떨어진다.

    // 공포도가 X인 모험가는 반드시 X명 이상으로 구성한 모험가 그룹에 참여해야한다.
    // 최대 몇개의 모험가 그룹을 만들 수 있을까?
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        List<Integer> party = new ArrayList<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            party.add(Integer.parseInt(st.nextToken()));
        }

        Collections.sort(party);

        int result = 0; // 그룹 개수
        int count = 0; // 현재 그룹에 포함된 인원 수

        for (int i = 0; i<party.size();i++) {
            count += 1;
            if (count >= party.get(i)) {
                result += 1;
                count = 0;
            }
        }

        System.out.println(result);


    }
}
