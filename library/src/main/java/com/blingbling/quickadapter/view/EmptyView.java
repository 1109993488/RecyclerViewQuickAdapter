package com.blingbling.quickadapter.view;

import android.support.annotation.IdRes;
import android.view.View;

import com.blingbling.quickadapter.manager.EmptyManager;
import com.blingbling.quickadapter.manager.status.EmptyStatus;

/**
 * Created by BlingBling on 2016/12/14.
 */

public abstract class EmptyView extends ItemView<Integer> implements View.OnClickListener {

    public EmptyView() {
        setData(EmptyStatus.STATUS_DEFAULT);
    }

    @Override
    public void setData(@EmptyStatus.Status Integer data) {
        super.setData(data);
    }

    @Override
    protected void onCreateView(ItemViewHolder holder) {
        holder.setOnClickListener(getNoDataRetryViewId(), this)
                .setOnClickListener(getFailRetryViewId(), this)
                .setOnClickListener(getFailNetWorkRetryViewId(), this);
    }

    @Override
    protected void onBindView(ItemViewHolder holder, @EmptyStatus.Status Integer data) {
        switch (data) {
            case EmptyStatus.STATUS_DEFAULT:
                visibility(holder, getDefaultViewId(), true);
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getNoDataViewId(), false);
                visibility(holder, getLoadFailViewId(), false);
                visibility(holder, getLoadFailNetWorkViewId(), false);
                break;
            case EmptyStatus.STATUS_LOADING:
                visibility(holder, getDefaultViewId(), false);
                visibility(holder, getLoadingViewId(), true);
                visibility(holder, getNoDataViewId(), false);
                visibility(holder, getLoadFailViewId(), false);
                visibility(holder, getLoadFailNetWorkViewId(), false);
                break;
            case EmptyStatus.STATUS_NO_DATA:
                visibility(holder, getDefaultViewId(), false);
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getNoDataViewId(), true);
                visibility(holder, getLoadFailViewId(), false);
                visibility(holder, getLoadFailNetWorkViewId(), false);
                break;
            case EmptyStatus.STATUS_FAIL:
                visibility(holder, getDefaultViewId(), false);
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getNoDataViewId(), false);
                visibility(holder, getLoadFailViewId(), true);
                visibility(holder, getLoadFailNetWorkViewId(), false);
                break;
            case EmptyStatus.STATUS_FAIL_NETWORK:
                visibility(holder, getDefaultViewId(), false);
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getNoDataViewId(), false);
                visibility(holder, getLoadFailViewId(), false);
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
        final EmptyManager.OnEmptyRetryClickListener listener = mQuickAdapter.emptyManager().getOnEmptyRetryClickListener();
        if (listener != null) {
            setData(EmptyStatus.STATUS_LOADING);
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
