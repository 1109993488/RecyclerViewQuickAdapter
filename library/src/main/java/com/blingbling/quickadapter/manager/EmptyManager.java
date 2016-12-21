package com.blingbling.quickadapter.manager;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.manager.status.EmptyStatus;
import com.blingbling.quickadapter.view.DefaultEmptyView;
import com.blingbling.quickadapter.view.EmptyView;
import com.blingbling.quickadapter.view.ItemView;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class EmptyManager extends BaseManager {

    private EmptyView mEmptyView;

    private boolean mHeaderAndEmptyEnable = false;
    private boolean mFooterAndEmptyEnable = false;
    private OnEmptyRetryClickListener mOnEmptyRetryClickListener;

    public EmptyManager(BaseQuickAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    public int getItemViewCount() {
        if (mEmptyView != null) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mEmptyView.getViewType();
    }

    @Override
    public ItemView getItemView(int viewType) {
        if (viewType == mEmptyView.getViewType()) {
            return mEmptyView;
        } else {
            return null;
        }
    }

    public void resetEmpty() {
        if (mEmptyView instanceof EmptyView) {
            if (mQuickAdapter.getDataViewCount() == 0) {
                mEmptyView.setData(EmptyStatus.STATUS_NO_DATA);
            } else {
                mEmptyView.setData(EmptyStatus.STATUS_EMPTY);
            }
        }
    }

    //user method

    public EmptyManager setHeaderAndEmpty(boolean isHeadAndEmpty) {
        return setHeaderFooterAndEmpty(isHeadAndEmpty, false);
    }

    public EmptyManager setHeaderFooterAndEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeaderAndEmptyEnable = isHeadAndEmpty;
        mFooterAndEmptyEnable = isFootAndEmpty;
        return this;
    }

    public boolean isHeaderAndEmptyEnable() {
        return mHeaderAndEmptyEnable;
    }

    public boolean isFooterAndEmptyEnable() {
        return mFooterAndEmptyEnable;
    }

    public void openEmptyView() {
        openEmptyView(new DefaultEmptyView());
    }

    public void openEmptyView(EmptyView emptyView) {
        if (emptyView == null) {
            throw new NullPointerException();
        }
        mEmptyView = emptyView;
    }

    public void emptyLoading() {
        setEmptyStatus(EmptyStatus.STATUS_LOADING);
    }

    public void emptyFail() {
        setEmptyStatus(EmptyStatus.STATUS_FAIL);
    }

    public void emptyFailNetWork() {
        setEmptyStatus(EmptyStatus.STATUS_FAIL_NETWORK);
    }

    private void setEmptyStatus(@EmptyStatus.Status int status) {
        if (mEmptyView == null) {
            return;
        }
        if (mQuickAdapter.getDataViewCount() == 0) {
            mEmptyView.setData(status);
        }
    }

    public EmptyManager setOnEmptyRetryClickListener(OnEmptyRetryClickListener listener) {
        mOnEmptyRetryClickListener = listener;
        return this;
    }

    public OnEmptyRetryClickListener getOnEmptyRetryClickListener() {
        return mOnEmptyRetryClickListener;
    }

    public interface OnEmptyRetryClickListener {
        void onEmptyRetryClick();
    }
}
