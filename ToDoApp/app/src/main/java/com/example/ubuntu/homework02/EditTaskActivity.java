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
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class EditTaskActivity extends AppCompatActivity {
    private EditText taskName;
    private EditText date;
    private EditText time;
    private RadioGroup radioGroup;
    private RadioButton btn;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private TimePickerDialog.OnTimeSetListener timeSetListener;

    @Override
    protected void onStart() {
        super.onStart();

        Task task= (Task)getIntent().getSerializableExtra(MainActivity.NAME_KEY2);
        Log.d("name",task.getDate());
        Log.d("name",task.getPriority());
        Log.d("name",task.getTime());
        Log.d("name",task.getTitle());

        taskName.setText(task.getTitle());
        date.setText(task.getDate());
        time.setText(task.getTime());
        if(task.getPriority().contains("High")){
            btn= findViewById(R.id.high);
            btn.setChecked(true);
        }

        if(task.getPriority().contains("Medium")){
            btn= findViewById(R.id.medium);
            btn.setChecked(true);
        }

        if(task.getPriority().contains("Low")){
            btn= findViewById(R.id.low);
            btn.setChecked(true);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Edit Tasks");

        taskName=findViewById(R.id.title_edit);
        date= findViewById(R.id.date_edit);
        time= findViewById(R.id.time_edit);
        radioGroup= findViewById(R.id.radioGroup2);
        

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal= Calendar.getInstance();
                int hours= cal.get(Calendar.HOUR_OF_DAY);
                int min= cal.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog= new TimePickerDialog(
                        EditTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        timeSetListener,hours, min,false
                );
                timePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                timePickerDialog.show();
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
                        EditTaskActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        timeSetListener= new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                String status = "AM";

                if(i > 11)
                {
                    status = "PM";
                }
                int hour;

                if(i > 11){

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

        findViewById(R.id.btn_saving).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (taskName.getText().length() == 0) {
                    taskName.setError("Enter Title");


                }
                if (date.getText().length() == 0) {
                    date.setError("Enter date");
                    Toast.makeText(EditTaskActivity.this, " Enter date", Toast.LENGTH_SHORT).show();

                }
                if (time.getText().length() == 0) {

                    time.setError("");
                    Toast.makeText(EditTaskActivity.this, " Enter time", Toast.LENGTH_SHORT).show();


                }

                if (date.getText().length() != 0 && time.getText().length() != 0 && taskName.getText().length() != 0) {


                    Intent intent = new Intent();
                    String name = taskName.getText().toString();
                    String dateText = date.getText().toString();
                    String timeText = time.getText().toString();
                    int id = radioGroup.getCheckedRadioButtonId();
                    RadioButton btn = findViewById(id);
                    String k = btn.getText().toString();
                    Task task = new Task(name, dateText, timeText, k);

                    intent.putExtra(MainActivity.NAME_KEY2, task);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }
}
