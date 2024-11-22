package stack;

import java.io.*;
import java.util.*;

public class Main_10799 {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();

        Stack<Character> stack = new Stack<>();

        int count = 0;

        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);

            if (c == '(') {
                stack.push(c);
            } else if (c == ')') {

                if (str.charAt(i-1) == '(') {
                    stack.pop();
                    count += stack.size();
                } else {
                    stack.pop();
                    count++;
                }

            }
        }

        System.out.println(count);

    }
}
