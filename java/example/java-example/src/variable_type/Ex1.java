package variable_type;

public class Ex1 {

    public static void main(String[] args) {

        int a = 10;
        int b = a;

        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // a를 20으로 변경
        a = 20;
        System.out.println("a를 20으로 변경");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        // b를 30으로 변경
        b = 30;
        System.out.println("b를 30으로 변경");
        System.out.println("a = " + a);
        System.out.println("b = " + b);
    }
}
