package com.blingbling.quickadapter.manager;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.ViewGroup;

import com.blingbling.quickadapter.BaseQuickAdapter;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class SpanSizeManager {

    private BaseQuickAdapter mQuickAdapter;

    public SpanSizeManager(BaseQuickAdapter quickAdapter) {
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
                    final int type = mQuickAdapter.getViewType(viewType);
                    if (type == BaseQuickAdapter.VIEW_TYPE_DATA) {
                        if (mGridSpanSizeLookup == null) {
                            return 1;
                        } else {
                            return mGridSpanSizeLookup.getSpanSize(gridLayoutManager, position - mQuickAdapter.getHeaderViewCount(), viewType);
                        }
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
            final int type = mQuickAdapter.getViewType(viewType);
            if (type != BaseQuickAdapter.VIEW_TYPE_DATA) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    private GridSpanSizeLookup mGridSpanSizeLookup;

    public void setGridSpanSizeLookup(GridSpanSizeLookup lookup) {
        mGridSpanSizeLookup = lookup;
    }

    public interface GridSpanSizeLookup {
        int getSpanSize(GridLayoutManager manager, int position, int viewType);
    }
}
