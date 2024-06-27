package timecomplexity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main24265 {

    // 입력의 크기 n이 주어지면 MenOfPassion 알고리즘 수행 시간을 예제 출력과 같은 방식으로 출력해보자.
    // MenOfPassion(A[], n) {
    //    sum <- 0;
    //    for i <- 1 to n - 1
    //        for j <- i + 1 to n
    //            sum <- sum + A[i] × A[j]; # 코드1
    //    return sum;
    // }

    public static void main(String[] args) throws IOException {

        // n이 7이라고 가정
        // i: 1 2 3 4 5 6
        // j:
        //  i가 1일 때,
        //  j는 2 3 4 5 6 -> 5회
        //  i가 2일 때,
        //  j는 3 4 5 6 -> 4회
        //  i가 3일 때,
        //  j는 4 5 6 -> 3회
        //  i가 4일 떄,
        //  j는 5 6 -> 2회
        //  i가 5일 때,
        //  j는 6 -> 1회
        // 수행 횟수: n(n-1)/2
        // 수행시간 O(n^2) -> 빅오표기법에서는 상수는 무시한다.

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        System.out.println(n*(n-1)/2);
        System.out.println(2);
    }
}
