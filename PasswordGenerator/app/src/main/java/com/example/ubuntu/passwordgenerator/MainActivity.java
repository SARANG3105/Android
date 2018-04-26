package com.example.ubuntu.passwordgenerator;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ExecutorService threadpool;
    public Handler handler;
    private TextView count;
    private TextView length;
    private SeekBar countbar;
    private SeekBar lengthbar;
    private ProgressDialog progressDialog;
    private TextView pass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        count= findViewById(R.id.textView_count);
        length= findViewById(R.id.textView_len);
        countbar= findViewById(R.id.seekbar_count);
        lengthbar= findViewById(R.id.seekbar_length);
        pass= findViewById(R.id.view_pass);
        progressDialog= new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Generating Passwords...");
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);

        threadpool= Executors.newFixedThreadPool(2);
        countbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                count.setText(Integer.toString(i+1));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        lengthbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                length.setText(Integer.toString(i+8));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.btn_thread).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int c= Integer.parseInt(count.getText().toString());
                int l=  Integer.parseInt(length.getText().toString());

                threadpool.execute(new Password(c,l));

            }
        });

        handler= new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {

                if(message.what==Password.START_K){
                    progressDialog.setProgress(0);
                    progressDialog.setMax(Integer.parseInt(count.getText().toString()));
                    progressDialog.show();
                    Log.d("msg", "Start Thread");
                }else if(message.what==Password.STOP){


                        progressDialog.dismiss();
                        final CharSequence[] charSequences= message.getData().getStringArrayList(Password.KEY_STROKE).toArray(new CharSequence[message.getData().getStringArrayList(Password.KEY_STROKE).size()]);
                        final AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Passwords");
                        final AlertDialog ab= builder.create();
                        builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                pass.setText(charSequences[i]);
                                dialogInterface.dismiss();
                            }
                        });

                        builder.show();
                    }

                else if(message.what==Password.PROGRESS){
                    progressDialog.incrementProgressBy(1);
                    Log.d("msg", "Start Thread"+message.getData().getSerializable(Password.KEY_STROKE));
                }
                return false;

            }
        });

        findViewById(R.id.btn_asyncTask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PasswordAsync(Integer.parseInt(count.getText().toString())).execute(Integer.parseInt(count.getText().toString()),
                        Integer.parseInt(length.getText().toString()));
            }
        });
    }

    class Password implements Runnable,Serializable {

        public static final String KEY_STROKE= "message";
        public static final int START_K= 0x00;
        public static final int STOP= 0x02;
        public static final int PROGRESS= 0x01;
        ArrayList<String> passwords;
        int counts;
        int length;

        public Password(int count,int length) {
            this.counts = count;
            this.length=length;
            passwords = new ArrayList<>();
        }

        @Override

        public void run() {


            Message msg1= new Message();
            msg1.what= START_K;
            handler.sendMessage(msg1);

                for (int i = 0; i < counts; i++) {
                    String pass = Util.getPassword(length);
                    passwords.add(pass);

                    Log.d("name", pass);
                    Message msg2 = new Message();
                    msg2.what=PROGRESS;
                    Bundle bundle= new Bundle();
                    bundle.putInt(KEY_STROKE,(Integer)counts);
                    msg2.setData(bundle);
                    handler.sendMessage(msg2);

                }


                Message message= new Message();
                message.what= STOP;
                Bundle bundle= new Bundle();
                Log.d("name", "stop");
                bundle.putStringArrayList(KEY_STROKE,passwords);
                message.setData(bundle);
                handler.sendMessage(message);



        }



    }

    class PasswordAsync extends AsyncTask<Integer , Integer, ArrayList<String>> {
        ArrayList<String> passwords;
        int coun;

        public PasswordAsync(int coun){
            this.coun=coun;
        }
        @Override
        protected ArrayList<String> doInBackground(Integer... integers) {
            passwords = new ArrayList<>();
                for (int i = 0; i < integers[0]; i++) {
                    String pass = Util.getPassword(integers[1]);
                    passwords.add(pass);
                    Log.d("name", pass);
                    publishProgress(i);
                }


            return passwords;

        }

        @Override
        protected void onPreExecute() {
            progressDialog= new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Generating Passwords Async...");
            progressDialog.setProgress(0);
            progressDialog.setMax((Integer)coun);
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();

        }



        @Override
        protected void onPostExecute(ArrayList<String> s) {
            Log.d("name","postexevute");
            progressDialog.dismiss();
            final CharSequence[] charSequences= s.toArray(new CharSequence[s.size()]);
            final AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Passwords");
            final AlertDialog ab= builder.create();
            builder.setItems(charSequences, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    pass.setText(charSequences[i]);
                    dialogInterface.dismiss();

                }});
            builder.show();
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressDialog.incrementProgressBy(1);


        }

    }
}

