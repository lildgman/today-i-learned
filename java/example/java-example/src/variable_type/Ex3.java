package variable_type;

public class Ex3 {

    public static void main(String[] args) {
        int a = 10;
        System.out.println("메서드 호출 전 : a = " + a);
        change(a);
        System.out.println("메서드 호출 후 : a = " + a);
    }

    private static void change(int x) {
        x = 20;
    }
}
