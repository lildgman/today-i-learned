package temp;

public class Member implements Comparable<Member>{

    int age;
    int height;

    public Member(int age, int height) {
        this.age = age;
        this.height = height;
    }

    @Override
    public int compareTo(Member o) {

        if (this.age > o.age) {
            return 1;
        } else if (this.age == o.age) {
            return 0;
        } else {
            return -1;
        }
    }
}
