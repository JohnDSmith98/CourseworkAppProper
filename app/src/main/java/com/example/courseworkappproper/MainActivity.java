package com.example.courseworkappproper;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button log, dog;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
       // DataModel testAdmin = new DataModel("Test", "Admin", "Management", "test@admin.com", "01/01/2001", 0, 100000.0, "admin", "test123");
       // DataModel a = new DataModel("Test", "Admin", "Management", "a", "01/01/2001", 0, 100000.0, "admin", "a");
       // dbHelper.adduser(testAdmin);
       // dbHelper.adduser(a);
        log=findViewById(R.id.loginbutton);
        dog = findViewById(R.id.button5);
        user = findViewById(R.id.usernamebox);
        pass = findViewById(R.id.passwordbox);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = user.getText().toString();
                String passw = pass.getText().toString();

                if (usern.isEmpty() || passw.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDatabaseHelper dbhelper = new MyDatabaseHelper(getApplicationContext());
                boolean auth = dbhelper.authUser(usern, passw);
                if (auth) {
                    Intent z = new Intent(MainActivity.this, landingpage.class);
                    startActivity(z);
                    finish();
                }
            }
        });

        dog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MainActivity.this, AdminUsersPage.class);
                startActivity(a);
            }
        });



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}