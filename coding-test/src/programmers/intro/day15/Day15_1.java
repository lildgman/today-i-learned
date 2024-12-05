package programmers.intro.day15;

public class Day15_1 {

    /**
     * 영어가 싫은 머쓱이는 영어로 표기되어있는 숫자를 수로 바꾸려고 합니다.
     * 문자열 numbers가 매개변수로 주어질 때, numbers를 정수로 바꿔 return 하도록 solution 함수를 완성해 주세요.
     */

    static long solution(String numbers) {
        String[] nums = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};

        for (int i = 0; i < nums.length; i++) {
            numbers = numbers.replace(nums[i], String.valueOf(i));
        }

        return Long.parseLong(numbers);
    }

    public static void main(String[] args) {

        System.out.println(solution("onetwothreefourfivesixseveneightnine"));
        System.out.println(solution("onefourzerosixseven"));
    }
}
