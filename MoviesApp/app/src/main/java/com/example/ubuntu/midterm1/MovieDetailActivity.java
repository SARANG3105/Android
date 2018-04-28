package com.example.ubuntu.midterm1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {
    private TextView title;
    private ImageView iv;
    private TextView desc;
    private TextView date;
    private TextView rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        setTitle("Movie Details");

        title = findViewById(R.id.textView2);
        iv= findViewById(R.id.imageView);
        desc= findViewById(R.id.textView3);
        date = findViewById(R.id.textView6);
        rating = findViewById(R.id.textView7);

        Movie movie= (Movie) getIntent().getSerializableExtra("MOVIE");
        title.setText("Title: "+movie.movieName);
        desc.setText("Overview: "+movie.overview);
        date.setText("Release Date: "+movie.releaseDate);
        rating.setText("Rating: "+movie.rating+"/10");

        Picasso.get().load("http://image.tmdb.org/t/p/w342/"+movie.imageBaseUrl).into(iv);
    }
}
