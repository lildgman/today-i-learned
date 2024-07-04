package sort;

public class SelectionSort {

    public static void selectionSort(int[] arr) {

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            // 찾은 최소값을 현재 위치와 교환
            int tmp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = tmp;
        }
    }

    public static void main(String[] args) {
        int[] arr = {50, 60, 30, 20, 70};
        StringBuilder sb = new StringBuilder();

        for (int i : arr) {
            sb.append(i).append(" ");
        }

        System.out.println("정렬 전");
        System.out.println(sb);
        sb.setLength(0);

        selectionSort(arr);

        for (int i : arr) {
            sb.append(i).append(" ");
        }
        System.out.println("정렬 후");
        System.out.println(sb);

    }
}
