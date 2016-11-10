package com.blingbling.quickadapter.manager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.view.ItemView;
import com.blingbling.quickadapter.view.BaseViewHolder;

/**
 * Created by BlingBling on 2016/11/21.
 */

public abstract class BaseManager {

    protected BaseQuickAdapter mQuickAdapter;

    public BaseManager(BaseQuickAdapter quickAdapter) {
        mQuickAdapter = quickAdapter;
    }

    public abstract int getItemViewCount();

    public abstract int getItemViewType(int position);

    public abstract ItemView getItemView(int viewType);

    public BaseViewHolder createViewHolder(ViewGroup parent, int viewType) {
        final ItemView itemView = getItemView(viewType);

        final View view = LayoutInflater.from(parent.getContext()).inflate(itemView.getLayoutId(), parent, false);
        final BaseViewHolder holder = new BaseViewHolder(view);

        itemView.setQuickAdapter(mQuickAdapter);
        itemView.createViewHolder(holder);
        return holder;
    }

    public void bindViewHolder(BaseViewHolder holder){
        getItemView(holder.getItemViewType()).bindViewHolder(holder);
    }
}
