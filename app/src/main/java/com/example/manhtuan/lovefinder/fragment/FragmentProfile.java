package com.example.manhtuan.lovefinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.customview.AttributeViewLayout;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.activity.MainActivityLogged;
import com.example.manhtuan.lovefinder.R;
import com.squareup.picasso.Picasso;

public class FragmentProfile extends Fragment {
    private ImageView imgProfilePicture;
    private LoveFinderUser loveFinderUser;
    private AttributeViewLayout name,birthday,gender,location,email,hometown;
    private TextView txtQuotes;
    private Picasso picasso = Picasso.get();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        bindView(view);
        getBundleArguments();

        picasso.load(loveFinderUser.getPhotoURL().get(0))
                .resize(400,400)
                .centerCrop()
                .placeholder(R.drawable.img_profile_placeholder)
                .into(imgProfilePicture);
        setAttributeViewLayout();
        return view;
    }

    private void setAttributeViewLayout() {
        txtQuotes.setText(loveFinderUser.getQuotes());

        name.setImg(R.drawable.ic_profile);
        name.setTxtThuocTinh(getString(R.string.profile_name));
        name.setTxtDacDiem(loveFinderUser.getName());

        birthday.setImg(R.drawable.ic_birthday);
        birthday.setTxtThuocTinh(getString(R.string.profile_birthday));
        birthday.setTxtDacDiem(loveFinderUser.getBirthday());

        gender.setImg(R.drawable.ic_gender);
        gender.setTxtThuocTinh(getString(R.string.profile_gender));
        gender.setTxtDacDiem(loveFinderUser.getGender());

        location.setImg(R.drawable.ic_location);
        location.setTxtThuocTinh(getString(R.string.profile_location));
        location.setTxtDacDiem(loveFinderUser.getLocation());

        hometown.setImg(R.drawable.ic_home_town);
        hometown.setTxtThuocTinh(getString(R.string.profile_hometown));
        hometown.setTxtDacDiem(loveFinderUser.getHometown());

        email.setImg(R.drawable.ic_email);
        email.setTxtThuocTinh(getString(R.string.profile_email));
        email.setTxtDacDiem(loveFinderUser.getEmail());
    }

    private void bindView(View view){
        imgProfilePicture = view.findViewById(R.id.imageViewProfilePictureFragmentProFile);
        txtQuotes = view.findViewById(R.id.textViewQuotesProfile);
        name = view.findViewById(R.id.AttributeViewLayoutNameProfile);
        birthday = view.findViewById(R.id.AttributeViewLayoutBirthdayProfile);
        gender = view.findViewById(R.id.AttributeViewLayoutGenderProfile);
        location = view.findViewById(R.id.AttributeViewLayoutLocationProfile);
        hometown = view.findViewById(R.id.AttributeViewLayoutHomeTownProfile);
        email = view.findViewById(R.id.AttributeViewLayoutEmailProfile);
    }

    public void getBundleArguments() {
        Bundle bundle = getArguments();
        loveFinderUser = (LoveFinderUser) bundle.getSerializable("user");
    }
}
