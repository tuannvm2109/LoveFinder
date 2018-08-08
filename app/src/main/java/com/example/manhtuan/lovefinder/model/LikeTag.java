package com.example.manhtuan.lovefinder.model;

import java.io.Serializable;

public class LikeTag implements Serializable{
    private int likePoint;
    private String name;

    public LikeTag() {
    }

    public LikeTag(int likePoint, String name) {
        this.likePoint = likePoint;
        this.name = name;
    }

    public int getLikePoint() {
        return likePoint;
    }

    public void setLikePoint(int likePoint) {
        this.likePoint = likePoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
