package com.blingbling.quickadapter.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by BlingBling on 2016/12/15.
 */

public class AlphaAnimation implements BaseAnimation {

    private static final float DEFAULT_ALPHA_FROM = 0f;
    private final float mFrom;

    public AlphaAnimation() {
        this(DEFAULT_ALPHA_FROM);
    }

    public AlphaAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator getAnimators(View view) {
        return ObjectAnimator.ofFloat(view, "alpha", mFrom, 1f);
    }
}
