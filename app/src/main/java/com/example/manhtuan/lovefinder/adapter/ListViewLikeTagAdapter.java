package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.LikeTag;

import java.util.List;

public class ListViewLikeTagAdapter extends BaseAdapter{
    private Context context;
    private List<LikeTag> liketagList;

    public ListViewLikeTagAdapter(Context context, List<LikeTag> liketagList) {
        this.context = context;
        this.liketagList = liketagList;
    }

    @Override
    public int getCount() {
        return liketagList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txt;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_liketag,parent,false);
            viewHolder.txt = convertView.findViewById(R.id.textViewNameListViewLikeTag);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.txt.setText(liketagList.get(position).getName());
        return convertView;
    }
}
