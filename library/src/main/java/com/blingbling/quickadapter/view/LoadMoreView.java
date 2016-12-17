package com.blingbling.quickadapter.view;

import android.support.annotation.IdRes;
import android.view.View;

import com.blingbling.quickadapter.manager.LoadMoreManager;
import com.blingbling.quickadapter.manager.status.LoadMoreStatus;

/**
 * Created by BlingBling on 2016/11/10.
 */


public abstract class LoadMoreView extends ItemView<Integer> implements View.OnClickListener {

    public LoadMoreView() {
        setData(LoadMoreStatus.STATUS_DEFAULT);
    }

    @Override
    public void setData(@LoadMoreStatus.Status Integer data) {
        super.setData(data);
    }

    @Override
    public Integer getData() {
        return super.getData();
    }

    @Override
    protected void onCreateView(ItemViewHolder holder) {
        holder.setOnClickListener(getLoadFailRetryViewId(), this);
    }

    @Override
    protected void onBindView(ItemViewHolder holder, @LoadMoreStatus.Status Integer data) {
        switch (data) {
            case LoadMoreStatus.STATUS_LOADING:
                visibility(holder, getLoadingViewId(), true);
                visibility(holder, getLoadFailViewId(), false);
                visibility(holder, getLoadEndViewId(), false);
                break;
            case LoadMoreStatus.STATUS_FAIL:
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getLoadFailViewId(), true);
                visibility(holder, getLoadEndViewId(), false);
                break;
            case LoadMoreStatus.STATUS_END:
                visibility(holder, getLoadingViewId(), false);
                visibility(holder, getLoadFailViewId(), false);
                visibility(holder, getLoadEndViewId(), true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        setData(LoadMoreStatus.STATUS_LOADING);
        final LoadMoreManager.OnLoadMoreListener listener = mQuickAdapter.loadMoreManager().getOnLoadMoreListener();
        if (listener != null) {
            listener.onLoadMoreRequested();
        }
    }

    protected void visibility(ItemViewHolder holder, int viewId, boolean visible) {
        if (viewId != 0) {
            holder.setVisible(viewId, visible);
        }
    }

    /**
     * loading view
     *
     * @return
     */
    @IdRes
    public abstract int getLoadingViewId();

    /**
     * load fail view
     *
     * @return
     */
    @IdRes
    public abstract int getLoadFailViewId();

    /**
     * load fail retry view
     *
     * @return
     */
    @IdRes
    public int getLoadFailRetryViewId() {
        return getLoadFailViewId();
    }

    /**
     * load end view，Can return 0
     *
     * @return
     */
    @IdRes
    public abstract int getLoadEndViewId();
}