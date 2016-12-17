package com.blingbling.quickadapter.demo.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.entity.HomeItem;
import com.blingbling.quickadapter.manager.AnimationManager;
import com.blingbling.quickadapter.manager.status.AnimationType;
import com.blingbling.quickadapter.view.BaseViewHolder;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem> {

    public HomeAdapter() {
        addItemView(R.layout.item_home);
        animationManager().setFirstOnly(false).openAnimation(AnimationType.ANIMATION_ALPHA);
    }

    @Override
    protected void onCreate(BaseViewHolder holder, int viewType) {
        holder.listenerOnItemClick();
    }

    @Override
    protected void onBind(BaseViewHolder holder, HomeItem item, int position) {
        holder.setText(R.id.info_text, item.getTitle());
        CardView cardView = holder.getView(R.id.card_view);
        cardView.setCardBackgroundColor(Color.parseColor(item.getColorStr()));
    }
}
