package bark.ch7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main_5430 {

    /*
    문제
    선영이는 주말에 할 일이 없어서 새로운 언어 AC를 만들었다.
    AC는 정수 배열에 연산을 하기 위해 만든 언어이다.
    이 언어에는 두 가지 함수 R(뒤집기)과 D(버리기)가 있다.

    함수 R은 배열에 있는 수의 순서를 뒤집는 함수이고,
    D는 첫 번째 수를 버리는 함수이다.
    배열이 비어있는데 D를 사용한 경우에는 에러가 발생한다.

    함수는 조합해서 한 번에 사용할 수 있다.
    예를 들어, "AB"는 A를 수행한 다음에 바로 이어서 B를 수행하는 함수이다.
    예를 들어, "RDD"는 배열을 뒤집은 다음 처음 두 수를 버리는 함수이다.

    배열의 초기값과 수행할 함수가 주어졌을 때, 최종 결과를 구하는 프로그램을 작성하시오.

    입력
    첫째 줄에 테스트 케이스의 개수 T가 주어진다. T는 최대 100이다.
    각 테스트 케이스의 첫째 줄에는 수행할 함수 p가 주어진다. p의 길이는 1보다 크거나 같고, 100,000보다 작거나 같다.
    다음 줄에는 배열에 들어있는 수의 개수 n이 주어진다. (0 ≤ n ≤ 100,000)
    다음 줄에는 [x1,...,xn]과 같은 형태로 배열에 들어있는 정수가 주어진다. (1 ≤ xi ≤ 100)
    전체 테스트 케이스에 주어지는 p의 길이의 합과 n의 합은 70만을 넘지 않는다.

    출력
    각 테스트 케이스에 대해서, 입력으로 주어진 정수 배열에 함수를 수행한 결과를 출력한다. 만약, 에러가 발생한 경우에는 error를 출력한다.
     */

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 테스트케이스 개수 t
        int t = Integer.parseInt(br.readLine());

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < t; i++) {
            // 수행할 함수
            String p = br.readLine();
            // 배열에 들어있는 수의 개수
            int n = Integer.parseInt(br.readLine());
            // 배열에 들어가있는 수
            // [1,2,3,4] 이런식으로 구성
            String input = br.readLine();

            Deque<Integer> deque = new ArrayDeque<>();
            String[] nums = input.substring(1, input.length() - 1).split(",");

            for (String num : nums) {
                if (!num.isEmpty()) {
                    deque.addLast(Integer.parseInt(num));
                }
            }

            // R: 배열 뒤직비
            // D: 첫번째 수 버리기
            // 여기서 실제로 배열을 뒤집지 않고 플래그를 사용
            // 덱의 특성을 활용하여 플래그에 따라 앞에서 제거하거나 뒤에서 제거하자

            boolean reversed = false;
            boolean isError = false;

            for (char c : p.toCharArray()) {
                if (c == 'R') { // R일 때 reversed를 전환
                    reversed = !reversed;
                } else if (c == 'D') { // D일 때 첫번째 수 제거
                    // 덱이 비어있으면 error
                    if (deque.isEmpty()) {
                        isError = true;
                        break;
                    }

                    // reversed가 참이면 뒤에서부터 제거
                    if (reversed) {
                        deque.pollLast();
                    } else {
                        deque.pollFirst();
                    }
                }
            }

            if (isError) {
                sb.append("error").append('\n');
            } else {
                sb.append('[');
                while (!deque.isEmpty()) {
                    // reversed가 참일 때 뒤에서부터 값 출력, 거짓일 때는 앞에서부터 값 출력
                    sb.append(reversed ? deque.pollLast() : deque.pollFirst());

                    if (!deque.isEmpty()) {
                        sb.append(',');
                    }
                }
                sb.append(']').append('\n');
            }


        }

        System.out.println(sb);
    }
}
