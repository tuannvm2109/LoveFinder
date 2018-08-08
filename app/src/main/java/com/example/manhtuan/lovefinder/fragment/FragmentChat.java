package com.example.manhtuan.lovefinder.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.activity.MainActivityLogged;
import com.example.manhtuan.lovefinder.adapter.ListViewChatAdapter;
import com.example.manhtuan.lovefinder.model.Message;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FragmentChat extends Fragment implements View.OnClickListener{
    private ListView listViewChat;
    private EditText edtChat;
    private TextView txtNameOtherUser;
    private ImageView imgSend,imgBack;
    private String threads = null;
    private List<Message> messageList;
    private final DatabaseReference myRef = MainActivityLogged.databaseRef;
    private final String userId = MainActivityLogged.loveFinderUser.getUserId();
    private LoveFinderUser otherUser;
    private String otherUserId;
    private ListViewChatAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat,container,false);
        getOtherUserAndThread();
        initStart(view);

        return view;
    }

    private void getOtherUserAndThread() {
        Bundle bundle = getArguments();
        otherUser = (LoveFinderUser) bundle.getSerializable("otherUser");
        otherUserId = otherUser.getUserId();
        if(MainActivityLogged.loveFinderUser.getThreads() != null){
            for(String thread : MainActivityLogged.loveFinderUser.getThreads()){
                if(thread.contains(otherUserId)){
                    threads = thread;
                }
            }
        }
    }

    private void initStart(View view) {
        messageList = new ArrayList<>();
        listViewChat = view.findViewById(R.id.listViewChatFragmentChat);
        edtChat = view.findViewById(R.id.editTextMessageFragmentChat);
        imgBack = view.findViewById(R.id.imageViewBackFragmentChat);
        imgSend = view.findViewById(R.id.imageViewSendFragmentChat);
        txtNameOtherUser = view.findViewById(R.id.textViewNameFragmentChat);
        imgSend.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        listViewChat.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
        listViewChat.setStackFromBottom(true);
        setView();
    }

    private void setView() {
        MainActivityLogged.imageViewMenuNavigation.setVisibility(View.GONE);
        txtNameOtherUser.setText(otherUser.getName());
        loadChat();
        adapter = new ListViewChatAdapter(
                messageList,
                getActivity(),
                R.layout.listview_chat,
                otherUser.getFbId());
        listViewChat.setAdapter(adapter);
        listViewChat.setDivider(null);
    }

    private void loadChat() {
        myRef.child("Thread").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(dataSnapshot.getKey().contains(userId)
                        && dataSnapshot.getKey().contains(otherUserId)){
                    ChildEventListener cel = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshotMessage, String s) {
                            Message message = dataSnapshotMessage.getValue(Message.class);
                            messageList.add(message);
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    myRef.child("Thread").child(dataSnapshot.getKey()).addChildEventListener(cel);
                    myRef.removeEventListener(this);
//                    databaseRef.removeEventListener(cel);
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void hideSoftKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageViewBackFragmentChat:
                hideSoftKeyboard();
                getFragmentManager().popBackStack();
                MainActivityLogged.imageViewMenuNavigation.setVisibility(View.VISIBLE);
                break;
            case R.id.imageViewSendFragmentChat:
                String messageContent = edtChat.getText().toString();
                Long time = (new Date()).getTime();
                final Message message = new Message(userId,messageContent,time);
                if(threads != null){
                    myRef.child("Thread").child(threads).push().setValue(message);
                }
                else {
                    myRef.child("User").child(userId).child("threads")
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            int index = (int) (dataSnapshot.getChildrenCount());
                            myRef.child("User").child(userId).child("threads").child(index +"")
                                    .setValue(userId+otherUserId);
                            myRef.child("User").child(otherUserId).child("threads").child(index +"")
                                    .setValue(userId+otherUserId);
                            myRef.child("Thread").child(userId+otherUserId).push().setValue(message);
                            threads = userId+otherUserId;
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                break;
        }
    }

}
