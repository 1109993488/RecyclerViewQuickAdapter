package com.blingbling.quickadapter.animation;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by BlingBling on 2016/12/15.
 */

public class ScaleInAnimation implements BaseAnimation {

    private static final float DEFAULT_SCALE_FROM = .5f;
    private final float mFrom;

    public ScaleInAnimation() {
        this(DEFAULT_SCALE_FROM);
    }

    public ScaleInAnimation(float from) {
        mFrom = from;
    }

    @Override
    public Animator getAnimators(View view) {
        AnimatorSet set = new AnimatorSet();
        set.play(ObjectAnimator.ofFloat(view, "scaleX", mFrom, 1f))
                .with(ObjectAnimator.ofFloat(view, "scaleY", mFrom, 1f));
        return set;
    }
}
