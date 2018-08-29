package com.hellapinot.thomassmith.thoughtscape_master;

import android.provider.BaseColumns;

public class TasksContract {

    public static final String TABLE_NAME = "Tasks_Backup";

    public static class Columns{

        public static final String _ID = BaseColumns._ID;
        public static final String TASKS_AID = "Associated_ID";
        public static final String TASKS_TITLE = "Title";
        public static final String TASKS_BODY = "Body";
        public static final String TASKS_STARTDATE = "Start_Date";
        public static final String TASKS_ENDDATE = "End_Date";
        public static final String TASKS_COMPLETE = "Task_Complete";

    }
}
