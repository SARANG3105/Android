package com.example.ubuntu.messageme;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private EditText fName;
    private EditText lName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private Button signupBtn;
    private Button cancel;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle(getString(R.string.signup_act));

        fName= findViewById(R.id.fname_et);
        lName= findViewById(R.id.lname_et);
        email= findViewById(R.id.email_et);
        password= findViewById(R.id.pass_et);
        confirmPassword= findViewById(R.id.confirmPass_et);
        mAuth= FirebaseAuth.getInstance();
        user= mAuth.getCurrentUser();
        signupBtn= findViewById(R.id.btn_signup);
        cancel= findViewById(R.id.btn_cancel);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("messageMe");
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isConnected()) {
                    String fname = fName.getText().toString();
                    String lname = lName.getText().toString();
                    String emailText = email.getText().toString();
                    String chPwd = password.getText().toString();
                    String rPwd = confirmPassword.getText().toString();

                    if(chPwd.equals(rPwd) ) {
                        performSignUp(emailText, chPwd, fname, lname);
                    } else {
                        Toast.makeText(SignUpActivity.this, "Password don't match", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SignUpActivity.this, "", Toast.LENGTH_LONG).show();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    public void performSignUp(final String email, final String password, String fName, String lName) {
        final String userName= fName+" "+lName;

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(userName).build();
                            user.updateProfile(profileUpdates);
                            User user= new User();
                            user.setEmail(email);
                            user.setName(userName);
                            user.setUserId(mAuth.getCurrentUser().getUid());
                            ref.child("users").push().setValue(user);
                            performLogin(email,password);
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void performLogin(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user =mAuth.getCurrentUser();
                            Intent intent= new Intent(SignUpActivity.this, InboxActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignUpActivity.this, task.getException().getMessage(),
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });

    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }
}
