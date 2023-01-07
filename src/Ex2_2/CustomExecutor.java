package Ex2_2;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import static java.lang.Thread.sleep;

public class CustomExecutor {
    PriorityQueue<Task<Integer>> priorityQueue;
    ThreadPoolExecutor threadPoolExecutor;


    public CustomExecutor(){
        this.priorityQueue = new PriorityQueue<>();
        ExecutorService pool = Executors.newFixedThreadPool(10);
        this.threadPoolExecutor = (ThreadPoolExecutor) pool;

        Thread TaskSubmitter = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(priorityQueue.isEmpty() || threadPoolExecutor.getActiveCount() == threadPoolExecutor.getPoolSize()){
                        try {
                            //Thread.sleep(400);
                            System.out.println("im supposed to sleep but i am not because java is stupid");
                        }
                        catch (InterruptedException e){
                            continue;
                        }
                    }
                    else{
                        Task<Integer> t= priorityQueue.poll();
                        if(t == null) continue;
                        Future<Integer> future= threadPoolExecutor.submit(t);
                    }
                }
            }
        });
        TaskSubmitter.start();
    }



    public void addNewTaskToQueue(Task<?> task){
        this.priorityQueue.add(task);
    }
}


