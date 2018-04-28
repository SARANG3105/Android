package com.example.ubuntu.messageme;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class InboxActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private InboxAdapter adapter;
    private ArrayList<Message> allMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        setTitle(getString(R.string.inbox));
        mAuth=FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("messageMe");
        listView= findViewById(R.id.listView);
        getMessages(mAuth.getCurrentUser().getUid());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent(InboxActivity.this, ReadMessageActivity.class);
                Bundle bundle = new Bundle();
                Message msg= allMessages.get(position);
                msg.setRead(true);
                ref.child("messages").child(user.getUid()).child(msg.getMsgId()).setValue(msg);
                getMessages(user.getUid());
                intent.putExtra("TAG",msg);
                startActivity(intent);


            }
        });


    }
    public void getMessages(final String threadId){
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot!=null) {
                    HashMap<String, Message> messages= new HashMap<>();
                    for(DataSnapshot snapshot: dataSnapshot.child("messages").child(threadId).getChildren()){
                        Message msg= (Message)snapshot.getValue(Message.class);
                        msg.setMsgId(snapshot.getKey());
                        messages.put(snapshot.getKey(),msg);

                    }

                    allMessages = new ArrayList<>(messages.values());
                    Collections.sort(allMessages);
                    adapter = new InboxAdapter(InboxActivity.this, R.layout.msg_not_read, allMessages);

                    listView.setAdapter(adapter);
                }else{
                    Toast.makeText(InboxActivity.this, "No Messages to Display.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message

                // ...
            }
        };
        ref.addValueEventListener(postListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_inbox,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.power){
            mAuth.signOut();
            Intent intent= new Intent(InboxActivity.this, MainActivity.class);
            startActivity(intent);
        }else if( item.getItemId()==R.id.compose){
            Intent intent= new Intent(InboxActivity.this, ComposeMessageActivity.class);
            startActivity(intent);
        }
        return true;
    }
}
