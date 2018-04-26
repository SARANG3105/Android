package com.example.ubuntu.venueapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static final String STATE= "state";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Log.d("date",dateFormat.format(date)); //2016/11/16 12:08:43


        findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Spinner spinner = (Spinner)findViewById(R.id.spinner);
                final String text = spinner.getSelectedItem().toString();
                Intent intent= new Intent(MainActivity.this, VenueActivity.class);
                intent.putExtra(STATE, text);
                startActivity(intent);
            }
        });
    }
}
