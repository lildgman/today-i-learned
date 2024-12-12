package programmers.intro.day22;

public class Day22_2 {

    /**
     * 점 네 개의 좌표를 담은 이차원 배열  dots가 다음과 같이 매개변수로 주어집니다.
     * <p>
     * [[x1, y1], [x2, y2], [x3, y3], [x4, y4]]
     * <p>
     * 주어진 네 개의 점을 두 개씩 이었을 때, 두 직선이 평행이 되는 경우가 있으면 1을 없으면 0을 return 하도록 solution 함수를 완성해보세요.
     */

    public int solution(int[][] dots) {
        if (ifParallel(dots[0], dots[1], dots[2], dots[3])) {
            return 1;
        }

        if (ifParallel(dots[0], dots[2], dots[1], dots[3])) {
            return 1;
        }

        if (ifParallel(dots[0], dots[3], dots[1], dots[2])) {
            return 1;
        }

        return 0;
    }

    private boolean ifParallel(int[] point1, int[] point2, int[] point3, int[] point4) {

        double slope1 = getSlope(point1, point2);
        double slope2 = getSlope(point3, point4);

        return Math.abs(slope1 - slope2) < 1e-9;
    }

    private double getSlope(int[] point1, int[] point2) {
        return (double)(point2[1] - point1[1]) / (point2[0] - point1[0]) ;
    }


    public static void main(String[] args) {
        System.out.println(new Day22_2().solution(new int[][]{{1, 4}, {9, 2}, {3, 8}, {11, 6}}));
        System.out.println(new Day22_2().solution(new int[][]{{3, 5}, {4, 1}, {2, 4}, {5, 10}}));
    }
}
