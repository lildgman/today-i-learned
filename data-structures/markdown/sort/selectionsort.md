# 선택 정렬
### [Stranger`s LAB Blog](https://st-lab.tistory.com/168) 

주어진 리스트에서 가장 작은 or 가장 큰 원소를 찾아 맨 앞의 원소와 교환하는 작업을 반복하는 정렬

### 단계 설명
1. 최소값(최대값) 찾기: 리스트에서 최소값(최대값)을 찾는다.
2. 위치 교환: 찾은 최소값(최대값)을 리스트 맨 앞 원소와 교환
3. 반복

~~~java
public class SelectionSort {

    public static void selectionSort(int[] arr) {

        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            // 현재 위치의 최소값 인덱스 찾기
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
~~~

### 장점
- 추가적인 메모리 소비가 적다.

### 단점
- 시간복잡도 O(n^2)
- 안정 정렬이 아님