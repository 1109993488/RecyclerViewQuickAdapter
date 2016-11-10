package com.blingbling.quickadapter.base.status;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/17.
 */

public interface LoadMoreStatus {
    int STATUS_DEFAULT = 1;
    int STATUS_LOADING = 2;
    int STATUS_FAIL = 3;
    int STATUS_END = 4;

    @IntDef({STATUS_DEFAULT, STATUS_LOADING, STATUS_FAIL, STATUS_END})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }
}
