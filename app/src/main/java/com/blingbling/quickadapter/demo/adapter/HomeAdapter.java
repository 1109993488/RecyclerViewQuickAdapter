package com.blingbling.quickadapter.demo.adapter;

import android.graphics.Color;
import android.support.v7.widget.CardView;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.base.ItemView;
import com.blingbling.quickadapter.base.status.AnimationType;
import com.blingbling.quickadapter.base.view.QuickViewHolder;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.entity.HomeItem;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem> {

    public HomeAdapter() {
        animationManager().setFirstOnly(false).openAnimation(AnimationType.ANIMATION_ALPHA_IN);
        addItemView(new ItemView(R.layout.item_home).listenerOnItemClick());
    }

    @Override
    protected void onBind(QuickViewHolder holder, HomeItem item, int position) {
        holder.setText(R.id.info_text, item.getTitle());
        CardView cardView = holder.getView(R.id.card_view);
        cardView.setCardBackgroundColor(Color.parseColor(item.getColorStr()));
    }
}
