package Ex2;

import static Ex2.Ex2_1.readFileAndCountRows;

public class CounterThread extends Thread{
    private final String fileName;
    private int lineCount;

    public CounterThread(String fileName){
        this.fileName = fileName;
        this.lineCount = 0;
    }

    @Override
    public void run() {
        //System.out.println(this.getName() + " Started counting lines");
        lineCount = readFileAndCountRows(this.fileName);
        setLineCount(lineCount);
        //System.out.println(this.getName() + " Ended counting lines");
    }

    public int getLineCount() {
        return lineCount;
    }

    private void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}
