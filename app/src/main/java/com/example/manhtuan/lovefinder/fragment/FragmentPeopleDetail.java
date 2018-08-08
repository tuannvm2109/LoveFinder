package com.example.manhtuan.lovefinder.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.customview.AttributeViewLayout;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.squareup.picasso.Picasso;

public class FragmentPeopleDetail extends Fragment implements View.OnClickListener{
    private AttributeViewLayout name,birthday,gender,location,email;
    private ImageView imgProfile;
    private int userPosition;
    private LoveFinderUser loveFinderUser;
    private Picasso picasso = Picasso.get();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_people,container,false);

        getUser();
        initStart(view);
        return view;
    }

    private void getUser() {
        Bundle bundle = getArguments();
        userPosition = bundle.getInt("positionpeople");
        loveFinderUser = (LoveFinderUser) bundle.getSerializable("user");
    }

    private void initStart(View itemView){
        imgProfile = itemView.findViewById(R.id.imageViewProfileFragmentDetailPeople);
        name = itemView.findViewById(R.id.AttributeViewLayoutNameFragmentDetailPeople);
        birthday = itemView.findViewById(R.id.AttributeViewLayoutBirthdayFragmentDetailPeople);
        gender = itemView.findViewById(R.id.AttributeViewLayoutGenderFragmentDetailPeople);
        location = itemView.findViewById(R.id.AttributeViewLayoutLocationFragmentDetailPeople);
        email = itemView.findViewById(R.id.AttributeViewLayoutEmailFragmentDetailPeople);

        name.setImg(R.drawable.ic_profile);
        name.setTxtThuocTinh(getActivity().getString(R.string.profile_name));
        name.setTxtDacDiem(loveFinderUser.getName());
        birthday.setImg(R.drawable.ic_birthday);
        birthday.setTxtThuocTinh(getActivity().getString(R.string.profile_birthday));
        birthday.setTxtDacDiem(loveFinderUser.getBirthday());
        gender.setImg(R.drawable.ic_gender);
        gender.setTxtThuocTinh(getActivity().getString(R.string.profile_gender));
        gender.setTxtDacDiem(loveFinderUser.getGender());
        location.setImg(R.drawable.ic_location);
        location.setTxtThuocTinh(getActivity().getString(R.string.profile_location));
        location.setTxtDacDiem(loveFinderUser.getLocation());
        email.setImg(R.drawable.ic_email);
        email.setTxtThuocTinh(getActivity().getString(R.string.profile_email));
        email.setTxtDacDiem(loveFinderUser.getEmail());
        if(loveFinderUser.getPhotoURL() != null){
            picasso.load(loveFinderUser.getPhotoURL().get(0))
                    .resize(450,600)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(imgProfile);
        }
        imgProfile.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getActivity().getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentChat fragmentChat = new FragmentChat();
        Bundle bundle = new Bundle();
        bundle.putInt("positionpeople",userPosition);
        fragmentChat.setArguments(bundle);
        fragmentTransaction.replace(R.id.nav_framelayout,fragmentChat);
        fragmentTransaction.addToBackStack("fchat");
        fragmentTransaction.commit();
    }
}
