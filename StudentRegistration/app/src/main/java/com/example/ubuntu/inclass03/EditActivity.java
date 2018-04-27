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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

public class EditActivity extends Activity implements View.OnClickListener {

    private EditText name;
    private EditText email;
    private RadioGroup dept;
    private RadioButton selcted;
    private SeekBar mood;
    private Button submit;
    private int value;
    private TextView dpt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        setTitle("Edit Activity");

        dpt = findViewById(R.id.textview_dpt);
        dpt.setVisibility(View.INVISIBLE);
        submit = findViewById(R.id.edit_btnsave);
        submit.setOnClickListener(this);

        name = findViewById(R.id.name1);
        name.setVisibility(View.INVISIBLE);
        email = findViewById(R.id.email_edit);
        email.setVisibility(View.INVISIBLE);
        dept = findViewById(R.id.dept_edit_eg);
        dept.setVisibility(View.INVISIBLE);
        mood = findViewById(R.id.seekbar_edit);
        mood.setVisibility(View.INVISIBLE);
        mood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                value=mood.getProgress();

            }


        });
        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY)) {
                Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY);
                String n = name.getText().toString();
                name.setVisibility(View.VISIBLE);
                name.setText(student.getName());
              /*  Intent i = new Intent();
                i.putExtra(DisplayActivity.NAME_KEY, n);
                setResult(RESULT_OK, i);*/
            } else if (getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY_ONE)) {
                Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY_ONE);
                String e = email.getText().toString();
                Log.d("demo", "s");
                email.setVisibility(View.VISIBLE);
                email.setText(student.getEmail());
               /* Intent i = new Intent();
                i.putExtra(DisplayActivity.NAME_KEY_ONE, e);
                setResult(RESULT_OK, i);*/
            } else if (getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY_TWO)) {
                Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY_TWO);
                dept.setVisibility(View.VISIBLE);
                dpt.setVisibility(View.VISIBLE);
            } else if (getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY3)) {
                Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY3);
                mood.setProgress(Integer.parseInt(student.getMood()));
                mood.setVisibility(View.VISIBLE);
            }


        }
    }

    @Override
    public void onClick(View view) {
        if (getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY)) {
            Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY);
            if (student != null && student.getName() != null) {
            String n = name.getText().toString();

            student.setName(n);

            Intent i = new Intent();
            i.putExtra(DisplayActivity.NAME_KEY, student);
            setResult(RESULT_OK, i);


        }}if(getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY_ONE)){
            Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY_ONE);
                if (student != null && student.getEmail() != null) {
            String e = email.getText().toString();

            student.setEmail(e);
            Log.d("name",student.getEmail());
            Intent i = new Intent();
            i.putExtra(DisplayActivity.NAME_KEY_ONE, student);
            setResult(RESULT_OK, i);
        }}if(getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY3)) {

            Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY3);
            if (student != null && student.getMood() != null) {
                String pb = Integer.toString(value);
                student.setMood(pb + " % Positive");
                Intent i = new Intent();
                i.putExtra(DisplayActivity.NAME_KEY3, student);
                setResult(RESULT_OK, i);

            }
        }if(getIntent().getExtras().containsKey(DisplayActivity.NAME_KEY_TWO)){
            Student student = (Student) getIntent().getExtras().getSerializable(DisplayActivity.NAME_KEY_TWO);
            if (student != null && student.getDepartment() != null) {
                int dep = dept.getCheckedRadioButtonId();


                selcted = (RadioButton) findViewById(dep);
                String val = selcted.getText().toString();
                student.setDepartment(val);
                Intent i = new Intent();
                i.putExtra(DisplayActivity.NAME_KEY_TWO, student);
                setResult(RESULT_OK, i);

            }
        }
        finish();


    }

}