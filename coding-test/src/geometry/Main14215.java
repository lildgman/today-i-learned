package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main14215 {

    // 영선이는 길이가 a, b, c인 세 막대를 가지고 있고, 각 막대의 길이를 마음대로 줄일 수 있다.
    //
    // 영선이는 세 막대를 이용해서 아래 조건을 만족하는 삼각형을 만들려고 한다.
    //
    //  각 막대의 길이는 양의 정수이다
    //  세 막대를 이용해서 넓이가 양수인 삼각형을 만들 수 있어야 한다.
    //  삼각형의 둘레를 최대로 해야 한다.
    // a, b, c가 주어졌을 때, 만들 수 있는 가장 큰 둘레를 구하는 프로그램을 작성하시오.


    public static void main(String[] args) throws IOException {
        // 삼각형이 되는 조건
        // 가장 큰 변이 다른 작은 두 변의 길이보다 작아야한다.
        // 작은 두 변의 길이보다 1이 작게되면 둘레가 가장 큰 삼각형일 것이다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] recArr = new int[3];

        for (int i = 0; i < recArr.length; i++) {
            recArr[i] = Integer.parseInt(st.nextToken());
        }

        // 배열정렬
        Arrays.sort(recArr);

        if (recArr[2] >= recArr[0] + recArr[1]) {
            recArr[2] = recArr[0] + recArr[1] - 1;
        }

        System.out.println(recArr[0] + recArr[1] + recArr[2]);

    }
}
