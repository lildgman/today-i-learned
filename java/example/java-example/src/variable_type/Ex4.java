package variable_type;

public class Ex4 {

    public static void main(String[] args) {
        Data data1 = new Data();
        data1.value = 10;
        System.out.println("메서드 호출 전: data1.value = " + data1.value);
        change(data1);
        System.out.println("메서드 호출 후: data1.value = " + data1.value);
    }

    private static void change(Data dataX) {
        dataX.value = 20;
    }
}
