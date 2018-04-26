package com.example.ubuntu.venueapp;

import java.io.Serializable;

/**
 * Created by ubuntu on 3/11/18.
 */

public class Venue implements Serializable {

    String venueId,venueName,categoryName,categoryIcon, checkInCount;

    @Override
    public String toString() {
        return "Venue{" +
                "venueId='" + venueId + '\'' +
                ", venueName='" + venueName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", categoryIcon='" + categoryIcon + '\'' +
                ", checkInCount='" + checkInCount + '\'' +
                '}';
    }
}
