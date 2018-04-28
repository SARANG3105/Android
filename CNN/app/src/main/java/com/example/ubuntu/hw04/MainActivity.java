package com.example.ubuntu.hw04;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;
/*
HW04
Sarangdeep Singh
Ishan Agarwal
Group05
 */

public class MainActivity extends AppCompatActivity implements MainAsyncTask.IList {
    private TextView viewCategory;
    private TextView title;
    private TextView publishDate;
    private TextView desc;
    private ImageView imageView;
    private TextView totalNews;
    private ImageButton next;
    private ImageButton prev;
    private ArrayList<News> newsList= new ArrayList<>();
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewCategory= findViewById(R.id.textView_category);
        title= findViewById(R.id.textView_title);
        title.setClickable(true);
        title.setMovementMethod(LinkMovementMethod.getInstance());
        publishDate= findViewById(R.id.textView_published);
        desc= findViewById(R.id.textView_desc);
        totalNews= findViewById(R.id.textView_count);
        next= findViewById(R.id.btn_next);
        next.setEnabled(false);
        prev= findViewById(R.id.btn_prev);
        prev.setEnabled(false);
        imageView= findViewById(R.id.imageView);

        final LinkedHashMap<String, String> topics= new LinkedHashMap<>();
        topics.put("Top Stories","cnn_topstories");
        topics.put("World","cnn_world");
        topics.put("U.S.","cnn_us");
        topics.put("Business","money_latest");
        topics.put("Politics","cnn_allpolitics");
        topics.put("Technology","cnn_tech");
        topics.put("Health","cnn_health");
        topics.put("Entertainment","cnn_showbiz");
        topics.put("Travel","cnn_travel");
        topics.put("Living","cnn_living");
        topics.put("Most Recent","cnn_latest");

        final CharSequence[] category= topics.keySet().toArray(new CharSequence[11]);


        findViewById(R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this, R.style.Theme_AppCompat_Light_Dialog_MinWidth);
                    builder.setTitle("Choose Category");
                    builder.setItems(category, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewCategory.setText(category[which]);
                            String type = (String) category[which];
                            new MainAsyncTask(MainActivity.this, MainActivity.this).execute("http://rss.cnn.com/rss/"+topics.get(type)+".rss");
                            Log.d("url","http://rss.cnn.com/rss/"+topics.get(type)+".rss");
                        }
                    });
                    builder.create().show();
                }else{
                    Toast.makeText(MainActivity.this, "No internet Connection",Toast.LENGTH_LONG).show();
                }
            }
        });
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.get(index).title!=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsList.get(index).newsLink));
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "No title", Toast.LENGTH_LONG).show();
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(newsList.get(index).newsLink!=null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsList.get(index).newsLink));
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "No image", Toast.LENGTH_LONG).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()==true){
                    index++;
                    if(index<newsList.size()) {
                        title.setText(newsList.get(index).title);
                        publishDate.setText(newsList.get(index).pubdate);
                        desc.setText(newsList.get(index).description);
                        totalNews.setText( (index+1)+" out of " + newsList.size());
                        Picasso.with(MainActivity.this).load(newsList.get(index).imageUrl).into(imageView);
                    }else if(index>=newsList.size()){
                        index=0;
                        title.setText(newsList.get(index).title);
                        publishDate.setText(newsList.get(index).pubdate);
                        desc.setText(newsList.get(index).description);
                        totalNews.setText("1 out of " + newsList.size());
                        Picasso.with(MainActivity.this).load(newsList.get(index).imageUrl).into(imageView);
                    }

                }else{
                    Toast.makeText(MainActivity.this, " No internet", Toast.LENGTH_LONG).show();
                }

            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isConnected()==true){
                    index--;
                    if(index>=0) {
                        title.setText(newsList.get(index).title);
                        publishDate.setText(newsList.get(index).pubdate);
                        desc.setText(newsList.get(index).description);
                        totalNews.setText( (index+1)+" out of " + newsList.size());
                        Picasso.with(MainActivity.this).load(newsList.get(index).imageUrl).into(imageView);
                    }else if(index<0){
                        index=newsList.size()-1;
                        title.setText(newsList.get(index).title);
                        publishDate.setText(newsList.get(index).pubdate);
                        desc.setText(newsList.get(index).description);
                        totalNews.setText(newsList.size()+" out of " + newsList.size());
                        Picasso.with(MainActivity.this).load(newsList.get(index).imageUrl).into(imageView);
                    }

                }else{
                    Toast.makeText(MainActivity.this, " No internet", Toast.LENGTH_LONG).show();
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
    public void setList(ArrayList<News> news) {
        newsList= news;
        index=0;
        if(news.size()<2){
            title.setText(news.get(index).title);
            publishDate.setText(news.get(index).pubdate);
            desc.setText(news.get(index).description);
        }else{
            next.setEnabled(true);
            prev.setEnabled(true);
            title.setText(news.get(index).title);
            publishDate.setText(news.get(index).pubdate);
            desc.setText(news.get(index).description);
            totalNews.setText("1 out of "+news.size());
            Picasso.with(MainActivity.this).load(news.get(index).imageUrl).into(imageView);
        }

    }
}
