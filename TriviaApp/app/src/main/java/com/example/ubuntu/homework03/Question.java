package com.example.ubuntu.homework03;

import java.io.Serializable;
import java.util.Arrays;
/*
Group05
Sarangdeep Singh
Ishan Agarwal
Homework03
 */


public class Question implements Serializable {

    private String questionNo;
    private String question;
    private String imageLink;
    private String[] options;
    private String ans;

    public Question() {

    }

    public Question(String questionNo, String question, String imageLink, String[] options, String ans) {
        this.questionNo = questionNo;
        this.question = question;
        this.imageLink = imageLink;
        this.options = options;
        this.ans = ans;
    }

    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public String getAns() {
        return ans;
    }

    public void setAns(String ans) {
        this.ans = ans;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionNo='" + questionNo + '\'' +
                ", question='" + question + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", options=" + Arrays.toString(options) +
                ", ans='" + ans + '\'' +
                '}';
    }

}
