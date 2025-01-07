package com.example.courseworkappproper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HolidayManagement extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_holiday_management);
        ListView list = findViewById(R.id.ApprovedList);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        Cursor appCursor = dbHelper.reqByStatus("approved");
        Cursor pendCursor = dbHelper.reqByStatus("pending");
        RequestAdapter appAdapter = new RequestAdapter(this, appCursor);
        list.setAdapter(appAdapter);
        ListView list2 = findViewById(R.id.CurrentReqList);
        RequestAdapter pendingAdapter = new RequestAdapter(this, pendCursor);
        list2.setAdapter(pendingAdapter);
        Button req = findViewById(R.id.ReqButton);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ReqIntent=new Intent(HolidayManagement.this, RequestPage.class);
                startActivity(ReqIntent);
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}