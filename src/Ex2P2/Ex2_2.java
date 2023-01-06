package Ex2P2;

import java.util.concurrent.Callable;

public class Ex2_2 {
    public static void main(String[] args) {
        Callable<Double> callable = () -> 100.0 * 20;
        var task = Task.createTask(callable, TaskType.COMPUTATIONAL);
        var task2 = Task.createTask(() -> "other task", TaskType.IO);
        var task3 = Task.createTask(() -> "Nothing");



        /*System.out.println(task.get());
        System.out.println(task2.get());
        System.out.println(task3.get());*/

        System.out.println(task.compareTo(task2));
        System.out.println(task.compareTo(task3));
        System.out.println(task3.compareTo(task2));
    }
}
