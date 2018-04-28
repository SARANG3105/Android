package com.example.ubuntu.midterm1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 3/10/18.
 */

public class FavAdapter extends ArrayAdapter<Movie> {
    boolean[] bool;
    Context context;
    ArrayList<Movie> arrayList;
    public FavAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        bool= new boolean[objects.size()];
        this.context= context;
        arrayList= (ArrayList<Movie>) objects;
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final Movie movie = getItem(position);
        final MovieAdapter.ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_listview, parent, false);
            viewHolder = new MovieAdapter.ViewHolder();
            viewHolder.release = convertView.findViewById(R.id.textView5);
            viewHolder.title = convertView.findViewById(R.id.textView4);
            viewHolder.iv = convertView.findViewById(R.id.imageView2);
            viewHolder.btn = convertView.findViewById(R.id.favoriteBtn);
            viewHolder.btn.setTag(position);

            viewHolder.rel = convertView.findViewById(R.id.relative);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MovieAdapter.ViewHolder) convertView.getTag();

        }
        viewHolder.title.setText(movie.movieName);
        viewHolder.release.setText(movie.releaseDate);
        SharedPreferences preferences= context.getSharedPreferences("PREF",context.MODE_PRIVATE);
        Gson gson= new Gson();
        String json= preferences.getString("MOVIE",null);
        Type type= new TypeToken<ArrayList<Movie>>() {}.getType();
        ArrayList<Movie> arr= gson.fromJson(json,type);
        if(arr.contains(arrayList.get(position))){
            viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_on);
            bool[position]=true;
        }else{
            viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_off);
            bool[position]=false;
        }
        viewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if(bool[position] ==true){
                    Log.d("dem", String.valueOf(bool[position]));

                    viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_off);
                   Ilist ilist = (Ilist) context;
                    Movie movie1 = arrayList.get(position);
                    ilist.removeMovie(movie1);
                    bool[position]=false;

                }
            }
        });
        Picasso.get().load("http://image.tmdb.org/t/p/w154/" + movie.imageBaseUrl).into(viewHolder.iv);
        return convertView;


    }

    public static class ViewHolder{
        ImageView iv;
        TextView title;
        TextView release;
        ImageButton btn;
        RelativeLayout rel;



    }
    public interface Ilist{
        void removeMovie(Movie movie);
    }
}
