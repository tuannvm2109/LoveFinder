package com.example.manhtuan.lovefinder.model;

import java.io.Serializable;

public class PostTag implements Serializable{
    private int likePoint;
    private String nameTag;

    public PostTag() {
    }

    public PostTag(int likePoint, String nameTag) {
        this.likePoint = likePoint;
        this.nameTag = nameTag;
    }

    public int getLikePoint() {
        return likePoint;
    }

    public void setLikePoint(int likePoint) {
        this.likePoint = likePoint;
    }

    public String getNameTag() {
        return nameTag;
    }

    public void setNameTag(String nameTag) {
        this.nameTag = nameTag;
    }
}
