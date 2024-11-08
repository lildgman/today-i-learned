package array;

public class Ex_01 {

    // 주어진 길이 N의 int 배열 arr에서 합이 100인
    // 서로 다른 위치의 두 원소가 존재하면 1, 존재하지 않으면 0 반환

    public static int func(int[] arr, int n) {

        int[] numArr = new int[101];

        for (int i = 0; i < arr.length; i++) {
            if (numArr[100 - arr[i]] == 1) {
                return 1;
            }

            numArr[arr[i]]++;
        }
        return 0;
    }

    public static void main(String[] args) {

        System.out.println(func(new int[]{1,52,48},3));
        System.out.println(func(new int[]{50,42},2));
        System.out.println(func(new int[]{4,13,63,87},4));
    }
}
