package com.hellapinot.thomassmith.thoughtscape_master.DataStructs;

public class TaskStruct {

    private String taskTitle;
    private String taskBody;
    private String taskEndDate;
    private String taskStartDate;
    private int taskProgress;
    private boolean taskComplete;

    public TaskStruct() {
        this.taskTitle = "";
        this.taskBody = "";
        this.taskEndDate = "";
        this.taskProgress = 0;
        this.taskComplete = false;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskBody() {
        return taskBody;
    }

    public void setTaskBody(String taskBody) {
        this.taskBody = taskBody;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public int getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(int taskProgress) {
        this.taskProgress = taskProgress;
    }

    public boolean isTaskComplete() {
        return taskComplete;
    }

    public void setTaskComplete(boolean taskComplete) {
        this.taskComplete = taskComplete;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }
}
