package bitopertaion;

public class BitOpertaionTest1 {

    static boolean getBit(int num, int i) {
        return (num&(1 << i)) != 0;
    }

    static int setBit(int num, int i) {
        return num | (1 << i);
    }

    static int clearBit(int num, int i) {
        return num & ~(1 << i);
    }

    static int clearLeftBits(int num, int i) {
        return num & ((1 << i) - 1);
    }

    static int clearRightBits(int num, int i) {
        return num & (-1 << (i + 1));
    }

    static int updateBit(int num, int i, boolean val) {
        return (num & ~(1 << i)) | ((val ? 1 : 0) << i);

    }

    public static void main(String[] args) {
        System.out.println(getBit(9,3));
        System.out.println(setBit(5,3));
        System.out.println(clearBit(9, 3));
        System.out.println(clearLeftBits(169, 3));
        System.out.println(clearRightBits(169, 3));
        System.out.println(updateBit(169, 3, false));
    }
}
