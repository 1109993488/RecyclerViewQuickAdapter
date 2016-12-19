package com.blingbling.quickadapter.manager;

import android.util.SparseArray;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.ItemView;

/**
 * Created by BlingBling on 2016/12/9.
 */

public class FooterManager extends BaseManager {

    private SparseArray<ItemView> mViews = new SparseArray<>(1);

    public FooterManager(BaseQuickAdapter quickAdapter) {
        super(quickAdapter);
    }

    @Override
    public int getItemViewCount() {
        return mViews.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mViews.keyAt(position);
    }

    @Override
    public ItemView getItemView(int viewType) {
        return mViews.get(viewType);
    }

    public FooterManager addFooterView(ItemView view) {
        int index = getItemViewCount();
        final int viewType = view.getLayoutId();
        if (getItemView(viewType) == null) {
            mViews.put(viewType, view);
        } else {
            throw new IllegalArgumentException("The specified view has been added.");
        }
        if (mQuickAdapter.getEmptyViewCount() == 0) {
            index += mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount();
            mQuickAdapter.notifyItemInserted(index);
        } else {
            if (mQuickAdapter.emptyManager().isFooterAndEmptyEnable()) {
                if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                    index += mQuickAdapter.getHeaderViewCount();
                }
                index += mQuickAdapter.getEmptyViewCount();
                mQuickAdapter.notifyItemInserted(index);
            }
        }
        return this;
    }

    public FooterManager removeFooterView(ItemView view) {
        int index = mViews.indexOfKey(view.getViewType());
        if (index > 0) {
            mViews.removeAt(index);
            if (mQuickAdapter.getEmptyViewCount() == 0) {
                index += mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount();
                mQuickAdapter.notifyItemRemoved(index);
            } else {
                if (mQuickAdapter.emptyManager().isFooterAndEmptyEnable()) {
                    if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                        index += mQuickAdapter.getHeaderViewCount();
                    }
                    index += mQuickAdapter.getEmptyViewCount();
                    mQuickAdapter.notifyItemRemoved(index);
                }
            }
        }
        return this;
    }

    public FooterManager removeAllFooterView() {
        final int size = getItemViewCount();
        if (size > 0) {
            mViews.clear();
            if (mQuickAdapter.getEmptyViewCount() == 0) {
                final int index = mQuickAdapter.getHeaderViewCount() + mQuickAdapter.getDataViewCount();
                mQuickAdapter.notifyItemRangeRemoved(index, size);
            } else {
                if (mQuickAdapter.emptyManager().isFooterAndEmptyEnable()) {
                    int index = 0;
                    if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                        index += mQuickAdapter.getHeaderViewCount();
                    }
                    index += mQuickAdapter.getEmptyViewCount();
                    mQuickAdapter.notifyItemRangeRemoved(index, size);
                }
            }
        }
        return this;
    }
}
