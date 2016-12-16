package com.blingbling.quickadapter.listener;

import android.view.View;

import com.blingbling.quickadapter.view.BaseViewHolder;

/**
 * Created by BlingBling on 2016/12/16.
 */

public interface OnItemClickListener {
    void onItemClick(BaseViewHolder holder, View view, int position);
}
