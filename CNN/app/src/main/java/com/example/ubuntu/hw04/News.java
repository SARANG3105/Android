package com.example.ubuntu.hw04;

/*
HW04
Sarangdeep Singh
Ishan Agarwal
Group05
 */

class News {
    String description;
    String title;
    String pubdate;
    String imageUrl;
    String newsLink;

    public News(){

    }

    @Override
    public String toString() {
        return "News{" +
                "description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", pubdate='" + pubdate + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", newsLink='" + newsLink + '\'' +
                '}';
    }
}
