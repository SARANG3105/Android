package com.example.ishanagarwal.inclass11;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText editText;
    private Button btn;
    private final OkHttpClient client = new OkHttpClient();
    private Handler handler;
    private int i=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        imageView = findViewById(R.id.imageView);
        editText = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                performLogin(text);

            }
        });

        handler = new Handler();


    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    public void performLogin(String keyword) {

        final Request request = new Request.Builder()
                .url("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=23a312d357ea7adc0b62822b1c805a31&format=json&nojsoncallback=1&text=" + keyword + "&extras=url_o")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String var = response.body().string();

                Gson gson = new Gson();
                final Photos photos = gson.fromJson(var, Photos.class);
                Log.d("demo", photos.toString());
                handler.post(new Runnable() {
                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void run() {
                        Picasso.get().load(photos.photos.photo.get(i).url_o).into(imageView);
                        imageView.setOnTouchListener(new SwipeClass(MainActivity.this)
                        {

                            public void onSwipeRight() {
                                Toast.makeText(MainActivity.this,"Right",Toast.LENGTH_SHORT).show();
                                Picasso.get().load(photos.photos.photo.get(i+1).url_o).into(imageView);
                                i++;
                           }
                            public void onSwipeLeft() {
                                if(i==0)
                                    Toast.makeText(MainActivity.this,"First Image",Toast.LENGTH_SHORT).show();
                                Picasso.get().load(photos.photos.photo.get(i-1).url_o).into(imageView);
                                i--;
                            }

                        });
                    }

                });

            }
        });

    }
}
