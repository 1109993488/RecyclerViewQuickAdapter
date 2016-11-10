package com.blingbling.quickadapter.view;

import android.support.annotation.LayoutRes;

import com.blingbling.quickadapter.BaseQuickAdapter;

/**
 * Created by BlingBling on 2016/11/15.
 */

public abstract class ItemView<T> {

    protected BaseQuickAdapter mAdapter;
    private ItemViewHolder mItemViewHolder;
    private T mData;
    private boolean mShouldBindData = false;

    public void setQuickAdapter(BaseQuickAdapter adapter) {
        mAdapter = adapter;
    }

    public void createViewHolder(BaseViewHolder holder) {
        if (mItemViewHolder == null) {
            mItemViewHolder = new ItemViewHolder(holder.itemView);
            onCreateView(mItemViewHolder);
        }
    }

    public void bindViewHolder(BaseViewHolder holder) {
        if (mShouldBindData) {
            onBindView(mItemViewHolder, mData);
            mShouldBindData = false;
        }
    }

    public void setData(T data) {
        mData = data;
        if (mItemViewHolder == null) {
            mShouldBindData = true;
        } else {
            onBindView(mItemViewHolder, mData);
        }
    }

    public T getData() {
        return mData;
    }

    public int getViewType() {
        return getLayoutId();
    }

    /**
     * ViewType and LayoutId are the same
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutId();

    protected abstract void onCreateView(ItemViewHolder holder);

    protected abstract void onBindView(ItemViewHolder holder, T data);
}
