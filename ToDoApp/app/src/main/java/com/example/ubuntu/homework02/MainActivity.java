package com.example.ubuntu.homework02;
/*
Homework 02
Sarangdeep Singh
Ishan Agarwal
Group 05
 */
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    public static String NAME_KEY = "name";
    public static String NAME_KEY2 = "name1";
    public static int REQ_CODE = 100;
    public static int REQ_CODE2 = 1001;
    private TextView taskName;
    private TextView totalTasks;
    private TextView priority;
    private TextView date;
    private TextView time;
    private LinkedList<Task> task_list;
    private String[] ind;
    int index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("View Tasks");

        task_list = new LinkedList<>();
        taskName = findViewById(R.id.taskTitle);
        totalTasks = findViewById(R.id.task_count);
        priority = findViewById(R.id.taskPriority);
        date = findViewById(R.id.taskDate);
        time = findViewById(R.id.taskTime);


        //listener on add button
        findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateTaskActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });
        //listener on delete button
        findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!task_list.isEmpty()) {
                    String[] val = totalTasks.getText().toString().split(" ");
                    int id = Integer.parseInt(val[1]);
                    int total = Integer.parseInt(val[3]);
                    Task task = task_list.get(id - 1);
                    task_list.remove(task);
                    if (task_list.size() != 0) {
                       // if (id == 1) {
                            Task task1 = task_list.getFirst();
                            taskName.setText(task1.getTitle());
                            date.setText(task1.getDate());
                            time.setText(task.getTime());
                            totalTasks.setText("Task " + (task_list.indexOf(task1) + 1) + " of " + task_list.size());
                            priority.setText(task1.getPriority() + " Priority");
                     /*   } else if (id == total) {
                            Task task1 = task_list.getLast();
                            taskName.setText(task1.getTitle());
                            date.setText(task1.getDate());
                            time.setText(task.getTime());
                            totalTasks.setText("Task " + (task_list.indexOf(task1) + 1) + " of " + task_list.size());
                            priority.setText(task1.getPriority() + " Priority");
                        }*/
                    } else if (task_list.isEmpty()) {
                        taskName.setText("Press + to add Task");
                        priority.setText("");
                        date.setText("");
                        time.setText("");
                        totalTasks.setText("Task " + task_list.size() + " of " + task_list.size());
                        Toast.makeText(MainActivity.this, "No more tasks", LENGTH_LONG).show();


                    }
                }
            }
        });

        //listener on previous button
        findViewById(R.id.btn_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] str = totalTasks.getText().toString().split(" ");
                if (Integer.parseInt(str[1]) > 1) {
                    int id = Integer.parseInt(str[1]) - 2;
                    Task task = task_list.get(id);
                    taskName.setText(task.getTitle());
                    priority.setText(task.getPriority() + " Priority");
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    totalTasks.setText("Task " + (task_list.indexOf(task) + 1) + " of " + task_list.size());
                }

            }
        });

        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] str = totalTasks.getText().toString().split(" ");
                if (Integer.parseInt(str[1]) < Integer.parseInt(str[3])) {
                    int id = Integer.parseInt(str[1]);
                    Task task = task_list.get(id);
                    taskName.setText(task.getTitle());
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    priority.setText(task.getPriority() + " Priority");
                    totalTasks.setText("Task " + (task_list.indexOf(task) + 1) + " of " + task_list.size());
                }

            }
        });

        //listener on Goto First button
        findViewById(R.id.btn_fastRewind).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!task_list.isEmpty()) {
                    Task task = task_list.getFirst();
                    taskName.setText(task.getTitle());
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    totalTasks.setText("Task " + (task_list.indexOf(task) + 1) + " of " + task_list.size());
                    priority.setText(task.getPriority() + " Priority");
                }
            }
        });

        //listener on Goto last button
        findViewById(R.id.btn_fastForward).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!task_list.isEmpty()) {
                    Task task = task_list.getLast();
                    taskName.setText(task.getTitle());
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    totalTasks.setText("Task " + (task_list.indexOf(task) + 1) + " of " + task_list.size());
                    priority.setText(task.getPriority() + " Priority");
                }
            }
        });

        findViewById(R.id.btn_edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!task_list.isEmpty()) {
                    Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                    ind = totalTasks.getText().toString().split(" ");
                    Task task = new Task();
                    task.setTitle(taskName.getText().toString());
                    task.setPriority(priority.getText().toString());
                    task.setTime(time.getText().toString());
                    task.setDate(date.getText().toString());
                    intent.putExtra(NAME_KEY2, task);
                    startActivityForResult(intent, REQ_CODE2);
                } else {
                    Toast.makeText(MainActivity.this, "No tasks to edit", LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                Task task = (Task) data.getExtras().get(NAME_KEY);
                task_list.add(task);
                Collections.sort(task_list, new Comparator<Task>() {
                    @Override
                    public int compare(Task task, Task t1)  {
                        int i=0;
                        try {
                            String sDate1 = task.getDate();
                            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                            String sDate2 = t1.getDate();
                            Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate2);
                            String stime1 = task.getTime();
                            Date time1 = new SimpleDateFormat("hh:mm aaa").parse(stime1);
                            String stime2 = t1.getTime();
                            Date time2 = new SimpleDateFormat("hh:mm aaa").parse(stime2);
                            if(!date1.equals(date2)) {
                                i = date1.compareTo(date2);
                                Log.d("da", Integer.toString(i));
                            }else if(date1.equals(date2)){
                                i=time1.compareTo(time2);
                            }

                        }catch(ParseException e){
                            Log.d("da", "hello");

                        }
                        return i;
                    }});
                if (task_list.isEmpty()) {
                    taskName.setText("Press + to add Task");
                    totalTasks.setText("Task " + task_list.size() + " of " + task_list.size());

                } else {
                    taskName.setText(task.getTitle());
                    totalTasks.setText("Task " + (task_list.indexOf(task)+1) + " of " + task_list.size());
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    priority.setText(task.getPriority() + " Priority");

                }

            }
        }
        if (requestCode == REQ_CODE2) {
            if (resultCode == RESULT_OK) {

                Task task = (Task) data.getExtras().get(NAME_KEY2);
                int id = Integer.parseInt(ind[1])-1;
                Log.d("da",Integer.toString(id-1));
                task_list.remove(id);
                task_list.add(id, task);
                Collections.sort(task_list, new Comparator<Task>() {
                    @Override
                    public int compare(Task task, Task t1)  {
                        int i=0;
                        try {
                            String sDate1 = task.getDate();
                            Date date1 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate1);
                            String sDate2 = t1.getDate();
                            Date date2 = new SimpleDateFormat("MM/dd/yyyy").parse(sDate2);
                            String stime1 = task.getTime();
                            Date time1 = new SimpleDateFormat("hh:mm aaa").parse(stime1);
                            String stime2 = t1.getTime();
                            Date time2 = new SimpleDateFormat("hh:mm aaa").parse(stime2);
                            if(!date1.equals(date2)) {
                                i = date1.compareTo(date2);
                                Log.d("da", Integer.toString(i));
                            }else if(date1.equals(date2)){
                                i=time1.compareTo(time2);
                            }

                        }catch(ParseException e){
                            Log.d("da", "hello");

                        }
                        return i;
                    }});
                Log.d("da",task_list.get(id).getTitle());
                if (task_list.isEmpty()) {

                    taskName.setText("Press + to add Task");
                    totalTasks.setText("Task " + task_list.size() + " of " + task_list.size());

                } else {
                    taskName.setText(task.getTitle());
                    totalTasks.setText("Task " + (task_list.indexOf(task)+1) + " of " + task_list.size());
                    date.setText(task.getDate());
                    time.setText(task.getTime());
                    priority.setText(task.getPriority() + " Priority");


                }
            }
        }
    }
}
