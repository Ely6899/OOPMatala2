package Ex2;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1{

    /**
     * Writes to the given file (lineCount) times a given string to the file, save it in the dist, and returns
     * the new file name, formatted by .txt
     * @param fileName Name of a file.
     * @param lineCount Amount of lines for the file.
     * @return New formatted file name(Add .txt format to the name).
     */
    public static String writeFile(String fileName, int lineCount) {
        try {
            String txtFormat = String.format("%s%s",fileName,".txt");
            FileOutputStream fileOutputStream = new FileOutputStream(txtFormat, false); //Override changes

            //Write a string (lineCount) times
            for (int i = 0; i < lineCount; i++) {
                fileOutputStream.write("SmallBASIC\n".getBytes());
            }

            fileOutputStream.close(); //Close file stream.
            return txtFormat;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * Counts the amount of lines the given file has, not including the last empty line generated.
     * @param fileName Name of a file.
     * @return line count of a file
     */
    public static int readFileAndCountRows(String fileName){
        int lineCount = 0;
        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while(true) {
                try {
                    if ((reader.readLine() == null)) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lineCount++;
            }

            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return lineCount;
    }


    /**
     * 1
     * @param n Number of files to generate
     * @param seed Random number generator seed
     * @param bound Row number bound for all files to be generated
     * @return Array of strings, which holds each generated file's name.
     */
    public static String[] createTextFiles(int n, long seed, int bound) {
        String[] generatedFiles = new String[n];
        Random rand = new Random(seed);
        int lineCount;
        for (int i = 1; i <= n; i++) {
            lineCount = rand.nextInt(bound);
            generatedFiles[i - 1] = writeFile(String.format("file%d", i), lineCount);
        }
        return generatedFiles;
    }


    /**
     * 2
     * @param fileNames Array of the file names generated
     * @return Number of all rows in total of the files given in the fileNames array.
     */
        public static int getNumOfLines(String[] fileNames) {
            int sum = 0;
            for(String fileName: fileNames){
                sum += readFileAndCountRows(fileName);
            }
            return sum;
        }


    public int getNumOfLinesThreads(String[] fileNames){
        int sum = 0;
        CounterThread[] threads = new CounterThread[fileNames.length];

        for(int i = 0; i < fileNames.length; i++){
            threads[i] = new CounterThread(fileNames[i]);
        }

        for(CounterThread thread: threads){
            thread.start();
        }

        for(CounterThread thread: threads){
            try {
                thread.join();
                sum += thread.getLineCount();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return sum;
    }

    public int getNumOfLinesThreadPool(String[] fileNames){
        int sum = 0;
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length);
        Future<Integer>[] futures = new Future[fileNames.length];

        for(int i = 0; i < fileNames.length; i++){
            futures[i] = threadPool.submit(new ThreadPoolHelper(fileNames[i]));
        }

        for (Future<Integer> future: futures){
            try {
                sum += future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown(); //Close thread-pool after finishing submissions

        return sum;
    }

    public static void main(String[] args) {
        Ex2_1 ex2_1 = new Ex2_1();
        String[] files = createTextFiles(3000, 1, 10); //Create files
        System.err.println("Finished generating files");

        long startTimeRegular = System.currentTimeMillis(); //Start measuring regular algorithm run-time.
        System.out.println(getNumOfLines(files)); //Calculate the number of rows of all generated files and print it.
        long endTimeRegular = System.currentTimeMillis(); //End measure of regular algorithm run-time.
        System.err.println("Regular algorithm run time end "+ (endTimeRegular - startTimeRegular) + "ms"); //Print regular algorithm run-time.

        long startTimeThread = System.currentTimeMillis();
        System.out.println(ex2_1.getNumOfLinesThreads(files)); //Calculate the number of rows of all generated files and print it.
        long endTimeThread = System.currentTimeMillis(); //End measure of regular algorithm run-time.
        System.err.println("Thread algorithm run time end "+ (endTimeThread - startTimeThread) + "ms"); //Print thread algorithm run-time.

        long startTimeThreadPool = System.currentTimeMillis();
        System.out.println(ex2_1.getNumOfLinesThreadPool(files)); //Calculate the number of rows of all generated files and print it.
        long endTimeThreadPool = System.currentTimeMillis(); //End measure of regular algorithm run-time.
        System.err.println("Thread-Pool algorithm run time end "+ (endTimeThreadPool - startTimeThreadPool) + "ms"); //Print thread algorithm run-time.
    }
}
