package Ex2P1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class Ex2_1Test {
     Ex2_1 ex2_1;
     Long seed;

    @BeforeEach
    void setUp(){
        ex2_1 = new Ex2_1();
        seed = 10L;
    }

    @Test
    void testCreateFilesRegular(){
        String[] files = {"file1.txt", "file2.txt", "file3.txt", "file4.txt", "file5.txt"};
        //Bound is arbitrary for this test.
        assertEquals(Arrays.toString(files), Arrays.toString(Ex2_1.createTextFiles(5, seed, 10)));
    }

    @Test
    void testCreateFilesEmpty(){
        String[] files = {};
        //Bound is arbitrary for this test.
        assertEquals(Arrays.toString(files), Arrays.toString(Ex2_1.createTextFiles(0, seed, 10)));
    }

    @Test
    void testCreateFilesException(){
        //Bound is arbitrary for this test.
        assertThrows(NegativeArraySizeException.class, () -> Ex2_1.createTextFiles(-12, seed, 10));
    }

    @Test
    void testRunTimes(){
        String[] files = Ex2_1.createTextFiles(1000, seed, 1000); //Create files
        System.err.println("Finished generating files");

        long startTimeRegular = System.currentTimeMillis(); //Start measuring regular algorithm run-time.
        System.out.println(Ex2_1.getNumOfLines(files)); //Calculate the number of rows of all generated files and print it.
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

        ex2_1.deleteFiles(files); //Delete all generated files.
    }

}