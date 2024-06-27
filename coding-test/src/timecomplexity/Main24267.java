package timecomplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main24267 {

    // MenOfPassion(A[], n) {
    //    sum <- 0;
    //    for i <- 1 to n - 2
    //        for j <- i + 1 to n - 1
    //            for k <- j + 1 to n
    //                sum <- sum + A[i] × A[j] × A[k]; # 코드1
    //    return sum;
    // }
    public static void main(String[] args) throws IOException {

        // n이 7이라 가정하면
        // i: 1~5
        // j: i+1 ~ 6
        // k: j+1 ~ 7

        // 수행횟수: n*(n-1)*(n-2) / 6

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        System.out.println(n*(n-1)*(n-2)/6);
        System.out.println(3);
    }
}
