package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.model.UserLastMessage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListViewMessageAdapter extends BaseAdapter {
    Context context;
    List<UserLastMessage> userLastMessageList;
    private Picasso picasso = Picasso.get();

    public ListViewMessageAdapter(Context context, List<UserLastMessage> userLastMessageList) {
        this.context = context;
        this.userLastMessageList = userLastMessageList;
    }

    public void setUserLastMessageList(List<UserLastMessage> userLastMessageList){
        this.userLastMessageList = userLastMessageList;
    }
    @Override
    public int getCount() {
        return userLastMessageList.size();
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
        ImageView imgProfile;
        TextView txtNameUser,txtLastMessage;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_message,parent,false);
            viewHolder.imgProfile = convertView.findViewById(R.id.imageViewProfileUserListViewMessage);
            viewHolder.txtLastMessage = convertView.findViewById(R.id.textViewLastMessageListViewMessage);
            viewHolder.txtNameUser = convertView.findViewById(R.id.textViewUserNameListViewMessage);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        UserLastMessage userLastMessage = userLastMessageList.get(position);
        LoveFinderUser otherUser = userLastMessage.getLoveFinderUser();
        picasso.load(otherUser.getPhotoURL().get(0))
                .resize(60,60)
                .centerCrop()
                .placeholder(R.drawable.img_profile_placeholder)
                .into(viewHolder.imgProfile);
        viewHolder.txtNameUser.setText(otherUser.getName());
        viewHolder.txtLastMessage.setText(userLastMessage.getLastMessage());

        return convertView;
    }
}
