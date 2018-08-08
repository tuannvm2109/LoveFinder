package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.Category;

import java.util.List;

public class RecycleViewCategoryAdapter extends RecyclerView.Adapter<RecycleViewCategoryAdapter.ViewHolder>{
    private Context context;
    private List<Category> categoryList;
    private RecycleViewLikeTagAdapter recycleViewLikeTagAdapter;
    private RecyclerView recyclerViewLikeTag;

    public RecycleViewCategoryAdapter(Context context, List<Category> categoryList, RecycleViewLikeTagAdapter recycleViewLikeTagAdapter, RecyclerView recyclerViewLikeTag) {
        this.context = context;
        this.categoryList = categoryList;
        this.recycleViewLikeTagAdapter = recycleViewLikeTagAdapter;
        this.recyclerViewLikeTag = recyclerViewLikeTag;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.recycleview_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtCategory.setText(categoryList.get(position).getName().toUpperCase());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtCategory;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardViewRecyclerViewCategory);
            txtCategory = itemView.findViewById(R.id.textViewCategoryRecycleCategory);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recycleViewLikeTagAdapter.changeLikeTagList(categoryList.get(getAdapterPosition()).getLikeTags());
                    recycleViewLikeTagAdapter.notifyDataSetChanged();
                    recyclerViewLikeTag.startLayoutAnimation();
                }
            });
        }
    }
}
