package variable_type;

public class Ex2 {

    public static void main(String[] args) {

        Data data1 = new Data();
        data1.value = 10;
        Data data2 = data1;

        System.out.println("data1의 참조값= " + data1);
        System.out.println("data2의 참조값 = " + data2);
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
        System.out.println();

        // data1 변경
        data1.value = 20;
        System.out.println("data1 변경 data1.value = 20");
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
        System.out.println();

        // data2 변경
        data2.value = 30;
        System.out.println("data2 변경 data2.value = 30");
        System.out.println("data1.value = " + data1.value);
        System.out.println("data2.value = " + data2.value);
    }
}

