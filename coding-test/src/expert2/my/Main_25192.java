package expert2.my;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

public class Main_25192 {

    /*
    문제
    알고리즘 입문방 오픈 채팅방에서는 새로운 분들이 입장을 할 때마다 곰곰티콘을 사용해 인사를 한다.
    이를 본 문자열 킬러 임스는 채팅방의 기록을 수집해 그 중 곰곰티콘이 사용된 횟수를 구해 보기로 했다.
    ENTER는 새로운 사람이 채팅방에 입장했음을 나타낸다.
    그 외는 채팅을 입력한 유저의 닉네임을 나타낸다. 닉네임은 숫자 또는 영문 대소문자로 구성되어 있다.

    새로운 사람이 입장한 이후 처음 채팅을 입력하는 사람은 반드시 곰곰티콘으로 인사를 한다. 그 외의 기록은 곰곰티콘을 쓰지 않은 평범한 채팅 기록이다.
    채팅 기록 중 곰곰티콘이 사용된 횟수를 구해보자!

    입력
    첫 번째 줄에는 채팅방의 기록 수를 나타내는 정수
    n이 주어진다. (1 <= N <= 100,000)

    두 번째 줄부터
    n개의 줄에 걸쳐 새로운 사람의 입장을 나타내는 ENTER, 혹은 채팅을 입력한 유저의 닉네임이 문자열로 주어진다. (1 <= 문자열 길이 <= 20)
    첫 번째 주어지는 문자열은 무조건 ENTER

    출력
    채팅 기록 중 곰곰티콘이 사용된 횟수를 출력하시오.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 첫번째 문자열은 무조건 ENTER
        // 새로운 사람이 입장하면 곰곰티콘 사용

        HashSet<String> set = null;
        int count = 0;
        for (int i = 0; i < n; i++) {
            String str = br.readLine();

            if (str.equals("ENTER")) {
                set = new HashSet<>();
            } else {
                set.add(str);
                count++;
            }


        }

        System.out.println(count);



    }
}
