package Ex2;

import java.util.concurrent.Callable;

public class ThreadPoolHelper implements Callable<Integer> {

    private final String fileName;

    public ThreadPoolHelper(String fileName){
        this.fileName = fileName;
    }
    @Override
    public Integer call() throws Exception {
        return Ex2_1.readFileAndCountRows(this.fileName);
    }
}
