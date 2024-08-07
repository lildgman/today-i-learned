package dfs_bfs.datastructures;

import java.util.Stack;

public class StackEx {

    public static void main(String[] args) {

        Stack<Integer> stack = new Stack<>();

        stack.push(5);
        stack.push(2);
        stack.push(3);
        stack.push(6);
        stack.pop();
        stack.push(1);
        stack.push(4);
        stack.pop();

        while (!stack.empty()) {
            System.out.println(stack.peek());
            stack.pop();
        }
    }
}
