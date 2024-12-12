package programmers.intro.day22;

public class Day22_1 {

    /**
     * 3x 마을 사람들은 3을 저주의 숫자라고 생각하기 때문에 3의 배수와 숫자 3을 사용하지 않습니다.
     * 3x 마을 사람들의 숫자는 다음과 같습니다.
     * <p>
     * 10진법	3x 마을에서 쓰는 숫자
     * 1	    1
     * 2	    2
     * 3	    4
     * 4	    5
     * 5	    7
     * 6        8
     * 7        10
     * 8        11
     * 9        14
     * 10       16
     * 11       17
     * 12       19
     * 13       20
     * 14       22
     * 15       25
     * 16       26
     * 17       28
     * 18       29
     * 19       40
     * <p>
     * 정수 n이 매개변수로 주어질 때, n을 3x 마을에서 사용하는 숫자로 바꿔 return하도록 solution 함수를 완성해주세요.
     */

    public int solution(int n) {

        int answer = 0;

        for (int i = 0; i < n; i++) {
            answer++;

            while (answer % 3 == 0 || String.valueOf(answer).contains("3")) {
                answer++;
            }

        }

        return answer;

    }

    public static void main(String[] args) {
        System.out.println(new Day22_1().solution(15));
    }
}
