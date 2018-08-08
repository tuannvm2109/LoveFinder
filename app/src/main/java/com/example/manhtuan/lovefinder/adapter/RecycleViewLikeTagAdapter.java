package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.LikeTag;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RecycleViewLikeTagAdapter extends RecyclerView.Adapter<RecycleViewLikeTagAdapter.ViewHolder> {
    private Context context;
    private List<LikeTag> likeTagList;

    public RecycleViewLikeTagAdapter(Context context) {
        this.context = context;
        this.likeTagList = new ArrayList<>();
    }

    public void resetLikeTagList(){
        this.likeTagList = new ArrayList<>();
    }

    public void changeLikeTagList(List<LikeTag> likeTags){
        likeTagList = new ArrayList<>(likeTags);
        for (Iterator<LikeTag> iterator = likeTagList.iterator(); iterator.hasNext();) {
            LikeTag likeTag = iterator.next();
            if (likeTag.getName().isEmpty()) {
                iterator.remove();
            }
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycleview_liketag,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = likeTagList.get(position).getName();
        holder.txtName.setText( name.toUpperCase());
    }

    @Override
    public int getItemCount() {
        return likeTagList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtName;
        public ViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.textViewNameRecycleLikeTag);
        }
    }
}
