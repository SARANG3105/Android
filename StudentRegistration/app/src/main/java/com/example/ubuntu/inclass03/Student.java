package com.example.ubuntu.inclass03;

import java.io.Serializable;
/*GROUP 5
Sarangdeep Singh
Ishan Agarwal

 */

/**
 * Created by ubuntu on 1/29/18.
 */

public class Student implements Serializable{

    private String name;
    private String email;
    private String department;
    private String mood;


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public String getMood() {
        return mood;
    }


    public Student(String name, String email, String department, String mood) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.mood = mood;
    }


    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", mood=" + mood +
                '}';
    }
}
