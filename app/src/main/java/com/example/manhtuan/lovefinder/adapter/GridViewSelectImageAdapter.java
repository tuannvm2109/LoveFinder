package com.example.manhtuan.lovefinder.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.example.manhtuan.lovefinder.R;
import com.example.manhtuan.lovefinder.activity.MainImageDetailActivity;
import com.example.manhtuan.lovefinder.customview.SquareImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class GridViewSelectImageAdapter extends BaseAdapter{
    private Context context;
    private List<String> imageURLList;
    private List<String> selectPhotoList;
    private Button btnSkipActivity;
    private Picasso picasso = Picasso.get();
    private final boolean[] checkSelect;

    public GridViewSelectImageAdapter(Context context, List<String> imageURLList, List<String> selectPhotoList, Button btnSkipActivity) {
        this.context = context;
        this.imageURLList = imageURLList;
        this.selectPhotoList = selectPhotoList;
        this.btnSkipActivity = btnSkipActivity;
        checkSelect = new boolean[imageURLList.size()];
    }

    @Override
    public int getCount() {
        return imageURLList.size();
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
        SquareImageView img;
        Target target;

        public ViewHolder() {
            this.img = null;
            this.target = null;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.gridview_selectimage,parent,false);
            viewHolder.img = convertView.findViewById(R.id.imageViewGridViewSelectImage);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.target = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                viewHolder.img.setImageBitmap(bitmap);
                if(checkSelect[position]){
                    viewHolder.img.setAlpha(0.5f);
                    viewHolder.img.setBackgroundResource(R.drawable.custom_border_selected);
                }
                else {
                    viewHolder.img.setAlpha(1f);
                    viewHolder.img.setBackgroundResource(0);
                    viewHolder.img.setPadding(0,0,0,0);
                }

                final ImageView imageView = viewHolder.img;
                if(selectPhotoList.size() == 0){
                    viewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MainImageDetailActivity.class);
                            int[] screenLocation = new int[2];
                            imageView.getLocationInWindow(screenLocation);
                            intent.putExtra("x", screenLocation[0])
                                    .putExtra("y", screenLocation[1])
                                    .putExtra("width", imageView.getWidth())
                                    .putExtra("height", imageView.getHeight())
                                    .putExtra("bitmap", bitmap)
                                    .putExtra("imageURL", imageURLList.get(position));
                            ((Activity) context).startActivityForResult(intent,123);
                        }
                    });
                }
                else {
                    viewHolder.img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!checkSelect[position]){
                                selectPhotoList.add(imageURLList.get(position));
                                checkSelect[position] = true;
                            }
                            else {
                                selectPhotoList.remove(imageURLList.get(position));
                                checkSelect[position] = false;
                            }
                            notifyDataSetChanged();
                            if(selectPhotoList.size() > 1) btnSkipActivity.setText(context.getString(R.string.OK));
                            else btnSkipActivity.setText(context.getString(R.string.Skip));
                        }
                    });
                }
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                viewHolder.img.setImageDrawable(placeHolderDrawable);
            }
        };

        picasso.load(imageURLList.get(position))
                .resize(190,190)
                .centerCrop()
                .placeholder(R.drawable.img_placeholder)
                .into(viewHolder.target);

        return convertView;
    }

}
