package recursion;

public class RecursionEx {
    // 재귀함수는 특정 입력에서 자기 자신을 호출하지않고 종료되어야 한다.

    public static void main(String[] args) {
        System.out.println(recursion(3));
    }

    private static int recursion(int i) {
        if (i == 0) {
            return i;
        }

        return i + recursion(i - 1);
    }
}
