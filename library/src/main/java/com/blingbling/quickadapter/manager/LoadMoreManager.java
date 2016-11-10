package com.blingbling.quickadapter.manager;

import android.support.annotation.IntDef;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.DefaultLoadMoreView;
import com.blingbling.quickadapter.view.ItemView;
import com.blingbling.quickadapter.view.LoadMoreView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * Created by BlingBling on 2016/11/20.
 */

public class LoadMoreManager extends BaseManager {

    public static final int STATUS_DEFAULT = 1;
    public static final int STATUS_LOADING = 2;
    public static final int STATUS_FAIL = 3;
    public static final int STATUS_END = 4;

    @IntDef({STATUS_DEFAULT, STATUS_LOADING, STATUS_FAIL, STATUS_END})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LoadMoreStatus {
    }

    private LoadMoreView mLoadMoreView = new DefaultLoadMoreView();

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
        if (mOnLoadMoreListener == null) {
            return 0;
        }
        if (!mEnableLoadMore) {
            return 0;
        }
        if (mQuickAdapter.getDataViewCount() == 0) {
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

    public void setLoadMoreView(LoadMoreView loadMoreView) {
        if (loadMoreView == null) {
            throw new NullPointerException();
        }
        mLoadMoreView = loadMoreView;
    }

    public LoadMoreView getLoadMoreView() {
        return mLoadMoreView;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener listener) {
        mOnLoadMoreListener = listener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return mOnLoadMoreListener;
    }

    public void setLoadMoreEnd(boolean loadMoreEnd) {
        this.mLoadMoreEnd = loadMoreEnd;
        mLoadMoreView.setData(STATUS_DEFAULT);
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

    public void loadMoreFail() {
        mLoadMoreView.setData(STATUS_FAIL);
    }

    public void loadMoreComplete() {
        mLoadMoreView.setData(STATUS_DEFAULT);
    }

    public void loadMoreEnd() {
        loadMoreEnd(false);
    }

    public void loadMoreEnd(boolean gone) {
        final int oldCount = getItemViewCount();
        mLoadMoreEnd = true;
        mLoadMoreEndGone = gone;
        mLoadMoreView.setData(STATUS_END);
        final int newCount = getItemViewCount();
        if (oldCount == 1 && newCount == 0) {
            mQuickAdapter.notifyItemRemoved(mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount() + mQuickAdapter.getFooterViewCount());
        }
    }

    public void setAutoLoadMoreSize(int autoLoadMoreSize) {
        if (autoLoadMoreSize >= 1) {
            mAutoLoadMoreSize = autoLoadMoreSize;
        }
    }

    public void autoLoadMore(int position) {
        if (getItemViewCount() == 0) {
            return;
        }
        if (position < mQuickAdapter.getItemCount() - mAutoLoadMoreSize) {
            return;
        }
        if (mLoadMoreView.getData() != STATUS_DEFAULT) {
            return;
        }
        mLoadMoreView.setData(STATUS_LOADING);
        if (mOnLoadMoreListener != null) {
            mOnLoadMoreListener.onLoadMoreRequested();
        }
    }

    public interface OnLoadMoreListener {
        void onLoadMoreRequested();
    }
}