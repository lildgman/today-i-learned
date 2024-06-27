package timecomplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main24264 {

    // 입력의 크기 n이 주어지면 MenOfPassion 알고리즘 수행 시간을 예제 출력과 같은 방식으로 출력해보자.
    // MenOfPassion(A[], n) {
    //    sum <- 0;
    //    for i <- 1 to n
    //        for j <- 1 to n
    //            sum <- sum + A[i] × A[j]; # 코드1
    //    return sum;
    // }

    public static void main(String[] args) throws IOException {
        // 수행시간 n^2
        // 차수: 2

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Long n = Long.parseLong(br.readLine());
        System.out.println(n * n);
        System.out.println(2);
    }
}
