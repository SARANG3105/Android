package com.example.ubuntu.messageme;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ubuntu on 4/25/18.
 */

public class InboxAdapter extends ArrayAdapter<Message>{
    private ArrayList<Message> msgs;
    private Context context;
    private static final int NOTREAD=0;
    private static final int READ=1;

    public InboxAdapter(@NonNull Context context, int resource, @NonNull List<Message> objects) {
        super(context, resource, objects);
        this.msgs= (ArrayList<Message>) objects;
        this.context= context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder= new ViewHolder();

        if(convertView==null){
            if(getItemViewType(position)== NOTREAD) {
                convertView = LayoutInflater.from(context).inflate(R.layout.msg_not_read, parent, false);
                holder.senderName= convertView.findViewById(R.id.sender);
                holder.date= convertView.findViewById(R.id.date);
                holder.msg= convertView.findViewById(R.id.msg);
            }else if(getItemViewType(position)==READ){
                convertView = LayoutInflater.from(context).inflate(R.layout.msg_read, parent, false);
                holder.senderName= convertView.findViewById(R.id.sender);
                holder.date= convertView.findViewById(R.id.date);
                holder.msg= convertView.findViewById(R.id.msg);
            }

            convertView.setTag(holder);
        }

        holder= (ViewHolder) convertView.getTag();
        final Message message= msgs.get(position);
        holder.msg.setText(message.getMessage().toString());
        holder.senderName.setText(message.getSender().toString());

        holder.date.setText(message.getCreatedAt().toString());
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        Message msg= msgs.get(position);
        if(msg.getRead()==false){
            return NOTREAD;
        }else{
            return READ;
        }
    }

    class ViewHolder{
        TextView senderName;
        TextView msg;
        TextView date;

    }
}
