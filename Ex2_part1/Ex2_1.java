package Ex2_part1;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.Random;
import java.io.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Ex2_1 {
    /**
     * Create n files, each file create with random number of lines between seed to bound
     * @param n  Number of files
     * @param seed
     * @param bound
     * @return Array of the files names
     */
    public static String[] createTextFiles(int n, int seed, int bound) {
        String[] names = new String[n];
        Random rand = new Random(seed);
        String filename = "file_";
        for (int i = 0; i < n; i++) {
            filename = filename + String.valueOf(i);
            try {
                FileWriter fw = new FileWriter(filename);
                int x = rand.nextInt(bound);
                for (int j = 1; j <= x; j++) {
                    fw.write("Naor Itamar\n");

                }
                System.out.println("Created file " + filename + "with " + x + "lines");

                fw.close();
                names[i] = filename;
                filename = "file_";
            } catch (IOException ex) {
                System.out.print("Error writing file\n" + ex);
            }
        }
        return names;
    }

    /**
     * Count the lines of all the files
     * @param fileNames
     * @return The number of lines
     */

    public static int getNumOfLines(String[] fileNames) {
        int linesCount = 0;
        for (int i = 0; i < fileNames.length; i++) {
            try {
                FileReader fr = new FileReader(fileNames[i]);
                BufferedReader br = new BufferedReader(fr);
                while (br.readLine() != null) {
                    linesCount++;
                }
                br.close();
                fr.close();
            } catch (IOException ex) {
                System.out.print("Error reading file\n" + ex);
                System.exit(2);
            }
        }
        return linesCount;
    }

    /**
     * Count the lines of all the files with thread
     * @param fileNames
     * @return The number of lines
     */
    public static int getNumOfLinesThreads(String[] fileNames) {
        int linesCount = 0;
        for (int i = 0; i < fileNames.length; i++) {
            myThread threadfile = new myThread(fileNames[i]);
            threadfile.run();
            while ((threadfile.isAlive())) {
            }
            linesCount += threadfile.getLines();
        }

        return linesCount;
    }
    /**
     * Count the lines of all the files with threadPool
     * @param fileNames
     * @return The number of lines
     */
    public static int getNumOfLinesThreadPool(String[] fileNames) throws ExecutionException, InterruptedException {

        int count = 0;
        try {
            List<Future<Integer>> tasks = new ArrayList<>();
            ExecutorService pool = Executors.newFixedThreadPool(fileNames.length);

            for (int i = 0; i < fileNames.length; i++) {
                Future<Integer> task = pool.submit(new myCallable(fileNames[i]));
                tasks.add(task);
            }
            for (Future task : tasks) {
                count += (int) task.get();
            }
            pool.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;

    }

}