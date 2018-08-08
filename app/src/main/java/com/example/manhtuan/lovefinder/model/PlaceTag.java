package com.example.manhtuan.lovefinder.model;

import java.io.Serializable;
import java.util.Date;

public class PlaceTag implements Serializable{
    private long time;
    private String name;
    private double latitude;
    private double longitude;

    public PlaceTag() {
    }

    public PlaceTag(long time, String name, double latitude, double longitude) {
        this.time = time;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
