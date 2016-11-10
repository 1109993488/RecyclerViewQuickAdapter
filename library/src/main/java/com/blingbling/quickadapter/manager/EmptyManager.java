package com.blingbling.quickadapter.manager;

import android.support.annotation.IntDef;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.DefaultEmptyView;
import com.blingbling.quickadapter.view.EmptyView;
import com.blingbling.quickadapter.view.ItemView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class EmptyManager extends BaseManager {
    public static final int STATUS_EMPTY = 1;
    public static final int STATUS_DEFAULT = 2;
    public static final int STATUS_LOADING = 3;
    public static final int STATUS_NO_DATA = 4;
    public static final int STATUS_FAIL = 5;
    public static final int STATUS_FAIL_NETWORK = 6;

    @IntDef({STATUS_EMPTY, STATUS_DEFAULT, STATUS_LOADING, STATUS_NO_DATA, STATUS_FAIL, STATUS_FAIL_NETWORK})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EmptyStatus {
    }

    private ItemView mItemView = new DefaultEmptyView();

    private boolean mOpenEmpty = false;
    private boolean mHeaderAndEmptyEnable = false;
    private boolean mFooterAndEmptyEnable = false;

    public EmptyManager(BaseQuickAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    public int getItemViewCount() {
        if (!mOpenEmpty) {
            return 0;
        }
        if (mQuickAdapter.getDataViewCount() != 0) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemView.getViewType();
    }

    @Override
    public ItemView getItemView(int viewType) {
        if (viewType == mItemView.getViewType()) {
            return mItemView;
        } else {
            return null;
        }
    }

    public void setEmptyView(ItemView itemView) {
        if (itemView == null) {
            throw new NullPointerException();
        }
        mItemView = itemView;
    }

    public void setEmptyView(EmptyView emptyView) {
        if (emptyView == null) {
            throw new NullPointerException();
        }
        mItemView = emptyView;
    }

    public ItemView getItemView() {
        return mItemView;
    }

    public void setEmptyStatus(@EmptyStatus int status) {
        if (mItemView instanceof EmptyView) {
            mItemView.setData(status);
        } else {
            throw new RuntimeException("If you setEmptyView(ItemView),you should getItemView().setData(Object).");
        }
    }

    public void openEmptyView(boolean open) {
        final int oldCount = getItemViewCount();
        mOpenEmpty = open;
        final int newCount = getItemViewCount();
        if (oldCount == 0 && newCount == 1) {
            mQuickAdapter.notifyItemInserted(getHeaderCount());
        } else if (oldCount == 1 && newCount == 0) {
            mQuickAdapter.notifyItemRemoved(getHeaderCount());
        }
    }

    private int getHeaderCount() {
        if (mHeaderAndEmptyEnable) {
            return mQuickAdapter.getHeaderViewCount();
        } else {
            return 0;
        }
    }

    public void setHeaderAndEmpty(boolean isHeadAndEmpty) {
        setHeaderFooterEmpty(isHeadAndEmpty, false);
    }

    public void setHeaderFooterEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeaderAndEmptyEnable = isHeadAndEmpty;
        mFooterAndEmptyEnable = isFootAndEmpty;
    }

    public boolean isHeaderAndEmptyEnable() {
        return mHeaderAndEmptyEnable;
    }

    public boolean isFooterAndEmptyEnable() {
        return mFooterAndEmptyEnable;
    }

    private OnEmptyRetryClickListener mOnEmptyRetryClickListener;

    public void setOnEmptyRetryClickListener(OnEmptyRetryClickListener listener) {
        mOnEmptyRetryClickListener = listener;
    }

    public OnEmptyRetryClickListener getOnEmptyRetryClickListener() {
        return mOnEmptyRetryClickListener;
    }

    public interface OnEmptyRetryClickListener {
        void onEmptyRetryClick();
    }
}
