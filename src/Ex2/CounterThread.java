package Ex2;

import static Ex2.Ex2_1.readFileAndCountRows;

public class CounterThread extends Thread{
    private int lineCount = 0;
    //private String fileName;

    public CounterThread(String fileName){
        super(fileName);
        //this.fileName=fileName;
    }

    @Override
    public void run() {
        this.lineCount = readFileAndCountRows(this.getName());
    }


     public int getLineCount() {
        return lineCount;
    }
}
