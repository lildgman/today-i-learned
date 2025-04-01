package bark.ch2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_2752 {

    /*
    문제
    동규는 세수를 하다가 정렬이 하고 싶어졌다.
    정수 세 개를 생각한 뒤에, 이를 오름차순으로 정렬하고 싶어졌다.
    정수 세 개가 주어졌을 때, 가장 작은 수, 그 다음 수, 가장 큰 수를 출력하는 프로그램을 작성하시오.

    입력
    정수 세 개가 주어진다. 이 수는 1보다 크거나 같고, 1,000,000보다 작거나 같다. 이 수는 모두 다르다.

    출력
    제일 작은 수, 그 다음 수, 제일 큰 수를 차례대로 출력한다.
     */

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int a = Integer.parseInt(st.nextToken());
        int b = Integer.parseInt(st.nextToken());
        int c = Integer.parseInt(st.nextToken());

        // 세 수를 직접 비교
        int max = Math.max(a, Math.max(b, c));
        int min = Math.min(a, Math.min(b, c));
        int mid = a + b + c - max - min; // 중간값은 a,b,c를 모두 더한 값에서 max와 min을 뺀 값

        System.out.println(min + " " + mid + " " + max);

//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        StringTokenizer st = new StringTokenizer(br.readLine());
//
//        int[] nums = new int[3];
//        nums[0] = Integer.parseInt(st.nextToken());
//        nums[1] = Integer.parseInt(st.nextToken());
//        nums[2] = Integer.parseInt(st.nextToken());
//
//        Arrays.sort(nums);
//
//        System.out.println(nums[0] + " " + nums[1] + " " + nums[2]);
    }
}
