package com.example.ubuntu.homework02;
/*
Homework 02
Sarangdeep Singh
Ishan Agarwal
Group 05
 */

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateTaskActivity extends AppCompatActivity {

    private static String TAG ="CreateTaskActivity";
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;
    private EditText title;
    private EditText date;
    private EditText time;
    private RadioGroup radioGroup;
    private RadioButton sel_radio;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Create Tasks");

        title= findViewById(R.id.create_title);
        radioGroup= findViewById(R.id.radioGroup);
        date= findViewById(R.id.create_date);
        time= findViewById(R.id.create_time);

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal= Calendar.getInstance();
                int hours= cal.get(Calendar.HOUR_OF_DAY);
                int min= cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        CreateTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListener,hours, min,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
                time.setError(null);
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal= Calendar.getInstance();
                int year= cal.get(Calendar.YEAR);
                int  month= cal.get(Calendar.MONTH);
                int day= cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog= new DatePickerDialog(
                        CreateTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
                    date.setError(null);
            }
        });

        timeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String status = "AM";

                if(i > 12)
                {
                    status = "PM";
                }
                int hour;

                if(i > 12){

                    hour = i - 12;
                }
                else {
                    hour = i;
                }
                String mm= String.format("%02d",i1);
                String hh= String.format("%02d",hour);
                String tim= hh+":"+mm+" "+status;
                time.setText(tim);

            }
        };
        dateSetListener= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    month= month+1;
                    String m= String.format("%02d",month);
                    String d= String.format("%02d",day);
                    String dat = m+"/"+d+"/"+year;
                    date.setText(dat);

            }
        };


        findViewById(R.id.btn_save_create).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(title.getText().length()==0 ){
                    title.setError("Enter Title");
                    // Toast.makeText(CreateTaskActivity.this, " Enter title", Toast.LENGTH_LONG).show();

                }
                if(date.getText().length()==0 ){
                    date.setError("Enter date");
                    Toast.makeText(CreateTaskActivity.this, " Enter date", Toast.LENGTH_LONG).show();

                }
                 if(time.getText().length()==0 ){

                         time.setError("");
                     Toast.makeText(CreateTaskActivity.this, " Enter time", Toast.LENGTH_LONG).show();


                }

                if(date.getText().length()!=0 && time.getText().length()!=0 && title.getText().length()!=0 ) {


                    int id = radioGroup.getCheckedRadioButtonId();
                    sel_radio = findViewById(id);
                    Intent intent = new Intent();
                    Task task = new Task();
                    task.setTitle(title.getText().toString());
                    task.setDate(date.getText().toString());
                    task.setTime(time.getText().toString());
                    task.setPriority(sel_radio.getText().toString());
                    intent.putExtra(MainActivity.NAME_KEY, task);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }


        });



    }
}
