package com.example.abhishek.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TimePicker;

import java.security.PrivateKey;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AddorEditActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener
        , TimePickerDialog.OnTimeSetListener {

    private int year = 0, month = 0, day = 0, hour = 0, minite = 0;
    private Switch reminderSwitch;
    private EditText titleEdittext, descriptionEdittext;
    private String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addor_edit);

        titleEdittext = findViewById(R.id.editactivity_title_edittext);
        descriptionEdittext = findViewById(R.id.editactivity_description_edittext);
        reminderSwitch = findViewById(R.id.editactivity_reminder_switch);

        reminderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    findViewById(R.id.editactivity_date_edittext).setClickable(true);
                    findViewById(R.id.editactivity_time_edittext).setClickable(true);
                } else {
                    findViewById(R.id.editactivity_date_edittext).setClickable(false);
                    findViewById(R.id.editactivity_time_edittext).setClickable(false);
                }
            }
        });
    }

    public void onDatePickerClicked(View view) {
        Calendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONDAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, AddorEditActivity.this, year, month, day);
        datePickerDialog.show();
    }

    public void onTimePickerClicked(View view) {
        Calendar calendar = new GregorianCalendar();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minite = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, AddorEditActivity.this, hour, minite, false);
        timePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        this.year = year;
        this.month = month;
        this.day = dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        this.hour = hourOfDay;
        this.minite = minute;
    }

    public void saveReminder(View view) {
        SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        editor.putInt("year", year);
        editor.putInt("month", month);
        editor.putInt("day", day);
        editor.putInt("hour", hour);
        editor.putInt("minute", minite);
        editor.commit();
        finish();
    }
}
