package com.example.assignment;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalTime;

public class EditSchedule extends AppCompatActivity
{
    private EditText event_name;
    private TextView event_time, event_date;

    private LocalTime time;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);
        initWidgets();
        time = LocalTime.now();
        event_date.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
        event_time.setText("Time: " + CalendarUtils.formattedTime(time));
    }

    private void initWidgets()
    {
        event_name = findViewById(R.id.event_name);
        event_date = findViewById(R.id.event_date);
        event_time = findViewById(R.id.event_time);
    }

    public void saveEvent(View view)
    {
        String eventName = event_name.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}