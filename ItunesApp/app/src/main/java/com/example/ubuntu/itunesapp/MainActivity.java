package com.example.ubuntu.itunesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AppsAsync.IList {
    ArrayList<App> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.mainActivity));



        findViewById(R.id.appList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  new AppsAsync(MainActivity.this,MainActivity.this).execute("https://itunes.apple.com/us/rss/topgrossingapplications/limit=100/xml");
            }
        });

        findViewById(R.id.history).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences= getSharedPreferences("PREF",MODE_PRIVATE);
                Gson gson= new Gson();
                String json= preferences.getString("APPSVIE",null);
                Type type= new TypeToken<ArrayList<App>>() {}.getType();
                arr= gson.fromJson(json,type);
                if(arr!=null) {
                    Intent intent = new Intent(MainActivity.this, AppsActivity.class);
                    intent.putExtra("APPS", arr);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, AppsActivity.class);
                    startActivity(intent);
                }
            }
        });

    }


    @Override
    public void setList(ArrayList<App> apps) {
        Intent intent = new Intent(MainActivity.this, AppsActivity.class);
        intent.putExtra("APPS1", apps);
        startActivity(intent);
    }
}
