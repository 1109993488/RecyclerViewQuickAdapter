package com.blingbling.quickadapter.manager;

import android.util.SparseArray;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.ItemView;

/**
 * Created by BlingBling on 2016/12/9.
 */

public class HeaderManager extends BaseManager {

    private SparseArray<ItemView> mViews = new SparseArray<>(1);

    public HeaderManager(BaseQuickAdapter quickAdapter) {
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

    public void addHeaderView(ItemView view) {
        final int index = getItemViewCount();
        final int viewType = view.getViewType();
        if (getItemView(viewType) == null) {
            mViews.put(viewType, view);
        } else {
            throw new IllegalArgumentException("The specified view has been added.");
        }
        if (mQuickAdapter.getEmptyViewCount() == 0) {
            mQuickAdapter.notifyItemInserted(index);
        } else {
            if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                mQuickAdapter.notifyItemInserted(index);
            }
        }
    }

    public void removeHeaderView(ItemView view) {
        final int index = mViews.indexOfKey(view.getViewType());
        if (index > 0) {
            mViews.removeAt(index);
            if (mQuickAdapter.getEmptyViewCount() == 0) {
                mQuickAdapter.notifyItemRemoved(index);
            } else {
                if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                    mQuickAdapter.notifyItemRemoved(index);
                }
            }
        }
    }

    public void removeAllHeaderView() {
        final int size = getItemViewCount();
        if (size > 0) {
            mViews.clear();
            if (mQuickAdapter.getEmptyViewCount() == 0) {
                mQuickAdapter.notifyItemRangeRemoved(0, size);
            } else {
                if (mQuickAdapter.emptyManager().isHeaderAndEmptyEnable()) {
                    mQuickAdapter.notifyItemRangeRemoved(0, size);
                }
            }
        }
    }
}
