package com.example.courseworkappproper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    public static final String USERS = "users";
    public static final String FNAME = "firstname";
    public static final String LNAME = "lastname";
    public static final String MNAME = "middlenames";
    public static final String GENDER = "gender";
    public static final String DOB = "dateofbirth";
    public static final String JOINDATE = "joindate";
    public static final String ID = "id";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, "testuser.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
            "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FNAME + " TEXT, " +
                    LNAME + " TEXT, " +
                    MNAME + " TEXT, " +
                    GENDER + " TEXT, " +
                    DOB + " TEXT, " +
                    JOINDATE + " TEXT)";


        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + USERS;
        db.execSQL(dropTable);
        onCreate(db);

    }

    public boolean adduser(DataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(FNAME, dataModel.getFName());
        cv.put(LNAME, dataModel.getLname());
        cv.put(MNAME, dataModel.getMname());
        cv.put(GENDER, dataModel.getGender());
        cv.put(DOB, dataModel.getDOB());
        cv.put(JOINDATE, dataModel.getJoin());

        long result = db.insert(USERS, null, cv);
        if(result == -1){
            return false;
        }else{
            return true;
        }

    }

    public List<DataModel> getAllUsers(){
        List<DataModel> outputList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS;

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String fname = cursor.getString(1);
                String lname = cursor.getString(2);
                String mname = cursor.getString(3);
                String gender = cursor.getString(4);
                String dob = cursor.getString(5);
                String joindate = cursor.getString(6);

                DataModel dataModel = new DataModel(fname, lname, mname, gender, dob, joindate);
                outputList.add(dataModel);

            }while(cursor.moveToNext());

        }else {
        }

        cursor.close();
        db.close();

        return outputList;
    }

    public boolean deleteUser(DataModel dataModel){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + USERS + " WHERE " + LNAME + " = '" + dataModel.getLname() + "'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }

    }
}