package com.blingbling.quickadapter.base;

import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.blingbling.quickadapter.listener.OnItemClickListener;
import com.blingbling.quickadapter.listener.OnItemLongClickListener;
import com.blingbling.quickadapter.listener.OnItemTouchListener;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by BlingBling on 2017/1/9.
 */

public final class ItemView {

    private static final int ITEM_VIEW_ID = 3;

    private int mViewType;
    private @LayoutRes int mLayoutId;

    private Set<Integer> mClickViewIds;
    private Set<Integer> mLongClickViewIds;
    private Set<Integer> mTouchViewIds;

    public ItemView(@LayoutRes int layoutId) {
        this(layoutId, layoutId);
    }

    public ItemView(int viewType, @LayoutRes int layoutId) {
        this.mViewType = viewType;
        this.mLayoutId = layoutId;
    }

    public int getViewType() {
        return mViewType;
    }

    @LayoutRes
    public int getLayoutId() {
        return mLayoutId;
    }

    private void checkClickViewIds() {
        if (mClickViewIds == null) {
            mClickViewIds = new HashSet<>();
        }
    }

    private void checkLongClickViewIds() {
        if (mLongClickViewIds == null) {
            mLongClickViewIds = new HashSet<>();
        }
    }

    private void checkTouchViewIds() {
        if (mTouchViewIds == null) {
            mTouchViewIds = new HashSet<>();
        }
    }

    public ItemView listenerOnItemClick() {
        checkClickViewIds();
        mClickViewIds.add(ITEM_VIEW_ID);
        return this;
    }

    public ItemView listenerOnItemClick(@IdRes int viewId) {
        checkClickViewIds();
        mClickViewIds.add(viewId);
        return this;
    }

    public ItemView listenerOnItemLongClick() {
        checkLongClickViewIds();
        mLongClickViewIds.add(ITEM_VIEW_ID);
        return this;
    }

    public ItemView listenerOnItemLongClick(@IdRes int viewId) {
        checkLongClickViewIds();
        mLongClickViewIds.add(viewId);
        return this;
    }

    public ItemView listenerOnItemTouch() {
        checkTouchViewIds();
        mTouchViewIds.add(ITEM_VIEW_ID);
        return this;
    }

    public ItemView listenerOnItemTouch(@IdRes int viewId) {
        checkTouchViewIds();
        mTouchViewIds.add(viewId);
        return this;
    }

    void bindClickListener(final BaseAdapter adapter, final RecyclerView.ViewHolder holder, final OnItemClickListener listener) {
        if (listener == null) {
            return;
        }
        for (Integer integer : mClickViewIds) {
            final View view;
            if (integer == ITEM_VIEW_ID) {
                view = holder.itemView;
            } else {
                view = holder.itemView.findViewById(integer);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(holder, v, pos - adapter.getHeaderViewCount());
                    }
                }
            });
        }
    }

    void bindLongClickListener(final BaseAdapter adapter, final RecyclerView.ViewHolder holder, final OnItemLongClickListener listener) {
        if (listener == null) {
            return;
        }
        for (Integer integer : mLongClickViewIds) {
            final View view;
            if (integer == ITEM_VIEW_ID) {
                view = holder.itemView;
            } else {
                view = holder.itemView.findViewById(integer);
            }
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemLongClick(holder, v, pos - adapter.getHeaderViewCount());
                    }
                    return true;
                }
            });
        }
    }

    void bindTouchListener(final BaseAdapter adapter, final RecyclerView.ViewHolder holder, final OnItemTouchListener listener) {
        if (listener == null) {
            return;
        }
        for (Integer integer : mTouchViewIds) {
            final View view;
            if (integer == ITEM_VIEW_ID) {
                view = holder.itemView;
            } else {
                view = holder.itemView.findViewById(integer);
            }
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int pos = holder.getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        return listener.onItemTouch(holder, v, event, pos - adapter.getHeaderViewCount());
                    } else {
                        return false;
                    }
                }
            });
        }
    }
}
