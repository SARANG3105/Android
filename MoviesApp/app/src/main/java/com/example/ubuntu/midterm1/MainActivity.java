package com.example.ubuntu.midterm1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity implements GetMovieAsync.IList, MovieAdapter.ListSend {
    private EditText movieName;
    private ArrayList<Movie> movies= new ArrayList<>();
    private ArrayList<Movie> fav= new ArrayList<>();
    ListView listView;
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getString(R.string.mainTitle));
        listView = findViewById(R.id.listView);
        textView= findViewById(R.id.results);
        textView.setVisibility(View.INVISIBLE);
        movieName= findViewById(R.id.editText);
       /* //recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);*/


        findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               String arr= movieName.getText().toString().replaceAll(" ","+");


               new GetMovieAsync(MainActivity.this,MainActivity.this).execute("https://api.themoviedb.org/3/search/movie?query="+arr+"&api_key=796ed0344b1ffc9fff145699bcea7eed&page=1");

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=  new Intent(MainActivity.this, MovieDetailActivity.class);
                intent.putExtra("MOVIE", movies.get(position));
                startActivity(intent);
            }
        });


    }

    @Override
    public void setList(ArrayList<Movie> movies) {
        this.movies= movies;
        textView.setVisibility(View.VISIBLE);
        Log.d("string", "hello");
       /* adapter = new MovieAdapter(movies, MainActivity.this);
        recyclerView.setAdapter(adapter);*/

        if(movies.size()>0) {
            adapter = new MovieAdapter(MainActivity.this, R.layout.movie_listview, movies);
            Log.d("string", String.valueOf(movies.size()));
            listView.setAdapter(adapter);
        }else{
            Toast.makeText(MainActivity.this, "No movies found", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.new_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sortRating:
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        String s= o1.rating;
                        String s2= o2.rating;
                        return  s2.compareTo(s);
                    }
                });
                adapter.notifyDataSetChanged();
                Log.d("string", movies.toString());
                return true;

            case R.id.pop:
                Collections.sort(movies, new Comparator<Movie>() {
                    @Override
                    public int compare(Movie o1, Movie o2) {
                        String s= o1.popularity;
                        String s2= o2.popularity;
                        return  s2.compareTo(s);
                    }
                });
                adapter.notifyDataSetChanged();
                Log.d("string", movies.toString());
                return true;
            case R.id.fav:
                if(fav!=null) {
                    Intent intent = new Intent(MainActivity.this, FavouriteMoviesActivity.class);
                    intent.putExtra("FAV", fav);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(MainActivity.this, FavouriteMoviesActivity.class);
                    startActivity(intent);
                }
                return true;
            case R.id.quit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void getList(Movie movies) {
        if(!fav.contains(movies)) {


            fav.add(movies);
            SharedPreferences preferences = getSharedPreferences("PREF", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            Gson gson = new Gson();
            String json = gson.toJson(fav);
            editor.putString("MOVIE", json);
            editor.commit();
        }

    }

    @Override
    public void removeMovie(Movie mov) {
        fav.remove(mov);
        SharedPreferences preferences=getSharedPreferences("PREF", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= preferences.edit();
        Gson gson= new Gson();
        String json= gson.toJson(fav);
        editor.putString("MOVIE",json);
        editor.commit();

    }
}

