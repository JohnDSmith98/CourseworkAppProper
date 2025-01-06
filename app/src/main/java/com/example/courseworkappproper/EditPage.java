package com.example.courseworkappproper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.VolleyError;

import java.util.Calendar;

public class EditPage extends AppCompatActivity {

    EditText edtFname, edtLname, edtDep, edtEmail, edtLeave, edtPass;
    Button btnJoindate, btnSaveNew;
    MyDatabaseHelper myDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_page);

        myDatabaseHelper = new MyDatabaseHelper(this);

        edtFname = findViewById(R.id.editTextText);
        edtLname = findViewById(R.id.editTextText2);
        edtDep = findViewById(R.id.editTextText3);
        edtLeave = findViewById(R.id.editTextText4);
        edtEmail = findViewById(R.id.editTextTextEmailAddress);
        btnJoindate = findViewById(R.id.button7);
        btnSaveNew = findViewById(R.id.button3);
        edtPass =findViewById(R.id.editTextText5);

        btnSaveNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strFname = edtFname.getText().toString();
                String strLname = edtLname.getText().toString();
                String strDep = edtDep.getText().toString();
                String strEmail = edtEmail.getText().toString();
                String strJoinD = btnJoindate.getText().toString();
                String strPass = edtPass.getText().toString();

                if (!strFname.isEmpty() && !strLname.isEmpty()) {
                    DataModel user = new DataModel(strFname, strLname, strDep, strEmail, strJoinD, 0, 35000.0, "", strPass);
                    boolean isInserted = myDatabaseHelper.adduser(user);

                    if (isInserted) {
                        Toast.makeText(EditPage.this, "User added locally successfully", Toast.LENGTH_SHORT).show();
                    }

                    Employee emp = new Employee();
                    emp.setFirstname(strFname);
                    emp.setLastname(strLname);
                    emp.setDepartment(strDep);
                    emp.setEmail(strEmail);
                    emp.setSalary(35000);
                    emp.setLeaves(0);
                    emp.setJoiningDate(strJoinD);

                    API.addEmployee(EditPage.this, emp, new API.OnResponse() {
                        @Override
                        public void Success(String message) {
                            Toast.makeText(EditPage.this, message, Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void Error(VolleyError error) {
                            Toast.makeText(EditPage.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        btnJoindate.setOnClickListener(new View.OnClickListener() { //Setting up the join date calender
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(EditPage.this, //Setting up the string conversion
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = year + "-" + (month + 1) + "-" + dayOfMonth; //Month has to be +1 as zero-indexed
                                btnJoindate.setText(date);
                            }
                        },
                        year, month, day);
                datePickerDialog.show(); //Display these as text on the button
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}