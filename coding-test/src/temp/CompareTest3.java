package temp;

import java.util.Arrays;
import java.util.Comparator;

public class CompareTest3 {

    public static void main(String[] args) {

        TestInteger2[] arr = new TestInteger2[10];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = new TestInteger2((int) (Math.random() * 100));
        }

        System.out.println("정렬 전 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();

        Arrays.sort(arr, comp);

        System.out.println("정렬 후 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }

    static Comparator<TestInteger2> comp = new Comparator<TestInteger2>() {
        @Override
        public int compare(TestInteger2 o1, TestInteger2 o2) {
            return o1.value - o2.value;
        }
    };
}

class TestInteger {
    int value;

    public TestInteger(int value) {
        this.value = value;
    }

}
