package com.example.manhtuan.lovefinder.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.manhtuan.lovefinder.R;

public class AttributeViewLayout extends LinearLayout {
    private TextView txtThuocTinh,txtDacDiem;
    private ImageView img;
    public AttributeViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.layout_attributeview, this);
        txtThuocTinh = (TextView) findViewById(R.id.textViewThuocTinhAttributeView);
        txtDacDiem = (TextView) findViewById(R.id.textViewDacDiemAttributeView);
        img = (ImageView) findViewById(R.id.imageViewAttributeView);
    }

    public void setTxtThuocTinh(String text){
        txtThuocTinh.setText(text);
    }
    public void setTxtDacDiem(String text){
        txtDacDiem.setText(text);
    }
    public void setImg(int imgResource){
        img.setImageResource(imgResource);
    }
}
