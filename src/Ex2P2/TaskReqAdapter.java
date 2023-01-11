package Ex2P2;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * This class represents a generic task.
 * we use this class in order to wrap the Task instance with a FutureTask.
 * this will help us to adapt the Task class that implements Callable to a FutureTask which implements Runnable.
 * by this adaption, we can submit the TaskReqAdapter to the thread-pool and use the priorityBlockingQueue comfortably,
 * and still get the value of the task when it is done.
 * this class implements the Adapter design pattern.
 * @param <V> generic type of the Task instance.
 * @extends FutureTask<V> class.
 * implements Comparable<V> interface.
 */
public class TaskReqAdapter<V> extends FutureTask<V> implements Comparable<TaskReqAdapter<V>> {

    private int priority; //a private field that represents the priority of the task.

    /**
     * A private constructor which builds a TaskReqAdapter instance with a given callable and priority.
     * calls the super constructor of FutureTask with the given callable and adds the priority.
     * @param callable Callable function.
     * priority - the priority of the TaskReqAdapter instance.
     */
    public TaskReqAdapter(Callable<V> callable, TaskType taskType) {
        super(callable);
        this.priority = taskType.getPriorityValue();
    }

    /**
     * a method that overrides the original compareTo method from the Comparable interface.
     * compare taskReqAdapters by their priority.
     * @param other the object to be compared.
     * @return a positive number if this taskReqAdapter has a higher priority than the other taskReqAdapter.
     *         a negative number if this taskReqAdapter has a lower priority than the other taskReqAdapter.
     *         0 if both taskReqAdapters have the same priority.
     */
    @Override
    public int compareTo(TaskReqAdapter<V> other) {
        return (this.priority - other.priority);
    }

    /**
     * a getter method for the priority field.
     * @return the priority of the taskReqAdapter.
     */
    public int getPriority(){
        return this.priority;
    }
}
