package com.example.courseworkappproper;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdminUsersPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_users_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView EmployeeList = findViewById(R.id.EmployeeList);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("users", null, null, null, null, null, null);
        EmployeeAdapter adapter = new EmployeeAdapter(this, cursor);
        EmployeeList.setAdapter(adapter);
    }
}