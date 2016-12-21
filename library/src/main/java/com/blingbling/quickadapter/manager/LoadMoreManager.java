package com.blingbling.quickadapter.manager;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.manager.status.LoadMoreStatus;
import com.blingbling.quickadapter.view.DefaultLoadMoreView;
import com.blingbling.quickadapter.view.ItemView;
import com.blingbling.quickadapter.view.LoadMoreView;


/**
 * Created by BlingBling on 2016/11/20.
 */

public class LoadMoreManager extends BaseManager {

    private LoadMoreView mLoadMoreView;

    private boolean mEnableLoadMore = true;
    private boolean mLoadMoreEnd = false;
    private boolean mLoadMoreEndGone = false;
    private OnLoadMoreListener mOnLoadMoreListener;

    private int mAutoLoadMoreSize = 1;

    public LoadMoreManager(BaseQuickAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    public int getItemViewCount() {
        if (mLoadMoreView == null || mOnLoadMoreListener == null) {
            return 0;
        }
        if (!mEnableLoadMore) {
            return 0;
        }
        if (mLoadMoreEnd && (mLoadMoreView.getLayoutId() == 0 || mLoadMoreEndGone)) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mLoadMoreView.getViewType();
    }

    @Override
    public ItemView getItemView(int viewType) {
        if (viewType == mLoadMoreView.getViewType()) {
            return mLoadMoreView;
        } else {
            return null;
        }
    }

    public void resetLoadMoreEnd() {
        this.mLoadMoreEnd = false;
        mLoadMoreView.setData(LoadMoreStatus.STATUS_DEFAULT);
    }

    public void autoLoadMore(int position) {
        if (position < mQuickAdapter.getItemCount() - mAutoLoadMoreSize) {
            return;
        }
        if (mLoadMoreView.getData() != LoadMoreStatus.STATUS_DEFAULT) {
            return;
        }
        mLoadMoreView.setData(LoadMoreStatus.STATUS_LOADING);
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMoreRequested();
        }
    }

    //user method

    public void setAutoLoadMoreSize(int autoLoadMoreSize) {
        if (autoLoadMoreSize >= 1) {
            mAutoLoadMoreSize = autoLoadMoreSize;
        }
    }

    public void openLoadMore(OnLoadMoreListener listener) {
        openLoadMore(new DefaultLoadMoreView(), listener);
    }

    public void openLoadMore(LoadMoreView loadMoreView, OnLoadMoreListener listener) {
        if (loadMoreView == null) {
            throw new NullPointerException();
        }
        if (listener == null) {
            throw new NullPointerException();
        }
        mLoadMoreView = loadMoreView;
        mOnLoadMoreListener = listener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public boolean isEnableLoadMore() {
        return mEnableLoadMore;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        int oldLoadMoreCount = mQuickAdapter.getLoadMoreViewCount();
        mEnableLoadMore = enableLoadMore;
        int newLoadMoreCount = mQuickAdapter.getLoadMoreViewCount();
        if (oldLoadMoreCount == 1 && newLoadMoreCount == 0) {
            mQuickAdapter.notifyItemRemoved(mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount() + mQuickAdapter.getFooterViewCount());
        } else if (oldLoadMoreCount == 0 && newLoadMoreCount == 1) {
            mQuickAdapter.notifyItemInserted(mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount() + mQuickAdapter.getFooterViewCount());
        }
    }

    public void loadMoreComplete() {
        setLoadMoreStatus(LoadMoreStatus.STATUS_DEFAULT);
    }

    public void loadMoreFail() {
        setLoadMoreStatus(LoadMoreStatus.STATUS_FAIL);
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreEnd(boolean gone) {
        final int oldCount = mQuickAdapter.getLoadMoreViewCount();
        mLoadMoreEnd = true;
        mLoadMoreEndGone = gone;
        setLoadMoreStatus(LoadMoreStatus.STATUS_END);
        final int newCount = mQuickAdapter.getLoadMoreViewCount();
        if (oldCount == 1 && newCount == 0) {
            mQuickAdapter.notifyItemRemoved(mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount() + mQuickAdapter.getFooterViewCount());
        }
    }

    private void setLoadMoreStatus(@LoadMoreStatus.Status int status) {
        if (mLoadMoreView == null) {
            return;
        }
        mLoadMoreView.setData(status);
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }
}
