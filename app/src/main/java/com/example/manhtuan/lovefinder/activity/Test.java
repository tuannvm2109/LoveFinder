package com.example.manhtuan.lovefinder.activity;

import android.util.Log;

import com.example.manhtuan.lovefinder.model.Category;
import com.example.manhtuan.lovefinder.model.LikeTag;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Test {
    public static void main(String[] args) {
        String s = "We're play EDM/ TRAP/ HIP HOP/ OPEN FORMAT and so on.\n" +
                "    We welcome all of you to come and have great time here. ";
        StringBuilder stringBuilder = new StringBuilder(" ");
        for (int k = 0 ; k < s.length() ; k++) {
            char c = s.charAt(k);
            if (!Character.isLetter(c)) {
                c = ' ';
            }
            stringBuilder.append(c);
        }
        stringBuilder.append(" ");
        System.out.println(stringBuilder.toString());
    }

}
