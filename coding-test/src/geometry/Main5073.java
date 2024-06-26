package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main5073 {
    //삼각형의 세 변의 길이가 주어질 때 변의 길이에 따라 다음과 같이 정의한다.

    //Equilateral :  세 변의 길이가 모두 같은 경우
    //Isosceles : 두 변의 길이만 같은 경우
    //Scalene : 세 변의 길이가 모두 다른 경우

    // 단 주어진 세 변의 길이가 삼각형의 조건을 만족하지 못하는 경우에는 "Invalid" 를 출력한다.
    // 예를 들어 6, 3, 2가 이 경우에 해당한다. 가장 긴 변의 길이보다 나머지 두 변의 길이의 합이 길지 않으면 삼각형의 조건을 만족하지 못한다.

    //세 변의 길이가 주어질 때 위 정의에 따른 결과를 출력하시오.


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());

            // 길이 비교를 위해서 배열에 입력한 값들을 담아주자
            int[] arr = new int[3];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            if (arr[0] == 0 && arr[1] == 0 && arr[2] == 0) {
                return;
            }

            // 배열 정렬
            Arrays.sort(arr);

            if (arr[2] >= arr[0] + arr[1]) {
                System.out.println("Invalid");
            } else if (arr[0] == arr[1] && arr[0] == arr[2]) {
                System.out.println("Equilateral");
            } else if (arr[0] == arr[1] || arr[0] == arr[2] || arr[1] == arr[2])  {
                System.out.println("Isosceles");
            } else {
                System.out.println("Scalene");
            }

        }


    }
}
