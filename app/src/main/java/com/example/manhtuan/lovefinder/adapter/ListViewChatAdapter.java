package com.example.manhtuan.lovefinder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.model.Message;
import com.facebook.login.widget.ProfilePictureView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ListViewChatAdapter extends BaseAdapter {
    private List<Message> messageList;
    private Context context;
    private int layout;
    private String otherUserFbId;

    public ListViewChatAdapter(List<Message> messageList, Context context, int layout, String otherUserFbId) {
        this.messageList = messageList;
        this.context = context;
        this.layout = layout;
        this.otherUserFbId = otherUserFbId;
    }

    @Override
    public int getCount() {
        return messageList.size();
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
        LinearLayout linearLayoutOtherUser,linearLayoutUser;
        ProfilePictureView profilePictureViewOtherUser;
        TextView txtContentOtherUser,txtContentUser,txtTimeOtherUser,txtTimeUser;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,parent,false);
            viewHolder.linearLayoutOtherUser = convertView.findViewById(R.id.linearLayoutOtherUserListViewChat);
            viewHolder.linearLayoutUser = convertView.findViewById(R.id.linearLayoutUserListViewChat);
            viewHolder.profilePictureViewOtherUser = convertView.findViewById(R.id.profilePictureViewOtherUserListViewChat);
            viewHolder.txtContentOtherUser = convertView.findViewById(R.id.textViewContentOtherUserListViewChat);
            viewHolder.txtContentUser = convertView.findViewById(R.id.textViewContentUserListViewChat);
            viewHolder.txtTimeOtherUser = convertView.findViewById(R.id.textViewTimeOtherUserListViewChat);
            viewHolder.txtTimeUser = convertView.findViewById(R.id.textViewTimeUserListViewChat);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");
        Message message = messageList.get(position);
        if(message.fromUser()){
            Date d = new Date(message.getTimeLong());
            String time = sdf.format(d);
            viewHolder.linearLayoutOtherUser.setVisibility(View.GONE);
            viewHolder.txtContentUser.setText(message.getContent());
            viewHolder.txtTimeUser.setText(time);
        }
        else{
            Date d = new Date(message.getTimeLong());
            String time = sdf.format(d);
            viewHolder.profilePictureViewOtherUser.setProfileId(otherUserFbId);
            viewHolder.linearLayoutUser.setVisibility(View.GONE);
            viewHolder.txtContentOtherUser.setText(message.getContent());
            viewHolder.txtTimeOtherUser.setText(time);
        }
        return convertView;
    }
}
