package dp;

public class Dp_Fibo {

    public static long[] d = new long[100];

    public static void main(String[] args) {
        // 첫번째 피보나치 수와 두 번째 피보나치 수는 1이다.
        d[1] = 1;
        d[2] = 1;

        int n = 50; // 50번째 피보나치 수 계산

        // 피보나치 함수 -> 반복문으로 구현
        for (int i = 3; i <= n; i++) {
            d[i] = d[i - 1] + d[i - 2];
        }

        System.out.println(d[n]);
    }
}
