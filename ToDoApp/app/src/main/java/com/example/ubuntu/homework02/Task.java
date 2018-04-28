package com.example.ubuntu.homework02;

/*
Homework 02
Sarangdeep Singh
Ishan Agarwal
Group 05
 */
import java.io.Serializable;

public class Task implements Serializable{

    private String title;
    private String date;
    private String time;
    private String priority;


    public Task() {
    }

    public Task(String title, String date, String time, String priority) {
        this.title = title;
        this.date = date;
        this.time = time;
        this.priority = priority;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
