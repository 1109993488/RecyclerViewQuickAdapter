package com.blingbling.quickadapter.base.view;

import android.support.annotation.IdRes;
import android.view.View;

import com.blingbling.quickadapter.base.LoadMoreManager;
import com.blingbling.quickadapter.base.status.LoadMoreStatus;

/**
 * Created by BlingBling on 2016/12/30.
 */

public abstract class LoadMoreView extends BaseView<Integer> implements View.OnClickListener {

    private View mViewLoading;
    private View mViewFail;
    private View mViewEnd;

    public LoadMoreView() {
        setData(LoadMoreStatus.STATUS_DEFAULT);
    }

    @Override
    protected void onCreateView(View view) {
        mViewLoading = view.findViewById(getLoadingViewId());
        mViewFail = view.findViewById(getLoadFailViewId());
        final int endViewId = getLoadEndViewId();
        if (endViewId != 0) {
            mViewEnd = view.findViewById(endViewId);
        }

        final int failRetryViewId = getLoadFailRetryViewId();
        if (failRetryViewId == 0) {
            mViewFail.setOnClickListener(this);
        } else {
            mViewFail.findViewById(failRetryViewId).setOnClickListener(this);
        }
    }

    @Override
    protected void onBindView(@LoadMoreStatus.Status Integer data) {
        switch (data) {
            case LoadMoreStatus.STATUS_DEFAULT:
                visibility(false, false, false);
                break;
            case LoadMoreStatus.STATUS_LOADING:
                visibility(true, false, false);
                break;
            case LoadMoreStatus.STATUS_FAIL:
                visibility(false, true, false);
                break;
            case LoadMoreStatus.STATUS_END:
                visibility(false, false, true);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (mQuickAdapter != null) {
            final LoadMoreManager.OnLoadMoreListener listener = mQuickAdapter.loadMoreManager().getOnLoadMoreListener();
            if (listener != null) {
                setData(LoadMoreStatus.STATUS_LOADING);
                listener.onLoadMoreRequested();
            }
        }
    }

    protected void visibility(boolean visibleLoading, boolean visibleFail, boolean visibleEnd) {
        mViewLoading.setVisibility(visibleLoading ? View.VISIBLE : View.GONE);
        mViewFail.setVisibility(visibleFail ? View.VISIBLE : View.GONE);
        if (mViewEnd != null) {
            mViewEnd.setVisibility(visibleEnd ? View.VISIBLE : View.GONE);
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