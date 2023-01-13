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
 * implements Comparator and Callable interfaces.
 */
public class Task<T> implements Comparator<Task<T>>, Callable<T>{

    private final TaskType taskType; //Task-type which also indicates priority.
    private final Callable<T> callable; //Custom callable function.

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
     * createTask implements the FactoryMethod design pattern. we want to hide from the user the complexity
     * of our constructor, and give him a simple way to create a Task instance using the class name itself.
     * @param callable Callable function.
     * @return Task instance.
     * @param <T> Genetic type of Task instance created.
     *
     */
    public static <T> Task<T> createTask(Callable<T> callable){
        return new Task<>(callable);
    }


    /**
     * A Static function which activates a private constructor according to the parameters given.
     * Overloads above createTask.
     * createTask implements the FactoryMethod design pattern. we want to hide from the user the complexity
     * of our constructor, and give him a simple way to create a Task instance using the class name itself.
     * @param callable Callable function.
     * @param taskType enum representing priority of the Task instance.
     * @return Task instance.
     * @param <T> Genetic type of Task instance created.
     */
    public static <T> Task<T> createTask(Callable<T> callable, TaskType taskType){
        return new Task<>(callable, taskType);
    }


    /**
     * the function activates the callable using the call() method.
     * @return call function value.
     * @throws RuntimeException if call() gets an exception like InterruptedException.
     */
    @Override
    public T call() throws Exception {
        try {
            return this.callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
