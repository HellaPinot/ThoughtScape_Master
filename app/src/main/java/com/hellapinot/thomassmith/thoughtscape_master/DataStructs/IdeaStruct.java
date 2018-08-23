package com.hellapinot.thomassmith.thoughtscape_master.DataStructs;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.hellapinot.thomassmith.thoughtscape_master.Activities.BaseActivity;
import com.hellapinot.thomassmith.thoughtscape_master.DataBaseHelper;
import com.hellapinot.thomassmith.thoughtscape_master.DataStructs.TaskStruct;
import com.hellapinot.thomassmith.thoughtscape_master.DiaryContract;

import java.util.ArrayList;

public class IdeaStruct {

    private static final String TAG = "IdeaStruct";

    private String title;
    private String body;
    private String projectStartDate = null;
    private String projectEndDate = null;
    private boolean focused;
    private ArrayList<TaskStruct> taskList;
    private int dbID;
    private Context context;

    //Used for new idea entries via floating action button in DailyDiaryActivity
    public IdeaStruct(Context context, int position, String title, String body){
        this.title = title;
        this.body = body;
        focused = false;
        taskList = new ArrayList<>();
        newEntry(context, position, title, body);
        this.context = context;
    }

    //Used for restoring data from SQLite when staring app
    public IdeaStruct(Context context, int dbID, String title, String body, String projectStartDate, String projectEndDate, String focused) {
        this.dbID = dbID;
        this.title = title;
        this.body = body;
        this.projectStartDate = projectStartDate;
        this.projectEndDate = projectEndDate;
        this.focused = Boolean.valueOf(focused);
        taskList = new ArrayList<>();
        this.context = context;
    }

    public void newEntry(Context context, int dfe, String title, String body){
        DataBaseHelper.getInstance(context).addEntry(dfe + 1, title, body, "", "", "false");
        dbID = DataBaseHelper.getInstance(context).getLastRow();

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        DataBaseHelper.getInstance(context).updateEntry(dbID, DiaryContract.Columns.DIARY_TITLE, title);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
        DataBaseHelper.getInstance(context).updateEntry(dbID, DiaryContract.Columns.DIARY_BODY, body);
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
        DataBaseHelper.getInstance(context).updateEntry(dbID, DiaryContract.Columns.DIARY_FOCUSED, Boolean.toString(focused));
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
        DataBaseHelper.getInstance(context).updateEntry(dbID, DiaryContract.Columns.DIARY_STARTDATE, projectStartDate);
    }

    public String getProjectEndDate() {
        return projectEndDate;
    }

    public void setProjectEndDate(String projectEndDate) {
        this.projectEndDate = projectEndDate;
        DataBaseHelper.getInstance(context).updateEntry(dbID, DiaryContract.Columns.DIARY_ENDDATE, projectEndDate);
    }
}
