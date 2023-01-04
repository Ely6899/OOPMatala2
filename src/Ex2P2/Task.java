package Ex2P2;

public class Task {
    private TaskType taskType;

    public Task(TaskType taskType){
        this.taskType = taskType;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public int getTaskPriority(){
        return taskType.getPriorityValue();
    }
}
