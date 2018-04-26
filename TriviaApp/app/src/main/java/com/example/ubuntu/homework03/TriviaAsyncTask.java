package com.example.ubuntu.homework03;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */


public class TriviaAsyncTask extends AsyncTask<String,Void, Bitmap> {
    private ProgressDialog progressDialog;
    Context context;
    ImageView view;
    public TriviaAsyncTask(Context context, ImageView view) {
        this.context= context;
        this.view= view;
    }

    @Override
    protected void onPreExecute() {
        progressDialog= new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Loading image");
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

            progressDialog.dismiss();
            view.setImageBitmap(bitmap);

    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        HttpURLConnection connection=null;
        Bitmap bitmap=null;
        try {
            URL url= new URL(strings[0]);
             connection= (HttpURLConnection) url.openConnection();
             bitmap= BitmapFactory.decodeStream(connection.getInputStream());



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
