package dp;

public class Fibo {

    public static void main(String[] args) {
        System.out.println(fibo(4));
    }

    public static int fibo(int a) {
        if (a == 1 || a == 2) {
            return 1;
        }

        return fibo(a - 1) + fibo(a - 2);
    }
}
