package com.blingbling.quickadapter.base;

import android.view.ViewGroup;

import com.blingbling.quickadapter.base.view.BaseView;

import java.util.ArrayList;

/**
 * Created by BlingBling on 2016/11/21.
 */

public abstract class BaseManager<T extends ViewGroup> {

    protected BaseAdapter mQuickAdapter;

    private T mLayout;
    protected ArrayList<BaseView> mViews = new ArrayList<>();
    private boolean mShouldBindLayout = false;

    public BaseManager(BaseAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    public final T getLayout() {
        return mLayout;
    }

    abstract int getItemViewCount();

    final T createLayout(ViewGroup parent) {
        if (mLayout == null) {
            mLayout = onCreateLayout(parent);
        }
        return mLayout;
    }

    abstract T onCreateLayout(ViewGroup parent);

    final void bindLayout() {
        if (mShouldBindLayout) {
            mLayout.removeAllViews();
            for (int i = 0, size = mViews.size(); i < size; i++) {
                mLayout.addView(mViews.get(i).getViewOrCreate(mLayout));
            }
            mShouldBindLayout = false;
        }
    }

    final void checkBindLayout() {
        mShouldBindLayout = true;
        if (mLayout != null) {
            bindLayout();
        }
    }
}
