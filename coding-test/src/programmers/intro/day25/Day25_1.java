package programmers.intro.day25;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class Day25_1 {

    /**
     * 문자열 "hello"에서 각 문자를 오른쪽으로 한 칸씩 밀고 마지막 문자는 맨 앞으로 이동시키면 "ohell"이 됩니다.
     * 이것을 문자열을 민다고 정의한다면 문자열 A와 B가 매개변수로 주어질 때,
     * A를 밀어서 B가 될 수 있다면 밀어야 하는 최소 횟수를 return하고
     * 밀어서 B가 될 수 없으면 -1을 return 하도록 solution 함수를 완성해보세요.
     */

    static int solution(String A, String B) {

        if (A.equals(B)) {
            return 0;
        }

        Deque<Character> deque = new ArrayDeque<>();
        for (char c : A.toCharArray()) {
            deque.add(c);
        }

        int count = 0;
        while (count < A.length()) {
            count++;
            deque.addFirst(deque.pollLast());

            StringBuilder sb = new StringBuilder();
            for (char c : deque) {
                sb.append(c);
            }

            if (sb.toString().equals(B)) {
                return count;
            }
        }



        return -1;
    }



    public static void main(String[] args) {
        System.out.println(solution("hello","ohell"));
        System.out.println(solution("apple","elppa"));
        System.out.println(solution("atat","tata"));
        System.out.println(solution("abc","abc"));
    }
}
