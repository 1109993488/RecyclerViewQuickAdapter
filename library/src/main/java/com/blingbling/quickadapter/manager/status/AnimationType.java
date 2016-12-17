package com.blingbling.quickadapter.manager.status;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/17.
 */

public interface AnimationType {
    int ANIMATION_ALPHA = 1;
    int ANIMATION_SCALE = 2;
    int ANIMATION_SLIDE_BOTTOM = 3;
    int ANIMATION_SLIDE_LEFT = 4;
    int ANIMATION_SLIDE_RIGHT = 5;

    @IntDef({ANIMATION_ALPHA, ANIMATION_SCALE, ANIMATION_SLIDE_BOTTOM, ANIMATION_SLIDE_LEFT, ANIMATION_SLIDE_RIGHT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }
}
