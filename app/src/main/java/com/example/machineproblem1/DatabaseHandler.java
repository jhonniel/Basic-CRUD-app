package com.example.machineproblem1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "studentdb";
    private static final String TABLE_NAME = "student_table";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_BIRTH = "birthday";
    private static final String COLUMN_COURSE= "course";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_SCHOOLID = "schoolid";



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME + " TEXT," + COLUMN_ADDRESS + " TEXT," + COLUMN_BIRTH + " TEXT," + COLUMN_GENDER + " TEXT," +COLUMN_COURSE+" TEXT,"+ COLUMN_SCHOOLID + " TEXT)";
        db.execSQL(CREATE_ITEM_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }
    public void insertData(String name,String address, String birthday, String gender,String course, String schoolid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_BIRTH, birthday);
        values.put(COLUMN_GENDER, gender);
        values.put(COLUMN_COURSE, course);
        values.put(COLUMN_SCHOOLID, schoolid);
        db.insert(TABLE_NAME, null, values);
        db.close();

    }
    public List<String> getAllLabels() {
        List<String> list = new ArrayList<String>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do{
                list.add(cursor.getString(6)+"  "+cursor.getString(1)+"  "+cursor.getString(2)+"  "+
                        cursor.getString(3)+"  "+cursor.getString(4)+"  "+
                        cursor.getString(5));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;

    }

}