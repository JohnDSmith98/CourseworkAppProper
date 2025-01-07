package com.example.courseworkappproper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminApproval extends AppCompatActivity {
    private RequestAdapter requestAdapter;
    private MyDatabaseHelper dbHelper;
    private ListView ReqList;
    private Button DenyBtn, ApproveBtn;
    private String requestedID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_approval);

        dbHelper = new MyDatabaseHelper(this);
        ReqList = findViewById(R.id.RequestsList);
        ApproveBtn = findViewById(R.id.ApproveHolButton);
        DenyBtn = findViewById(R.id.DenyHolButton);
        UpdateReqs();

        ReqList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) requestAdapter.getItem(position);
                requestedID = String.valueOf(cursor.getInt(cursor.getColumnIndexOrThrow("_id")));
            }
        });

        DenyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestedID != null){
                    denyReq();
                    requestedID = null;
                    UpdateReqs();
                }else{
                    Toast.makeText(AdminApproval.this, "Error: No request is selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ApproveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(requestedID != null){
                    appReq();
                    requestedID = null;
                    UpdateReqs();
                }else {
                    Toast.makeText(AdminApproval.this, "Error: No request is selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void UpdateReqs(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
       // Cursor cursor = db.query("requests", null, null, null, null, null, null, null);
        Cursor pendCursor = dbHelper.reqByStatus("pending");
        RequestAdapter pendingAdapter = new RequestAdapter(this, pendCursor);
        ReqList.setAdapter(pendingAdapter);
        if (requestAdapter == null){
            requestAdapter = new RequestAdapter(this, pendCursor);
            ReqList.setAdapter(requestAdapter);
        }else{
            requestAdapter.changeCursor(pendCursor);
        }
    }

    private void denyReq(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("requests", "_id = ?", new String[]{requestedID});

        if(rowsDeleted > 0){
            Toast.makeText(this, "Request denied", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private void appReq(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("status", "approved");

        int rowsUpdated = db.update("requests", cv, "_id = ?", new String[]{requestedID});

        if (rowsUpdated > 0) {
            Toast.makeText(this, "Request successfully approved", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "There was an error approving this request", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }
}