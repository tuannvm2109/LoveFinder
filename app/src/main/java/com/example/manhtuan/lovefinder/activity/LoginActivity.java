package com.example.manhtuan.lovefinder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.manhtuan.lovefinder.NLP.OpenNLPSentimentAnalysis;
import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.Category;
import com.example.manhtuan.lovefinder.model.FacebookUser;
import com.example.manhtuan.lovefinder.model.LikeTag;
import com.example.manhtuan.lovefinder.model.PlaceTag;
import com.example.manhtuan.lovefinder.model.PostTag;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {
    private LoginButton loginButtonFacebook;
    private CallbackManager callbackManager;
    private FacebookUser fbUser;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private boolean checkUserNew;
    private Set<String> likeTagEntityList;
    private OpenNLPSentimentAnalysis NLP_SA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        try {
//            PackageInfo info = null;
//            try {
//                info = getPackageManager().getPackageInfo(
//                        "com.example.manhtuan.appfacebook",
//                        PackageManager.GET_SIGNATURES);
//            } catch (PackageManager.NameNotFoundException e) {
//                e.printStackTrace();
//            }
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (NoSuchAlgorithmException e) {
//
//        }
        fbUser = new FacebookUser();
        callbackManager = CallbackManager.Factory.create();
        setNLP();
        setLoginButton();
    }

    private void setNLP() {
        //SentimentAnalysis
        InputStream trainData = null;
        try {
            trainData = getAssets().open("traindata.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        NLP_SA = new OpenNLPSentimentAnalysis(trainData);
        NLP_SA.trainModel();

        //NamedEntityRecognition
        likeTagEntityList = new HashSet<>();
        InputStream liketagStream = null;
        try {
            liketagStream = getAssets().open("like_tag.txt");
            BufferedReader in= new BufferedReader(
                    new InputStreamReader(liketagStream, "UTF-8"));
            String result;

            while ((result=in.readLine()) != null) {
                result = result.trim();
                likeTagEntityList.add(result);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setLoginButton() {
        loginButtonFacebook = findViewById(R.id.buttonLoginFacebook);
        loginButtonFacebook.setReadPermissions(Arrays.asList(
                "public_profile","email","user_hometown","user_location","user_birthday",
                "user_likes","user_gender","user_photos","user_posts","user_tagged_places"));
        loginButtonFacebook.registerCallback(callbackManager,new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                handleFacebookAccessToken(loginResult.getAccessToken());
                getUserInformation();
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",fbUser);
                bundle.putString("userId",firebaseUser.getUid());
                bundle.putBoolean("checkUserNew",checkUserNew);
                Intent intent = new Intent(LoginActivity.this,MainActivityLogged.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        checkUserNew = task.getResult().getAdditionalUserInfo().isNewUser();
                    } else {
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }

                }
            });
    }
    private void getUserInformation() {
        //GraphRequest getUserInfo
        final GraphRequest graphRequest = GraphRequest.newMeRequest(
            AccessToken.getCurrentAccessToken(),
            new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
//                    Log.d("responseFacebook",response.toString());
                    fbUser.setName(getPersionalInfoResponse(response,"name"));
                    fbUser.setFbId(getPersionalInfoResponse(response,"id"));
                    fbUser.setEmail(getPersionalInfoResponse(response,"email"));
                    fbUser.setGender(getPersionalInfoResponse(response,"gender"));
                    fbUser.setLocation(getPersionalInfoResponse(response,"location"));
                    fbUser.setBirthday(getPersionalInfoResponse(response,"birthday"));
                    fbUser.setQuotes(getPersionalInfoResponse(response,"quotes"));
                    fbUser.setHometown(getPersionalInfoResponse(response,"hometown"));
//                    fbUser.setMusic(getHobbyResponse(response,"music"));
//                    fbUser.setMovie(getHobbyResponse(response,"movies"));
//                    fbUser.setGame(getHobbyResponse(response,"games"));
//                    fbUser.setLike(getHobbyResponse(response,"likes"));
//                    fbUser.setAthletes(getHobbyResponse(response,"favorite_athletes"));
//                    fbUser.setTeam(getHobbyResponse(response,"favorite_teams"));
                    fbUser.setPlaceTags(getPlaceTagResponse(response));
                    fbUser.setCategories(getCategoryAndLikeTagResponse(response));
                    getProfileAlbum(response);
//                    for(Category p : fbUser.getCategories()){
//                        Log.d("asdcategory","CATEGORY : " + p.getName() + "  " + p.getLikePoint());
//                        for(LikeTag lt : p.getLikeTags()){
//                            if(!lt.getName().equals(""))
//                            Log.d("asdcategory","tag : " + lt.getName() + " " + lt.getLikePoint());
//                        }
//                    }
                }
            });
        Bundle parameters = new Bundle();
        parameters.putString("fields",
                "id,name,email,location,birthday,gender,music.limit(100),albums," +
                "movies.limit(100),games.limit(100),likes.limit(100){about,name}," +
                "favorite_athletes.limit(100),favorite_teams.limit(100),quotes,hometown," +
                "posts.limit(100){message,name,description},tagged_places,photos.limit(100){images}"
        );
        graphRequest.setParameters(parameters);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                graphRequest.executeAndWait();
            }
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private String getPersionalInfoResponse(GraphResponse response,String para){
        String field = "";
        try{
            if(para.equals("location") || para.equals("hometown")){
                if(!response.getJSONObject().isNull(para)){
                    JSONObject locationJSON = response.getJSONObject().getJSONObject(para);
                    field = locationJSON.getString("name");
                }
            }
            else if(para.equals("birthday")){
                if(!response.getJSONObject().isNull(para)){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                    field = response.getJSONObject().getString(para);
                }
            }
            else{
                if (!response.getJSONObject().isNull(para)) {
                    field = response.getJSONObject().getString(para);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return field;
    }
    private List<String> getHobbyResponse(GraphResponse response,String para){
        List<String> hobbyList = null;
        try{
            if(para.equals("favorite_athletes") || para.equals("favorite_teams")){
                if(!response.getJSONObject().isNull(para)) {
                    hobbyList = new ArrayList<>();
                    JSONArray jsonObjectHobby = response.getJSONObject().getJSONArray(para);
                    for (int i = 0; i < jsonObjectHobby.length(); i++) {
                        JSONObject jsonObject = jsonObjectHobby.getJSONObject(i);
                        String hobbyName = jsonObject.getString("name");
                        hobbyList.add(hobbyName);
                    }
                }
            }
            else
            if(!response.getJSONObject().isNull(para)) {
                hobbyList = new ArrayList<>();
                JSONObject jsonObjectHobby = response.getJSONObject().getJSONObject(para);
                JSONArray data = jsonObjectHobby.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String hobbyName = jsonObject.getString("name");
                    hobbyList.add(hobbyName);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hobbyList;
    }
    private void getProfileAlbum(GraphResponse response){
        try{
            String id;
            JSONObject jsonObject = response.getJSONObject().getJSONObject("albums");
            final JSONArray jsonArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.getJSONObject(i).getString("name").equals("Profile Pictures")) {
                    id = jsonArray.getJSONObject(i).getString("id");
                    new GraphRequest(
                            AccessToken.getCurrentAccessToken(),
                            "/" + id + "?fields=photos{images},name",
                            null,
                            HttpMethod.GET,
                            new GraphRequest.Callback() {
                                public void onCompleted(GraphResponse responsePhoto) {
//                                                Log.d("asdresponse",responsePhoto.toString());
                                    fbUser.setPhotoURL(getPhotoResponse(responsePhoto));
                                }
                            }
                    ).executeAndWait();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private List<String> getPhotoResponse(GraphResponse response){
        List<String> photoList = null;
        try{
            photoList = new ArrayList<>();
                JSONObject jsonObjectPhoto = response.getJSONObject().getJSONObject("photos");
                JSONArray photoData = jsonObjectPhoto.getJSONArray("data");
                for (int j = 0; j < photoData.length(); j++) {
                    JSONObject jsonObjectOfPhotoData = photoData.getJSONObject(j);
                    if(!jsonObjectOfPhotoData.isNull("images")) {
                        JSONArray jsonArrayImages = jsonObjectOfPhotoData.getJSONArray("images");
                        if(!jsonArrayImages.isNull(2)) {
                            JSONObject jsonObjectImage = jsonArrayImages.getJSONObject(2);
                            String imageURL = jsonObjectImage.getString("source");
                            photoList.add(imageURL);
                        }
                    }
                }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return photoList;
    }
    private List<Category> getCategoryAndLikeTagResponse(GraphResponse response){
        List<Category> categoryList = null;
        try{
            if(!response.getJSONObject().isNull("likes")) {
                categoryList = new ArrayList<>();
                JSONObject jsonObjectHobby = response.getJSONObject().getJSONObject("likes");
                JSONArray data = jsonObjectHobby.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    String name = jsonObject.getString("name");
                    String about = "";
                    if(!jsonObject.isNull("about")) {
                        about = jsonObject.getString("about");
                    }

                    for(String[] lac : getLikeTagAndCategory(name + " " + about)){
                        String categoryName = lac[0];
                        LikeTag likeTag = new LikeTag();
                        likeTag.setName(lac[1]);
                        likeTag.setLikePoint(NLP_SA.getLikePoint(name));

                        if(!checkSameCategory(categoryList,categoryName,likeTag)){
                            List<LikeTag> likeTagList = new ArrayList<>();
                            likeTagList.add(likeTag);
                            categoryList.add(new Category(categoryName,likeTagList));
                        }
                    }
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        sortCategory(categoryList);
        return categoryList;
    }
    private void sortCategory(List<Category> categoryList){
        for(Category category : categoryList){
            Collections.sort(category.getLikeTags(), new Comparator<LikeTag>() {
                @Override
                public int compare(LikeTag lt1, LikeTag lt2) {
                    if(lt1.getLikePoint() >= lt2.getLikePoint()) return -1;
                    else return 1;
                }
            });
        }
        Collections.sort(categoryList, new Comparator<Category>() {
            @Override
            public int compare(Category cat1, Category cat2) {
                if(cat1.getLikePoint() >= cat2.getLikePoint()) return -1;
                else return 1;
            }
        });
    }
    private List<String[]> getLikeTagAndCategory(String name) {
//        Log.d("asdfffffh",name);
        List<String[]> stringsList = new ArrayList<>();
        for(String s : likeTagEntityList){
            String[] words = s.split(",");
            for (int i = 1; i < words.length; i++) {
                String nameCheck,wordCheck;
                if(words[i].contains(" ")){
                    wordCheck = words[i].toLowerCase();
                    nameCheck = name.toLowerCase();
                }
                else {
                    wordCheck = " " + words[i] + " ";
//                    StringBuilder stringBuilder = new StringBuilder(" ");
//                    for (int k = 0 ; k < s.length() ; k++) {
//                        char c = s.charAt(k);
//                        if (!Character.isLetter(c)) {
//                            c = ' ';
//                        }
//                        stringBuilder.append(c);
//                    }
//                    stringBuilder.append(" ");
//                    nameCheck = stringBuilder.toString();
//                    System.out.println(stringBuilder.toString());

//                    nameCheck = " " + name.replaceAll("\\W"," ") + " ";
                    nameCheck = name;

                }
                if(nameCheck.contains(wordCheck)){
                    String[] strings;
                    if(words[1].equals(words[0])){
                        strings = new String[]{words[0],""};
                    }
                    else {
                        strings = new String[]{words[0],words[1].trim()};
                    }
                    stringsList.add(strings);
                    break;
                }
            }
        }
//        for(String[] strings : stringsList){
//            Log.d("asdfffffh",strings[0] + " --- " + strings[1]);
//        }
//        Log.d("asdfffffh",stringsList.size() +"");
        return stringsList;
    }
    private boolean checkSameCategory(List<Category> categoryList,String categoryName, LikeTag likeTag){
        for(Category cat : categoryList){
            if(cat.getName().equals(categoryName)){
                if(!checkSameLikeTag(cat.getLikeTags(),likeTag)){
                    cat.getLikeTags().add(likeTag);
                }
                return true;
            }
        }
        return false;

    }
    private boolean checkSameLikeTag(List<LikeTag> likeTagList,LikeTag likeTag){
        for(LikeTag lt : likeTagList){
            if(lt.getName().equals(likeTag.getName())){
                lt.setLikePoint(lt.getLikePoint() + likeTag.getLikePoint());
                return true;
            }
        }
        return false;

    }
    private List<PlaceTag> getPlaceTagResponse(GraphResponse response){
        List<PlaceTag> placeTagList = new ArrayList<>();
        try{
            if(!response.getJSONObject().isNull("tagged_places")) {
                JSONObject jsonObjectPost = response.getJSONObject().getJSONObject("tagged_places");
                JSONArray data = jsonObjectPost.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    //get time
                    String created_time = jsonObject.getString("created_time");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date time = sdf.parse(created_time.substring(0,10));
                    long timeLong = time.getTime();
                    //get name
                    JSONObject jsonPlace = jsonObject.getJSONObject("place");
                    String name = jsonPlace.getString("name");
                    //get latitude and longitude
                    JSONObject jsonLocation = jsonPlace.getJSONObject("location");
                    double latitude = Double.parseDouble(jsonLocation.getString("latitude"));
                    double longitude = Double.parseDouble(jsonLocation.getString("longitude"));

                    PlaceTag placeTag = new PlaceTag(timeLong,name,latitude,longitude);
//                    Log.d("asdplacetag",time.toString()+"");
//                    Log.d("asdplacetag",name);
//                    Log.d("asdplacetag",latitude +"");
//                    Log.d("asdplacetag",longitude +"");
                    placeTagList.add(placeTag);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return placeTagList;
    }




    private List<PostTag> getPostTagResponse(GraphResponse response){
        List<PostTag> postTagList = new ArrayList<>();;
        try{
            if(!response.getJSONObject().isNull("posts")) {
                JSONObject jsonObjectPost = response.getJSONObject().getJSONObject("posts");
                JSONArray data = jsonObjectPost.getJSONArray("data");
                for (int i = 0; i < data.length(); i++) {
                    JSONObject jsonObject = data.getJSONObject(i);
                    if(!jsonObject.isNull("name")) {
                        String shareName = jsonObject.getString("name");
                        if(!jsonObject.isNull("description")){
                            shareName += jsonObject.getString("description");
                        }
                        for(String nameTag : getNameTag(shareName)){
                            PostTag postTag = new PostTag();
                            postTag.setNameTag(nameTag);
                            if(!jsonObject.isNull("message")) {
                                String message = jsonObject.getString("message");
                                postTag.setLikePoint(NLP_SA.getLikePoint(message));
                            }
                            else postTag.setLikePoint(1);
                            if(!checkSamePostTag(postTagList,postTag))
                                postTagList.add(postTag);
                        }
                    }
                    else{
                        if(!jsonObject.isNull("message")) {
                            String message = jsonObject.getString("message");
                            for(String nameTag : getNameTag(message)){
                                PostTag postTag = new PostTag();
                                postTag.setNameTag(nameTag);
                                postTag.setLikePoint(NLP_SA.getLikePoint(message));
                                if(!checkSamePostTag(postTagList,postTag)){
                                    postTagList.add(postTag);

                                }
                            }
                        }
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return postTagList;
    }
    private Set<String> getNameTag(String name) {
        Set<String> nameTagList = new HashSet<>();
        for(String s : likeTagEntityList){
            String[] words = s.split(",");
            for (int i = 0; i < words.length; i++) {
                if(name.toLowerCase().contains(words[i])){
                    nameTagList.add(words[0]);
                    break;
                }
            }
        }
        return nameTagList;
    }
    private boolean checkSamePostTag(List<PostTag> postTagList,PostTag postTag){
        for(PostTag pt : postTagList){
            if(pt.getNameTag().equals(postTag.getNameTag())){
                pt.setLikePoint(pt.getLikePoint() + postTag.getLikePoint());
                return true;
            }
        }
        return false;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        LoginManager.getInstance().logOut();
        super.onStart();
    }
//@Override
//public void onStart() {
//    super.onStart();
//    // Check if user is signed in (non-null) and update UI accordingly.
//    FirebaseUser currentUser = mAuth.getCurrentUser();
//    updateUI(currentUser);
//}

}
