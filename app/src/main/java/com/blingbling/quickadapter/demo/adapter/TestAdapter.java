package com.blingbling.quickadapter.demo.adapter;

import android.graphics.Color;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.manager.AnimationManager;
import com.blingbling.quickadapter.view.BaseViewHolder;
import com.blingbling.quickadapter.view.ItemView;
import com.blingbling.quickadapter.view.ItemViewHolder;


public class TestAdapter extends BaseQuickAdapter<String> {

    public TestAdapter() {
        emptyManager().openEmptyView(true);
        animationManager().setFirstOnly(false).openAnimation(AnimationManager.ANIMATION_SCALE);
        addItemView(android.R.layout.simple_list_item_1);
        headerManager().addHeaderView(new ItemView() {
            @Override
            public int getLayoutId() {
                return R.layout.view_header;
            }

            @Override
            protected void onCreateView(ItemViewHolder holder) {

            }

            @Override
            protected void onBindView(ItemViewHolder holder, Object data) {

            }


        });
        footerManager().addFooterView(new ItemView() {
            @Override
            public int getLayoutId() {
                return R.layout.view_footer;
            }

            @Override
            protected void onCreateView(ItemViewHolder holder) {

            }

            @Override
            protected void onBindView(ItemViewHolder holder, Object data) {

            }
        });
    }

    @Override
    protected void onCreate(BaseViewHolder holder, int viewType) {
        holder.listenerOnItemClick().listenerOnItemLongClick();
    }

    @Override
    protected void onBind(BaseViewHolder holder, String item, int position) {
        holder.setText(android.R.id.text1, item);
        holder.itemView.setBackgroundColor(Color.GREEN);
    }

}
