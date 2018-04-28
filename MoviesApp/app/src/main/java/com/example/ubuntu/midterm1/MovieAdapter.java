package com.example.ubuntu.midterm1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import android.util.Log;


/**
 * Created by ubuntu on 3/9/18.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {//RecyclerView.Adapter<MovieAdapter.ViewHolder> implements View.OnClickListener{
    ArrayList<Movie> arrayList;
    ArrayList<Movie> fav= new ArrayList<>();
    Context context;
    boolean[] bool;

    public MovieAdapter(@NonNull Context context, int resource, @NonNull List<Movie> objects) {
        super(context, resource, objects);
        arrayList= (ArrayList<Movie>) objects;
        this.context= context;
        bool= new boolean[arrayList.size()];

    }

    /* public MovieAdapter(ArrayList<Movie> arrayList, Context context) {
         this.arrayList = arrayList;
         this.context = context;

     }

     @Override
     public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_listview, parent, false);
         ViewHolder viewHolder = new ViewHolder(view);
         return viewHolder;
     }

     @Override
     public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
         Movie movie = arrayList.get(position);
         viewHolder.title.setText(movie.movieName);
         viewHolder.release.setText(movie.releaseDate);
         Log.d("movie", movie.movieName+getItemCount());
         Picasso.get().load("http://image.tmdb.org/t/p/w154/"+movie.imageBaseUrl).into(viewHolder.iv);

         viewHolder.btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(bool==false){

                     viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_on);
                     bool =true;
                 }else if(bool==true){
                     viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_off);
                     bool= false;
                 }
             }
         });
         viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(context, MovieDetailActivity.class);
                 intent.putExtra("MOVIE",arrayList.get(position));
                 context.startActivity(intent);
             }
         });

     }

     @Override
     public int getItemCount() {
         return arrayList.size();
     }

     @Override
     public void onClick(View v) {

     }
     public interface CustomListener{
         public void whenClicked(View v, int position);
     }

     public static class ViewHolder extends RecyclerView.ViewHolder{
         ImageView iv;
         TextView title;
         TextView release;
         ImageButton btn;
         Context context;
         RelativeLayout relativeLayout;
         boolean bool=false;
         public ViewHolder(View itemView) {
             super(itemView);
             release= itemView.findViewById(R.id.textView5);
             title = itemView.findViewById(R.id.textView4);
             iv= itemView.findViewById(R.id.imageView2);
             btn= itemView.findViewById(R.id.favoriteBtn);
             context = itemView.getContext();
             int id= getAdapterPosition();
             relativeLayout= itemView.findViewById(R.id.relative);


         }
     }
 */
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull final ViewGroup parent) {
        final Movie movie = getItem(position);
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.release = convertView.findViewById(R.id.textView5);
            viewHolder.title = convertView.findViewById(R.id.textView4);
            viewHolder.iv = convertView.findViewById(R.id.imageView2);
            viewHolder.btn = convertView.findViewById(R.id.favoriteBtn);
            viewHolder.btn.setTag(position);

            viewHolder.rel = convertView.findViewById(R.id.relative);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

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
               if (bool[position]==false) {

                   viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_on);
                   Log.d("dem", String.valueOf(bool[position]));
                   ListSend listSend = (ListSend) context;
                   Movie movie1 = arrayList.get(position);
                   listSend.getList(movie1);
                   bool[position]=true;


               }else if(bool[position] ==true){
                   Log.d("dem", String.valueOf(bool[position]));

                   viewHolder.btn.setImageResource(android.R.drawable.btn_star_big_off);
                   ListSend listSend = (ListSend) context;
                   Movie movie1 = arrayList.get(position);
                   listSend.removeMovie(movie1);
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

    public interface ListSend{
        void getList(Movie movie);
        void removeMovie(Movie mov);
    }
}
