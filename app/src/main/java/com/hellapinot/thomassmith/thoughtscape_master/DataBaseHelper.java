package com.hellapinot.thomassmith.thoughtscape_master;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProjectBackup.db";;
    public static final int DATABASE_VERSION = 4;
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
        String sSQL;
//        sSQL = "CREATE TABLE Diary (_id INTEGER PRIMARY KEY NOT NULL, Title TEXT NOT NULL, Body TEXT, Start_Date TEXT, End_Date TEXT, Focused TEXT);";
        sSQL = "CREATE TABLE " + DiaryContract.TABLE_NAME + " ( " +
                DiaryContract.Columns._ID + " INTEGER PRIMARY KEY NOT NULL, " +
                DiaryContract.Columns.DIARY_DFE + " INTEGER, " +
                DiaryContract.Columns.DIARY_TITLE + " TEXT NOT NULL, " +
                DiaryContract.Columns.DIARY_BODY + " TEXT, " +
                DiaryContract.Columns.DIARY_STARTDATE + " TEXT, " +
                DiaryContract.Columns.DIARY_ENDDATE + " TEXT, " +
                DiaryContract.Columns.DIARY_FOCUSED + " TEXT);";
        db.execSQL(sSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if( newVersion != oldVersion){
            db.execSQL("DROP TABLE IF EXISTS "+ DiaryContract.TABLE_NAME);
            onCreate(db);
        }
    }

    public Cursor getDiaryData(int Day){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + DiaryContract.TABLE_NAME + " WHERE " + DiaryContract.Columns.DIARY_DFE + " = " + Day + ";";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void addEntry(int dfe, String title, String body, String startDate, String endDate, String focused){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO Diary_Backup " + "(" +
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

    public void updateEntry(int dbID, String column, String update){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE Diary_Backup SET " + column +
                " = '" + update + "' WHERE " + DiaryContract.Columns._ID + " = " + dbID + ";";
        Log.d(TAG, "updateEntry: " + query);
        db.execSQL(query);
    }

    public int getLastRow(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM Diary_Backup", null);
        data.moveToLast();
        int id = data.getInt(0);
        Log.d(TAG, "getLastRow: " + id);
        return id;
    }




}
