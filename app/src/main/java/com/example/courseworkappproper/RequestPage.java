package com.example.courseworkappproper;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class RequestPage extends AppCompatActivity {
    private Button btnFirstdate, btnLastdate, b1;
    private MyDatabaseHelper myDatabaseHelper;
    private String startdate, enddate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_request_page);
        myDatabaseHelper = new MyDatabaseHelper(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        b1 = findViewById(R.id.ReqSubButton);
        btnFirstdate = findViewById(R.id.FirstDate);
        btnLastdate = findViewById(R.id.LastDate);

        btnFirstdate.setOnClickListener(new View.OnClickListener() { //Setting up the Starting date using Android calender functionality
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestPage.this, //Setting up the string conversion
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = year + "-" + (month + 1) + "-" + dayOfMonth; //Month has to be +1 as zero-indexed - formatted to match API
                                btnFirstdate.setText(date);
                            }
                        },
                        year, month, day);
                datePickerDialog.show(); //Display these as text on the button
            }
        });

        btnLastdate.setOnClickListener(new View.OnClickListener() { //Setting up the Ending date calender using Android calender functionality
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestPage.this, //Setting up the string conversion
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String date = year + "-" + (month + 1) + "-" + dayOfMonth; //Month has to be +1 as zero-indexed - formatted to match API
                                btnLastdate.setText(date);
                            }
                        },
                        year, month, day);
                datePickerDialog.show(); //Display these as text on the button
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
            }
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues cv = new ContentValues();
                cv.put("email", "Steven Hall");
                cv.put("start_date", btnFirstdate.getText().toString());
                cv.put("end_date", btnLastdate.getText().toString());
                cv.put("status", "pending");

                boolean isInserted = myDatabaseHelper.addReq(cv);
                if(isInserted){
                    Toast.makeText(RequestPage.this, "Request successfully submitted", Toast.LENGTH_SHORT).show();
                    makeNotification();
                }else {
                    Toast.makeText(RequestPage.this,"Request Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

        public void makeNotification() {
            String chanelID = "my_channel";
            NotificationCompat.Builder builder = new
                    NotificationCompat.Builder(this, chanelID);

            builder.setSmallIcon(R.drawable.companylogo)
                    .setContentTitle("My notification")
                    .setContentText("Holiday Request Confirmed!")
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            Intent intent = new Intent(this, landingpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("data", "data from main activity");

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = notificationManager.getNotificationChannel(chanelID);

                if (notificationChannel == null) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    new NotificationChannel(
                            chanelID, "My_Notification",
                            NotificationManager.IMPORTANCE_HIGH);
                    notificationChannel.setLightColor(Color.BLUE);
                    notificationChannel.enableVibration(true);
                    notificationManager.createNotificationChannel(notificationChannel);
                }
                notificationManager.notify(0, builder.build());
            }
        }


    }