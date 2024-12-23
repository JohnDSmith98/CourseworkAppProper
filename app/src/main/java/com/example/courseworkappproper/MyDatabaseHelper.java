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
    public static final String ID = "_id";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String DEPARTMENT = "department";
    public static final String EMAIL = "email";
    public static final String JOININGDATE = "joiningdate";
    public static final String LEAVES = "leaves";
    public static final String SALARY = "salary";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, "testuser.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
            "CREATE TABLE " + USERS + " (" +
                ID + " INTEGER PRIMARY KEY, " +
                    FIRSTNAME + " TEXT, " +
                    LASTNAME + " TEXT, " +
                    DEPARTMENT + " TEXT, " +
                    EMAIL + " TEXT, " +
                    JOININGDATE + " TEXT, " +
                    LEAVES + " INTEGER," +
                    SALARY + " REAL)";


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

        cv.put(FIRSTNAME, dataModel.getFirstname());
        cv.put(LASTNAME, dataModel.getLastname());
        cv.put(DEPARTMENT, dataModel.getDepartment());
        cv.put(EMAIL, dataModel.getEmail());
        cv.put(JOININGDATE, dataModel.getJoiningdate());
        cv.put(LEAVES, dataModel.getLeaves());
        cv.put(SALARY, dataModel.getSalary());

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
                String dep = cursor.getString(3);
                String email = cursor.getString(4);
                String joindate = cursor.getString(5);
                Integer leaves = cursor.getInt(6);
                Double salary = cursor.getDouble(7);

                DataModel dataModel = new DataModel(fname, lname, dep, email, joindate, leaves, salary);
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
        String query = "DELETE FROM " + USERS + " WHERE " + EMAIL + " = '" + dataModel.getEmail() + "'";

        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()){
            return false;
        }else{
            return true;
        }

    }
}