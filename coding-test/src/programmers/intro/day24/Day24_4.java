package programmers.intro.day24;

public class Day24_4 {

    /**
     * 1부터 13까지의 수에서, 1은 1, 10, 11, 12, 13 이렇게 총 6번 등장합니다.
     * 정수 i, j, k가 매개변수로 주어질 때, i부터 j까지 k가 몇 번 등장하는지 return 하도록 solution 함수를 완성해주세요.
     */

    static int solution(int i, int j, int k) {

        int answer = 0;

        for (int x = i; x <= j; x++) {

            answer += getOneCount(x, k);
        }

        return answer;
    }

    static int getOneCount(int num, int target) {

        int count = 0;

//        for (char c : String.valueOf(num).toCharArray()) {
//
//            if (c == target + 48) {
//                count++;
//            }
//        }

        while (num != 0) {
            if (num % 10 == target) {
                count++;
            }
            num /= 10;

        }


        return count;
    }

    public static void main(String[] args) {
        System.out.println(solution(1, 13, 1));
        System.out.println(solution(10, 50, 5));
        System.out.println(solution(3, 10, 2));
    }
}
