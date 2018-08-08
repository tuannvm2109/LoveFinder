package com.example.manhtuan.lovefinder.model;

import com.example.manhtuan.lovefinder.activity.MainActivityLogged;

public class Message {
    private String userID;
    private String content;
    private long timeLong;

    public Message(){

    }
    public Message(String userID, String content, long timeLong) {
        this.userID = userID;
        this.content = content;
        this.timeLong = timeLong;
    }
    public boolean fromUser(){
        if(userID.equals(MainActivityLogged.loveFinderUser.getUserId())) return true;
        else return false;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimeLong() {
        return timeLong;
    }

    public void setTimeLong(long timeLong) {
        this.timeLong = timeLong;
    }
}
