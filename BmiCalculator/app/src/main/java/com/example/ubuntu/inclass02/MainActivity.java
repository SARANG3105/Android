package com.example.ubuntu.inclass02;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    private EditText age;
    private EditText weight;
    private EditText feet;
    private EditText inches;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn= findViewById(R.id.button_calculate);
        btn.setOnClickListener(this);
        age= findViewById(R.id.editText_years);
        weight= findViewById(R.id.editText_pounds);
        feet= findViewById(R.id.editText_feet);
        inches= findViewById(R.id.editText_inches);

    }

    @Override
    public void onClick(View view) {
        try{
            DecimalFormat numberFormat = new DecimalFormat("#.00");
        int a= Integer.parseInt(age.getText().toString());
        float w= Float.parseFloat(weight.getText().toString());
        float f= Float.parseFloat(feet.getText().toString());
        float i= Float.parseFloat(inches.getText().toString());
        TextView res= findViewById(R.id.Result);
        TextView bmi_final= findViewById(R.id.BMI);
        TextView color= findViewById(R.id.color);
            TextView normal_bmi= findViewById(R.id.NormalBMI);
            TextView lastline= findViewById(R.id.lastLine);
        if(a<18  ) {
            age.setError("Age should be >18");
        }else{
            float h= (f*12)+i;

            float bmi= 703*(w/(h*h));
            float wt_req;
            if(bmi<18.5) {
                color.setTextColor(Color.RED);
                res.setText("Result");
                bmi_final.setText("BMI = " + numberFormat.format(bmi));
                color.setText("(Underweight)");
                normal_bmi.setText("Normal BMI Range: 18.5-25");
                wt_req = (float)(18.5*h*h)/703;
                lastline.setText("You will need to gain "+numberFormat.format((wt_req-w))+" lbs to reach a BMI of 18.5");

            }else if(bmi>=18.5 && bmi<25){
                color.setTextColor(Color.GREEN);
                res.setText("Result");
                bmi_final.setText("BMI = " + numberFormat.format(bmi));
                color.setText("(Normal)");
                normal_bmi.setText("Normal BMI Range: 18.5-25");

                lastline.setText("Keep up the good work !!");
            }else if(bmi>=25 && bmi<30){
                color.setTextColor(Color.rgb(255,165,0));
                res.setText("Result");
                bmi_final.setText("BMI = " + numberFormat.format(bmi));
                color.setText("(Overweight)");
                normal_bmi.setText("Normal BMI Range: 18.5-25");
                wt_req = (float)(25*h*h)/703;
                lastline.setText("You will need to lose "+numberFormat.format((w-wt_req))+" lbs to reach a BMI of 25");
            }else if(bmi>=30){
                color.setTextColor(Color.MAGENTA);
                res.setText("Result");
                bmi_final.setText("BMI = " + numberFormat.format(bmi));
                color.setText("(Obese)");
                normal_bmi.setText("Normal BMI Range: 18.5-25");
                wt_req = (float)(25*h*h)/703;
                lastline.setText("You will need to lose "+numberFormat.format((w-wt_req))+" lbs to "+"\n"+" reach a BMI of 25");
            }

        }


        }catch (NumberFormatException e){
            if(age.getText().toString().length()==0){
                age.setError("age is required");
            }else if(weight.getText().toString().length()==0){
                weight.setError("weight is required");

            }else if(feet.getText().toString().length()==0){
                feet.setError("Height in feet is required");

            }else if( inches.getText().toString().length()==0){
                inches.setError("Height in inches is required");
            }
        }
    }
}
