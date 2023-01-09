package Ex2_part1;


import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long start ;
        long end ;
        int count;
        String[] s = Ex2_1.createTextFiles( 1000, 2, 10000);
        start = System.currentTimeMillis();
        count = Ex2_1.getNumOfLines(s);
        end = System.currentTimeMillis()-start;
        System.out.println("Normal -  lines: "+ count +" time: "+end+"ms");

        start = System.currentTimeMillis();
        count = Ex2_1.getNumOfLinesThreads(s);
        end = System.currentTimeMillis()-start;
        System.out.println("Thread -  lines: "+ count +" time: "+end+"ms");

        start = System.currentTimeMillis();
        count = Ex2_1.getNumOfLinesThreadPool(s);
        end = System.currentTimeMillis()-start;
        System.out.println("ThreaPool-  lines: "+ count +" time: "+end+"ms");


    }
}