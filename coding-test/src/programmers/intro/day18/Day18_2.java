package programmers.intro.day18;

public class Day18_2 {

    static int solution(int n) {
        double sqrt = Math.sqrt(n);

        return sqrt == (int) sqrt ? 1 : 2;
    }

    public static void main(String[] args) {
        System.out.println(solution(144));
        System.out.println(solution(976));
    }
}
