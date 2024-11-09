package programmers.basic;

public class Day10_5 {

    public static String solution(int q, int r, String code) {

        StringBuilder sb = new StringBuilder();

//        for (int i = 0; i < code.length(); i++) {
//            if (i % q == r) {
//                sb.append(code.charAt(i));
//            }
//        }

        for (int i = r; i < code.length(); i += q) {
            sb.append(code.charAt(i));
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(solution(3,1,"qjnwezgrpirldywt"));
    }
}
