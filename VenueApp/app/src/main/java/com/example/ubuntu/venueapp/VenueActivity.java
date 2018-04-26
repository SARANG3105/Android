package com.example.ubuntu.venueapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Observer;

public class VenueActivity extends AppCompatActivity implements VenueAsync.IList {
    boolean[] arr;
    private ArrayList<Venue> venues;
    private ListView listView;
    private VenueAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);
        String state = getIntent().getStringExtra(MainActivity.STATE);
        listView = findViewById(R.id.listView);
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        Log.d("date", dateFormat.format(date));
        new VenueAsync(VenueActivity.this, VenueActivity.this).execute("https://api.foursquare.com/v2/venues/search?client_id=ZQTUVFEB2G2XBDY2A32ITFR4RIGHJTT12OYP5S4K0BFLVMSW&client_secret=TIE1QXTLUREK5OF1RKN01M2Q5GEDHR0SPCDQKHXFCQUCRY3V&v=" + dateFormat.format(date) + "&nea\n" +
                "r=" + state);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (arr != null) {
                    if (arr[firstVisibleItem] == true) {
                        ImageView vi = (ImageView) view.findViewById(R.id.savedIv);
                        vi.setImageResource(R.drawable.visited);
                    } else if (arr[firstVisibleItem] == false) {
                        ImageView vi = (ImageView) view.findViewById(R.id.savedIv);
                        //vi.setImageResource(R.drawable.unvisited);
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (arr[position] == false) {

                    ImageView vi = (ImageView) view.findViewById(R.id.savedIv);
                    Toast.makeText(VenueActivity.this, "Successfully Marked", Toast.LENGTH_SHORT).show();
                    vi.setImageResource(R.drawable.visited);
                    SharedPreferences preferences = getSharedPreferences("VENUES", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(venues.get(position).venueId, String.valueOf(position));
                    editor.commit();
                    arr[position] = true;

                } else if (arr[position] == true) {
                    ImageView vi = (ImageView) view.findViewById(R.id.savedIv);
                    vi.setImageResource(R.drawable.unvisited);
                    SharedPreferences preferences = getSharedPreferences("VENUES", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove(venues.get(position).venueId);
                    editor.commit();
                    arr[position] = false;
                    Toast.makeText(VenueActivity.this, "Successfully Unmarked", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    public void getList(ArrayList<Venue> venues) {
        this.venues = venues;
        arr = new boolean[venues.size()];
        if (venues != null) {
            adapter = new VenueAdapter(VenueActivity.this, R.layout.venue_listview, venues);
            listView.setAdapter(adapter);
        } else {
            Toast.makeText(VenueActivity.this, "No Venues", Toast.LENGTH_LONG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.venue_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences preferences = getSharedPreferences("VENUES", MODE_PRIVATE);

        if(item.getItemId()==R.id.marked){
            String[] str= preferences.getAll().keySet().toArray(new String[0]);
            StringBuilder st= new StringBuilder();

            for(String s:str) {

                st.append(s+",");
            }
            adapter.getFilter().filter(st.toString());
        }else if(item.getItemId()==R.id.clear){
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            arr = new boolean[venues.size()];
            adapter.getFilter().filter("");
            adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }
}
