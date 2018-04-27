package com.example.ubuntu.inclass03;
/*GROUP 5
Sarangdeep Singh
Ishan Agarwal

 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class DisplayActivity extends Activity implements View.OnClickListener {
    public static int REQ_CODE = 1000;
    final static String NAME_KEY = "name";
    final static String NAME_KEY_ONE = "name_one";
    final static String NAME_KEY_TWO = "name_two";
    final static String NAME_KEY3 = "name_three";

    private TextView textView11;
    private TextView textView12;
    private TextView textView13;
    private TextView textView14;
    private ImageButton imageButton1;
    private ImageButton imageButton2;
    private ImageButton imageButton3;
    private ImageButton imageButton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        textView11 = findViewById(R.id.textView11);
        textView12 = findViewById(R.id.textView12);
        textView13 = findViewById(R.id.textView13);
        textView14 = findViewById(R.id.textView14);
        imageButton1 = findViewById(R.id.imageButton6);
        imageButton1.setOnClickListener(this);
        imageButton2 = findViewById(R.id.imageButton7);
        imageButton2.setOnClickListener(this);
        imageButton3 = findViewById(R.id.imageButton8);
        imageButton3.setOnClickListener(this);
        imageButton4 = findViewById(R.id.imageButton3);
        imageButton4.setOnClickListener(this);
        setTitle("Display Activity");
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(MainActivity.NAME_KEY)) {
                Student student = (Student) getIntent().getExtras().getSerializable(MainActivity.NAME_KEY);
                textView14.setText(student.getMood() + " % Positive");
                textView11.setText(student.getName());
                textView12.setText(student.getEmail());
                textView13.setText(student.getDepartment());
            }
        }
    }

    @Override
    public void onClick(View view) {
        String n = textView11.getText().toString();
        String e = textView12.getText().toString();


        String val = textView13.getText().toString();
        String f = textView14.getText().toString();
        String[] d = f.split(" ");
        String k = d[0];
        Log.d("sss", f);
        Student student = new Student(n, e, val, k);
        if (view.getId() == imageButton1.getId()) {
            Intent intent = new Intent("com.example.ubuntu.inclass03.intent.action.VIEW");

            intent.putExtra(NAME_KEY, student);
            startActivityForResult(intent, REQ_CODE);
        } else if (view.getId() == imageButton2.getId()) {
            Intent intent = new Intent("com.example.ubuntu.inclass03.intent.action.VIEW");

            intent.putExtra(NAME_KEY_ONE, student);
            startActivityForResult(intent, REQ_CODE);
        } else if (view.getId() == imageButton3.getId()) {
            Intent intent = new Intent("com.example.ubuntu.inclass03.intent.action.VIEW");

            intent.putExtra(NAME_KEY_TWO, student);
            startActivityForResult(intent, REQ_CODE);
        } else {
            Intent intent = new Intent("com.example.ubuntu.inclass03.intent.action.VIEW");

            intent.putExtra(NAME_KEY3, student);
            startActivityForResult(intent, REQ_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                if (data.getExtras().containsKey(NAME_KEY)) {

                    Student student = (Student) data.getExtras().getSerializable(NAME_KEY);
                    if (student != null && student.getName() != null) {
                        Log.d("name", student.getName());
                        textView11.setText(student.getName());
                    }
                }
                if (data.getExtras().containsKey(NAME_KEY_ONE)) {

                    Student student = (Student) data.getExtras().getSerializable(NAME_KEY_ONE);
                    if (student != null && student.getEmail() != null) {

                        textView12.setText(student.getEmail());
                    }
                }
                if (data.getExtras().containsKey(NAME_KEY3)) {
                    Student student = (Student) data.getExtras().getSerializable(NAME_KEY3);
                    if (student != null && student.getMood() != null) {

                        textView14.setText(student.getMood());
                    }
                }
                if (data.getExtras().containsKey(NAME_KEY_TWO)) {
                    Student student = (Student) data.getExtras().getSerializable(NAME_KEY_TWO);
                    if (student != null && student.getDepartment() != null) {

                        textView13.setText(student.getDepartment());
                    }

                }
            }

        }
    }
}

