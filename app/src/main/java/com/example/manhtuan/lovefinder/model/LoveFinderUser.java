package com.example.manhtuan.lovefinder.model;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class LoveFinderUser extends FacebookUser {
    private String userId;
    private List<String> threads;
    public LoveFinderUser(){

    }

    public LoveFinderUser(FacebookUser facebookUser) {
        super(facebookUser.getName(), facebookUser.getEmail(),
                facebookUser.getFbId(), facebookUser.getLocation(),
                facebookUser.getGender(), facebookUser.getBirthday(),
                facebookUser.getHometown(),facebookUser.getQuotes(),
                facebookUser.getPhotoURL(),facebookUser.getCategories(),
                facebookUser.getPlaceTags());
        this.userId = null;
        threads = new ArrayList<>();
    }

    public float compareOtherUser(LoveFinderUser otherUser){
        float point = 0;
        List<Category> categoryListOtherUser = new ArrayList<>();
        for(Category category1 : super.getCategories()){
            for(Category category2 : otherUser.getCategories()){
                if(category1.getName().equals(category2.getName())){
                    category2.setComparePoint(category1);
                    float samePoint = category2.getComparePoint();
                    if(samePoint > 1 && category2.getLikeTags().size() > 1){
                        categoryListOtherUser.add(category2);
                        point += samePoint;
                    }

                }
            }
        }

        Collections.sort(categoryListOtherUser, new Comparator<Category>() {
            @Override
            public int compare(Category c1, Category c2) {
                if(c1.getComparePoint() > c2.getComparePoint()) return -1;
                else return 1;
            }
        });


        otherUser.setCategories(categoryListOtherUser);
//        for(Category p : otherUser.getCategories()){
//            Log.d("asdtestcategory","CATEGORY : " + p.getName() + "  " + p.getLikePoint());
//            for(LikeTag lt : p.getLikeTags()){
//                Log.d("asdtestcategory","tag : " + lt.getName() + " " + lt.getLikePoint());
//            }
//        }
        return point;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getThreads() {
        return threads;
    }

    public void setThreads(List<String> threads) {
        this.threads = threads;
    }
}
