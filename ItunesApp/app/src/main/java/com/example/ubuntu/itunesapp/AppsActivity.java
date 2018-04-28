package com.example.ubuntu.itunesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AppsActivity extends AppCompatActivity {
    private ArrayList<App> apps;
    AppAdapter adapter;
    ArrayList<App> vist= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps);
        setTitle(getString(R.string.appsActivity));
        ListView listView = findViewById(R.id.listView);
        if(getIntent().hasExtra("APPS1")) {
            apps = (ArrayList<App>) getIntent().getSerializableExtra("APPS1");
            Log.d("apps", apps.toString());

            adapter = new AppAdapter(AppsActivity.this, R.layout.app_detail, apps);
            listView.setAdapter(adapter);
        }else if(getIntent().hasExtra("APPS")){
            vist= (ArrayList<App>) getIntent().getSerializableExtra("APPS");

            adapter = new AppAdapter(AppsActivity.this, R.layout.app_detail, vist);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                App app= apps.get(position);
                vist.add(apps.get(position));
                SharedPreferences preferences=getSharedPreferences("PREF", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= preferences.edit();
                Gson gson= new Gson();
                String json= gson.toJson(vist);
                editor.putString("APPSVIE",json);
                editor.commit();

                Intent intent= new Intent(AppsActivity.this, PreviewActivity.class);
                intent.putExtra("APP",app);
                startActivity(intent);

            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuapps,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sortByApp:
                Collections.sort(apps, new Comparator<App>() {
                    @Override
                    public int compare(App o1, App o2) {
                        String s= o1.appTitle;
                        String s1= o2.appTitle;
                        return s.compareTo(s1);
                    }

                });
                adapter.notifyDataSetChanged();
                return true;
            case R.id.sortbyDev:
                Collections.sort(apps, new Comparator<App>() {
                    @Override
                    public int compare(App o1, App o2) {
                        String s= o1.devName;
                        String s1= o2.devName;
                        return s.compareTo(s1);
                    }
                });
                adapter.notifyDataSetChanged();
            case R.id.sortByPrice:
                Collections.sort(apps, new Comparator<App>() {
                    @Override
                    public int compare(App o1, App o2) {
                        String s= o1.price;
                        String s1= o2.price;
                        return s.compareTo(s1);
                    }
                });
                adapter.notifyDataSetChanged();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
