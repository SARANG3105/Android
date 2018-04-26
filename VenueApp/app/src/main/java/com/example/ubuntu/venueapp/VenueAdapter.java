package com.example.ubuntu.venueapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 3/11/18.
 */

public class VenueAdapter extends ArrayAdapter<Venue> implements Filterable {
    Context context;
    ArrayList<Venue> venues;
    ArrayList<Venue> filterd;
    Filter filter=null;
    public VenueAdapter(@NonNull Context context, int resource, @NonNull List<Venue> objects) {
        super(context, resource, objects);
        this.context = context;
        this.venues = new ArrayList<>(objects);
        this.filterd= new ArrayList<>(objects);
    }

    @Override
    public int getCount() {
        return filterd.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Venue venue = filterd.get(position);

        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.venue_listview, parent, false);

            viewHolder.badge = convertView.findViewById(R.id.badgeIv);
            viewHolder.category = convertView.findViewById(R.id.categoryName);
            viewHolder.icon = convertView.findViewById(R.id.iconIV);
            viewHolder.venueName = convertView.findViewById(R.id.venueName);
            viewHolder.select = convertView.findViewById(R.id.savedIv);
            viewHolder.rel = convertView.findViewById(R.id.rel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.venueName.setText(venue.venueName);
        viewHolder.category.setText(venue.categoryName);
        Picasso.get().load(venue.categoryIcon).into(viewHolder.icon);
        viewHolder.select.setImageResource(R.drawable.unvisited);
        if (Integer.parseInt(venue.checkInCount) <= 100) {
            viewHolder.badge.setImageResource(R.drawable.bronze);
        } else if (Integer.parseInt(venue.checkInCount) >= 101 && Integer.parseInt(venue.checkInCount) <= 500) {
            viewHolder.badge.setImageResource(R.drawable.silver);
        } else if (Integer.parseInt(venue.checkInCount) > 500) {
            viewHolder.badge.setImageResource(R.drawable.gold);
        }

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
         filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                ArrayList<Venue> filterVenue= new ArrayList<>();
                String[] c= constraint.toString().split(",");
                Log.d("str",""+c[0]+c.length);
                if(constraint==null||constraint.length()==0){
                    results.count= venues.size();
                    results.values= venues;
                }else{
                    for(Venue v:venues){
                        for(int i=0;i<c.length;i++) {
                            if (v.venueId.equals(c[i])) {
                                filterVenue.add(v);
                            }
                        }
                    }
                    Log.d("hello",""+filterVenue.size());
                    results.values=filterVenue;
                    results.count= filterVenue.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filterd = (ArrayList<Venue>) results.values;
                notifyDataSetChanged();
            }
        };
        return filter;
    }


    public class ViewHolder{
        TextView venueName;
        TextView category;
        ImageView badge;
        ImageView select;
        ImageView icon;
        RelativeLayout rel;
    }
}
