package temp;

import java.util.Comparator;

public class Member2 implements Comparator<Member2> {

    int age;
    int weight;

    @Override
    public int compare(Member2 o1, Member2 o2) {

        if (o1.weight > o2.weight) {
            return 1;
        } else if (o1.weight == o2.weight) {
            return 0;
        } else {
            return -1;
        }
    }
}
