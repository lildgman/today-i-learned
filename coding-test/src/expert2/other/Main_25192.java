package expert2.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main_25192 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 채팅 기록 수
        int n = Integer.parseInt(br.readLine());

        // 채팅한 유저들의 목록
        HashSet<String> users = new HashSet<>();

        // 곰곰티콘 사용 횟수
        int count = 0;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();
            if (str.equals("ENTER")) {
                // 새로운 채탕방 입장 시 set 초기화
                users.clear();
            } else {
                if (!users.contains(str)) { // 처음 등장하는 유저라면
                    // 유저를 set에 add
                    users.add(str);
                    // 곰곰티콘 사용횟수 증가
                    count++;
                }
            }
        }

        System.out.println(count);
    }
}
