package com.example.courseworkappproper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    public static final String ROLE = "role";
    public static final String PASS = "pass";

    public MyDatabaseHelper(@Nullable Context context) {
        super(context, "testuser.db", null, 10);
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
                LEAVES + " INTEGER, " +
                SALARY + " REAL, " +
                ROLE + " TEXT DEFAULT 'user', " +
                PASS + " TEXT" + ")";

        String createReqTable =
            "CREATE TABLE " + "requests " + " (" +
                 ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                 EMAIL + " TEXT, " +
                 "start_date TEXT, " +
                 "end_date TEXT, " +
                 "status TEXT DEFAULT 'pending')";

        db.execSQL(createTable);
        db.execSQL(createReqTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTable = "DROP TABLE IF EXISTS " + USERS;
        db.execSQL(dropTable);
        db.execSQL("DROP TABLE IF EXISTS requests");
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
        cv.put(ROLE, dataModel.getRole());
        cv.put(PASS, dataModel.getPass());

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
                String role = cursor.getString(8);
                String pass = cursor.getString(9);

                DataModel dataModel = new DataModel(fname, lname, dep, email, joindate, leaves, salary, role, pass);
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

    public boolean addReq(ContentValues cv){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert("requests", null, cv);
        db.close();
        if(result == -1){
            Log.d("DBInsert", "Request added successfully: " + cv.toString());
            return false;
        }else{
            Log.e("DBInsert", "Failed to add request: " + cv.toString());
            return true;
        }
    }

    public boolean updateReq(int id, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", status);

        int updatedRows = db.update("requests", cv, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return updatedRows > 0;
    }

    public boolean authUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + USERS + " WHERE " + EMAIL + " = ? AND " + PASS + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});//Using placeholders to prevent SQL injection

        boolean isAuthenticated = cursor.getCount() > 0;//If the count is greater than 0, the combination exists

        cursor.close();
        db.close();

        return isAuthenticated;
    }

    public void clearList(){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("users", null, null);
        db.close();
    }

    public Cursor reqByStatus(String status){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query("requests", null, "status = ?", new String[]{status}, null, null, null);
    }
}