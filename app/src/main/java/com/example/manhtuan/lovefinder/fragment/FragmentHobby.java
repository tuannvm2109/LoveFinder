package com.example.manhtuan.lovefinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.manhtuan.lovefinder.model.Hobby;
import com.example.manhtuan.lovefinder.adapter.RecycleViewHobbyAdapter;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.activity.MainActivityLogged;
import com.example.manhtuan.lovefinder.R;

import java.util.ArrayList;

public class FragmentHobby extends Fragment {
    private RecyclerView recyclerViewBar;
    private ArrayList<Hobby> hobbyArrayList;
    private LoveFinderUser loveFinderUser = MainActivityLogged.loveFinderUser;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hobby,container,false);
        recyclerViewBar = view.findViewById(R.id.recycleViewBarHobby);
        listView = view.findViewById(R.id.listViewHobby);

        setRecyclerViewBar();

        return view;
    }

    private void setRecyclerViewBar(){
        hobbyArrayList = new ArrayList<>();
        hobbyArrayList.add(new Hobby(getString(R.string.music),R.drawable.ic_music));
        hobbyArrayList.add(new Hobby(getString(R.string.movie),R.drawable.ic_movie));
        hobbyArrayList.add(new Hobby(getString(R.string.game),R.drawable.ic_game));
        hobbyArrayList.add(new Hobby(getString(R.string.Athlete),R.drawable.ic_athlete));
        hobbyArrayList.add(new Hobby(getString(R.string.team),R.drawable.ic_team));
        hobbyArrayList.add(new Hobby(getString(R.string.like),R.drawable.ic_like));
        RecycleViewHobbyAdapter adapter = new RecycleViewHobbyAdapter(getActivity(),hobbyArrayList,listView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        recyclerViewBar.setLayoutManager(linearLayoutManager);
        recyclerViewBar.hasFixedSize();
        recyclerViewBar.setAdapter(adapter);
    }
}
