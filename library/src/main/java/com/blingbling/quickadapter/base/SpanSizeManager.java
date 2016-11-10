package com.blingbling.quickadapter.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class SpanSizeManager {

    private BaseAdapter mQuickAdapter;

    public SpanSizeManager(BaseAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        final RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    final int viewType = mQuickAdapter.getItemViewType(position);
                    if (mQuickAdapter.isDataView(viewType)) {
                        return mQuickAdapter.getSpanSize(gridLayoutManager, position - mQuickAdapter.getHeaderViewCount());
                    } else {
                        return gridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }

    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        final ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp instanceof StaggeredGridLayoutManager.LayoutParams) {
            final int viewType = holder.getItemViewType();
            if (mQuickAdapter.isDataView(viewType)) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }
}
