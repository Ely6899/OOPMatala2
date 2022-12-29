package Ex2;

import java.util.Arrays;
import java.util.Random;

public class Ex2_1{


    /**
     *
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
            generatedFiles[i - 1] = FileReadWrite.writeFile(String.format("file%d", i), lineCount);
        }
        return generatedFiles;
    }


    /**
     *
     * @param fileNames Array of the file names generated
     * @return Number of all rows in total of the files given in the fileNames array.
     */
    public static int sumNumOfLines(String[] fileNames){
        int sum = 0;
        for(String fileName: fileNames){
            sum += FileReadWrite.readFileAndCountRows(fileName);
        }
        return sum;
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis(); //Start measuring regular algorithm run-time.
        String[] files = createTextFiles(100, 515656169, 10); //Create files
        System.out.println(Arrays.toString(files));
        System.out.println(sumNumOfLines(files)); //Calculate the number of rows of all generated files and print it.
        long endTime = System.currentTimeMillis(); //End measure of regular algorithm run-time.
        System.out.print("Regular algorithm run time "+ (endTime - startTime)); //Print regular algorithm run-time.
    }
}
