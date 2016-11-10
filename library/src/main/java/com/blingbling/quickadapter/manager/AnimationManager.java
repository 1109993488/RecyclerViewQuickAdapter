package com.blingbling.quickadapter.manager;

import android.animation.Animator;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.animation.AlphaAnimation;
import com.blingbling.quickadapter.animation.BaseAnimation;
import com.blingbling.quickadapter.animation.ScaleAnimation;
import com.blingbling.quickadapter.animation.SlideBottomAnimation;
import com.blingbling.quickadapter.animation.SlideLeftAnimation;
import com.blingbling.quickadapter.animation.SlideRightAnimation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/15.
 */

public class AnimationManager {
    public static final int ANIMATION_ALPHA = 1;
    public static final int ANIMATION_SCALE = 2;
    public static final int ANIMATION_SLIDE_BOTTOM = 3;
    public static final int ANIMATION_SLIDE_LEFT = 4;
    public static final int ANIMATION_SLIDE_RIGHT = 5;

    @IntDef({ANIMATION_ALPHA, ANIMATION_SCALE, ANIMATION_SLIDE_BOTTOM, ANIMATION_SLIDE_LEFT, ANIMATION_SLIDE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface AnimationType {
    }

    private BaseQuickAdapter mQuickAdapter;

    private BaseAnimation mAnimation;
    private boolean mFirstOnly = true;
    private int mLastPosition = -1;
    private int mAnimationDuration = 300;
    private Interpolator mAnimationInterpolator = new LinearInterpolator();

    public AnimationManager(BaseQuickAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    public void addAnimation(RecyclerView.ViewHolder holder) {
        if (mAnimation != null) {
            final int position = holder.getLayoutPosition();
            if (!mFirstOnly || position > mLastPosition) {
                final View view = holder.itemView;
                view.clearAnimation();

                Animator anim = mAnimation.getAnimators(view);
                anim.setInterpolator(mAnimationInterpolator);
                anim.setDuration(mAnimationDuration).start();

                mLastPosition = position;
            }
        }
    }

    public AnimationManager setDuration(int duration) {
        mAnimationDuration = duration;
        return this;
    }

    public AnimationManager setFirstOnly(boolean firstOnly) {
        mFirstOnly = firstOnly;
        return this;
    }

    public AnimationManager setDuration(Interpolator interpolator) {
        mAnimationInterpolator = interpolator;
        return this;
    }

    public void openAnimation() {
        openAnimation(ANIMATION_ALPHA);
    }

    public void openAnimation(@AnimationType int animationType) {
        switch (animationType) {
            case ANIMATION_ALPHA:
                openAnimation(new AlphaAnimation());
                break;
            case ANIMATION_SCALE:
                openAnimation(new ScaleAnimation());
                break;
            case ANIMATION_SLIDE_BOTTOM:
                openAnimation(new SlideBottomAnimation());
                break;
            case ANIMATION_SLIDE_LEFT:
                openAnimation(new SlideLeftAnimation());
                break;
            case ANIMATION_SLIDE_RIGHT:
                openAnimation(new SlideRightAnimation());
                break;
        }
    }

    public void openAnimation(BaseAnimation animation) {
        mAnimation = animation;
    }
}
