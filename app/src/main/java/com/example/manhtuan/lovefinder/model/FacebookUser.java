package com.example.manhtuan.lovefinder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class FacebookUser implements Serializable{
    private String name;
    private String email;
    private String fbId;
    private String location;
    private String gender;
    private String birthday;
    private String hometown;
    private String quotes;
//    private List<String> music;
//    private List<String> movie;
//    private List<String> game;
//    private List<String> athletes;
//    private List<String> team;
//    private List<String> like;
    private List<String> photoURL;
    private List<Category> categories;
    private List<PlaceTag> placeTags;

    public FacebookUser(){
    }

    public FacebookUser(String name, String email, String fbId, String location,
                        String gender, String birthday, String hometown, String quotes,
                        List<String> photoURL, List<Category> categories, List<PlaceTag> placeTags) {
        this.name = name;
        this.email = email;
        this.fbId = fbId;
        this.location = location;
        this.gender = gender;
        this.birthday = birthday;
        this.hometown = hometown;
        this.quotes = quotes;
        this.photoURL = photoURL;
        this.categories = categories;
        this.placeTags = placeTags;
    }

    //    public FacebookUser(String name, String email, String fbId, String location,
//                        String gender, String birthday, String hometown, String quotes,
//                        List<String> music, List<String> movie, List<String> game,
//                        List<String> athletes, List<String> team, List<String> like,
//                        List<String> photoURL) {
//        this.name = name;
//        this.email = email;
//        this.fbId = fbId;
//        this.location = location;
//        this.gender = gender;
//        this.birthday = birthday;
//        this.hometown = hometown;
//        this.quotes = quotes;
//        this.music = music;
//        this.movie = movie;
//        this.game = game;
//        this.athletes = athletes;
//        this.team = team;
//        this.like = like;
//        this.photoURL = photoURL;
//    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public String getQuotes() {
        return quotes;
    }

    public void setQuotes(String quotes) {
        this.quotes = quotes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

//    public List<String> getMusic() {
//        return music;
//    }
//
//    public void setMusic(List<String> music) {
//        this.music = music;
//    }
//
//    public List<String> getMovie() {
//        return movie;
//    }
//
//    public void setMovie(List<String> movie) {
//        this.movie = movie;
//    }
//
//    public List<String> getGame() {
//        return game;
//    }
//
//    public void setGame(List<String> game) {
//        this.game = game;
//    }
//
//    public List<String> getAthletes() {
//        return athletes;
//    }
//
//    public void setAthletes(List<String> athletes) {
//        this.athletes = athletes;
//    }
//
//    public List<String> getTeam() {
//        return team;
//    }
//
//    public void setTeam(List<String> team) {
//        this.team = team;
//    }
//
//    public List<String> getLike() {
//        return like;
//    }
//
//    public void setLike(List<String> like) {
//        this.like = like;
//    }

    public List<String> getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(List<String> photoURL) {
        this.photoURL = photoURL;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<PlaceTag> getPlaceTags() {
        return placeTags;
    }

    public void setPlaceTags(List<PlaceTag> placeTags) {
        this.placeTags = placeTags;
    }
}
