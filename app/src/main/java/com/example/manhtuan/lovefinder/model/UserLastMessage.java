package com.example.manhtuan.lovefinder.model;

public class UserLastMessage {
    private LoveFinderUser loveFinderUser;
    private String lastMessage;

    public UserLastMessage(LoveFinderUser loveFinderUser, String lastMessage) {
        this.loveFinderUser = loveFinderUser;
        this.lastMessage = lastMessage;
    }

    public LoveFinderUser getLoveFinderUser() {
        return loveFinderUser;
    }

    public void setLoveFinderUser(LoveFinderUser loveFinderUser) {
        this.loveFinderUser = loveFinderUser;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }
}
