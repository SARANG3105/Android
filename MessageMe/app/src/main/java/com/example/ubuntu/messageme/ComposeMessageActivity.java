package com.example.ubuntu.messageme;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ComposeMessageActivity extends AppCompatActivity {
    private EditText msg;
    private TextView contactName;
    private Button send;
    private ImageButton contacts;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase database;
    private DatabaseReference ref;
    private ArrayList<User> allUsers;
    private String recieverID;
    private String recieverEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_message);

        msg= findViewById(R.id.msg_et);
        contactName= findViewById(R.id.name_tv);
        send= findViewById(R.id.send_btn);
        contacts= findViewById(R.id.contact_btn);
        mAuth= FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        database= FirebaseDatabase.getInstance();
        ref= database.getReference("messageMe");
        Message msgs= (Message) getIntent().getSerializableExtra("TAG");
        if(msgs!=null){
            contacts.setVisibility(View.INVISIBLE);
            recieverID= msgs.getSenderID();
            recieverEmail= msgs.getSenderEmail();
            contactName.setText(msgs.getSender());

        }
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!msg.getText().toString().isEmpty() && msg.getText()!=null
                        && !recieverEmail.isEmpty() && !recieverID.isEmpty()) {
                    String message = msg.getText().toString();
                    sendMessage(recieverID, recieverEmail, message);
                    Toast.makeText(ComposeMessageActivity.this,"Sent", Toast.LENGTH_SHORT).show();
                    finish();
                }if(recieverID.isEmpty() || recieverEmail.isEmpty()){
                    Toast.makeText(ComposeMessageActivity.this,"Select a user", Toast.LENGTH_SHORT).show();

                } if(msg.getText().toString().isEmpty()){
                    Toast.makeText(ComposeMessageActivity.this,"Enter a message", Toast.LENGTH_SHORT).show();
                }
            }
        });

        contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: ");
                getUsers();
            }
        });
    }

    public void sendMessage(String recieverID, String recieverEmail,String message){
        Message msg= new Message();
        msg.setRead(false);
        msg.setMessage(message);
        msg.setReciever(recieverEmail);
        msg.setSender(user.getDisplayName());
        msg.setSenderEmail(user.getEmail());
        msg.setSenderID(user.getUid());
        ref.child("messages").child(recieverID).push().setValue(msg);
    }

    public void getUsers(){

        ref.child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot!=null) {
                            final Map<String, User> userMap= new HashMap<String, User>();
                            for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                                User usr= snapshot.getValue(User.class);
                                if(!usr.getUserId().equals(mAuth.getCurrentUser().getUid())) {
                                    userMap.put(usr.getName(), usr);
                                }
                            }
                            final CharSequence[] seq=userMap.keySet().toArray(new CharSequence[0]);
                            AlertDialog.Builder build= new AlertDialog.Builder(ComposeMessageActivity.this);
                            build.setTitle("Users");
                            build.setItems(seq, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String usr= (String) seq[which];
                                    User u= userMap.get(usr);
                                    recieverID= u.getUserId();
                                    recieverEmail= u.getEmail();
                                    contactName.setText(usr);
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alertDialog = build.create();
                            alertDialog.show();
                        }else{
                            Toast.makeText(ComposeMessageActivity.this, "No threads to Display.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

    }
}
