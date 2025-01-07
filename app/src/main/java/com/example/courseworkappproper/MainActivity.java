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
    private Button log, dog, fog; //Initialising the buttons and edit-text fields needed for log in
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase(); //Setting up a database to be created on launch - won't recreate an existing one of current version
        DataModel a = new DataModel("Test", "Admin", "Management", "a", "01/01/2001", 0, 100000.0, "admin", "a");
        dbHelper.adduser(a); //Adds the testing Admin user to the database on launch
        log=findViewById(R.id.loginbutton);
        user = findViewById(R.id.usernamebox);
        pass = findViewById(R.id.passwordbox);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usern = user.getText().toString();
                String passw = pass.getText().toString();

                if (usern.isEmpty() || passw.isEmpty()) { //Validating that both username and password fields have entries
                    Toast.makeText(getApplicationContext(), "Please enter both username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyDatabaseHelper dbhelper = new MyDatabaseHelper(getApplicationContext()); //Validating that the email/password combination exists
                boolean auth = dbhelper.authUser(usern, passw);
                if (auth) {
                    Intent z = new Intent(MainActivity.this, landingpage.class);
                    startActivity(z);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(), "Username or password incorrect", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}