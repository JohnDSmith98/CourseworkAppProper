package com.example.courseworkappproper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class landingpage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_landingpage);
       Button holiday=findViewById(R.id.HolidayButton);
       Button settings=findViewById(R.id.settings);
       Button edit=findViewById(R.id.EditButton);
       Button reqs = findViewById(R.id.ReqsBtn);
       Button users = findViewById(R.id.UserBtn);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landingpage.this, SettingsPage.class);
                startActivity(intent);
            }
        });
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hols = new Intent(landingpage.this, HolidayManagement.class);
                startActivity(hols);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditP = new Intent(landingpage.this, EditPage.class);
                startActivity(EditP);
            }
        });

        reqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(landingpage.this, AdminApproval.class);
                startActivity(a);
            }
        });

        users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(landingpage.this, AdminUsersPage.class);
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