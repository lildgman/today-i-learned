package sort;

public class CountingSort {

    public static void main(String[] args) {

        int[] arr = new int[100]; // 수열의 원소개수 100개
        int[] counting = new int[31]; // 수의 범위 : 0 ~ 31
        int[] result = new int[100]; // 정렬될 배열

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * 31); // 0~30
        }

        // Counting Sort

        // 1번 과정
        // array를 한 번 순회하면서 각 값이 나올때 마다 해당 값을 index로 하는 새로운 배열의 값을 1 증가 시킨다.
        for (int i = 0; i < arr.length; i++) {
            // array의 value 값을 index로 하는 counting배열 값 1 증가
            counting[arr[i]]++;
        }

        // 2번 과정
        // counting 배열의 각 값을 누적합으로 변환
        for (int i = 1; i < counting.length; i++) {
            counting[i] += counting[i - 1];
        }

        // 3번 과정
        // i 번쨰 원소를 인덱스로 하는 counting 배열을 1 감소시킨 뒤
        // counting 배열의 값을 인덱스로 하여 result에 value 값을 저장한다.
        for (int i = arr.length - 1; i >= 0; i--) {
            int value = arr[i];
            counting[value]--;
            result[counting[value]] = value;
        }


        System.out.println("===== arr =====");
        for (int i : arr) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("===== counting[] =====");
        for (int i : counting) {
            System.out.print(i + " ");
        }
        System.out.println();

        System.out.println("===== result[] =====");
        for (int i : result) {
            System.out.print(i + " ");

        }
    }
}
