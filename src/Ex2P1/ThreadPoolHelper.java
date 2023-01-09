package Ex2P1;

import java.util.concurrent.Callable;

import static Ex2P1.Ex2_1.readFileAndCountRows;

/**
 * Helper class which contains the call function required for the threadPool execution.
 */
public class ThreadPoolHelper implements Callable<Integer> {
    private int lineCount;
    private final String fileName;

    /**
     * Builds an instance which will help the threadPool get the data required for running the call function.
     * @param fileName Name of a text file.
     */
    public ThreadPoolHelper(String fileName){
        this.fileName = fileName;
        this.lineCount = 0;
    }

    /**
     * Overrides the Callable super class function.
     * Call is activated automatically when we pass a new instance of ThreadPoolHelper
     * to a submit function call of a threadPoolExecutor.
     * This specific call function handles counting the line count of the file name
     * given in the constructor, and returns the Integer value.
     * @return Line count of the file name initialized in the constructor.
     */
    @Override
    public Integer call(){
        lineCount = readFileAndCountRows(this.fileName);
        return lineCount;
    }
}
