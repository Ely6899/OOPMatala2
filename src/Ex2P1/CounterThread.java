package Ex2P1;

import static Ex2P1.Ex2_1.readFileAndCountRows;

/**
 * Helper class which holds the result value of a thread.
 * It acts as a single thread.
 */
public class CounterThread extends Thread{
    private int lineCount;

    /**
     * Builds a new instance by calling the parent constructor(Thread constructor which gets a custom thread name)
     * In general, this builder generates a new thread, and sets the thread name to match the file name
     * on which it will run on.
     * @param fileName Name of a text file.
     */
    public CounterThread(String fileName){
        super(fileName);
    }


    /**
     * Overridden function call of the Thread super class.
     * Sets the class lineCount value after calling readFileAndCountRows().
     */
    @Override
    public void run() {
        this.lineCount = readFileAndCountRows(this.getName());
    }


    /**
     * Gets the line count of the instance, calculated by the thread.
     * @return Line count of file defined in the constructor.
     */
     public int getLineCount() {
        return lineCount;
    }
}
