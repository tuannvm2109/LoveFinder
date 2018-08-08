package com.example.manhtuan.lovefinder.adapter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtuan.lovefinder.customview.LockableBottomSheetBehavior;
import com.example.manhtuan.lovefinder.fragment.FragmentChat;
import com.example.manhtuan.lovefinder.fragment.FragmentPeopleDetail;
import com.example.manhtuan.lovefinder.model.Category;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import com.example.manhtuan.lovefinder.customview.ImageProgressView;

public class RecycleViewPeopleAdapter extends RecyclerView.Adapter<RecycleViewPeopleAdapter.ViewHolder>{
    private Picasso picasso = Picasso.get();
    private final int yearNow = Calendar.getInstance().get(Calendar.YEAR);
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseRef = database.getReference();

    private Context context;
    private List<LoveFinderUser> loveFinderUserArrayList;
    private String userid;
    private Boolean bottomSheetIsExpanded = false;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    public RecycleViewPeopleAdapter(Context context, List<LoveFinderUser> loveFinderUserArrayList,String userid) {
        this.context = context;
        this.loveFinderUserArrayList = loveFinderUserArrayList;
        this.userid = userid;
        fragmentManager = ((Activity) context).getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycleview_people,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final LoveFinderUser loveFinderUser = loveFinderUserArrayList.get(position);
        List<Category> categoryList = loveFinderUser.getCategories();
        setNameAgeImage(loveFinderUser,holder);

        holder.imageProgressView.setStoriesCount(loveFinderUser.getPhotoURL().size());
        RecycleViewCategoryAdapter recycleCategoryAdapter = new RecycleViewCategoryAdapter(
                context,categoryList,holder.recycleViewLikeTagAdapter,holder.recyclerViewLikeTag);
        holder.recyclerViewCategory.setAdapter(recycleCategoryAdapter);
    }

    private void setNameAgeImage(LoveFinderUser loveFinderUser,ViewHolder holder) {
        int yearBirth = Integer.parseInt(loveFinderUser.getBirthday().substring(6,10));
        int age = yearNow - yearBirth;
        holder.txtAge.setText(age + "");
        holder.txtName.setText(loveFinderUser.getName());
        if(loveFinderUser.getPhotoURL() != null){
            picasso.load(loveFinderUser.getPhotoURL().get(0))
                    .resize(450,600)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(holder.imgProfilePicture);
        }
    }

    @Override
    public int getItemCount() {
        return loveFinderUserArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txtName,txtAge;
        ImageView imgNope,imgView, imgMessage,imgProfilePicture, imgPullUp;
        RecyclerView recyclerViewCategory,recyclerViewLikeTag;
        RecycleViewLikeTagAdapter recycleViewLikeTagAdapter;
        BottomSheetBehavior bottomSheetBehavior;
        ImageProgressView imageProgressView;
        Button btnLeft,btnRight;
        public ViewHolder(View itemView) {
            super(itemView);
            bindViewViewHolder();
            setRecyclerView();
//            setStoriesProgressView();
            bottomSheetBehavior.setBottomSheetCallback(new MyBottomSheetCallback());
        }

        private void bindViewViewHolder(){
            imgNope = itemView.findViewById(R.id.imageViewNopeRecycleViewPeople);
            imgView = itemView.findViewById(R.id.imageViewViewRecycleViewPeople);
            imgMessage = itemView.findViewById(R.id.imageViewMessageRecycleViewPeople);
            imgPullUp = itemView.findViewById(R.id.imageViewPullUpRecyclerViewPeople);
            txtName = itemView.findViewById(R.id.textViewNameRecycleViewPeople);
            txtAge = itemView.findViewById(R.id.textViewAgeRecycleViewPeople);
            imgProfilePicture = itemView.findViewById(R.id.imageViewProfileRecycleViewPeople);
            btnRight = itemView.findViewById(R.id.buttonRightRecyclerViewPeople);
            btnLeft = itemView.findViewById(R.id.buttonLeftRecyclerViewPeople);
            recyclerViewCategory = itemView.findViewById(R.id.recycleViewCategoryRecycleViewPeople);
            recyclerViewLikeTag =  itemView.findViewById(R.id.recycleViewLikeTagRecycleViewPeople);
            imageProgressView = itemView.findViewById(R.id.imageProgressView);
            bottomSheetBehavior = BottomSheetBehavior.from(itemView.findViewById(R.id.bottomSheet));
            btnRight.setOnClickListener(this);
            btnLeft.setOnClickListener(this);
            imgMessage.setOnClickListener(this);
            imgView.setOnClickListener(this);
            imgNope.setOnClickListener(this);
            imgPullUp.setOnClickListener(this);
        }
        private void setRecyclerView(){
            LayoutAnimationController leftController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_from_left);
            LayoutAnimationController bottomController = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_slide_from_bottom);
            LinearLayoutManager llmLikeTag = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            LinearLayoutManager llmcategory = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
            RecyclerView.OnItemTouchListener scrollTouchListener = new RecyclerView.OnItemTouchListener() {
                @Override
                public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                    int action = e.getAction();
                    switch (action) {
                        case MotionEvent.ACTION_MOVE:
                            rv.getParent().requestDisallowInterceptTouchEvent(true);
                            break;
                    }
                    return false;
                }

                @Override
                public void onTouchEvent(RecyclerView rv, MotionEvent e) {

                }

                @Override
                public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

                }
            };

            recyclerViewCategory.hasFixedSize();
            recyclerViewCategory.setLayoutManager(llmcategory);
            recyclerViewCategory.setLayoutAnimation(bottomController);
            recyclerViewCategory.addOnItemTouchListener(scrollTouchListener);

            recyclerViewLikeTag.hasFixedSize();
            recyclerViewLikeTag.setLayoutManager(llmLikeTag);
            recyclerViewLikeTag.setLayoutAnimation(leftController);
            recyclerViewLikeTag.addOnItemTouchListener(scrollTouchListener);

            recycleViewLikeTagAdapter = new RecycleViewLikeTagAdapter(context);
            recyclerViewLikeTag.setAdapter(recycleViewLikeTagAdapter);
        }
        class MyBottomSheetCallback extends BottomSheetBehavior.BottomSheetCallback {

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    if (bottomSheetBehavior instanceof LockableBottomSheetBehavior) {
                        ((LockableBottomSheetBehavior) bottomSheetBehavior).setLocked(true);
                    }
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageViewPullUpRecyclerViewPeople:
                    changeBottomSheetState(bottomSheetIsExpanded);
                    break;
                case R.id.buttonLeftRecyclerViewPeople:
                    imageProgressView.previous();
                    reloadImage();
                    break;
                case R.id.buttonRightRecyclerViewPeople:
                    imageProgressView.next();
                    reloadImage();
                    break;
                case R.id.imageViewNopeRecycleViewPeople:
                    final String blockUserId = loveFinderUserArrayList.get(getLayoutPosition()).getUserId();
                    String blockUserName = loveFinderUserArrayList.get(getLayoutPosition()).getName();
                    addBlockUserToFirebase(blockUserId);
                    loveFinderUserArrayList.remove(getLayoutPosition());
                    notifyDataSetChanged();
                    Toast.makeText(context, "You had blocked " + blockUserName, Toast.LENGTH_SHORT).show();
                    break;
                case R.id.imageViewViewRecycleViewPeople:
                    startFragmentPeopleDatail();
                    break;
                case R.id.imageViewMessageRecycleViewPeople:
                    startFragmentChat();
                    break;
            }
        }
        private void addBlockUserToFirebase(final String blockUserId){
            databaseRef.child("User").child(userid).child("blockuser")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int index = (int) dataSnapshot.getChildrenCount();
                            databaseRef.child("User").child(userid).child("blockuser").child(index + "")
                                    .setValue(blockUserId);
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        }
        private void reloadImage(){
            int position = imageProgressView.getCurrent();
            picasso.load(loveFinderUserArrayList.get(getAdapterPosition()).getPhotoURL().get(position))
                    .resize(450,600)
                    .centerCrop()
                    .placeholder(R.drawable.img_placeholder)
                    .error(R.drawable.img_placeholder)
                    .into(imgProfilePicture);
        }
        private void changeBottomSheetState(boolean expand){
            if(!expand){
                recyclerViewCategory.startLayoutAnimation();
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                imgPullUp.setImageResource(R.drawable.ic_arrow_drop_down);
                bottomSheetIsExpanded = true;
            }
            else{
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                imgPullUp.setImageResource(R.drawable.ic_arrow_drop_up);
                recycleViewLikeTagAdapter.resetLikeTagList();
                recycleViewLikeTagAdapter.notifyDataSetChanged();
                bottomSheetIsExpanded = false;
            }
        }
        private void startFragmentPeopleDatail(){
            FragmentPeopleDetail fragmentPeopleDetail = new FragmentPeopleDetail();
            Bundle bundleView = new Bundle();
            bundleView.putInt("positionpeople",getLayoutPosition());
            bundleView.putSerializable("user",loveFinderUserArrayList.get(getLayoutPosition()));
            fragmentPeopleDetail.setArguments(bundleView);
            fragmentTransaction.replace(R.id.nav_framelayout,fragmentPeopleDetail);
            fragmentTransaction.addToBackStack("fpeopledetail");
            fragmentTransaction.commit();
        }
        private void startFragmentChat(){
            FragmentChat fragmentChat = new FragmentChat();
            Bundle bundleMessage = new Bundle();
            bundleMessage.putSerializable("otherUser",loveFinderUserArrayList.get(getLayoutPosition()));
            fragmentChat.setArguments(bundleMessage);
            fragmentTransaction.setCustomAnimations(
                    R.animator.translate_right_enter_activity,
                    R.animator.translate_left_exit_activity,
                    R.animator.translate_left_enter_activity,
                    R.animator.translate_right_exit_activity);
            fragmentTransaction.replace(R.id.nav_framelayout,fragmentChat);
            fragmentTransaction.addToBackStack("fchat");
            fragmentTransaction.commit();
        }
    }
}
