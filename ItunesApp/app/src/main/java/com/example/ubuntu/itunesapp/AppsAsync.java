package com.example.ubuntu.itunesapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ubuntu on 3/11/18.
 */

public class AppsAsync extends AsyncTask<String, Void, ArrayList<App>> {
    ProgressDialog progressDialog;
    Context context;
    IList iList;
    public AppsAsync(IList iList,Context context) {
        this.context = context;
        this.iList= iList;
    }

    @Override
    protected ArrayList<App> doInBackground(String... params) {
        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        ArrayList<App> result = new ArrayList<>();

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                result = AppsParser.AppsPULLParser.parseApps(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    protected void onPreExecute() {
        progressDialog=new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    @Override
    protected void onPostExecute(ArrayList<App> apps) {
        super.onPostExecute(apps);
        progressDialog.dismiss();
        iList.setList(apps);

    }

    public interface IList{
        public void setList(ArrayList<App> apps);
    }
}
