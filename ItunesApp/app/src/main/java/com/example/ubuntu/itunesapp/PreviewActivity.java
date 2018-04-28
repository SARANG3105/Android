package com.example.ubuntu.itunesapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PreviewActivity extends AppCompatActivity {
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        iv= findViewById(R.id.imageView);

        App app= (App) getIntent().getSerializableExtra("APP");
        Picasso.get().load(app.largePhoto).into(iv);

    }
}
