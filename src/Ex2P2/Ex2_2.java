package Ex2P2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Ex2_2 {
    public static void main(String[] args) {
        /*Callable<Integer> callable = () -> 100 * 20;
        var task = Task.createTask(callable, TaskType.COMPUTATIONAL);
        var task2 = Task.createTask(() -> "IO task", TaskType.IO);
        var task3 = Task.createTask(() -> "Nothing");

        CustomExecutor customExecutor = new CustomExecutor<>();
        customExecutor.submit(task);
        customExecutor.submit(task2);
        customExecutor.submit(task3);

        try {
            System.out.println(task.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(task2.get());
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        try {
            System.out.println(task3.get(1, TimeUnit.MINUTES));
        } catch (ExecutionException | TimeoutException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println(task.compareTo(task2));
        System.out.println(task.compareTo(task3));
        System.out.println(task3.compareTo(task2));

        customExecutor.gracefullyTerminate();*/
    }
}
