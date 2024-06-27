package timecomplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main24266 {

    // MenOfPassion(A[], n) {
    //    sum <- 0;
    //    for i <- 1 to n
    //        for j <- 1 to n
    //            for k <- 1 to n
    //                sum <- sum + A[i] × A[j] × A[k]; # 코드1
    //    return sum;
    // }

    public static void main(String[] args) throws IOException {
        // 수행횟수 -> n^3
        // 차수 3

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Long n = Long.parseLong(br.readLine());

        System.out.println(n * n * n);
        System.out.println(3);
    }
}
