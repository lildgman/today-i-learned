package stack_queue_deque.mysolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main2346 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // queuestack을 구성하는 자료구조 개수
        int n = Integer.parseInt(br.readLine());

        // 자료구조 종류를 나타내는 수열, 0이면 큐, 1이면 스택
        int[] aArr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < aArr.length; i++) {
            aArr[i] = Integer.parseInt(st.nextToken());
        }

        // 자료구조 안에 들어있는 원소
        int[] bArr = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < bArr.length; i++) {
            bArr[i] = Integer.parseInt(st.nextToken());
        }

        // 삽입할 수열의 길이
        int m = Integer.parseInt(br.readLine());

        // queuestack에 삽입할 원소를 담고있는 길이 m의 수열
        int[] cArr = new int[m];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < cArr.length; i++) {
            cArr[i] = Integer.parseInt(st.nextToken());
        }

        StringBuilder sb = new StringBuilder();

        Deque<Integer> deque = new ArrayDeque<>();
        // 자료구조가 큐인경우에만 원소값을 deque에 담는다.
        for (int i = 0; i < aArr.length; i++) {
            if (aArr[i] == 0) {
                deque.addLast(bArr[i]);
            }
        }

        //
        for (int i = 0; i < cArr.length; i++) {
            deque.addFirst(cArr[i]);
            sb.append(deque.pollLast()).append(' ');
        }

        System.out.println(sb);

    }
}
