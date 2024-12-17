package com.example.courseworkappproper;

import android.content.Intent;
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

public class EditPage extends AppCompatActivity {

    EditText edtFname, edtLname, edtMname;
    Button btnDob, btnGender, btnJoindate, btnSaveNew;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_page);

        myDatabaseHelper = new MyDatabaseHelper(this);

        edtFname = findViewById(R.id.editTextText);
        edtLname = findViewById(R.id.editTextText2);
        edtMname = findViewById(R.id.editTextText3);
        btnDob = findViewById(R.id.button6);
        btnGender = findViewById(R.id.button5);
        btnJoindate = findViewById(R.id.button7);
        btnSaveNew = findViewById(R.id.button3);

        btnSaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFname = edtFname.getText().toString();
                String strLname = edtLname.getText().toString();
                String strMname = edtMname.getText().toString();

                if (!strFname.isEmpty() && !strLname.isEmpty()) {
                    DataModel user = new DataModel(strFname, strLname, strMname, "", "", "");
                    boolean isInserted = myDatabaseHelper.adduser(user);

                    if (isInserted) {
                        Toast.makeText(EditPage.this, "User added successfully", Toast.LENGTH_SHORT).show();
                    }
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