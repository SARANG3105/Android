package com.example.ubuntu.midterm1;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by ubuntu on 3/9/18.
 */

public class Movie implements Serializable {
    String movieName;
    String overview;
    String releaseDate;
    String rating;
    String popularity;
    String imageBaseUrl;

    @Override
    public String toString() {
        return "Movie{" +
                "movieName='" + movieName + '\'' +
                ", overview='" + overview + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                ", rating='" + rating + '\'' +
                ", popularity='" + popularity + '\'' +
                ", imageBaseUrl='" + imageBaseUrl + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (movieName != null ? !movieName.equals(movie.movieName) : movie.movieName != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        if (rating != null ? !rating.equals(movie.rating) : movie.rating != null) return false;
        if (popularity != null ? !popularity.equals(movie.popularity) : movie.popularity != null)
            return false;
        return imageBaseUrl != null ? imageBaseUrl.equals(movie.imageBaseUrl) : movie.imageBaseUrl == null;
    }

    @Override
    public int hashCode() {
        int result = movieName != null ? movieName.hashCode() : 0;
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        result = 31 * result + (imageBaseUrl != null ? imageBaseUrl.hashCode() : 0);
        return result;
    }
}
