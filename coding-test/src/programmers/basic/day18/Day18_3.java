package programmers.basic.day18;

public class Day18_3 {

    public static int solution(String binomial) {

        int answer = 0;

        String[] split = binomial.split(" ");
        int num1 = Integer.parseInt(split[0]);
        int num2 = Integer.parseInt(split[2]);


        switch (split[1]) {
            case "+":
                answer = num1 + num2;
                break;
            case "-":
                answer = num1 - num2;
                break;
            case "*":
                answer = num1 * num2;
                break;
        }

        return answer;


    }

    public static void main(String[] args) {
        System.out.println(solution("43 + 12"));
        System.out.println(solution("0 - 7777"));
        System.out.println(solution("40000 * 40000"));
    }
}
