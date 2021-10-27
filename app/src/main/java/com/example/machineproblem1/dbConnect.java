package com.example.machineproblem1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class dbConnect extends SQLiteOpenHelper {

    public static final String DB_NAME = "dbStudent.db";
    public static final String TBL_STUDENT = "tblStudent";
    public static final String S_ID = "ID";
    public static final String S_LASTNAME = "LastName";
    public static final String S_FIRSTNAME = "FirstName";
    public static final String S_ADDRESS = "Address";
    public static final String S_GENDER = "Gender";
    public static final String S_BIRTHDAY = "Birthday";
    public static final String S_CONTACT = "Contact";


    public dbConnect(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    //Creating Tables for Database
    public void onCreate(SQLiteDatabase db) {
        try {
            String strQuery = "CREATE TABLE " + TBL_STUDENT + "(" + S_ID + " INTEGER Primary Key AUTOINCREMENT," + S_LASTNAME + " TEXT, " + S_FIRSTNAME + " TEXT, " + S_ADDRESS + " TEXT," + S_GENDER + " TEXT," + S_BIRTHDAY + " TEXT," + S_CONTACT + " TEXT)";
            db.execSQL(strQuery);
        } catch (Exception ex) {
            Toast.makeText(null, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    //Delete the previous table if there is an existing table name
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_STUDENT);
        onCreate(db);
    }


    public boolean AddRecord(Student student) {
        //Uses the Student class to get the setter and getter
        //Add record to the database dbStudent
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(S_LASTNAME, student.getLastName());
        cv.put(S_FIRSTNAME, student.getFirstName());
        cv.put(S_BIRTHDAY, student.getBirthday());
        cv.put(S_GENDER, student.getGender());
        cv.put(S_ADDRESS, student.getAddress());
        cv.put(S_CONTACT, student.getContact());


        long insert = db.insert(TBL_STUDENT, null, cv);

        return insert != -1;
    }


    public List<Student> viewAllData() {
        //Search the Record individually using ListView and ArrayList
        List<Student> returnAllData = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor CheckRecord = db.rawQuery("SELECT * FROM " + TBL_STUDENT, null);

        if (CheckRecord.getCount() > 0) {
            while (CheckRecord.moveToNext()) {
                int studID = CheckRecord.getInt(0);
                String studLastName = CheckRecord.getString(1);
                String studFirstName = CheckRecord.getString(2);
                String studBirthday = CheckRecord.getString(3);
                String studGender = CheckRecord.getString(4);
                String studAddress = CheckRecord.getString(5);
                String studContact = CheckRecord.getString(6);

                Student allStudent = new Student(studID, studLastName, studFirstName, studBirthday, studGender, studAddress, studContact);
                returnAllData.add(allStudent);
            }
        } else {

        }
        CheckRecord.close();
        db.close();
        return returnAllData;
    }


    public boolean DeleteRecord(Student student) {

        //Delete a record from the student table base on what the id is on the User input
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor strQuery = db.rawQuery("DELETE FROM " + TBL_STUDENT + " WHERE " + S_ID + " = " + student.getStudentID(), null);

        return strQuery.moveToFirst();
    }

    public boolean UpdateRecord(Student student) {
        //Updates a row according to the data inputted and which Id it has
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(S_ID, student.getStudentID());
        cv.put(S_LASTNAME, student.getLastName());
        cv.put(S_FIRSTNAME, student.getFirstName());
        cv.put(S_BIRTHDAY, student.getBirthday());
        cv.put(S_GENDER, student.getGender());
        cv.put(S_ADDRESS, student.getAddress());
        cv.put(S_CONTACT, student.getContact());


        db.update(TBL_STUDENT, cv, "ID=?", new String[]{String.valueOf(student.getStudentID())});
        return true;
    }
}


