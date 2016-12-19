package com.blingbling.quickadapter.manager.status;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/17.
 */

public interface EmptyStatus {
    int STATUS_EMPTY = 1;
    int STATUS_DEFAULT = 2;
    int STATUS_NO_DATA = 3;
    int STATUS_LOADING = 4;
    int STATUS_FAIL = 5;
    int STATUS_FAIL_NETWORK = 6;

    @IntDef({STATUS_EMPTY, STATUS_DEFAULT, STATUS_NO_DATA, STATUS_LOADING, STATUS_FAIL, STATUS_FAIL_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    @interface Status {
    }
}
