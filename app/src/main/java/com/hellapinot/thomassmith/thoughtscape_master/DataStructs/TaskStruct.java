package com.hellapinot.thomassmith.thoughtscape_master.DataStructs;

import android.content.Context;
import android.util.Log;

import com.hellapinot.thomassmith.thoughtscape_master.DataBaseHelper;
import com.hellapinot.thomassmith.thoughtscape_master.TasksContract;

public class TaskStruct {

    private int dbID;
    private String taskTitle;
    private String taskBody;
    private String taskEndDate;
    private String taskStartDate;
    private int taskProgress;
    private boolean taskComplete;
    private Context context;

    private static final String TAG = "TaskStruct";


    // Default constructor for new tasks added via Floating Action Button
    public TaskStruct(Context context, int dbID) {
        this.context = context;
        this.dbID = dbID;
        this.taskTitle = "";
        this.taskBody = "";
        this.taskStartDate = "";
        this.taskEndDate = "";
        this.taskProgress = 0;
        this.taskComplete = false;
    }

    public TaskStruct(Context context, int dbID, String taskTitle, String taskBody, String taskStartDate, String taskEndDate, String taskComplete){
        this.context = context;
        this.dbID = dbID;
        this.taskTitle = taskTitle;
        this.taskBody = taskBody;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.taskProgress = 0;
        this.taskComplete = Boolean.valueOf(taskComplete);
        Log.d(TAG, "TaskStruct: task complete set to " + Boolean.getBoolean(taskComplete));
    }


    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
        DataBaseHelper.getInstance(context).updateTaskEntry(dbID, TasksContract.Columns.TASKS_TITLE, taskTitle);
    }

    public String getTaskBody() {
        return taskBody;
    }

    public void setTaskBody(String taskBody) {
        this.taskBody = taskBody;
        DataBaseHelper.getInstance(context).updateTaskEntry(dbID, TasksContract.Columns.TASKS_BODY, taskBody);
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
        DataBaseHelper.getInstance(context).updateTaskEntry(dbID, TasksContract.Columns.TASKS_ENDDATE, taskEndDate);
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
        DataBaseHelper.getInstance(context).updateTaskEntry(dbID, TasksContract.Columns.TASKS_COMPLETE, Boolean.toString(taskComplete));
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
        DataBaseHelper.getInstance(context).updateTaskEntry(dbID, TasksContract.Columns.TASKS_STARTDATE, taskStartDate);
    }
}
