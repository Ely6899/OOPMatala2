package Ex2P2;

import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>, Callable<T>{
    private final TaskType taskType;
    private final Callable<T> callable;
    private Future<T> value;

    private Task(Callable<T> callable){
        this.callable = callable;
        this.taskType = TaskType.OTHER;
    }

    private Task(Callable<T> callable, TaskType taskType){
        this.callable = callable;
        this.taskType = taskType;
    }

    public static <T> Task<T> createTask(Callable<T> callable){
        return new Task<>(callable);
    }

    public static <T> Task<T> createTask(Callable<T> callable, TaskType taskType){
        return new Task<>(callable, taskType);
    }

    @Override
    public T call() throws Exception {
        return this.callable.call();
    }


    public T get(){
        try {
            return value.get();
        } catch (InterruptedException | ExecutionException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public T get(long timeWait, TimeUnit timeUnit){
        try {
            return value.get(timeWait, timeUnit);
        } catch (InterruptedException | ExecutionException | TimeoutException | NullPointerException e) {
            throw new RuntimeException(e);
        }
    }

    public TaskType getTaskType(){
        return this.taskType;
    }


    @Override
    public int hashCode() {
        return this.taskType.getPriorityValue();
    }

    @Override
    public int compareTo(Task o) {
        return this.hashCode() - o.hashCode();
    }
}
