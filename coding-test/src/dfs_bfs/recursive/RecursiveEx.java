package dfs_bfs.recursive;

public class RecursiveEx {

    // 반복문으로 구현한 n!
    public static int factorialIterative(int n) {
        int result = 1;

        for (int i = 1; i <= n; i++) {
            result *= i;
        }

        return result;
    }

    // 재귀적으로 구현한 n!
    public static int factorialRecursive(int n) {

        // n이 1이하인 경우 1 반환
        if (n <= 1) {
            return 1;
        }

        return n * factorialRecursive(n - 1);
    }

    public static void main(String[] args) {

        System.out.println("반복적으로 구현한 n!: " + factorialIterative(5));
        System.out.println("재귀적으로 구현한 n!: "+ factorialRecursive(5));
    }
}
