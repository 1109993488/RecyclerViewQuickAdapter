package com.blingbling.quickadapter.view;

import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.listener.OnItemClickListener;
import com.blingbling.quickadapter.listener.OnItemLongClickListener;

import java.util.HashSet;
import java.util.LinkedHashSet;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class BaseViewHolder extends ItemViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private BaseQuickAdapter mQuickAdapter;

    private final LinkedHashSet<View> mClickViews = new LinkedHashSet<>();
    private final LinkedHashSet<View> mLongClickViews = new LinkedHashSet<>();


    public BaseViewHolder(BaseQuickAdapter quickAdapter, View view) {
        super(view);
        mQuickAdapter = quickAdapter;
    }

    public HashSet<View> getClickViews() {
        return mClickViews;
    }

    public HashSet<View> getLongClickViews() {
        return mLongClickViews;
    }

    /**
     * Add links into a TextView.
     *
     * @param viewId The id of the TextView to linkify.
     */
    public ItemViewHolder linkify(int viewId) {
        final TextView view = getView(viewId);
        mClickViews.add(view);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public BaseViewHolder listenerOnItemClick() {
        mClickViews.add(itemView);
        itemView.setOnClickListener(this);
        return this;
    }

    public BaseViewHolder listenerOnItemClick(int viewId) {
        final View view = getView(viewId);
        mClickViews.add(view);
        view.setOnClickListener(this);
        return this;
    }

    public BaseViewHolder listenerOnItemLongClick() {
        mLongClickViews.add(itemView);
        itemView.setOnLongClickListener(this);
        return this;
    }

    public BaseViewHolder listenerOnItemLongClick(int viewId) {
        final View view = getView(viewId);
        mLongClickViews.add(view);
        view.setOnLongClickListener(this);
        return this;
    }

    @Override
    public void onClick(View v) {
        final OnItemClickListener listener = mQuickAdapter.getOnItemClickListener();
        if (listener != null) {
            listener.onItemClick(this, v, getLayoutPosition() - mQuickAdapter.getHeaderViewCount());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        final OnItemLongClickListener listener = mQuickAdapter.getOnItemLongClickListener();
        if (listener != null) {
            listener.onItemLongClick(this, v, getLayoutPosition() - mQuickAdapter.getHeaderViewCount());
        }
        return true;
    }
}