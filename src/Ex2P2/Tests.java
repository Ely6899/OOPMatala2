package Ex2P2;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {
    public static final Logger logger = LoggerFactory.getLogger(Tests.class);


    @Test
    public void partialTest() {
        CustomExecutor customExecutor = new CustomExecutor();
        var task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Sum of 1 through 10 = " + sum);
        Callable<Double> callable1 = () -> {
            return 1000 * Math.pow(1.02, 5);
        };
        Callable<String> callable2 = () -> {
            StringBuilder sb = new StringBuilder("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            return sb.reverse().toString();
        };
// var is used to infer the declared type automatically
        var priceTask = customExecutor.submit(() -> {
            return 1000 * Math.pow(1.02, 5);
        }, TaskType.COMPUTATIONAL);
        var reverseTask = customExecutor.submit(callable2, TaskType.IO);
        final Double totalPrice;
        final String reversed;
        try {
            totalPrice = (Double) priceTask.get();
            reversed = (String) reverseTask.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        logger.info(() -> "Reversed String = " + reversed);
        logger.info(() -> String.valueOf("Total Price = " + totalPrice));
        logger.info(() -> "Current maximum priority = " + customExecutor.getCurrentMax());
        customExecutor.gracefullyTerminate();
    }

    @Test
    public void testTask() {
        Task<Integer> newTask = Task.createTask(() -> {
            int sum = 0;
            for (int i = 0; i < 10; i++)
                sum += i;
            return sum;
        }, TaskType.COMPUTATIONAL);
        assertEquals(TaskType.COMPUTATIONAL, newTask.getTaskType());
    }

    @Test
    public void testCustomExecutor() {
        CustomExecutor customExecutor = new CustomExecutor();
        Task<Integer> task = Task.createTask(() -> {
            int sum = 0;
            for (int i = 1; i <= 10; i++) {
                sum += i;
            }
            return sum;
        }, TaskType.COMPUTATIONAL);
        var sumTask = customExecutor.submit(task);
        final int sum;
        try {
            sum = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask2 = customExecutor.submit(task);
        final int sum2;
        try {
            sum2 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask3 = customExecutor.submit(task);
        final int sum3;
        try {
            sum3 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask4 = customExecutor.submit(task);
        final int sum4;
        try {
            sum4 = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        Future<Integer> sumTask5 = customExecutor.submit(task);
        final int sum5;
        try {
            sum5 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask6 = customExecutor.submit(task);
        final int sum6;
        try {
            sum6 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask7 = customExecutor.submit(task);
        final int sum7;
        try {
            sum7 = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask8 = customExecutor.submit(task);
        final int sum8;
        try {
            sum8 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask9 = customExecutor.submit(task);
        final int sum9;
        try {
            sum9 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask10 = customExecutor.submit(task);
        final int sum10;
        try {
            sum10 = sumTask.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask11 = customExecutor.submit(task);
        final int sum11;
        try {
            sum11 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }
        var sumTask12 = customExecutor.submit(task);
        final int sum12;
        try {
            sum12 = sumTask2.get(1, TimeUnit.MILLISECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            throw new RuntimeException(e);
        }

        assertEquals(1, customExecutor.getCurrentMax());
    }
}