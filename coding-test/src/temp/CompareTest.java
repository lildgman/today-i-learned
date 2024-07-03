package temp;

import java.util.Comparator;

public class CompareTest {

    public static void main(String[] args) {

        Member3 userA = new Member3(20, 160);
        Member3 userB = new Member3(20, 190);
        Member3 userC = new Member3(20, 150);

        int isTall = memberComp.compare(userA, userB);

        System.out.println(isTall);

    }

    public static Comparator<Member3> memberComp = (o1, o2) -> o1.height - o2.height;

}


