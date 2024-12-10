package programmers.intro.day20;

public class Day20_4 {

    /**
     * 한 개 이상의 항의 합으로 이루어진 식을 다항식이라고 합니다. 다항식을 계산할 때는 동류항끼리 계산해 정리합니다.
     * 덧셈으로 이루어진 다항식 polynomial이 매개변수로 주어질 때,
     * 동류항끼리 더한 결괏값을 문자열로 return 하도록 solution 함수를 완성해보세요. 같은 식이라면 가장 짧은 수식을 return 합니다.
     */

    static String solution(String polynomial) {

        int xCount = 0;
        int constant = 0;

        String[] split = polynomial.split(" \\+ ");

        for (String hang : split) {
            // x항의 경우 계산
            if (hang.contains("x")) {
                if (hang.equals("x")) {
                    xCount++;
                } else {
                    String replaceHang = hang.replace("x", "");
                    xCount += Integer.parseInt(replaceHang);
                }
            }
             else {
                constant += Integer.parseInt(hang);
            }
        }

        StringBuilder sb = new StringBuilder();

        if (xCount != 0) {
            if (xCount == 1) {
                sb.append("x");
            } else if (xCount == -1) {
                sb.append("-x");
            } else {
                sb.append(xCount).append("x");
            }
        }

        if (constant != 0) {

            // x항이 있다면
            if (!sb.isEmpty()) {
                sb.append(" + ").append(constant);
            } else {
                sb.append(constant);
            }
        }

        return sb.toString();

    }

    public static void main(String[] args) {
        System.out.println(solution("3x + 7 + x"));
        System.out.println(solution("x + x + x"));
    }
}
