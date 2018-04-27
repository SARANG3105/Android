package com.example.ishanagarwal.inclass11;

import android.provider.ContactsContract;

import java.util.ArrayList;

/**
 * Created by ishanagarwal on 4/9/18.
 */

public class Photos {
InsidePhotos photos;



    public class Images{
        String url_o;

        @Override
        public String toString() {
            return "Images{" +
                    "url_o='" + url_o + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Photos{" +
                "photos=" + photos +
                '}';
    }
    public class InsidePhotos {
        ArrayList<Images> photo;

        public InsidePhotos(){
            photo= new ArrayList<>();
        }

        @Override
        public String toString() {
            return "InsidePhotos{" +
                    "photo=" + photo +
                    '}';
        }
    }
}