package com.hellapinot.thomassmith.thoughtscape_master;

import android.provider.BaseColumns;

public class DiaryContract {

    static final String TABLE_NAME = "Diary_Backup";

    public static class Columns {

        public static final String _ID = BaseColumns._ID;
        public static final String DIARY_DFE = "Days_From_Epoch";
        public static final String DIARY_TITLE = "Title";
        public static final String DIARY_BODY = "Body";
        public static final String DIARY_STARTDATE = "Start_Date";
        public static final String DIARY_ENDDATE = "End_Date";
        public static final String DIARY_FOCUSED = "Focused";

        private Columns(){

        }
    }
}
