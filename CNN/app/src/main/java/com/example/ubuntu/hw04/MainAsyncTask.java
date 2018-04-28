package com.example.ubuntu.hw04;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.xml.sax.SAXException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/*
HW04
Sarangdeep Singh
Ishan Agarwal
Group05
 */

public class MainAsyncTask extends AsyncTask<String, Void, ArrayList<News>> {
    private Context context;
    private IList iList;
    private ProgressDialog progressDialog;


    public MainAsyncTask(Context context, IList iList) {
        this.context=context;
        this.iList=iList;
    }


    @Override
    protected void onPreExecute() {
       progressDialog= new ProgressDialog(context, ProgressDialog.STYLE_SPINNER);
       progressDialog.setTitle("Loading News...");
       progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<News> newsList) {
        progressDialog.dismiss();
        iList.setList(newsList);
        Log.d("news", newsList.toString());
    }

    @Override
    protected ArrayList<News> doInBackground(String... strings) {
        HttpURLConnection connection = null;
        String result = null;
        ArrayList<News> newsList= new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                newsList= NewsParser.NewsSAXparser.parseNews(connection.getInputStream());
                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return newsList;
    }

    public interface IList{
        void setList(ArrayList<News> newsList);
    }
}
