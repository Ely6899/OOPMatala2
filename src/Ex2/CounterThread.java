package Ex2;

import static Ex2.Ex2_1.readFileAndCountRows;

public class CounterThread extends Thread{
    private int lineCount = 0;

    public CounterThread(String fileName){
        super(fileName);
    }

    @Override
    public void run() {
        lineCount = readFileAndCountRows(this.getName());
    }


     public int getLineCount() {
        return lineCount;
    }
}
