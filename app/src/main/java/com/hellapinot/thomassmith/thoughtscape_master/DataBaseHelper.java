package com.hellapinot.thomassmith.thoughtscape_master;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProjectBackup.db";;
    public static final int DATABASE_VERSION = 6;
    private static DataBaseHelper instance = null;
    private static final String TAG = "DataBaseHelper";


    private DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DataBaseHelper getInstance(Context context){
        if(instance == null){
            instance = new DataBaseHelper(context);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sSQL = "CREATE TABLE " + DiaryContract.TABLE_NAME + " ( " +
                DiaryContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DiaryContract.Columns.DIARY_DFE + " INTEGER, " +
                DiaryContract.Columns.DIARY_TITLE + " TEXT NOT NULL, " +
                DiaryContract.Columns.DIARY_BODY + " TEXT, " +
                DiaryContract.Columns.DIARY_STARTDATE + " TEXT, " +
                DiaryContract.Columns.DIARY_ENDDATE + " TEXT, " +
                DiaryContract.Columns.DIARY_FOCUSED + " TEXT);";
        db.execSQL(sSQL);

        String mSQL = "CREATE TABLE " + TasksContract.TABLE_NAME + " ( " +
                TasksContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                TasksContract.Columns.TASKS_AID + " INTEGER, " +
                TasksContract.Columns.TASKS_TITLE + " TEXT NOT NULL, " +
                TasksContract.Columns.TASKS_BODY + " TEXT, " +
                TasksContract.Columns.TASKS_STARTDATE + " TEXT, " +
                TasksContract.Columns.TASKS_ENDDATE + " TEXT, " +
                TasksContract.Columns.TASKS_COMPLETE + " TEXT);";
        db.execSQL(mSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion != oldVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ DiaryContract.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS "+ TasksContract.TABLE_NAME);
            onCreate(db);

        }
    }

    public Cursor getDiaryData(int Day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DiaryContract.TABLE_NAME + " WHERE " + DiaryContract.Columns.DIARY_DFE + " = " + Day + ";";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addDiaryEntry(int dfe, String title, String body, String startDate, String endDate, String focused){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + DiaryContract.TABLE_NAME + "(" +
                DiaryContract.Columns.DIARY_DFE + ", " +
                DiaryContract.Columns.DIARY_TITLE + ", " +
                DiaryContract.Columns.DIARY_BODY + ", " +
                DiaryContract.Columns.DIARY_STARTDATE + ", " +
                DiaryContract.Columns.DIARY_ENDDATE + ", " +
                DiaryContract.Columns.DIARY_FOCUSED + ") VALUES (" +
                dfe + ", '" +
                title + "', '" +
                body + "', '" +
                startDate + "', '" +
                endDate + "', '" +
                focused + "');";
                db.execSQL(query);
    }

    public void updateDiaryEntry(int dbID, String column, String update){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Diary_Backup SET " + column +
                " = '" + update + "' WHERE " + DiaryContract.Columns._ID + " = " + dbID + ";";
        Log.d(TAG, "updateDiaryEntry: " + query);
        db.execSQL(query);
    }

    public int getLastRowDiary(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Diary_Backup", null);
        data.moveToLast();
        int id = data.getInt(0);
        Log.d(TAG, "getLastRowDiary: " + id);
        return id;
    }

    public void deleteDiaryEntry(int dbID){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + DiaryContract.TABLE_NAME + " WHERE " + DiaryContract.Columns._ID + " = " + dbID;
        db.execSQL(query);
        query = "DELETE FROM " + TasksContract.TABLE_NAME + " WHERE " + TasksContract.Columns.TASKS_AID + " = " + dbID;
        db.execSQL(query);
    }





    public Cursor getTaskData(int aid){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TasksContract.TABLE_NAME + " WHERE " + TasksContract.Columns.TASKS_AID + " = " + aid + ";";
        return db.rawQuery(query, null);
    }

    public void addTaskEntry(int aid, String title, String body, String startDate, String endDate, String focused){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TasksContract.TABLE_NAME + "(" +
                TasksContract.Columns.TASKS_AID + ", " +
                TasksContract.Columns.TASKS_TITLE + ", " +
                TasksContract.Columns.TASKS_BODY + ", " +
                TasksContract.Columns.TASKS_STARTDATE + ", " +
                TasksContract.Columns.TASKS_ENDDATE + ", " +
                TasksContract.Columns.TASKS_COMPLETE + ") VALUES (" +
                aid + ", '" +
                title + "', '" +
                body + "', '" +
                startDate + "', '" +
                endDate + "', '" +
                focused + "');";
        db.execSQL(query);
    }

    public void updateTaskEntry(int dbID, String column, String update){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE "+ TasksContract.TABLE_NAME + " SET " + column +
                " = '" + update + "' WHERE " + TasksContract.Columns._ID + " = " + dbID + ";";
        Log.d(TAG, "updateTaskEntry: " + query);
        db.execSQL(query);
    }

    public int getLastRowTasks(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TasksContract.TABLE_NAME, null);
        data.moveToLast();
        int id = data.getInt(0);
        return id;
    }





}
