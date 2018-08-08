package com.example.manhtuan.lovefinder.activity;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.fragment.FragmentHobby;
import com.example.manhtuan.lovefinder.fragment.FragmentMessage;
import com.example.manhtuan.lovefinder.model.FacebookUser;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.fragment.FragmentPeople;
import com.example.manhtuan.lovefinder.fragment.FragmentProfile;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareMessengerGenericTemplateContent;
import com.facebook.share.model.ShareMessengerGenericTemplateElement;
import com.facebook.share.model.ShareMessengerURLActionButton;
import com.facebook.share.widget.MessageDialog;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivityLogged extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private ImageView imgfb,imgtw,imggg,imgms;
    private ShareButton shareBtnFb;
    private Dialog dialog;
    private String userId;
    private Picasso picasso = Picasso.get();
    public static ImageView imageViewMenuNavigation;
    public static LoveFinderUser loveFinderUser;
    public static List<LoveFinderUser> peopleAround;
    public static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static final DatabaseReference databaseRef = database.getReference();
    public static final FirebaseStorage storage = FirebaseStorage.getInstance();
    public static final StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_logged);

        setData();
        setView();
        getPeopleAround();
    }

    private void setData() {
        peopleAround = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        FacebookUser facebookUser = (FacebookUser) bundle.getSerializable("user");
        userId = bundle.getString("userId");
        boolean checkUserNew = bundle.getBoolean("checkUserNew");
        loveFinderUser = new LoveFinderUser(facebookUser);
        if(checkUserNew){
            databaseRef.child("User").child(userId).setValue(loveFinderUser);
        }
//        databaseRef.child("User").child(userId).setValue(loveFinderUser);

    }

    // Sua di nha
    private void setView() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment = new FragmentPeople();
        Bundle bundle = new Bundle();
        bundle.putSerializable("peopleAround", (Serializable) peopleAround);
        bundle.putString("userId", userId);
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_framelayout,fragment);
        fragmentTransaction.addToBackStack("fpeople");
        fragmentTransaction.commit();

        imageViewMenuNavigation = findViewById(R.id.imagaViewNavigationMenu);
        imageViewMenuNavigation.setOnClickListener(this);
        setDialogShare();
    }
    private void setDialogShare(){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_share);
        dialog.getWindow().setLayout(350,350);
        imgfb = dialog.findViewById(R.id.imageViewIconFacebookDialogShare);
        imgtw = dialog.findViewById(R.id.imageViewIconTwitterDialogShare);
        imggg = dialog.findViewById(R.id.imageViewIconGoogleDialogShare);
        imgms = dialog.findViewById(R.id.imageViewIconMessengerDialogShare);
        shareBtnFb = dialog.findViewById(R.id.shareButtonFB);

        shareBtnFb.setOnClickListener(this);
        imgfb.setOnClickListener(this);
        imggg.setOnClickListener(this);
        imgms.setOnClickListener(this);
        imgtw.setOnClickListener(this);
    }

    private void getPeopleAround() {
        final ChildEventListener celGetPeopleAround = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                LoveFinderUser lfu = dataSnapshot.getValue(LoveFinderUser.class);
                lfu.setUserId(dataSnapshot.getKey());
                if(!lfu.getUserId().equals(userId)){
                    peopleAround.add(lfu);
                } else {
                    loveFinderUser = lfu;
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseRef.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setNavigationView();
                Collections.sort(peopleAround, new Comparator<LoveFinderUser>() {
                    @Override
                    public int compare(LoveFinderUser lfu1, LoveFinderUser lfu2) {
                        float cmp1 = loveFinderUser.compareOtherUser(lfu1);
                        float cmp2 = loveFinderUser.compareOtherUser(lfu2);
                        if (cmp1 < cmp2) {
                            return 1;
                        } else {
                            if (cmp1 == cmp2) {
                                return 0;
                            } else {
                                return -1;
                            }
                        }
                    }
                });
                FragmentPeople.customRecycleViewPeopleAdapter.notifyDataSetChanged();
                databaseRef.child("User").removeEventListener(celGetPeopleAround);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        databaseRef.child("User").addChildEventListener(celGetPeopleAround);
    }

    private void setNavigationView(){
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().hide();
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        ImageView imgHeader = headerLayout.findViewById(R.id.imageViewProfilePicture);
        TextView txtnameHeader = headerLayout.findViewById(R.id.textViewNameNavHeader);
        TextView txtemailHeader = headerLayout.findViewById(R.id.textViewEmailNavHeader);
        if (loveFinderUser.getPhotoURL() != null){
            picasso.load(loveFinderUser.getPhotoURL().get(0))
                    .resize(100,100)
                    .centerCrop()
                    .placeholder(R.drawable.img_profile_placeholder)
                    .error(R.drawable.img_profile_placeholder)
                    .into(imgHeader);
        }
        txtnameHeader.setText(loveFinderUser.getName());
        txtemailHeader.setText(loveFinderUser.getEmail());
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        Fragment fragment;
        if (id == R.id.menu_profile) {
            fragment = new FragmentProfile();
            Bundle bundle = new Bundle();
            bundle.putSerializable("user",loveFinderUser);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.nav_framelayout,fragment);
            fragmentTransaction.addToBackStack("fprofile");
            fragmentTransaction.commit();
        } else if (id == R.id.menu_your_hobby) {
            fragment = new FragmentHobby();
            fragmentTransaction.replace(R.id.nav_framelayout,fragment);
            fragmentTransaction.addToBackStack("fhobby");
            fragmentTransaction.commit();
        } else if (id == R.id.menu_home) {
            getPeopleAround();
        } else if (id == R.id.menu_message) {
            fragment = new FragmentMessage();
            Bundle bundle = new Bundle();
            bundle.putString("userId", userId);
            bundle.putSerializable("peopleAround", (Serializable) peopleAround);
            fragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.nav_framelayout,fragment);
            fragmentTransaction.addToBackStack("fmessage");
            fragmentTransaction.commit();
        } else if (id == R.id.menu_share) {
            dialog.show();
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() >0){
            int lastFragEntry = getFragmentManager().getBackStackEntryCount()-1;
            String lastFragTag = getFragmentManager().getBackStackEntryAt(lastFragEntry).getName();
            getFragmentManager().popBackStack();
            if(lastFragTag.equals("fchat")){
                imageViewMenuNavigation.setVisibility(View.VISIBLE);
            }
        }
        else super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imagaViewNavigationMenu:
                DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(Gravity.LEFT);
                break;
            case R.id.imageViewIconFacebookDialogShare :
                shareBtnFb.performClick();
                break;
            case R.id.shareButtonFB :
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse("https://developers.facebook.com"))
                        .build();
                shareBtnFb.setShareContent(content);
                break;
            case R.id.imageViewIconGoogleDialogShare:
                // Launch the Google+ share dialog with attribution to your app.
                Intent shareIntent = new PlusShare.Builder(this)
                        .setType("text/plain")
                        .setText("Welcome to the Google+ platform.")
                        .setContentUrl(Uri.parse("https://developers.google.com/+/"))
                        .getIntent();

                startActivityForResult(shareIntent, 0);
                break;
            case R.id.imageViewIconMessengerDialogShare:
////                Resources res = this.getResources();
////                String mimeType = "image/jpeg";
////                Uri contentUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
////                        "://" + res.getResourcePackageName(R.drawable.icon_birthday)
////                        + '/' + res.getResourceTypeName(R.drawable.icon_birthday)
////                        + '/' + res.getResourceEntryName(R.drawable.icon_birthday));
////                ShareToMessengerParams shareToMessengerParams =
////                        ShareToMessengerParams.newBuilder(contentUri, mimeType)
////                                .build();
////                // Sharing from an Activity
////                MessengerUtils.shareToMessenger(
////                        this,
////                        0,
////                        shareToMessengerParams);
                ShareMessengerURLActionButton actionButton =
                        new ShareMessengerURLActionButton.Builder()
                                .setTitle("Visit Facebook")
                                .setUrl(Uri.parse("https://www.facebook.com"))
                                .build();
                ShareMessengerGenericTemplateElement genericTemplateElement =
                        new ShareMessengerGenericTemplateElement.Builder()
                                .setTitle("Visit Facebook")
                                .setSubtitle("Visit Messenger")
//                                .setImageUrl(Uri.parse("heeps://Your/Image/Url"))
                                .setButton(actionButton)
                                .build();
                ShareMessengerGenericTemplateContent genericTemplateContent =
                        new ShareMessengerGenericTemplateContent.Builder()
                                .setPageId("https://www.facebook.com/manhtuan21") // Your page ID, required
                                .setGenericTemplateElement(genericTemplateElement)
                                .build();
                MessageDialog.show(this, genericTemplateContent);
                break;
            case R.id.imageViewIconTwitterDialogShare:
                Intent tweet = new Intent(Intent.ACTION_VIEW);
                String message = "manhtuan";
                tweet.setData(Uri.parse("http://twitter.com/?status=" + Uri.encode(message)));//where message is your string message
                startActivity(tweet);
                break;

        }
    }


    private void upProfilePictureToFireBase() {
//        ProfilePictureView profilePictureFB = (ProfilePictureView) findViewById(R.id.profilePictureUserMain);
//        profilePictureFB.setProfileId(loveFinderUser.getFbId());
//        ImageView fbImage = ( ( ImageView)profilePictureFB.getChildAt( 0));
//        Bitmap bitmap  = ( (BitmapDrawable) fbImage.getDrawable()).getBitmap();
        new LoadProfilePictureTask()
                .execute("https://graph.facebook.com/"+loveFinderUser.getFbId()+"/picture?type=large");
    }
    private class LoadProfilePictureTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap bitmap) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            StorageReference userRef = storageRef.child("UserImage").child(loveFinderUser.getUserId()).child("0");
            UploadTask uploadTask = userRef.putBytes(byteArray);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(MainActivityLogged.this, "Error upload image", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });
        }
    }
}
