package com.example.ubuntu.midterm1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FavouriteMoviesActivity extends AppCompatActivity implements FavAdapter.Ilist {
    FavAdapter adapter;
     ArrayList<Movie> arr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        setTitle("Favourite Movies");

        SharedPreferences preferences= getSharedPreferences("PREF",MODE_PRIVATE);
        Gson gson= new Gson();
        String json= preferences.getString("MOVIE",null);
        Type type= new TypeToken<ArrayList<Movie>>() {}.getType();
        arr= gson.fromJson(json,type);

       /*arr = (ArrayList<Movie>) getIntent().getSerializableExtra("FAV");*/
        ListView listView = findViewById(R.id.favList);
if(arr!=null) {
    adapter = new FavAdapter(this, R.layout.movie_listview, arr);
    listView.setAdapter(adapter);
}
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FavouriteMoviesActivity.this, MovieDetailActivity.class);
                intent.putExtra("MOVIE", arr.get(position));
                startActivity(intent);
            }
        });
    }
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.menu2,menu);
            return super.onCreateOptionsMenu(menu);
        }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // Handle item selection
            switch (item.getItemId()) {

                case R.id.home:
                    Intent intent = new Intent(FavouriteMoviesActivity.this, MainActivity.class);
                    startActivity(intent);
                case R.id.sortRating:
                    Collections.sort(arr, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie o1, Movie o2) {
                            String s= o1.rating;
                            String s2= o2.rating;
                            return  s2.compareTo(s);
                        }
                    });
                    adapter.notifyDataSetChanged();

                    return true;

                case R.id.pop:
                    Collections.sort(arr, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie o1, Movie o2) {
                            String s= o1.popularity;
                            String s2= o2.popularity;
                            return  s2.compareTo(s);
                        }
                    });
                    adapter.notifyDataSetChanged();

                    return true;
                case R.id.quit:
                    finish();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }


    @Override
    public void removeMovie(Movie movie) {
        arr.remove(movie);
        adapter.notifyDataSetChanged();
        SharedPreferences preferences=getSharedPreferences("PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        Gson gson= new Gson();
        String json= gson.toJson(arr);
        editor.putString("MOVIE",json);
        editor.commit();


    }
}
