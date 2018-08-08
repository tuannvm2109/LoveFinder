package com.example.manhtuan.lovefinder.customview;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class ImageProgressView extends LinearLayout {
    private final LayoutParams FRAME_LAYOUT_PARAM = new LayoutParams(0,LayoutParams.MATCH_PARENT,1);
    private final LayoutParams SPACE_LAYOUT_PARAM = new LayoutParams(7, LayoutParams.MATCH_PARENT);
    private List<FrameLayout> frameLayouts;
    private int storiesCount;
    private int current;

    public ImageProgressView(Context context) {
        this(context, null);
    }

    public ImageProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        frameLayouts = new ArrayList<>();
        storiesCount = -1;
        current = 0;
        setOrientation(HORIZONTAL);
    }

    public ImageProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        frameLayouts = new ArrayList<>();
        storiesCount = -1;
        current = 0;
        setOrientation(HORIZONTAL);
    }

    @TargetApi(21)
    public ImageProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        frameLayouts = new ArrayList<>();
        storiesCount = -1;
        current = 0;
        setOrientation(HORIZONTAL);
    }

    public void setStoriesCount(int storiesCount) {
        this.storiesCount = storiesCount;
        this.removeAllViews();
        for(int i = 0; i < this.storiesCount; ++i) {
            FrameLayout fl = this.createFrameLayout();
            if(i == 0){
                fl.setBackgroundColor(getResources().getColor(android.R.color.white));
            }
            this.addView(fl);
            frameLayouts.add(fl);
            if (i + 1 < this.storiesCount) {
                this.addView(this.createSpace());
            }
        }
    }

    private FrameLayout createFrameLayout() {
        FrameLayout fl = new FrameLayout(this.getContext());
        fl.setLayoutParams(this.FRAME_LAYOUT_PARAM);
        fl.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        return fl;
    }

    private View createSpace() {
        View v = new View(this.getContext());
        v.setLayoutParams(this.SPACE_LAYOUT_PARAM);
        return v;
    }

    public void next() {
        if (current < storiesCount-1) {
            FrameLayout pre = frameLayouts.get(this.current);
            FrameLayout now = frameLayouts.get(this.current + 1);
            pre.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            now.setBackgroundColor(getResources().getColor(android.R.color.white));
            current++;
        }
    }

    public void previous() {
        if (current>0) {
            FrameLayout pre = frameLayouts.get(this.current);
            FrameLayout now = frameLayouts.get(this.current - 1);
            pre.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
            now.setBackgroundColor(getResources().getColor(android.R.color.white));
            current--;
        }
    }
    public int getCurrent() {
        return this.current;
    }



//    public void setStoryDuration(long duration) {
//        for(int i = 0; i < this.progressBars.size(); ++i) {
//            ((jp.shts.android.storiesprogressview.PausableProgressBar)this.progressBars.get(i)).setDuration(duration);
//            ((jp.shts.android.storiesprogressview.PausableProgressBar)this.progressBars.get(i)).setCallback(this.callback(i));
//        }
//
//    }
//
//    private Callback callback(final int index) {
//        return new Callback() {
//            public void onStartProgress() {
//                jp.shts.android.storiesprogressview.StoriesProgressView.this.current = index;
//            }
//
//            public void onFinishProgress() {
//                if (jp.shts.android.storiesprogressview.StoriesProgressView.this.isReverse) {
//                    jp.shts.android.storiesprogressview.StoriesProgressView.this.isReverse = false;
//                    if (jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener != null) {
//                        jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener.onPrev();
//                    }
//
//                    if (0 <= jp.shts.android.storiesprogressview.StoriesProgressView.this.current - 1) {
//                        jp.shts.android.storiesprogressview.PausableProgressBar p = (jp.shts.android.storiesprogressview.PausableProgressBar) jp.shts.android.storiesprogressview.StoriesProgressView.this.progressBars.get(jp.shts.android.storiesprogressview.StoriesProgressView.this.current - 1);
//                        p.setMinWithoutCallback();
//                        ((jp.shts.android.storiesprogressview.PausableProgressBar) jp.shts.android.storiesprogressview.StoriesProgressView.this.progressBars.get(--jp.shts.android.storiesprogressview.StoriesProgressView.this.current)).startProgress();
//                    } else {
//                        ((jp.shts.android.storiesprogressview.PausableProgressBar) jp.shts.android.storiesprogressview.StoriesProgressView.this.progressBars.get(jp.shts.android.storiesprogressview.StoriesProgressView.this.current)).startProgress();
//                    }
//
//                } else {
//                    int next = jp.shts.android.storiesprogressview.StoriesProgressView.this.current + 1;
//                    if (next <= jp.shts.android.storiesprogressview.StoriesProgressView.this.progressBars.size() - 1) {
//                        if (jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener != null) {
//                            jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener.onNext();
//                        }
//
//                        ((jp.shts.android.storiesprogressview.PausableProgressBar) jp.shts.android.storiesprogressview.StoriesProgressView.this.progressBars.get(next)).startProgress();
//                    } else {
//                        jp.shts.android.storiesprogressview.StoriesProgressView.this.isComplete = true;
//                        if (jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener != null) {
//                            jp.shts.android.storiesprogressview.StoriesProgressView.this.storiesListener.onComplete();
//                        }
//                    }
//
//                }
//            }
//        };
//    }
//
//    public void startStories() {
//        ((jp.shts.android.storiesprogressview.PausableProgressBar)this.progressBars.get(0)).startProgress();
//    }
//
//    public void destroy() {
//        Iterator var1 = this.progressBars.iterator();
//
//        while(var1.hasNext()) {
//            jp.shts.android.storiesprogressview.PausableProgressBar p = (jp.shts.android.storiesprogressview.PausableProgressBar)var1.next();
//            p.clear();
//        }
//
//    }
//
//    public void pause() {
//        ((jp.shts.android.storiesprogressview.PausableProgressBar)this.progressBars.get(this.current)).pauseProgress();
//    }
//
//    public void resume() {
//        ((jp.shts.android.storiesprogressview.PausableProgressBar)this.progressBars.get(this.current)).resumeProgress();
//    }
//
//    public interface StoriesListener {
//        void onNext();
//
//        void onPrev();
//
//        void onComplete();
//    }
}

