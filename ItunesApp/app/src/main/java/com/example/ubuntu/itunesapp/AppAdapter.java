package com.example.ubuntu.itunesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 3/11/18.
 */

public class AppAdapter extends ArrayAdapter<App> {
    ArrayList<App> apps;
    Context context;


    public AppAdapter(@NonNull Context context, int resource, @NonNull List<App> objects) {
        super(context, resource, objects);
        this.apps= (ArrayList<App>) objects;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder= new ViewHolder();
        App app= getItem(position);
        if(convertView== null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.app_detail, parent, false);
            viewHolder.appName= convertView.findViewById(R.id.appName);
            viewHolder.devName= convertView.findViewById(R.id.dev);
            viewHolder.iv = convertView.findViewById(R.id.imageView2);
            viewHolder.price = convertView.findViewById(R.id.price);
            viewHolder.releaseDate= convertView.findViewById(R.id.releaseDate);
            viewHolder.curr= convertView.findViewById(R.id.curr);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

        }
        viewHolder.releaseDate.setText(app.releaseDate);
        viewHolder.price.setText(app.price);
        viewHolder.devName.setText(app.devName);
        viewHolder.appName.setText(app.appTitle);
        viewHolder.curr.setText(app.currency);
        Picasso.get().load(app.smallPhoto).into(viewHolder.iv);

        return convertView;
    }

    public class ViewHolder{
        TextView appName;
        TextView devName;
        TextView price;
        ImageView iv;
        TextView releaseDate;
        TextView curr;
    }
}
