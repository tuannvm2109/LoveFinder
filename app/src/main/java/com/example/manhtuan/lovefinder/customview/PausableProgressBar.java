//package com.example.manhtuan.lovefinder.customview;
//
//import android.content.Context;
//import android.support.annotation.AttrRes;
//import android.support.annotation.NonNull;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.ScaleAnimation;
//import android.view.animation.Transformation;
//import android.view.animation.Animation.AnimationListener;
//import android.widget.FrameLayout;
//import jp.shts.android.storiesprogressview.R.color;
//import jp.shts.android.storiesprogressview.R.id;
//import jp.shts.android.storiesprogressview.R.layout;
//
//final class PausableProgressBar extends FrameLayout {
//    private static final int DEFAULT_PROGRESS_DURATION = 2000;
//    private View frontProgressView;
//    private View maxProgressView;
//    private jp.shts.android.storiesprogressview.PausableProgressBar.PausableScaleAnimation animation;
//    private long duration;
//    private jp.shts.android.storiesprogressview.PausableProgressBar.Callback callback;
//
//    public PausableProgressBar(Context context) {
//        this(context, (AttributeSet)null);
//    }
//
//    public PausableProgressBar(@NonNull Context context, @Nullable AttributeSet attrs) {
//        this(context, attrs, 0);
//    }
//
//    public PausableProgressBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        this.duration = 2000L;
//        LayoutInflater.from(context).inflate(layout.pausable_progress, this);
//        this.frontProgressView = this.findViewById(id.front_progress);
//        this.maxProgressView = this.findViewById(id.max_progress);
//    }
//
//    public void setDuration(long duration) {
//        this.duration = duration;
//    }
//
//    public void setCallback(@NonNull jp.shts.android.storiesprogressview.PausableProgressBar.Callback callback) {
//        this.callback = callback;
//    }
//
//    void setMax() {
//        this.finishProgress(true);
//    }
//
//    void setMin() {
//        this.finishProgress(false);
//    }
//
//    void setMinWithoutCallback() {
//        this.maxProgressView.setBackgroundResource(color.progress_secondary);
//        this.maxProgressView.setVisibility(0);
//        if (this.animation != null) {
//            this.animation.setAnimationListener((AnimationListener)null);
//            this.animation.cancel();
//        }
//
//    }
//
//    private void finishProgress(boolean isMax) {
//        if (isMax) {
//            this.maxProgressView.setBackgroundResource(color.progress_max_active);
//        }
//
//        this.maxProgressView.setVisibility(isMax ? 0 : 8);
//        if (this.animation != null) {
//            this.animation.setAnimationListener((AnimationListener)null);
//            this.animation.cancel();
//            if (this.callback != null) {
//                this.callback.onFinishProgress();
//            }
//        }
//
//    }
//
//    public void startProgress() {
//        this.maxProgressView.setVisibility(8);
//        this.animation = new jp.shts.android.storiesprogressview.PausableProgressBar.PausableScaleAnimation(0.0F, 1.0F, 1.0F, 1.0F, 0, 0.0F, 1, 0.0F);
//        this.animation.setDuration(this.duration);
//        this.animation.setInterpolator(new LinearInterpolator());
//        this.animation.setAnimationListener(new AnimationListener() {
//            public void onAnimationStart(Animation animation) {
//                jp.shts.android.storiesprogressview.PausableProgressBar.this.frontProgressView.setVisibility(0);
//                if (jp.shts.android.storiesprogressview.PausableProgressBar.this.callback != null) {
//                    jp.shts.android.storiesprogressview.PausableProgressBar.this.callback.onStartProgress();
//                }
//
//            }
//
//            public void onAnimationRepeat(Animation animation) {
//            }
//
//            public void onAnimationEnd(Animation animation) {
//                if (jp.shts.android.storiesprogressview.PausableProgressBar.this.callback != null) {
//                    jp.shts.android.storiesprogressview.PausableProgressBar.this.callback.onFinishProgress();
//                }
//
//            }
//        });
//        this.animation.setFillAfter(true);
//        this.frontProgressView.startAnimation(this.animation);
//    }
//
//    public void pauseProgress() {
//        if (this.animation != null) {
//            this.animation.pause();
//        }
//
//    }
//
//    public void resumeProgress() {
//        if (this.animation != null) {
//            this.animation.resume();
//        }
//
//    }
//
//    void clear() {
//        if (this.animation != null) {
//            this.animation.setAnimationListener((AnimationListener)null);
//            this.animation.cancel();
//            this.animation = null;
//        }
//
//    }
//
//    private class PausableScaleAnimation extends ScaleAnimation {
//        private long mElapsedAtPause = 0L;
//        private boolean mPaused = false;
//
//        PausableScaleAnimation(float fromX, float toX, float fromY, float toY, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue) {
//            super(fromX, toX, fromY, toY, pivotXType, pivotXValue, pivotYType, pivotYValue);
//        }
//
//        public boolean getTransformation(long currentTime, Transformation outTransformation, float scale) {
//            if (this.mPaused && this.mElapsedAtPause == 0L) {
//                this.mElapsedAtPause = currentTime - this.getStartTime();
//            }
//
//            if (this.mPaused) {
//                this.setStartTime(currentTime - this.mElapsedAtPause);
//            }
//
//            return super.getTransformation(currentTime, outTransformation, scale);
//        }
//
//        void pause() {
//            if (!this.mPaused) {
//                this.mElapsedAtPause = 0L;
//                this.mPaused = true;
//            }
//        }
//
//        void resume() {
//            this.mPaused = false;
//        }
//    }
//
//    interface Callback {
//        void onStartProgress();
//
//        void onFinishProgress();
//    }
//}
//
