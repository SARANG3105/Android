package com.example.ubuntu.midterm1;

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
import java.util.ArrayList;

/**
 * Created by ubuntu on 3/9/18.
 */

public class GetMovieAsync extends AsyncTask<String, Void , ArrayList<Movie>> {
    ProgressDialog progressDialog;
    Context context;
    IList iList;
    public GetMovieAsync(Context context, IList iList) {
        this.context = context;
        this.iList=iList;
    }

    @Override
    protected void onPreExecute() {
        progressDialog= new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
       progressDialog.dismiss();
       Log.d("string", movies.toString());
       iList.setList(movies);
    }

    @Override
    protected ArrayList<Movie> doInBackground(String... params) {
        HttpURLConnection connection = null;
        ArrayList<Movie> result = new ArrayList<>();
        BufferedReader reader= null;
        StringBuilder sb= new StringBuilder();
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
               reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

               String line ="";
               while ((line= reader.readLine())!=null){
                   sb.append(line);
               }

                JSONObject root = new JSONObject(sb.toString());
                JSONArray movies = root.getJSONArray("results");
                for (int i=0;i<movies.length();i++) {
                    JSONObject movieJson = movies.getJSONObject(i);
                    Movie movie = new Movie();
                    movie.movieName= movieJson.getString("title");
                    movie.overview = movieJson.getString("overview");
                    movie.imageBaseUrl = movieJson.getString("poster_path");
                    movie.popularity = movieJson.getString("popularity");
                    movie.releaseDate = movieJson.getString("release_date");
                    movie.rating = movieJson.getString("vote_average");

                    result.add(movie);
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return result;

    }

    public interface IList{
         void setList(ArrayList<Movie> movies);
    }
}
