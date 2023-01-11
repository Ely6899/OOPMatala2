package Ex2P1;

import java.io.*;
import java.util.Random;
import java.util.concurrent.*;

public class Ex2_1{

    /**
     * Writes to the given file (lineCount) times a fixed string to the file, save it in the dist, and returns
     * the new file name, formatted by .txt in the end.
     * This function works on a single file.
     * @param fileName Name of a file.
     * @param lineCount Amount of lines for the file.
     * @return New formatted file name(Add .txt format to the name).
     */
    public static String writeFile(String fileName, int lineCount) {
        try {
            String txtFormat = String.format("%s%s",fileName,".txt"); //Format file to end in .txt
            FileOutputStream fileOutputStream = new FileOutputStream(txtFormat, false); //Override changes

            //Write a string (lineCount) times
            for (int i = 0; i < lineCount; i++) {
                fileOutputStream.write("SmallBASIC\n".getBytes());
            }

            fileOutputStream.close(); //Close file stream before exiting the function.
            return txtFormat;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * Counts the amount of lines the given file has, not including the last empty line generated.
     * This function works on a single file.
     * @param fileName Name of a file.
     * @return line count of a file
     */
    public static int readFileAndCountRows(String fileName){
        int lineCount = 0;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(fileName));

            while(true) {
                try {
                    if ((reader.readLine() == null))
                        break; //writeFile function guarantees only last line is empty.
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                lineCount++;
            }

            try {
                reader.close(); //Close file reader before exiting the function.
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
     * Creates (n) new text files, when each file holds a pseudo-random amount of lines dictated
     * by the seed parameter, up to a line limit given in the bound parameter.
     * It works by iterating n times on the writeFile function.
     * Returns a new array of strings, which holds the names of the new files created.
     * @param n Number of files to generate
     * @param seed Random number generator seed
     * @param bound Row number bound for all files to be generated
     * @return Array of strings, which holds each generated file's name.
     */
    public static String[] createTextFiles(int n, long seed, int bound) {
        try{
            String[] generatedFiles = new String[n];
            Random rand = new Random(seed); //Random instance dictated by seed given.
            int lineCount;

            for (int i = 1; i <= n; i++) {
                lineCount = rand.nextInt(bound); //Pseudo-randomized line count.
                generatedFiles[i - 1] = writeFile(String.format("file%d", i), lineCount);
            }
            return generatedFiles;
        } catch (NegativeArraySizeException e){
            throw new NegativeArraySizeException(e.getMessage());
        }
    }


    /**
     * 2
     * Counts the number of lines each file contains, and sums them all up.
     * Returns an integer value of the sum of lines of all the files in the fileNames array.
     * Works by iterating on every fileName from the fileNames array, and calling readFileAndCountRows()
     * on each of them.
     * @param fileNames Array of the file names generated
     * @return Number of all rows in total of the files given in the fileNames array.
     */
        public static int getNumOfLines(String[] fileNames) {
            int sum = 0; //result sum
            for(String fileName: fileNames){
                sum += readFileAndCountRows(fileName);
            }
            return sum;
        }


    /**
     * 3
     * This function counts the number of rows of each file and sums them.
     * Works with thread calls. Each thread is designated to a file in the array
     * and calculates separately the line count of each file.
     * The result of each count is held in the new CounterThread instances,
     * and in the end of a thread call, we get the value from the relevant instance,
     * and in a loop it adds up all the file counts, until the last thread, in which we
     * return the sum of lines.
     * The amount of threads working is the same as the size of the array of file names given,
     * meaning that for each file in the array, we assign a CounterThread which acts as a thread.
     * @param fileNames Array of the file names generated
     * @return Number of all rows in total of the files given in the fileNames array.
     */
    public int getNumOfLinesThreads(String[] fileNames){
        int sum = 0; //result sum
        CounterThread[] threads = new CounterThread[fileNames.length]; //An array of the custom threads.

        //Loop creates new threads.
        for(int i = 0; i < fileNames.length; i++){
            threads[i] = new CounterThread(fileNames[i]);
        }

        //Start the run function of each thread.
        for(CounterThread thread: threads){
            thread.start();
        }

        //Summation of each thread result.
        for(CounterThread thread: threads){
            try {
                thread.join(); //Makes the specific thread wait until it terminates before it continues to the next line.
                sum += thread.getLineCount(); //Add thread result to the sum answer after thread is terminated.
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return sum;
    }


    /**
     * 4
     * This function counts the number of rows of each file and sums them.
     * Works with the built-in thread-pool java implementation.
     * The thread-pool type used for this method is the ExecutorService thread-pool.
     * It acts by giving a Future for each callable function given to the submit
     * function.
     * The size of thread-pool is the same as the size of the array of file names given,
     * meaning, that the amount of threads in the pool will be the same as the size of
     * array of files.
     * Each thread submitted then returns a Future value, which acts as a value holder of
     * the threadPoolHelper submitted.
     * Therefore, after we get all futures, we sum them all up in a loop, and we get
     * the sum of lines of all the files given in the array parameter.
     * @param fileNames Array of the file names generated.
     * @return Number of all rows in total of the files given in the fileNames array.
     */
    public int getNumOfLinesThreadPool(String[] fileNames){
        int sum = 0; //result sum
        ExecutorService threadPool = Executors.newFixedThreadPool(fileNames.length); //new threadPool instance
        Future<Integer>[] futures = new Future[fileNames.length]; //Array of futures to be calculated.

        //Calculate all futures asynchronously and save pending result in future instance.
        for(int i = 0; i < fileNames.length; i++){
            futures[i] = threadPool.submit(new ThreadPoolHelper(fileNames[i]));
        }

        //Summation of results
        for (Future<Integer> future: futures){
            try {
                sum += future.get(); //Get value held in the future instance, and add it to the summation answer.
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown(); //Close thread-pool after finishing submissions
        try {
            //Don't continue until all thread in the threadPool have finished executing.
            while(!(threadPool.awaitTermination(10, TimeUnit.MINUTES)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return sum;
    }


    /**
     * Delete all files whose names are in the array given in the parameter.
     * Deletion is local to the project path.
     * @param fileNames Array of the file names generated.
     */
    public void deleteFiles(String[] fileNames){
        for(String fileName: fileNames){
            File file = new File(fileName);
            file.delete();
        }
    }
}
