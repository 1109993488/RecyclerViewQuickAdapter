package com.blingbling.quickadapter.demo.adapter;

import android.graphics.Color;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.base.ItemView;
import com.blingbling.quickadapter.base.status.AnimationType;
import com.blingbling.quickadapter.base.view.QuickViewHolder;


public class TestAdapter extends BaseQuickAdapter<String> {

    public TestAdapter() {
        animationManager().setFirstOnly(false).openAnimation(AnimationType.ANIMATION_SCALE_IN);
        addItemView(new ItemView(android.R.layout.simple_list_item_1)
                .listenerOnItemClick()
                .listenerOnItemLongClick());
    }

    @Override
    protected void onBind(QuickViewHolder holder, String item, int position) {
        holder.setText(android.R.id.text1, item);
        holder.itemView.setBackgroundColor(Color.GREEN);
    }

}
