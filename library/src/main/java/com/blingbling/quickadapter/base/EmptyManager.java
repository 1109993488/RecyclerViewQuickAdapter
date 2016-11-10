package com.blingbling.quickadapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.blingbling.quickadapter.base.view.BaseView;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class EmptyManager extends BaseManager<FrameLayout> {

    private boolean mHeaderAndEmptyEnable = false;
    private boolean mFooterAndEmptyEnable = false;
    private OnEmptyRetryClickListener mOnEmptyRetryClickListener;

    public EmptyManager(BaseAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    int getItemViewCount() {
        if (mViews.size() == 0) {
            return 0;
        }
        if (mQuickAdapter.getDataViewCount() != 0) {
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

    public EmptyManager setHeaderAndEmpty(boolean isHeadAndEmpty) {
        return setHeaderFooterAndEmpty(isHeadAndEmpty, false);
    }

    public EmptyManager setHeaderFooterAndEmpty(boolean isHeadAndEmpty, boolean isFootAndEmpty) {
        mHeaderAndEmptyEnable = isHeadAndEmpty;
        mFooterAndEmptyEnable = isFootAndEmpty;
        return this;
    }

    public boolean isHeaderAndEmptyEnable() {
        return mHeaderAndEmptyEnable;
    }

    public boolean isFooterAndEmptyEnable() {
        return mFooterAndEmptyEnable;
    }

    public void setEmptyView(BaseView emptyView) {
        if (emptyView == null) {
            throw new NullPointerException();
        }
        emptyView.setQuickAdapter(mQuickAdapter);

        final int oldCount = getItemViewCount();
        mViews.clear();
        mViews.add(emptyView);
        checkBindLayout();
        final int newCount = getItemViewCount();
        if (oldCount == 0 && newCount == 1) {
            int pos = 0;
            if (mHeaderAndEmptyEnable) {
                pos += mQuickAdapter.getHeaderViewCount();
            }
            mQuickAdapter.notifyItemInserted(pos);
        }
    }

    public EmptyManager setOnEmptyRetryClickListener(OnEmptyRetryClickListener listener) {
        mOnEmptyRetryClickListener = listener;
        return this;
    }

    public OnEmptyRetryClickListener getOnEmptyRetryClickListener() {
        return mOnEmptyRetryClickListener;
    }

    public interface OnEmptyRetryClickListener {
        void onEmptyRetryClick();
    }
}
