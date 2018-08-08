package com.example.manhtuan.lovefinder.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.adapter.GridViewSelectImageAdapter;
import com.example.manhtuan.lovefinder.model.FacebookUser;

import java.util.ArrayList;
import java.util.List;

public class SelectImageActivity extends AppCompatActivity implements View.OnClickListener{
    private GridView gridView;
    private Button btnSkip;
    private Dialog dialogNotification;
    private FacebookUser facebookUser;
    private boolean checkUserNew;
    private String userid;
    private GridViewSelectImageAdapter adapter;
    private List<String> selectedPhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        initStart();
        getFacebookUser();
        showNotification(getString(R.string.message_dialog_select_main_picture));
        
        adapter = new GridViewSelectImageAdapter(
                this,
                facebookUser.getPhotoURL(),
                selectedPhotoList,
                btnSkip);
        gridView.setAdapter(adapter);

    }

    private void getFacebookUser() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        if(bundle != null){
            facebookUser = (FacebookUser) bundle.getSerializable("user");
            userid = bundle.getString("userid");
            checkUserNew = bundle.getBoolean("checkusernew");
        }
        else Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
    }

    private void initStart() {
        gridView = findViewById(R.id.gridViewSelectImage);
        btnSkip = findViewById(R.id.buttonSkipSelectImage);
        btnSkip.setOnClickListener(this);

        dialogNotification = new Dialog(this);
        dialogNotification.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogNotification.setContentView(R.layout.dialog_notification);
        TextView txtOk = dialogNotification.findViewById(R.id.textViewOKDialogSelectMainPicture);
        txtOk.setOnClickListener(this);
        dialogNotification.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        selectedPhotoList = new ArrayList<>();
    }

    private void showNotification(String message){
        TextView txtMessage = dialogNotification.findViewById(R.id.textViewDialogSelectMainPicture);
        txtMessage.setText(message);
        dialogNotification.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonSkipSelectImage:
                facebookUser.setPhotoURL(selectedPhotoList);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user",facebookUser);
                bundle.putString("userid",userid);
                Log.d("asdffff",userid);
                bundle.putBoolean("checkusernew",checkUserNew);
                Intent intent = new Intent(this,MainActivityLogged.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
                break;
            case R.id.textViewOKDialogSelectMainPicture:
                dialogNotification.dismiss();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 123 && resultCode == RESULT_OK){
            selectedPhotoList.add(data.getStringExtra("mainImageUrl"));
            facebookUser.getPhotoURL().remove(data.getStringExtra("mainImageUrl"));
            adapter.notifyDataSetChanged();
            showNotification(getString(R.string.message_dialog_select_image));
        }
    }
}
