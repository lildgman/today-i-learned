package sort.dongbin;

public class QuickSortEx {

    public static void quickSort(int[] arr, int start, int end) {

        // 원소가 1개인 경우 종료
        if (start >= end) {
            return;
        }

        // pivot은 첫번째 요소
        int pivot = start;
        int left = start + 1;
        int right = end;

        // 교차되기 전까지 반복
        while (left <= right) {
            // 피벗보다 큰 데이터를 찾을 때까지 반복
            while (left <= end && arr[left] <= arr[pivot]) {
                left++;
            }

            // 피벗보다 작은 데이터를 찾을 때까지 반복
            while (right > start && arr[right] >= arr[pivot]) {
                right--;
            }

            // 엇갈렸다면 작은 데이터와 피벗을 교체
            if (left > right) {
                int tmp = arr[pivot];
                arr[pivot] = arr[right];
                arr[right] = tmp;
            } else {
                // 엇갈리지 않았다면 작은 데이터와 큰 데이터 교체
                int tmp = arr[left];
                arr[left] = arr[right];
                arr[right] = tmp;
            }

            // 분할 이후 왼쪽 부분과 오른쪽 부분 각각 정렬 수행
            quickSort(arr, start, right - 1);
            quickSort(arr, right + 1, end);

        }


    }

    public static void main(String[] args) {
        int n = 10;
        int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        System.out.println("정렬 전");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }

        System.out.println();

        quickSort(arr, 0, n - 1);

        System.out.println("정렬 후");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
