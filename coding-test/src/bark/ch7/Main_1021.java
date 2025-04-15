package bark.ch7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_1021 {

    /*
    문제
    지민이는 N개의 원소를 포함하고 있는 양방향 순환 큐를 가지고 있다.
    지민이는 이 큐에서 몇 개의 원소를 뽑아내려고 한다.
    지민이는 이 큐에서 다음과 같은 3가지 연산을 수행할 수 있다.

    첫 번째 원소를 뽑아낸다. 이 연산을 수행하면, 원래 큐의 원소가 a1, ..., ak이었던 것이 a2, ..., ak와 같이 된다.
    왼쪽으로 한 칸 이동시킨다. 이 연산을 수행하면, a1, ..., ak가 a2, ..., ak, a1이 된다.
    오른쪽으로 한 칸 이동시킨다. 이 연산을 수행하면, a1, ..., ak가 ak, a1, ..., ak-1이 된다.
    큐에 처음에 포함되어 있던 수 N이 주어진다.
    그리고 지민이가 뽑아내려고 하는 원소의 위치가 주어진다. (이 위치는 가장 처음 큐에서의 위치이다.)
    이때, 그 원소를 주어진 순서대로 뽑아내는데 드는 2번, 3번 연산의 최솟값을 출력하는 프로그램을 작성하시오.

    입력
    첫째 줄에 큐의 크기 N과 뽑아내려고 하는 수의 개수 M이 주어진다. N은 50보다 작거나 같은 자연수이고, M은 N보다 작거나 같은 자연수이다.
    둘째 줄에는 지민이가 뽑아내려고 하는 수의 위치가 순서대로 주어진다.
    위치는 1보다 크거나 같고, N보다 작거나 같은 자연수이다.

    출력
    첫째 줄에 문제의 정답을 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 큐의 크기
        int n = Integer.parseInt(st.nextToken());
        // 뽑아내려는 수의 개수
        int m = Integer.parseInt(st.nextToken());

        // 수를 저장할 Deque
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            deque.addLast(i);
        }

        st = new StringTokenizer(br.readLine());
        int count = 0;

        for (int i = 0; i < m; i++) {
            int target = Integer.parseInt(st.nextToken());

            // 뽑아내려는 수의 위치
            int index = 0;
            for (int num : deque) {

                if (num == target) { // 입력한 수와 덱에 저장된 수가 일치하면 break;
                    break;
                }
                index++;
            }

            // 회전 방향을 정해야함
            // index가 작을 경우 왼쪽으로 회전
            // 반대의 경우 오른쪽으로 회전
            if (index < deque.size() - index) { // 왼쪽방향
                for (int j = 0; j < index; j++) {
                    deque.addLast(deque.pollFirst());
                    count++;
                }
            } else { // 오른쪽 방향
                for (int j = 0; j < deque.size() - index; j++) {
                    deque.addFirst(deque.pollLast());
                    count++;
                }
            }

            deque.pollFirst();

        }

        System.out.println(count);


    }

}

