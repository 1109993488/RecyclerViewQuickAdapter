package com.blingbling.quickadapter.manager.status;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/17.
 */

public interface AnimationType {
    int ANIMATION_ALPHA_IN = 1;
    int ANIMATION_SCALE_IN = 2;
    int ANIMATION_SLIDE_IN_BOTTOM = 3;
    int ANIMATION_SLIDE_IN_LEFT = 4;
    int ANIMATION_SLIDE_IN_RIGHT = 5;

    @IntDef({ANIMATION_ALPHA_IN, ANIMATION_SCALE_IN, ANIMATION_SLIDE_IN_BOTTOM, ANIMATION_SLIDE_IN_LEFT, ANIMATION_SLIDE_IN_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }
}
