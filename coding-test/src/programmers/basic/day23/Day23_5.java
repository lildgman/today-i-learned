package programmers.basic.day23;

import java.time.LocalDate;

public class Day23_5 {

    /**
     * 정수 배열 date1과 date2가 주어집니다.
     * 두 배열은 각각 날짜를 나타내며 [year, month, day] 꼴로 주어집니다.
     * 각 배열에서 year는 연도를, month는 월을, day는 날짜를 나타냅니다.
     * <p>
     * 만약 date1이 date2보다 앞서는 날짜라면 1을, 아니면 0을 return 하는 solution 함수를 완성해 주세요.
     */

    public static int solution(int[] date1, int[] date2) {
        LocalDate localDate1 = LocalDate.of(date1[0], date1[1], date1[2]);
        LocalDate localDate2 = LocalDate.of(date2[0], date2[1], date2[2]);

        return localDate1.isBefore(localDate2) ? 1 : 0;
    }

    public static void main(String[] args) {
        System.out.println(solution(new int[]{2021,12,28}, new int[]{2021,12,29}));
        System.out.println(solution(new int[]{1024, 10, 24}, new int[]{1024, 10, 24}));
    }
}
