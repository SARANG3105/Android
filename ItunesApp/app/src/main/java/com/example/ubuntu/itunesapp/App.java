package com.example.ubuntu.itunesapp;

import java.io.Serializable;

/**
 * Created by ubuntu on 3/11/18.
 */

public class App implements Serializable {
    String appID;
    String appTitle;
    String appUrl;
    String devName;
    String price;
    String smallPhoto;
    String largePhoto;
    String currency;
    String releaseDate;

    @Override
    public String toString() {
        return "App{" +
                "appID='" + appID + '\'' +
                ", appTitle='" + appTitle + '\'' +
                ", appUrl='" + appUrl + '\'' +
                ", devName='" + devName + '\'' +
                ", price='" + price + '\'' +
                ", smallPhoto='" + smallPhoto + '\'' +
                ", largePhoto='" + largePhoto + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        App app = (App) o;

        if (appID != null ? !appID.equals(app.appID) : app.appID != null) return false;
        if (appTitle != null ? !appTitle.equals(app.appTitle) : app.appTitle != null) return false;
        if (appUrl != null ? !appUrl.equals(app.appUrl) : app.appUrl != null) return false;
        if (devName != null ? !devName.equals(app.devName) : app.devName != null) return false;
        if (price != null ? !price.equals(app.price) : app.price != null) return false;
        if (smallPhoto != null ? !smallPhoto.equals(app.smallPhoto) : app.smallPhoto != null)
            return false;
        return largePhoto != null ? largePhoto.equals(app.largePhoto) : app.largePhoto == null;
    }

    @Override
    public int hashCode() {
        int result = appID != null ? appID.hashCode() : 0;
        result = 31 * result + (appTitle != null ? appTitle.hashCode() : 0);
        result = 31 * result + (appUrl != null ? appUrl.hashCode() : 0);
        result = 31 * result + (devName != null ? devName.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (smallPhoto != null ? smallPhoto.hashCode() : 0);
        result = 31 * result + (largePhoto != null ? largePhoto.hashCode() : 0);
        return result;
    }
}

