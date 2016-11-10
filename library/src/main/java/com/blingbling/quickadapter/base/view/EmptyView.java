package com.blingbling.quickadapter.base.view;

import android.support.annotation.IdRes;
import android.view.View;

import com.blingbling.quickadapter.base.EmptyManager;
import com.blingbling.quickadapter.base.status.EmptyStatus;

/**
 * Created by BlingBling on 2016/12/14.
 */

public abstract class EmptyView extends BaseView<Integer> implements View.OnClickListener {

    private View mViewDefault;
    private View mViewLoading;
    private View mViewNoData;
    private View mViewFail;
    private View mViewFailNetWork;

    public EmptyView() {
        setData(EmptyStatus.STATUS_DEFAULT);
    }

    @Override
    protected void onCreateView(View view) {
        mViewDefault = view.findViewById(getDefaultViewId());
        mViewLoading = view.findViewById(getLoadingViewId());
        mViewNoData = view.findViewById(getNoDataViewId());
        mViewFail = view.findViewById(getFailViewId());
        mViewFailNetWork = view.findViewById(getFailNetWorkViewId());

        final int noDataRetryViewId = getNoDataRetryViewId();
        if (noDataRetryViewId != 0) {
            mViewNoData.findViewById(noDataRetryViewId).setOnClickListener(this);
        }
        mViewFail.findViewById(getFailRetryViewId()).setOnClickListener(this);
        mViewFailNetWork.findViewById(getFailNetWorkRetryViewId()).setOnClickListener(this);
    }

    @Override
    protected void onBindView(@EmptyStatus.Status Integer data) {
        switch (data) {
            case EmptyStatus.STATUS_EMPTY:
                visibility(false, false, false, false, false);
                break;
            case EmptyStatus.STATUS_DEFAULT:
                visibility(true, false, false, false, false);
                break;
            case EmptyStatus.STATUS_LOADING:
                visibility(false, true, false, false, false);
                break;
            case EmptyStatus.STATUS_NO_DATA:
                visibility(false, false, true, false, false);
                break;
            case EmptyStatus.STATUS_FAIL:
                visibility(false, false, false, true, false);
                break;
            case EmptyStatus.STATUS_FAIL_NETWORK:
                visibility(false, false, false, false, true);
                break;
        }
    }

    protected void visibility(boolean visibleDefault, boolean visibleLoading, boolean visibleNoData, boolean visibleFail, boolean visibleFailNetWork) {
        mViewDefault.setVisibility(visibleDefault ? View.VISIBLE : View.GONE);
        mViewLoading.setVisibility(visibleLoading ? View.VISIBLE : View.GONE);
        mViewNoData.setVisibility(visibleNoData ? View.VISIBLE : View.GONE);
        mViewFail.setVisibility(visibleFail ? View.VISIBLE : View.GONE);
        mViewFailNetWork.setVisibility(visibleFailNetWork ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (mQuickAdapter != null) {
            final EmptyManager.OnEmptyRetryClickListener listener = mQuickAdapter.emptyManager().getOnEmptyRetryClickListener();
            if (listener != null) {
                setData(EmptyStatus.STATUS_LOADING);
                listener.onEmptyRetryClick();
            }
        }
    }

    @IdRes
    public abstract int getDefaultViewId();

    @IdRes
    public abstract int getLoadingViewId();

    @IdRes
    public abstract int getNoDataViewId();

    @IdRes
    public int getNoDataRetryViewId() {
        return getNoDataViewId();
    }

    @IdRes
    public abstract int getFailViewId();

    @IdRes
    public int getFailRetryViewId() {
        return getFailViewId();
    }

    @IdRes
    public abstract int getFailNetWorkViewId();

    @IdRes
    public int getFailNetWorkRetryViewId() {
        return getFailNetWorkViewId();
    }
}
