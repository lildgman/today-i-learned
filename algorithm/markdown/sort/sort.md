# 정렬 알고리즘
## 선택 정렬 알고리즘
처리되지 않은 데이터 중 **가장 작은 데이터를 선택해 맨 앞에 있는 데이터와 바꾸는 것을 반복** 한다.

~~~java
public class SelectSortEx {

    public static void main(String[] args) {

        int n = 10;
        int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        System.out.println("정렬 전");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        for (int i = 0; i < n; i++) {

            int min_index = i; // 가장 장은 원소의 인덱스
            for (int j = i + 1; j < n; j++) {
                if (arr[min_index] > arr[j]) {
                    min_index = j;
                }
            }

            int temp = arr[i];
            arr[i] = arr[min_index];
            arr[min_index] = temp;
        }

        System.out.println("정렬 후");
        for (int i = 0; i < n; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
~~~
- 선택정렬은 N번 만큼 가장 작은 수를 찾아 맨 앞으로 보내야한다.
- 전체 연산 횟수
  - N + (N - 1) + (N - 2) + ... + 2
- `(N² + N - 2) / 2`로 표현할 수 있는데 빅오표기법에 따라 O(N²)라 작성 가능하다.


## 삽입 정렬
처리되지 않은 데이터를 하나씩 골라 적절한 위치에 `삽입`한다.<Br>
선택 정렬에 비해 구현 난이도가 높지만 일반적으로 더 효율적으로 동작한다.

### 예시
`7, 5, 9, 0, 3, 1, 6, 2, 4, 8`가 있다 할 때, <Br>

1.  첫번째 데이터 7은 정렬이 되어있다 하고, 두번째 데이터인 5가 어떤 위치로 들어갈 지 판단한다. `왼쪽 혹은 오른쪽`으로 들어가는 경우만 존재한다.
2. 작으면 왼쪽, 크면 오른쪽으로 삽입한다.
3. 이후 값들을 위와 같은 방법으로 반복

~~~java
public class InsertionSortEx {

    public static void main(String[] args) {

        int n = 10;
        int[] arr = {7, 5, 9, 0, 3, 1, 6, 2, 4, 8};

        System.out.println("정렬 전");

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();

        // 삽입정렬
        for (int i = 0; i < n; i++) {
            // 인덱스 i부터 1까지 감소하며 반복
            for (int j = i; j > 0; j--) {

                // 한 칸씩 왼쪽으로 이동
                if (arr[j] < arr[j - 1]) {

                    // 스와프
                    int tmp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = tmp;
                } else {
                    // 자기보다 큰 값을 만났을 경우
                    break;
                }

            }
        }

        System.out.println("정렬 후");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}
~~~
- 삽입 정렬의 시간복잡도는 `O(N²)`, 반복문이 두 번 중첩되어 사용된다.
- 삽입 정렬은 `현재 리스트의 데이터가 거의 정렬되어 있는 상태면 매우 빠르게 동작한다.`
  - 최선의 경우 `O(N)`


## 퀵 정렬
- 기준 데이터를 설정하고 기준 데이터보다 큰 데이터와 작은 데이터의 위치를 바꾸는 방법이다.
- 일반적인 상황에서 가장 많이 사용되는 정렬 알고리즘
- 병합 정렬과 함께 대부분 프로그래밍 언어의 정렬 라이브러리의 근간이 되는 알고리즘
- 가장 기본적인 퀵 정렬은 `첫 번째 데이터를 기준 데이터(Pivot)`로 설정한다.

