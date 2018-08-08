package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.model.Hobby;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.activity.MainActivityLogged;
import com.example.manhtuan.lovefinder.R;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewHobbyAdapter extends RecyclerView.Adapter<RecycleViewHobbyAdapter.ViewHolder>{
    private Context context;
    private List<Hobby> hobbyArrayList;
    private LoveFinderUser loveFinderUser = MainActivityLogged.loveFinderUser;
    private ArrayAdapter adapterListHobby;
    private ListView lv;

    public RecycleViewHobbyAdapter(Context context, List<Hobby> hobbyArrayList,ListView lv) {
        this.context = context;
        this.hobbyArrayList = hobbyArrayList;
        this.lv = lv;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycleview_barhobby,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.img.setImageResource(hobbyArrayList.get(position).getIcon());
        holder.txt.setText(hobbyArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return hobbyArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txt;
        ImageView img;

        private void setListView(List hobby){
            if(hobby != null){
                adapterListHobby = new ArrayAdapter(context,R.layout.listview_hobby,hobby);
                lv.setAdapter(adapterListHobby);
            }
            else{
                hobby = new ArrayList();
                adapterListHobby = new ArrayAdapter(context,R.layout.listview_hobby,hobby);
                lv.setAdapter(adapterListHobby);
            }
        }

        public ViewHolder(final View itemView) {
            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.textViewHobbyRecycleHobby);
            img = (ImageView) itemView.findViewById(R.id.imageViewIconRecycleHobby);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    switch (getAdapterPosition()){
//                        case 0:
//                            List music = loveFinderUser.getMusic();
//                            setListView(music);
//                            break;
//                        case 1:
//                            List movie = loveFinderUser.getMovie();
//                            setListView(movie);
//                            break;
//                        case 2:
//                            List game = loveFinderUser.getGame();
//                            setListView(game);
//                            break;
//                        case 3:
//                            List athletes = loveFinderUser.getAthletes();
//                            setListView(athletes);
//                            break;
//                        case 4:
//                            List team = loveFinderUser.getTeam();
//                            setListView(team);
//                            break;
//                        case 5:
//                            List like = loveFinderUser.getLike();
//                            setListView(like);
//                            break;
//                    }
//                }
//            });
        }
    }
}
