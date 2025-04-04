package bark.ch4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main_1406 {

    /*
     * https://www.acmicpc.net/problem/1406
     *
     * 이 편집기에는 '커서'라는 것이 있어 문장의 맨 앞, 맨 뒤, 문장 중간에 위치할 수 있다.
     * 길이가 L인 문자열이 현재 편집기에 입력되어 있으면 커서가 위치할 수 있는 곳은 L+1 가지 경우가 있다.
     *
     * 지원하는 명령어는 다음과 같다
     * L: 커서를 왼쪽으로 한 칸 옮김(커서가 문장 맨 앞이면 무시)
     * D: 커서를 오른쪽으로 한 칸 옮김(커서가 문장 맨 뒤면 무시)
     * B: 커서 왼쪽에 있는 문자 삭제(커서가 문장 맨 앞이면 무시)
     *  - 삭제로 인해 커서는 한칸 왼쪽으로 이동한 것처럼 나타나지만, 실제로 커서의 오른쪽에 있던 문자는 그대로이다.
     * P $: $ 라는 문자를 커서 왼쪽에 추가
     *
     * 초기 편집기에 입력되어 있는 문자열이 주어지고, 그 이후 입력한 명령어가 차례로 주어졌을 때,
     * 모든 명령어를 수행하고 난 후 편집기에 입력되어 있는 문자열을 구하는 프로그램 작성
     *
     * 입력
     * 첫째줄: 초기 편집기에 입력되어 있는 문자열이 주어진다. 길이 N이고 100,000을 넘지 않음, 소문자로만 이루어짐
     * 둘째줄: 입력할 명령어 개수 M
     * 셋째줄부터 M개 줄을 걸쳐 입력할 명령어가 순서대로 주어짐
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 초기에 입력되어 있는 문자열
        String str = br.readLine();
        // 입력할 명령어 개수
        int m = Integer.parseInt(br.readLine());

        // 문자열를 문자 단위로 LikedList에 추가
        LinkedList<Character> list = new LinkedList<>();
        for (int i = 0; i < str.length(); i++) {
            list.add(str.charAt(i));
        }

        // 리스트를 순회하기 위한 ListIterator
        ListIterator<Character> li = list.listIterator();

        // 커서를 맨 뒤로 이동
        while (li.hasNext()) {
            li.next();
        }

        for (int i = 0; i < m; i++) {
            String cmdStr = br.readLine();

            char cmd = cmdStr.charAt(0);


            switch (cmd) {
                case 'L': // L -> 커서가 문장 맨 앞이 아닐 때 왼쪽으로 한 칸 이동
                    if (li.hasPrevious()) {
                        li.previous();
                    }
                    break;
                case 'D': // D -> 커서가 문장 맨 뒤가 아닐 때 오른쪽으로 한 칸 이동:
                    if (li.hasNext()) {
                        li.next();
                    }
                    break;
                case 'B': // B -> 커서 왼쪽에 문자 삭제    :
                    if (li.hasPrevious()) {
                        li.previous();
                        li.remove();
                    }
                    break;
                case 'P': // P $ -> 커서 왼쪽에 $ 추가
                    char c = cmdStr.charAt(2);
                    li.add(c);
                    break;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (Character c : list) {
            sb.append(c);
        }

        System.out.println(sb);
    }
}
