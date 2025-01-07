package com.example.courseworkappproper;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class EmployeeAdapter extends CursorAdapter {
    public EmployeeAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_2, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor){
        TextView textView = (TextView) view.findViewById(android.R.id.text1);
        TextView textView2 = (TextView) view.findViewById(android.R.id.text2);
        String EmpLname = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.LASTNAME));
        String EmpFname = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.FIRSTNAME));
        textView.setText(EmpLname);
        textView2.setText(EmpFname);
    }
}