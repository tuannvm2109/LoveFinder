package com.example.manhtuan.lovefinder.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.adapter.ListViewMessageAdapter;
import com.example.manhtuan.lovefinder.model.LoveFinderUser;
import com.example.manhtuan.lovefinder.model.UserLastMessage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentMessage  extends Fragment{
    private ListView listView;
    private EditText edtSearch;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseRef = database.getReference();

    private String userId;
    private List<LoveFinderUser> peopleAround;
    private List<String> threadName;
    private List<UserLastMessage> userLastMessageList;
    private ListViewMessageAdapter listViewMessageAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        setView(view);
        setData();
        getChatData();
        return view;
    }

    private void getChatData() {
        final ChildEventListener celGetThreads = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                threadName.add((String) dataSnapshot.getValue());
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
        databaseRef.child("User").child(userId).child("threads").addChildEventListener(celGetThreads);
        databaseRef.child("User").child(userId).child("threads").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(String thread : threadName){
                    for (final LoveFinderUser otherUser : peopleAround){
                        if(thread.contains(otherUser.getUserId())){

                            Query lastQuery = databaseRef.child("Thread").child(thread).orderByKey().limitToLast(1);
                            lastQuery.addChildEventListener(new ChildEventListener() {
                                @Override
                                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                                    Log.d("asdfff",dataSnapshot.getValue().toString());
                                    String lastMessage = dataSnapshot.child("content").getValue().toString();
                                    UserLastMessage ulm = new UserLastMessage(otherUser,lastMessage);
                                    userLastMessageList.add(ulm);
                                    listViewMessageAdapter.notifyDataSetChanged();
                                    databaseRef.removeEventListener(this);
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
                    }
                }
                databaseRef.removeEventListener(celGetThreads);
                databaseRef.removeEventListener(this);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setView(View view) {
        listView = view.findViewById(R.id.listViewFragmentMessage);
        edtSearch = view.findViewById(R.id.editTextSearchUserFragmentMessage);
        edtSearch = view.findViewById(R.id.editTextSearchUserFragmentMessage);
        listView.setDivider(null);

    }

    private void setData() {
        Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        peopleAround = (List<LoveFinderUser>) bundle.getSerializable("peopleAround");
        threadName = new ArrayList<>();
        userLastMessageList = new ArrayList<>();
        listViewMessageAdapter = new ListViewMessageAdapter(getActivity(),userLastMessageList);
        listView.setAdapter(listViewMessageAdapter);

        edtSearch.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keyCode, KeyEvent event){
                if (event.getAction() == KeyEvent.ACTION_DOWN){
                    switch (keyCode){
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            List<UserLastMessage> userSearch = new ArrayList<>();
                            for(UserLastMessage ulm : userLastMessageList){
                                if(ulm.getLoveFinderUser().getName().toLowerCase()
                                        .contains(edtSearch.getText().toString().toLowerCase())){
                                    userSearch.add(ulm);
                                }
                            }
                            listViewMessageAdapter.setUserLastMessageList(userSearch);
                            listViewMessageAdapter.notifyDataSetChanged();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

    }
}
