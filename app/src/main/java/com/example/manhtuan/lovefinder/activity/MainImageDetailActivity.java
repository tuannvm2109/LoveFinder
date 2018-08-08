package com.example.manhtuan.lovefinder.activity;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;
import com.squareup.picasso.Picasso;

public class MainImageDetailActivity extends AppCompatActivity
        implements ViewTreeObserver.OnPreDrawListener, View.OnClickListener{
    private int x,y,width,height,left,top;
    private float widthScale,heightScale;
    private ImageView imgView;
    private TextView txt;
    private Button btnOk,btnCancel;
    private String imageURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_image_detail);
        anhXa();

        overridePendingTransition(R.anim.alpha_enter_activity,R.anim.alpha_exit_activity);
        Bundle bundle = getIntent().getExtras();
        x = bundle.getInt("x");
        y = bundle.getInt("y");
        width = bundle.getInt("width");
        height = bundle.getInt("height");
        Bitmap bitmap = (Bitmap) bundle.get("bitmap");
        imageURL = bundle.getString("imageURL");
        imgView.setImageBitmap(bitmap);
        ViewTreeObserver observer = imgView.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override public boolean onPreDraw() {
                imgView.getViewTreeObserver().removeOnPreDrawListener(this);
                int[] screenLocation = new int[2];
                imgView.getLocationOnScreen(screenLocation);
                left = x - screenLocation[0];
                top = y - screenLocation[1];
                widthScale = (float) width / imgView.getWidth();
                heightScale = (float) height / imgView.getHeight();
                enterAnimation();
                return true;
            }
        });
    }

    private void anhXa() {
        imgView = findViewById(R.id.imageViewMainImageDetail);
        txt = findViewById(R.id.textViewMainImageDetail);
        btnOk = findViewById(R.id.buttonOkMainImageDetail);
        btnCancel = findViewById(R.id.buttonCancelMainImageDetail);
        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void enterAnimation() {
        AnimationSet set = new AnimationSet(true);
        Animation anim = new ScaleAnimation(widthScale, 1, heightScale, 1);
        Animation animT = new TranslateAnimation(left, 0, top, 0);
        set.addAnimation(anim);
        set.addAnimation(animT);
        set.setDuration(1000);
        imgView.startAnimation(set);

        Animation animAlpha = new AlphaAnimation(0,1);
        animAlpha.setStartOffset(1000);
        animAlpha.setDuration(700);
        txt.startAnimation(animAlpha);
        btnOk.startAnimation(animAlpha);
        btnCancel.startAnimation(animAlpha);
    }

    @Override
    public boolean onPreDraw() {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonOkMainImageDetail:
                Intent intent = new Intent();
                intent.putExtra("mainImageUrl",imageURL);
                setResult(RESULT_OK,intent);
                finish();
                break;
            case R.id.buttonCancelMainImageDetail:
                finish();
                break;
        }
    }
}
