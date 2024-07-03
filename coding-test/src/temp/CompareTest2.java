package temp;

import java.util.Arrays;

public class CompareTest2 {

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

        Arrays.sort(arr);

        System.out.println("정렬 후 arr");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i].value + " ");
        }
        System.out.println();
    }
}

class TestInteger2 implements Comparable<TestInteger2> {
    int value;

    public TestInteger2(int value) {
        this.value = value;
    }


    @Override
    public int compareTo(TestInteger2 o) {
        return this.value - o.value;
    }
}
