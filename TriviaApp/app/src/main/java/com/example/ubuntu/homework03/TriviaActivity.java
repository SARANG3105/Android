package com.example.ubuntu.homework03;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import android.util.Log;
import android.widget.Toast;

import org.w3c.dom.Text;
/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */


public class TriviaActivity extends AppCompatActivity {
    public static String ANSWERS = "answers";
    public static int CHECKED = 10000;
    public static int PACKET = 100;
    private static String SEND = "msg";
    ArrayList<Question> questions = new ArrayList<>();
    private Button quit;
    private TextView questNo;
    private TextView quest;
    private Button next;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private TextView time;
    private boolean[] answers;
    private CountDownTimer countDownTimer;
    private TriviaAsyncTask taska;
    private boolean isPaused = false;

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
        isPaused=true;
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

    @Override
    protected void onStart() {
        super.onStart();

        if (isConnected() == true) {
            questions = (ArrayList<Question>) getIntent().getSerializableExtra(MainActivity.NAME);

                questNo.setText("Q" + (Integer.parseInt(questions.get(0).getQuestionNo()) + 1));

                quest.setText(questions.get(0).getQuestion());
                answers = new boolean[questions.size()];
                radioGroup = findViewById(R.id.radiogroup);
                radioGroup.removeAllViews();
                time = findViewById(R.id.textView);

                for (int i = 0; i < questions.get(0).getOptions().length; i++) {
                    RadioButton rdbtn = new RadioButton(TriviaActivity.this);
                    rdbtn.setId(98765 + i);
                    rdbtn.setText(questions.get(0).getOptions()[i]);
                    radioGroup.addView(rdbtn);
                }
                taska = (TriviaAsyncTask) new TriviaAsyncTask(TriviaActivity.this, imageView).execute(questions.get(0).getImageLink());
                countDownTimer = new CountDownTimer(120000, 1000) {

                    public void onTick(long millisUntilFinished) {

                        time.setText(getResources().getString(R.string.time) + millisUntilFinished / 1000+"seconds");
                        time.setBackgroundResource(R.color.colorPrimary);
                    }

                    public void onFinish() {
                        if(isPaused==true){
                            finish();
                        }else if(isPaused==false) {
                            Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
                            intent.putExtra(ANSWERS, answers);
                            startActivityForResult(intent, PACKET);
                        }

                    }
                }.start();
        } else if (isConnected() == false) {
            Toast.makeText(TriviaActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == PACKET) {
            countDownTimer.cancel();
            questNo.setText("");
            time.setText("");
            imageView.setImageBitmap(null);
            quest.setText("");
            onStart();
            Log.d("intent", "start");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        quit = findViewById(R.id.button_quit);
        questNo = findViewById(R.id.textView8);

        quest = findViewById(R.id.textView7);
        next = findViewById(R.id.button2);
        imageView = findViewById(R.id.imageView2);
        radioGroup = findViewById(R.id.radiogroup);
        questions = (ArrayList<Question>) getIntent().getSerializableExtra(MainActivity.NAME);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton btn = findViewById(checkedId);

                String[] arr = questNo.getText().toString().split("Q");
                int index = Integer.parseInt(arr[1]) - 1;
                if (checkedId != -1) {
                    if (btn.getText().toString().equals(questions.get(index).getOptions()[Integer.parseInt(questions.get(index).getAns())])) {
                        answers[index] = true;
                        Log.d("id", Boolean.toString(answers[index]));
                    } else {
                        answers[index] = false;
                    }

                    Log.d("id", btn.getText().toString());
                    Log.d("id", Integer.toString(answers.length));
                }
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPaused=true;
                countDownTimer.cancel();
                Intent intent = new Intent(TriviaActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //intent.putExtra("EXIT", true);
                Log.d("intent", "Done");
                startActivity(intent);
                finish();


            }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnected() == true) {
                    radioGroup.clearCheck();
                    radioGroup.removeAllViews();
                    String[] arr = questNo.getText().toString().split("Q");
                    int index = Integer.parseInt(arr[1]);
                    if (index < questions.size()) {
                        if (questions.get(index).getImageLink() == "" || questions.get(index).getImageLink() == null) {
                            questNo.setText("Q" + (Integer.parseInt(questions.get(index).getQuestionNo()) + 1));
                            quest.setText(questions.get(index).getQuestion());
                            for (int i = 0; i < questions.get(index).getOptions().length; i++) {
                                RadioButton rdbtn = new RadioButton(TriviaActivity.this);
                                rdbtn.setId(98765 + i);
                                rdbtn.setText(questions.get(index).getOptions()[i]);
                                radioGroup.addView(rdbtn);
                                rdbtn.setChecked(false);
                            }

                            imageView.setImageResource(R.drawable.trivia);
                        } else if (questions.get(index).getImageLink() != "" || questions.get(index).getImageLink() != null) {
                            taska = (TriviaAsyncTask) new TriviaAsyncTask(TriviaActivity.this, imageView).execute(questions.get(index).getImageLink());
                            questNo.setText("Q" + (Integer.parseInt(questions.get(index).getQuestionNo()) + 1));
                            quest.setText(questions.get(index).getQuestion());

                            for (int i = 0; i < questions.get(index).getOptions().length; i++) {
                                RadioButton rdbtn = new RadioButton(TriviaActivity.this);
                                rdbtn.setId(98765 + i);
                                rdbtn.setText(questions.get(index).getOptions()[i]);
                                rdbtn.setChecked(false);
                                radioGroup.addView(rdbtn);

                            }


                        }
                    }
                    else if (index == questions.size()) {
                        countDownTimer.cancel();
                        Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
                        intent.putExtra(ANSWERS, answers);
                        startActivityForResult(intent, PACKET);

                    }
                } else if (isConnected() == false) {

                    countDownTimer.cancel();
                    Intent intent = new Intent(TriviaActivity.this, StatsActivity.class);
                    intent.putExtra(ANSWERS, answers);
                    startActivityForResult(intent, PACKET);
                    Toast.makeText(TriviaActivity.this, "No Internet Connection", Toast.LENGTH_LONG).show();


                }
            }

        });
    }
}
