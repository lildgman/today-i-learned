package stack_queue_deque.othersolved;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main4949 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String s;

        while (true) {
            s = br.readLine();

            if (s.equals(".")) {
                break;
            }

            sb.append(valid(s)).append('\n');
        }

        System.out.println(sb);
    }

    public static String valid(String s) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {

            char c = s.charAt(i);

            // '(' 이거다 '[' 일 때 는 push
            if (c == '(' || c == '[') {
                stack.push(c);
            } else if (c == ')') {

                // 스택이 비어있거나 스택 제일 위에 있는 값이 '(' 가 아닐 경우
                if (stack.empty() || stack.peek() != '(') {
                    return "no";
                } else {
                    stack.pop();
                }
            } else if (c == ']') {
                // 스택이 비어있거나 스택 제일 위에 있는 값이 '[' 가 아닐 경우
                if (stack.empty() || stack.peek() != '[') {
                    return "no";
                } else {
                    stack.pop();
                }
            }
        }

        if (stack.empty()) {
            return "yes";
        } else {
            return "no";
        }

    }
}
