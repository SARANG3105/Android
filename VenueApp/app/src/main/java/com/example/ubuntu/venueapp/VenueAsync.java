package com.example.ubuntu.venueapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.ArrayList;

/**
 * Created by ubuntu on 3/11/18.
 */

public class VenueAsync extends AsyncTask<String, Void, ArrayList<Venue>> {
    IList iList;
    Context context;
    ProgressDialog dialog;
    public VenueAsync(IList iList, Context context) {
        this.context= context;
        this.iList= iList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog= new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage(context.getString(R.string.loading));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Venue> venues) {
        super.onPostExecute(venues);
        dialog.dismiss();
        iList.getList(venues);
    }

    @Override
    protected ArrayList<Venue> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<Venue> result = new ArrayList<>();
        BufferedReader reader=null;
        StringBuilder builder=new StringBuilder();
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                while((line=reader.readLine())!=null){
                    builder.append(line);
                    Log.d("venue",line);
                }
                JSONObject root = new JSONObject(builder.toString());
                JSONObject obj1=root.getJSONObject("response");
                JSONArray venues = obj1.getJSONArray("venues");
                for(int i=0;i<venues.length();i++){
                    JSONObject venue= venues.getJSONObject(i);
                    Venue v= new Venue();
                    v.venueId= venue.getString("id");
                    v.venueName=venue.getString("name");
                    JSONArray categories = venue.getJSONArray("categories");
                    JSONObject obj= categories.getJSONObject(0);
                    v.categoryName = obj.getString("name");
                    JSONObject icon=obj.getJSONObject("icon");
                    v.categoryIcon =icon.getString("prefix")+icon.getString("suffix");

                    JSONObject stats = venue.getJSONObject("stats");
                    v.checkInCount = stats.getString("checkinsCount");

                    result.add(v);
                    Log.d("venue",v.toString());
                }

            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;

    }

    public interface IList{
        void getList(ArrayList<Venue> venues);
    }
}
