package com.blingbling.quickadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blingbling.quickadapter.base.status.LoadMoreStatus;
import com.blingbling.quickadapter.base.view.DefaultLoadMoreView;
import com.blingbling.quickadapter.base.view.LoadMoreView;


/**
 * Created by BlingBling on 2016/11/20.
 */

public class LoadMoreManager extends BaseManager<FrameLayout> {

    private @LoadMoreStatus.Status int mLoadMoreStatus = LoadMoreStatus.STATUS_DEFAULT;

    private boolean mEnableLoadMore = true;
    private boolean mLoadMoreEnd = false;
    private boolean mLoadMoreEndGone = false;
    private OnLoadMoreListener mOnLoadMoreListener;

    private int mAutoLoadMoreSize = 1;

    public LoadMoreManager(BaseAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    int getItemViewCount() {
        if (mViews.size() == 0 || mOnLoadMoreListener == null) {
            return 0;
        }
        if (!mEnableLoadMore) {
            return 0;
        }
        final LoadMoreView emptyView = (LoadMoreView) mViews.get(0);
        if (mLoadMoreEnd && (emptyView.getLoadEndViewId() == 0 || mLoadMoreEndGone)) {
            return 0;
        }
        if (mQuickAdapter.getDataViewCount() == 0) {
            return 0;
        }
        return 1;
    }

    @Override
    FrameLayout onCreateLayout(ViewGroup parent) {
        final FrameLayout frameLayout = new FrameLayout(parent.getContext());
        ViewGroup.LayoutParams lp = mViews.get(0).getViewOrCreate(parent).getLayoutParams();
        frameLayout.setLayoutParams(new RecyclerView.LayoutParams(lp.width, lp.height));
        return frameLayout;
    }

    void resetLoadMoreEnd() {
        this.mLoadMoreEnd = false;
        setLoadMoreStatus(LoadMoreStatus.STATUS_DEFAULT);
    }

    void autoLoadMore(int position) {
        if (position < mQuickAdapter.getItemCount() - mAutoLoadMoreSize) {
            return;
        }
        if (mLoadMoreStatus != LoadMoreStatus.STATUS_DEFAULT) {
            return;
        }
        setLoadMoreStatus(LoadMoreStatus.STATUS_LOADING);
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMoreRequested();
        }
    }

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
        loadMoreView.setQuickAdapter(mQuickAdapter);
        mViews.clear();
        mViews.add(loadMoreView);
        mOnLoadMoreListener = listener;
        checkBindLayout();
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public boolean isEnableLoadMore() {
        return mEnableLoadMore;
    }

    public void setEnableLoadMore(boolean enableLoadMore) {
        int oldLoadMoreCount = getItemViewCount();
        mEnableLoadMore = enableLoadMore;
        int newLoadMoreCount = getItemViewCount();
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
        mLoadMoreStatus = status;
        if (mViews.size() != 0) {
            mViews.get(0).setData(status);
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }
}
