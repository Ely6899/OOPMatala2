package Ex2P2;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * This class represents a generic class, which will hold a
 * callable function which should return accordingly a value of the
 * generic type.
 * The value calculated by the Callable function is stored in a Future class, since task is intended to be
 * running on a thread-pool which accepts them.
 * Each task instance will also hold a priority value of the task,
 * which will indicate a thread-pool executing it to figure out its execution priority.
 * @param <T> A genetic type of the Task instance.
 */
public class Task<T> implements Comparator<Task<T>>, Callable<T>{

    private final TaskType taskType; //Task-type which also indicates priority.
    private final Callable<T> callable; //Custom callable function.
    private Future<T> value; //Value of the instance returned by callable
    //private T result; //Regular result storing


    /**
     * A private constructor which defaults the task-type to be of the least significance.
     * @param callable Callable function.
     */
    private Task(Callable<T> callable){
        this.callable = callable;
        this.taskType = TaskType.OTHER;
    }


    /**
     * A private constructor which builds a task with a given callable and taskType.
     * @param callable Callable function.
     * @param taskType enum representing priority of the Task instance.
     */
    private Task(Callable<T> callable, TaskType taskType){
        this.callable = callable;
        this.taskType = taskType;
    }


    /**
     * A Static function which activates a private constructor according to the parameters given.
     * @param callable Callable function.
     * @return Task instance.
     * @param <T> Genetic type of Task instance created.
     */
    public static <T> Task<T> createTask(Callable<T> callable){
        return new Task<T>(callable);
    }


    /**
     * A Static function which activates a private constructor according to the parameters given.
     * Overloads above createTask.
     * @param callable Callable function.
     * @param taskType enum representing priority of the Task instance.
     * @return Task instance.
     * @param <T> Genetic type of Task instance created.
     */
    public static <T> Task<T> createTask(Callable<T> callable, TaskType taskType){
        return new Task<T>(callable, taskType);
    }


    /**
     * In addition to activating the callable field call function,
     * it determines the maxPriority in the call moment of the
     * task queue defined in CustomExecutor.
     * @return call function value.
     * @throws Exception whenever an exception occurs.
     */
    @Override
    public T call() throws Exception {
        if(CustomExecutor.demoPriorityBlockingQueue.isEmpty())
            CustomExecutor.maxPriority = 10;
        else
            CustomExecutor.maxPriority = CustomExecutor.demoPriorityBlockingQueue.peek().hashCode();
        return this.callable.call();
    }

    /**
     * Sets the future value.
     * @param future new future value.
     */
    public void setFuture(Future<T> future){
        this.value = future;
    }


    /**
     * Calls the Future field's get function, which gets the actual value in the Future instance.
     * @return concrete value of Future instance.
     * @throws ExecutionException whenever there is a problem with the execution.
     * @throws InterruptedException whenever the thread was interrupted during execution.
     */
    public T get() throws ExecutionException, InterruptedException {
        return value.get();
    }


    /**
     * Overloads above get function. same functionality, but new parameters act as waiting time.
     * @param timeWait Time to wait for the execution.
     * @param timeUnit Units of the time
     * @return concrete value of Future instance.
     * @throws ExecutionException whenever there is a problem with the execution.
     * @throws InterruptedException whenever the thread was interrupted during execution.
     * @throws TimeoutException whenever waiting has timed out.
     */
    public T get(long timeWait, TimeUnit timeUnit) throws ExecutionException, InterruptedException, TimeoutException {
        value.get(timeWait, timeUnit);
        return (T)value.resultNow();
    }


    /**
     * Getter of task type.
     * @return TaskType value.
     */
    public TaskType getTaskType(){
        return this.taskType;
    }


    /**
     * Getter of priority value.
     * @return int representing priority value of the instance.
     */
    public int getPriority(){
        return this.taskType.getPriorityValue();
    }


    /**
     * Gets a string of the taskType.
     * @return String value of the task type.
     */
    public String toString(){
         return this.getTaskType().toString();
    }


    /**
     * Gets priority value of the instance.
     * Critical for instance comparison.
     * @return int value representing the priority of the instance.
     */
    @Override
    public int hashCode(){
        return this.getPriority();
    }


    /**
     * Compares 2 Task instances by subtracting priority values of the given
     * Tasks.
     * 0 - Tasks are of the same priority.
     * >0 - first object has a higher priority then second object.
     * <0 - first object has a lower priority then second object.
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return compare integer according to the priorities.
     */
    @Override
    public int compare(Task o1, Task o2) {
        return o1.getPriority()- o2.getPriority();
    }
}
