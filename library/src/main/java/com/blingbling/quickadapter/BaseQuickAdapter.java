package com.blingbling.quickadapter;

import android.view.View;

import com.blingbling.quickadapter.base.BaseAdapter;
import com.blingbling.quickadapter.base.view.QuickViewHolder;

/**
 * Created by BlingBling on 2017/1/9.
 */

public abstract class BaseQuickAdapter<T> extends BaseAdapter<T, QuickViewHolder> {

    @Override
    protected QuickViewHolder createViewHolder(int viewType, View view) {
        return new QuickViewHolder(view);
    }

}
