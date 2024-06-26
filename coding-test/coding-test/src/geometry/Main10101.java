package geometry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main10101 {
    // 총 3개의 줄에 걸쳐 삼각형의 각의 크기가 주어진다.
    // 모든 정수는 0보다 크고, 180보다 작다.
    // 세 각의 크기가 모두 60이면, Equilateral
    // 세 각의 합이 180이고, 두 각이 같은 경우에는 Isosceles
    // 세 각의 합이 180이고, 같은 각이 없는 경우에는 Scalene
    // 세 각의 합이 180이 아닌 경우에는 Error

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int a = Integer.parseInt(br.readLine());
        int b = Integer.parseInt(br.readLine());
        int c = Integer.parseInt(br.readLine());

        if (a + b + c != 180) {
            System.out.println("Error");
        } else if (a == 60 && b == 60 && c == 60) {
            System.out.println("Equilateral");
        } else if (a + b + c == 180) {
            if (a == b || a == c || b == c) {
                System.out.println("Isosceles");
            } else  {
                System.out.println("Scalene");
            }
        }
    }
}
