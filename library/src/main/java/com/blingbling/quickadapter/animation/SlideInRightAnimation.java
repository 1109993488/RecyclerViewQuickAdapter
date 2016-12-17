package com.blingbling.quickadapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by BlingBling on 2016/12/15.
 */

public class SlideInRightAnimation implements BaseAnimation {

    @Override
    public Animator getAnimators(View view) {
        return ObjectAnimator.ofFloat(view, "translationX", view.getRootView().getWidth(), 0);
    }
}
