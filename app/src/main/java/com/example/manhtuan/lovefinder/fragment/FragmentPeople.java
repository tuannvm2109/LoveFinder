package com.example.manhtuan.lovefinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.adapter.RecycleViewPeopleAdapter;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;

import java.util.List;

public class FragmentPeople extends Fragment {
    private RecyclerView recyclerView;
    private List<LoveFinderUser> peopleAround;
    private String userId;
    public static RecycleViewPeopleAdapter customRecycleViewPeopleAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_people,container,false);
        bindView(view);
        getBundleArgument();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity(),LinearLayoutManager.HORIZONTAL,false);
        customRecycleViewPeopleAdapter = new RecycleViewPeopleAdapter(getActivity(),peopleAround, userId);
        recyclerView.hasFixedSize();
        SnapHelper helper = new LinearSnapHelper();
        helper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customRecycleViewPeopleAdapter);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                    if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
//                        NestedScrollView scrollView = view.findViewById(R.id.scrollViewRecycleViewPeople);
//                        NestedScrollView.(NestedScrollView.FOCUS_UP);
//                    }
//            }
//        });

        return view;
    }

    private void bindView(View view) {
        recyclerView = view.findViewById(R.id.recycleViewPeople);
    }

    private void getBundleArgument() {
        Bundle bundle = getArguments();
        peopleAround = (List<LoveFinderUser>) bundle.getSerializable("peopleAround");
        userId = bundle.getString("userId");
    }

}
