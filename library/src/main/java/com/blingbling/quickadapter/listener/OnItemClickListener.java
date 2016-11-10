package com.blingbling.quickadapter.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by BlingBling on 2016/12/16.
 */

public interface OnItemClickListener {
    void onItemClick(RecyclerView.ViewHolder holder, View view, int position);
}
