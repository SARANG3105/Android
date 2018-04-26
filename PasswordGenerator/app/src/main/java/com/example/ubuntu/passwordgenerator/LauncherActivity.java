package com.example.ubuntu.passwordgenerator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        findViewById(R.id.launcher).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);
    }
}
