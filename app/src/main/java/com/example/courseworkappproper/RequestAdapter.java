package com.example.courseworkappproper;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class RequestAdapter extends CursorAdapter {
    public RequestAdapter(Context context, Cursor cursor){
        super(context,cursor,0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent){
        return LayoutInflater.from(context).inflate(R.layout.list_item_req, parent,false);
    }

    public void bindView(View view, Context context, Cursor cursor){
        TextView textView = view.findViewById(R.id.tvName);
        TextView textView2 = view.findViewById(R.id.tvStartDate);
        TextView textView3 = view.findViewById(R.id.tvEndDate);
        String email = cursor.getString(cursor.getColumnIndexOrThrow("email"));
        String start = cursor.getString(cursor.getColumnIndexOrThrow("start_date"));
        String end = cursor.getString(cursor.getColumnIndexOrThrow("end_date"));
        textView.setText(email);
        textView2.setText(start);
        textView3.setText(end);
    }
}
