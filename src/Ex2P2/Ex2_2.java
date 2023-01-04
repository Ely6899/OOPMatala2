package Ex2P2;

public class Ex2_2 {
    public static void main(String[] args) {
        Task task = new Task(TaskType.COMPUTATIONAL);
        Task task1 = new Task(TaskType.IO);

        System.out.println(task.getTaskType());
        System.out.println(task.getTaskPriority());

        System.out.println(task1.getTaskType());
        System.out.println(task1.getTaskPriority());
    }
}
