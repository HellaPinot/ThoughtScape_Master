package com.hellapinot.thomassmith.thoughtscape_master.DataStructs;

import com.hellapinot.thomassmith.thoughtscape_master.DataStructs.TaskStruct;

import java.util.ArrayList;

public class IdeaStruct {

    private String title;
    private String body;

    private String projectStartDate = null;
    private String projectEndDate = null;
    private boolean focused;
    private ArrayList<TaskStruct> taskList;

    public IdeaStruct(String title, String body){
        this.title = title;
        this.body = body;
        focused = false;
        taskList = new ArrayList<>();
    }

    public IdeaStruct(String title, String body, String projectStartDate, String projectEndDate, String focused) {
        this.title = title;
        this.body = body;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.focused = Boolean.valueOf(focused);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public ArrayList<TaskStruct> getTaskList() {
        return taskList;
    }

    public void addTask() {
        this.taskList.add(new TaskStruct());
    }

    public String getProjectStartDate() {
        return projectStartDate;
    }

    public void setProjectStartDate(String projectStartDate) {
        this.projectStartDate = projectStartDate;
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
    }
}
