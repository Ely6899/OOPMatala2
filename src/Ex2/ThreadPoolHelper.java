package Ex2;

import java.util.concurrent.Callable;

import static Ex2.Ex2_1.readFileAndCountRows;

public class ThreadPoolHelper implements Callable<Integer> {
    private int lineCount;
    private final String fileName;

    public ThreadPoolHelper(String fileName){
        this.fileName = fileName;
        this.lineCount = 0;
    }

    @Override
    public Integer call(){
        lineCount = readFileAndCountRows(this.fileName);
        return lineCount;
    }
}
