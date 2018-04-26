package com.example.ubuntu.homework03;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.opengl.Visibility;
import android.util.Log;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */

public class MainActivity extends AppCompatActivity implements MainAsyncTask.IList{
    private Button start;
    private Button exit;
    private TextView ready;
    private ImageView img;
    private ArrayList<Question> questions=new ArrayList<>();
    public static String NAME="name";


    @Override
    protected void onStart() {
        super.onStart();
        if(isConnected()==true) {
            new MainAsyncTask(MainActivity.this, MainActivity.this).execute("http://dev.theappsdr.com/apis/trivia_json/trivia_text.php");
        }else if(isConnected()==false){
            img.setVisibility(View.VISIBLE);
            start.setEnabled(true);
            Toast.makeText(MainActivity.this, "No Internet Connection",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=findViewById(R.id.brn_start);
        start.setEnabled(false);
        exit=findViewById(R.id.btn_exit);
        ready=findViewById(R.id.txt_ready);
        ready.setVisibility(View.INVISIBLE);
        img= findViewById(R.id.imageView);
        img.setVisibility(View.INVISIBLE);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()==false){
                    Toast.makeText(MainActivity.this, "No internet Connections",Toast.LENGTH_LONG).show();


                }
                if(isConnected()==true) {
                    Intent intent = new Intent(MainActivity.this, TriviaActivity.class);
                    intent.putExtra(NAME, questions);
                    startActivity(intent);
                    finish();
                }


            }
        });

    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    @Override
    public void handleList(ArrayList<Question> questions) {
        this.questions=questions;


    }

    @Override
    public void visibility(boolean b) {
        if(b==true){
            start.setEnabled(true);
            img.setVisibility(View.VISIBLE);
            ready.setVisibility(View.VISIBLE);
        }
    }
}
