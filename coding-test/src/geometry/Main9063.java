package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main9063 {
    public static void main(String[] args) throws IOException {
        // 첫째 줄에는 점의 개수 N (1 ≤ N ≤ 100,000) 이 주어진다.
        // 이어지는 N 줄에는 각 점의 좌표가 두 개의 정수로 한 줄에 하나씩 주어진다.
        // 각각의 좌표는 -10,000 이상 10,000 이하의 정수이다.

        // 첫째 줄에 N 개의 점을 둘러싸는 최소 크기의 직사각형의 넓이를 출력하시오.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 점의 개수
        int n = Integer.parseInt(br.readLine());

        // 점의 개수만큼 x좌표와 y좌표를 담아줄 배열 생성
        int[] xArr = new int[n];
        int[] yArr = new int[n];

        // 반복문을 돌면서 x y 입력 했을 때 x좌표 배열, y좌표 배열에 담아줄 수 있게 하자
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            xArr[i] = Integer.parseInt(st.nextToken());
            yArr[i] = Integer.parseInt(st.nextToken());
        }

        // 입력한 대로 담아진 배열들을 정렬하자
        Arrays.sort(xArr);
        Arrays.sort(yArr);

        // 넓이를 계산해주자
        System.out.println((xArr[n-1] - xArr[0])*(yArr[n-1] - yArr[0]));

    }

}
