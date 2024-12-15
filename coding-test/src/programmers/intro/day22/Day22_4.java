package programmers.intro.day22;


public class Day22_4 {

    /**
     * 분수를 소수로 고칠 때 유한소수로 나타낼 수 있는 분수인지 판별하려고 합니다. 유한소수가 되기 위한 분수의 조건은 다음과 같습니다.
     * <p>
     * 기약분수로 나타내었을 때, 분모의 소인수가 2와 5만 존재해야 합니다.
     * 두 정수 a와 b가 매개변수로 주어질 때, a/b가 유한소수이면 1을, 무한소수라면 2를 return하도록 solution 함수를 완성해주세요.
     */

    private static int gcd(int a, int b) {

        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }

        return a;
    }

    static int solution(int a, int b) {

        // 기약분수가 되기 위해서는 분모, 분자를 두 수의 최대공약수로 나눠주면 된다.
        // 최대공약수를 구하기 위해 유클리드 호제법을 사용하자
        // 유클리드 호제법은 2개의 자연수 a,b가 있을 떄 a를 b로 나눈 나머지를 r이라 하면
        // a와b의 최대공약수는 b와r의 최대공약수와 같다

        int gcd = gcd(a, b);
        b /= gcd;

        // 분모의 소인수가 2와 5만 존재하는 경우 판별
        while (b % 2 == 0) {
            b /= 2;
        }

        while (b % 5 == 0) {
            b /= 5;
        }

        return b == 1 ? 1 : 2;

    }

    public static void main(String[] args) {
        System.out.println(solution(7, 20));
        System.out.println(solution(11, 22));
        System.out.println(solution(12, 21));
    }
}
