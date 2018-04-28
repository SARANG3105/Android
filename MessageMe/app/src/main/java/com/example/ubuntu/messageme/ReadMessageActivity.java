package com.example.ubuntu.messageme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReadMessageActivity extends AppCompatActivity {
    private TextView sender;
    private TextView msg;
    private Message msgs;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_message);
        sender=findViewById(R.id.textView3);
        msg= findViewById(R.id.textView4);
        mAuth= FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        ref= database.getReference("messageMe");
        msgs= (Message) getIntent().getSerializableExtra("TAG");

        sender.setText(msgs.getSender());
        msg.setText(msgs.getMessage());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_read_message,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.del){
            ref.child("messages").child(user.getUid()).child(msgs.getMsgId()).removeValue();
            finish();
        }else if(item.getItemId()==R.id.reply){
            Intent intent= new Intent(ReadMessageActivity.this, ComposeMessageActivity.class);
            intent.putExtra("TAG", msgs);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
