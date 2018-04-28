package com.example.ubuntu.homework01;
/*
Homework-01
Sarangdeep Singh
Group 05
 */
import android.support.v7.app.ActionBar;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private EditText bill;
    private RadioGroup radioGroup;
    private RadioButton selected;
    private SeekBar seekBar;
    private int value;
    private TextView seekbar_percent;
    private TextView tip;
    private TextView bill_final;
    private float b;
    private float t;
    private RadioButton cust;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("Tip Calculator");

        seekbar_percent = findViewById(R.id.textView2);
        bill = findViewById(R.id.editText);
        radioGroup = findViewById(R.id.radioGroup);
        tip= findViewById(R.id.textView_tip);
        seekBar = findViewById(R.id.seekBar);
        value = seekBar.getProgress();
        seekbar_percent.setText(Integer.toString(value));
        bill_final = findViewById(R.id.textView_total);
        cust= findViewById(R.id.radioButton4);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                    int sel = radioGroup.getCheckedRadioButtonId();
                    selected = findViewById(sel);
                if(selected.getId()!=cust.getId()) {
                    String val = selected.getText().toString();
                    String[] num = val.split("%");
                    t = Float.parseFloat(num[0]);
                    tip.setText(Float.toString(t));
                    float amount = b + (b * (t / 100));
                    bill_final.setText(Float.toString(amount));
                }else{
                    float prog=seekBar.getProgress();
                    tip.setText(Float.toString(prog));
                    float amount = b + (b * (prog / 100));
                    bill_final.setText(Float.toString(amount));
                }
            }
        });



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean bo) {
                if(bill.getText().length()!=0 || bill.getText().toString()!=null) {
                    seekbar_percent.setText(Integer.toString(i));
                    cust.setChecked(true);

                }else if(bill.getText().length() == 0|| bill.getText()==null){
                    bill_final.setText("0.00");

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                if(bill.getText().length() == 0|| bill.getText()==null) {
                    bill_final.setText("0.00");
                }
                }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (bill.getText().length() != 0) {
                    value = seekBar.getProgress();
                    seekbar_percent.setText(Integer.toString(value));
                    float prog = seekBar.getProgress();
                    tip.setText(Float.toString(prog));
                    float amount = b + (b * (prog / 100));
                    bill_final.setText(Float.toString(amount));
                }else if(bill.getText().length() == 0|| bill.getText()==null){
                    bill_final.setText("0.00");
                }
            }
        });

        bill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(bill.getText().length() == 0|| bill.getText()==null) {
                    bill_final.setText("0.00");
                    tip.setText("0.00");
                }


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (bill.getText().length() != 0) {
                        b = Float.parseFloat(bill.getText().toString());
                            int sel = radioGroup.getCheckedRadioButtonId();
                            selected = findViewById(sel);
                        if(selected.getId()!=cust.getId()) {
                            String val = selected.getText().toString();
                            String[] num = val.split("%");
                            t = Float.parseFloat(num[0]);
                            tip.setText(Float.toString(t));
                            float amount = b + (b * (t / 100));
                            bill_final.setText(Float.toString(amount));
                        }else{
                            float prog=seekBar.getProgress();
                            tip.setText(Float.toString(prog));
                            float amount = b + (b * (prog / 100));
                            bill_final.setText(Float.toString(amount));
                        }
                    }else if(bill.getText().length() == 0|| bill.getText()==null){
                        bill_final.setText("0.00");

                    }
                }

            @Override
            public void afterTextChanged(Editable editable) {
            if (bill.getText().length() == 0 || bill.getText()==null) {
                bill.setError("Enter Bill Total");
                bill_final.setText("0.00");

             }
            }
        });

        findViewById(R.id.btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        finish();
            }
        });
    }
}
