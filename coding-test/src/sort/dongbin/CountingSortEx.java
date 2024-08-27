package sort.dongbin;

public class CountingSortEx {

    public static final int MAX_VALUE = 15;

    public static void main(String[] args) {
        int n = 15;

        // 모든 원소 값은 0보다 크거나 같다고 가정
        int[] arr = {7, 5, 9, 0, 14, 1, 6, 2, 9, 1, 4, 8, 0, 5, 12};

        // 모든 범위를 포함하는 배열 선언
        int[] count = new int[MAX_VALUE + 1];

        for (int i = 0; i < n; i++) {
            count[arr[i]] += 1;
        }

        for (int i = 0; i <= MAX_VALUE; i++) {
            for (int j = 0; j < count[i]; j++) {
                System.out.print(i + " ");
            }
        }
    }
}
