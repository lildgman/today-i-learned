package programmers.intro.day10;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Day10_4 {

    /**
     * 정수가 담긴 배열 numbers와 문자열 direction가 매개변수로 주어집니다.
     * 배열 numbers의 원소를 direction방향으로 한 칸씩 회전시킨 배열을 return하도록 solution 함수를 완성해주세요.
     */

    static int[] solution(int[] numbers, String direction) {

        Deque<Integer> deque = Arrays.stream(numbers)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));

        if ("right".equals(direction)) {
            deque.addFirst(deque.pollLast());
        } else if ("left".equals(direction)) {
            deque.addLast(deque.pollFirst());

        }

        return deque.stream()
                .mapToInt(i -> i)
                .toArray();

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(solution(new int[]{1,2,3},"right")));
        System.out.println(Arrays.toString(solution(new int[]{4, 455, 6, 4, -1, 45, 6},"left")));
    }
}
