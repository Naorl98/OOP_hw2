public class Main {
    public static void main(String[] args) {
        int count =  Ex2_1.getNumOfLines( Ex2_1.createTextFiles( 10000, 2, 10000));
        System.out.println("lines = " + count);

    }
}