package com.example.ubuntu.homework03;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */

public class MainAsyncTask extends AsyncTask<String, Void, ArrayList<Question>> {
    private ProgressDialog progressDialog;
    private Context context;
    private ImageView img;
    private TextView txt;
    private Button btn;
    private IList iList;


    public MainAsyncTask(Context context,IList iList){
        this.txt=txt;
        this.context=context;
        this.img=img;
        this.btn=btn;
        this.iList=iList;
    }
    @Override
    protected void onPreExecute() {
        progressDialog= new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle(R.string.progDialogTitle);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(ArrayList<Question> s) {
       progressDialog.dismiss();
       iList.handleList(s);
       iList.visibility(true);



    }

    @Override
    protected ArrayList<Question> doInBackground(String... strings) {
        HttpURLConnection connection=null;
        BufferedReader reader=null;
        ArrayList<Question> questions= new ArrayList<>();
        try {
            URL url = new URL(strings[0]);
            connection= (HttpURLConnection) url.openConnection();
            reader= new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line="";
            while((line=reader.readLine())!=null){
                String[] arr= line.split(";");
                int size= ((arr.length-1)-3);
                String[] opt= new String[size];
                String ans="";

                for(int i=3;i<arr.length-1;i++){
                    opt[i-3]=arr[i];
                    Log.d("length", Integer.toString(questions.size()));
                    ans=arr[arr.length-1];
                }

                Question question=new Question(arr[0], arr[1],arr[2],opt, ans);
                questions.add(question);
                Log.d("name",question.toString());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(reader!=null)
                    reader.close();
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return questions;
    }

    public static interface IList{
        public void handleList(ArrayList<Question> questions);
        public void visibility(boolean b);

    }

}
