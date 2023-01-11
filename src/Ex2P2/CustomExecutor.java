package Ex2P2;

import java.util.Comparator;
import java.util.concurrent.*;


/**
 * A custom comparator which will be used as
 * the comparator for the priority queue
 */
class TaskComparator implements Comparator<Object> {

    /**
     * Compares two objects and returns who are bigger numerically.
     * 0 - (o1 == o2)
     * <0 - (o1 < o2)
     * >0 - (o1 > o2)
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return int value of comparison of 2 objects, based on priority values.
     */
    @Override
    public int compare(Object o1, Object o2) {
        if((o1 instanceof FutureTask<?> && o2 instanceof FutureTask<?>) ||
                (o1 instanceof Task<?> && o2 instanceof Task<?>)){
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
        else throw new IllegalArgumentException("Object is not task type");
    }
}


/**
 * A custom thread-pool which considers the queue order according to
 * the priority values as the order in which the priority queue will be ordered.
 * This customExecutor works with the Task instances, and supports
 * different types of Task instances(hence, why Task is generic).
 * Each Task which runs in the thread-pool will run asynchronously.
 */
public class CustomExecutor extends ThreadPoolExecutor {
    static int maxPriority = 0; //the maximum priority currently in the queue.

    /**
     * builds a new ThreadPoolExecutor based on a priorityBlockingQueue
     * which sorts the tasks according to the priority values.
     */
    public CustomExecutor() {
        super(  Runtime.getRuntime().availableProcessors() / 2,
                Runtime.getRuntime().availableProcessors() - 1,
                300,
                TimeUnit.MILLISECONDS,
                new PriorityBlockingQueue<>(10, new TaskComparator()));
    }


    /**
     * the function creates a new TaskReqAdapter instance which will be submitted to the thread-pool.
     * TaskReqAdapter is a FutureTask that wraps the generic task that implements callable with a runnable.
     * and returns the future value derived from the execution.
     * @param task A generic task
     * @return futureTask of the value of the task callable invoked to execute() of the thread-pool.
     * @param <V> Generic type of the Task instance given.
     * @throws NullPointerException whenever the task equals to null
     * @throws RuntimeException if the thread or the futureTask gets interrupted due to OS or timeout.
     */
    public <V> Future<V> submit(Task<V> task){
        if (task == null) throw new NullPointerException("task is null");

        TaskReqAdapter<V> taskReqAdapter = new TaskReqAdapter<>(task, task.getTaskType());
        try{
            super.execute(taskReqAdapter);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return taskReqAdapter;
    }

    /**
     * the function casts the runnable to a TaskReqAdapter in order to get its priority
     * if the priority is bigger than the maxPriority, maxPriority will be updated.
     * and then calls the super.beforeExecute() function with the same t and r.
     */
    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        TaskReqAdapter<?> taskReqAdapter = (TaskReqAdapter<?>) r;
        if(taskReqAdapter.getPriority() > maxPriority){
            maxPriority = taskReqAdapter.getPriority();
        }
        super.beforeExecute(t, r);
    }


    /**
     * Acts like the original submit, however, since it doesn't explicitly get
     * a Task instance, we build the instance inside the function, using
     * the createTask() static method, and then activate the original submit with the new instance.
     * @param callable A callable instance
     * @param taskType TaskType enum value representing the priority of the task.
     * @return FutureTask of the value of the task callable invoked to submit() of the thread-pool.
     * @param <V> Generic type of the callable function. which means it is the return type.
     */
    public <V> Future<V> submit(Callable<V> callable, TaskType taskType){
        return this.submit(Task.createTask(callable, taskType));
    }


    /**
     * Acts like the original submit, however, since it doesn't explicitly get
     * a Task instance, we build the instance inside the function, using
     * the createTask() static method, and then activate the original submit with the new instance.
     * @param callable A callable function
     * @return FutureTask of the value of the task callable invoked to submit() of the thread-pool.
     * @param <V> Generic type of the callable function. which means it is the return type.
     */
    public <V> Future<V> submit(Callable<V> callable){
        return this.submit(Task.createTask(callable));
    }


    /**
     * Shuts done the thread-pool and awaits the termination of all running threads remaining.
     * since awaitTermination is a function that checks if the thread-pool is terminated in one specific
     * given time, we use a while loop to check all the time if the thread-pool is terminated.
     * timeout - the time to wait for the termination of the thread-pool.
     * @throws RuntimeException if the thread or the futureTask gets interrupted.
     */
    public void gracefullyTerminate(){
        try {
            this.shutdown();
            while(!(this.awaitTermination(10, TimeUnit.MINUTES)));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Gets the value of maxPriority, which is calculated in beforeExecute() function.
     * The value returned represents the max priority value currently in the priority queue in the
     * moment of invoking this method.
     * @return maxPriority value in the moment of invoking the method.
     */
    public int getCurrentMax() {
        return maxPriority;
    }

}


