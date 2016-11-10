package com.blingbling.quickadapter.base.view;

import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blingbling.quickadapter.base.BaseAdapter;

/**
 * Created by BlingBling on 2016/12/29.
 */

public abstract class BaseView<T> {

    protected BaseAdapter mQuickAdapter;
    private View mView;

    private T mData;
    private boolean mShouldBindData = false;

    public final void setQuickAdapter(BaseAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    public final View getViewOrCreate(ViewGroup parent) {
        if (mView == null) {
            mView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), parent, false);
            onCreateView(mView);
            if (mShouldBindData) {
                onBindView(mData);
                mShouldBindData = false;
            }
        }
        return mView;
    }

    public final void setData(T data) {
        mData = data;
        if (mView == null) {
            mShouldBindData = true;
        } else {
            onBindView(mData);
        }
    }

    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void onCreateView(View view);

    protected abstract void onBindView(T data);

}
