package com.example.manhtuan.lovefinder.model;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Category implements Serializable{
    private String name;
    private List<LikeTag> likeTags;
    private int totalPoint = 0;
    private float comparePoint = 0;
    public Category() {
    }

    public Category(String name, List<LikeTag> likeTags) {
        this.name = name;
        this.likeTags = likeTags;
        for(LikeTag likeTag : likeTags){
            totalPoint += likeTag.getLikePoint();
        }
    }

    public void setComparePoint(Category cat2) {
        float point = 0;
        point += (getGeneralPoint(this.getLikePoint(),cat2.getLikePoint()))/2;
        List<LikeTag> likeTagList1 = this.getLikeTags();
        List<LikeTag> likeTagList2 = cat2.getLikeTags();
        for(LikeTag lt1 : likeTagList1){
            for(LikeTag lt2 : likeTagList2){
                if(lt1.getName().equals(lt2.getName())){
                    point += getGeneralPoint(lt1.getLikePoint(),lt2.getLikePoint());
                }
            }
        }
        this.comparePoint = point;
    }

    @Exclude
    public float getComparePoint(){
        return this.comparePoint;
    }

    @Exclude
    private int getGeneralPoint(int point1,int point2){
        if((point1 * point2) >= 0){
            if(point1 >= 0){
                if(point1 >= point2){
                    return point2;
                }
                else return point1;
            }
            else {
                if(point1 >= point2){
                    return -point1;
                }
                else return -point2;
            }
        }
        else {
            if(Math.abs(point1) >= Math.abs(point2)){
                return -Math.abs(point2);
            }
            else return -Math.abs(point1);
        }
    }

    @Exclude
    public int getLikePoint(){
        return totalPoint;
    }
    public void setLikePoint(int likePoint) {
        this.totalPoint = likePoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<LikeTag> getLikeTags() {
        return likeTags;
    }

    public void setLikeTags(List<LikeTag> likeTags) {
        this.likeTags = likeTags;
    }
}
