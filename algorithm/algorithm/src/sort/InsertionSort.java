package sort;

public class InsertionSort {

    public static void main(String[] args) {
        int[] arr = {5, 9, 10, 6, 15, 20};

        for (int i = 1; i < arr.length; i++) {
            int target = arr[i];
            int j = i - 1;

            // 타켓이 이전 원소보다 크기 전까지 반복
            while (j >= 0 && target < arr[j]) {
                arr[j + 1] = arr[j]; // 이전 원소를 한 칸씩 뒤로 미룸
                j--;
            }

            // while문을 빠져나왔다는 것은 target이 앞의 원소보다 크다
            // 타켓을 j+1에 위치해야함
            arr[j + 1] = target;
        }

        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}
