package com.example.ubuntu.inclass03;
/*GROUP 5
Sarangdeep Singh
Ishan Agarwal

 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    final static String NAME_KEY = "name";
    private EditText name;
    private EditText email;
    private RadioGroup dept;
    private RadioButton selcted;
    private SeekBar mood;
    private Button submit;
    private int value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Main Activity");
        submit = findViewById(R.id.btn_mainSubmit);
        submit.setOnClickListener(this);
        name = findViewById(R.id.name1);
        email = findViewById(R.id.email_edit);
        dept = findViewById(R.id.dept_edit_eg);
        dept.check(R.id.rb_mainSIS);
        mood = findViewById(R.id.seekbar_edit);
        mood.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                value = mood.getProgress();
            }
        });


    }

    @Override
    public void onClick(View view) {

        String n = name.getText().toString();
        String e = email.getText().toString();
        int dep = dept.getCheckedRadioButtonId();
        selcted = (RadioButton) findViewById(dep);
        String val = selcted.getText().toString();
        String f = Integer.toString(value);
        Student student = new Student(n, e, val, f);
        if (n.length()==0) {
            name.setError("enter name");
        }else if(e.length()==0){
            email.setError("Enter Email");
        } else if(!e.contains("@") || !e.contains("uncc.edu")){
            email.setError("Enter Full Student Email");
        }else if(value==0){
            Toast.makeText(this, "What happened to your mood?", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, DisplayActivity.class);


            intent.putExtra(NAME_KEY, student);
            startActivity(intent);

        }
    }
}
