package com.example.ubuntu.homework03;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */

public class StatsActivity extends AppCompatActivity {
    private Button tryAgain;
    private Button quit;
    public static String TEST="start";
    private ProgressBar bar;
    private boolean[] ans;
    private TextView progress;
    private TextView view1;
    private TextView view2;
    //private  Intent returnIntent;

    @Override
    protected void onStart() {
        super.onStart();

        ans= (boolean[]) getIntent().getExtras().get(TriviaActivity.ANSWERS);
        int counter=0;
        for(boolean b:ans){

            if(b==true){
                counter+=1;
                Log.d("ans",Integer.toString(ans.length));
            }
        }
        float prog= ((100*counter)/ans.length);
        Log.d("ans",Float.toString(prog));
        bar= findViewById(R.id.progressBar);
        bar.setProgress((int)prog);
        if(prog==100.0){
            view1= findViewById(R.id.textView5);
            view1.setText(R.string.comp);
            view2= findViewById(R.id.textView6);
            view2.setText("");
        }

        progress= findViewById(R.id.textView9);
        progress.setText(Float.toString(prog)+"%");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        quit= findViewById(R.id.button3);
        tryAgain= findViewById(R.id.button4);

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatsActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("EXIT", true);
                Log.d("intent", "Done");
                startActivity(intent);
                finish();
            }
        });
        tryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()==true) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra(TEST, 1);
                    setResult(StatsActivity.RESULT_OK, returnIntent);
                    Log.d("intent", "Done");
                    finish();
                }else if(isConnected()==false){
                    Toast.makeText(StatsActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

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
}
