package Ex2P2;

import java.util.concurrent.*;

public class CustomExecutor{
    private final PriorityBlockingQueue<Object> priorityBlockingQueue;
    private final ThreadPoolExecutor threadPoolExecutor;
    private int currMaxPriority = 3;

    public CustomExecutor(){
        int minPoolSize = Runtime.getRuntime().availableProcessors() / 2;
        int maxPoolSize = Runtime.getRuntime().availableProcessors() - 1;
        this.priorityBlockingQueue = new PriorityBlockingQueue<>();
        this.threadPoolExecutor = new ThreadPoolExecutor(
                minPoolSize,
                maxPoolSize,
                300,
                TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10));
    }

    public <T> Future<T> submit(Task<T> task){
        Future<T> future;
        priorityBlockingQueue.add(task);
        currMaxPriority = Math.min(3, task.getTaskType().getPriorityValue());
        try {
            future = threadPoolExecutor.submit(task.call());
            task.setFuture(future);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return future;
    }

    public <T> Future<T> submit(Callable<T> callable, TaskType taskType){
        var newTask = Task.createTask(callable, taskType);
        return submit(newTask);
    }

    public <T> Future<T> submit(Callable<T> callable){
        var newTask = Task.createTask(callable);
        return submit(newTask);
    }

    public void gracefullyTerminate(){
        this.threadPoolExecutor.shutdown();
    }

    public int getCurrentMax() {
        return this.currMaxPriority;
    }
}