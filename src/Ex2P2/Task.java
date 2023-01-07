package Ex2P2;

import java.util.Comparator;
import java.util.concurrent.*;

public class Task<T> implements Comparable<Task<T>>, Callable<Callable<T>>{
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
    public Callable<T> call() throws Exception {
        return this.callable;
    }

    public void setFuture(Future<T> future){
         this.value = future;
    }

    public T get() throws ExecutionException, InterruptedException {
        return value.get();
    }

    public T get(long timeWait, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
            return value.get(timeWait, timeUnit);
    }

    public TaskType getTaskType(){
        return this.taskType;
    }


    @Override
    public int hashCode() {
        return this.taskType.getPriorityValue();
    }

    @Override
    public int compareTo(Task<T> o) {
        return this.hashCode() - o.hashCode();
    }
}
