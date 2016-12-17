package com.blingbling.quickadapter.manager;

import android.animation.Animator;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.animation.AlphaInAnimation;
import com.blingbling.quickadapter.animation.BaseAnimation;
import com.blingbling.quickadapter.animation.ScaleInAnimation;
import com.blingbling.quickadapter.animation.SlideInBottomAnimation;
import com.blingbling.quickadapter.animation.SlideInLeftAnimation;
import com.blingbling.quickadapter.animation.SlideInRightAnimation;
import com.blingbling.quickadapter.manager.status.AnimationType;

/**
 * Created by BlingBling on 2016/12/15.
 */

public class AnimationManager {

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
                final Animator anim = mAnimation.getAnimators(holder.itemView);
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
        openAnimation(AnimationType.ANIMATION_ALPHA);
    }

    public void openAnimation(@AnimationType.Type int animationType) {
        switch (animationType) {
            case AnimationType.ANIMATION_ALPHA:
                openAnimation(new AlphaInAnimation());
                break;
            case AnimationType.ANIMATION_SCALE:
                openAnimation(new ScaleInAnimation());
                break;
            case AnimationType.ANIMATION_SLIDE_BOTTOM:
                openAnimation(new SlideInBottomAnimation());
                break;
            case AnimationType.ANIMATION_SLIDE_LEFT:
                openAnimation(new SlideInLeftAnimation());
                break;
            case AnimationType.ANIMATION_SLIDE_RIGHT:
                openAnimation(new SlideInRightAnimation());
                break;
        }
    }

    public void openAnimation(BaseAnimation animation) {
        mAnimation = animation;
    }
}
