package com.blingbling.quickadapter.view;

import android.support.annotation.IdRes;
import android.view.View;

import com.blingbling.quickadapter.manager.EmptyManager;

/**
 * Created by BlingBling on 2016/12/14.
 */

public abstract class EmptyView extends ItemView<Integer> implements View.OnClickListener {

    public EmptyView() {
        setData(EmptyManager.STATUS_DEFAULT);
    }

    @Override
    public void setData(@EmptyManager.EmptyStatus Integer data) {
        super.setData(data);
    }

    @Override
    protected void onCreateView(ItemViewHolder holder) {
        holder.setOnClickListener(getNoDataRetryViewId(), this)
                .setOnClickListener(getFailRetryViewId(), this)
                .setOnClickListener(getFailNetWorkRetryViewId(), this);
    }

    @Override
    protected void onBindView(ItemViewHolder holder, @EmptyManager.EmptyStatus Integer data) {
        visibility(holder, getDefaultViewId(), false);
        visibility(holder, getLoadingViewId(), false);
        visibility(holder, getNoDataViewId(), false);
        visibility(holder, getLoadFailViewId(), false);
        visibility(holder, getLoadFailNetWorkViewId(), false);
        switch (data) {
            case EmptyManager.STATUS_DEFAULT:
                visibility(holder, getDefaultViewId(), true);
                break;
            case EmptyManager.STATUS_LOADING:
                visibility(holder, getLoadingViewId(), true);
                break;
            case EmptyManager.STATUS_NO_DATA:
                visibility(holder, getNoDataViewId(), true);
                break;
            case EmptyManager.STATUS_FAIL:
                visibility(holder, getLoadFailViewId(), true);
                break;
            case EmptyManager.STATUS_FAIL_NETWORK:
                visibility(holder, getLoadFailNetWorkViewId(), true);
                break;
        }
    }

    protected void visibility(ItemViewHolder holder, int viewId, boolean visible) {
        if (viewId != 0) {
            holder.setVisible(viewId, visible);
        }
    }

    @Override
    public void onClick(View v) {
        final EmptyManager.OnEmptyRetryClickListener listener = mAdapter.emptyManager().getOnEmptyRetryClickListener();
        if (listener != null) {
            setData(EmptyManager.STATUS_LOADING);
            listener.onEmptyRetryClick();
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
    public abstract int getLoadFailViewId();

    @IdRes
    public int getFailRetryViewId() {
        return getLoadFailViewId();
    }

    @IdRes
    public abstract int getLoadFailNetWorkViewId();

    @IdRes
    public int getFailNetWorkRetryViewId() {
        return getLoadFailNetWorkViewId();
    }
}
