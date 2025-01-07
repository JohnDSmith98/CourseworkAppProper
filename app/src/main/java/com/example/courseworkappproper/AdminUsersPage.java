package com.example.courseworkappproper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.VolleyError;

import java.util.List;

public class AdminUsersPage extends AppCompatActivity {
    private EmployeeAdapter adapter;
    private MyDatabaseHelper dbHelper;
    private ListView EmployeeList;
    private Button newUser;

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

        newUser = findViewById(R.id.NewUserButton);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent z = new Intent(AdminUsersPage.this, EditPage.class);
                startActivity(z);
            }
        });

        EmployeeList = findViewById(R.id.EmployeeList);
        dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("users", null, null, null, null, null, null);
        Log.d("CursorCount", "cursor.getCount() = " + cursor.getCount());
        adapter = new EmployeeAdapter(this, cursor);
        EmployeeList.setAdapter(adapter);
        UpdateList();
    }

    private void UpdateList(){
        API.getAllEmployees(this, new API.EmpsRecieved() {
            @Override
            public void Success(List<Employee> employees) {
                dbHelper.clearList();

                for (Employee employee : employees){
                    DataModel dataModel = convertToDataModel(employee);
                    dbHelper.adduser(dataModel);
                }
                Refresh();
            }

            @Override
            public void Error(VolleyError error) {
                Toast.makeText(AdminUsersPage.this, "Failed to pull from API: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                Refresh();
            }
        });
    }

    private void Refresh(){
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query("users", null, null, null, null, null, null);
        adapter.changeCursor(cursor);
    }

    private DataModel convertToDataModel(Employee employee){
        return new DataModel
            (employee.getFirstname(),
            employee.getLastname(),
            employee.getDepartment(),
            employee.getEmail(),
            employee.getJoiningDate(),
            employee.getLeaves(),
            employee.getSalary(),
            "user",
            ""
        );
    }
}