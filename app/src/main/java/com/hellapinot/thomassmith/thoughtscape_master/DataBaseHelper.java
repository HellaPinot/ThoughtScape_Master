package com.hellapinot.thomassmith.thoughtscape_master;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "project_backup";
    public static final String COL0 = "id";
    public static final String COL1 = "daysFromEpoch";
    public static final String COL2 = "itemInList";
    public static final String COL3 = "title";
    public static final String COL4 = "body";
    public static final String COL5 = "start_date";
    public static final String COL6 = "end_date";
    public static final String COL7 = "focused";

    private Context context;


    public DataBaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if(doesDatabaseExist(TABLE_NAME)){


        }else {
            String createTable = "CREATE TABLE " + TABLE_NAME + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " + COL1 +  " INTEGER, " + COL2 +  " INTEGER, " + COL3 + " TEXT, " + COL4 + " TEXT, " +
                    COL5 + " TEXT, " + COL6 + " TEXT, " + COL7 +  " BOOLEAN)";
            db.execSQL(createTable);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    private boolean doesDatabaseExist(String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public Cursor getDataDiary(int daysFromEpoch){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL1 + " = '" + daysFromEpoch + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }
}
