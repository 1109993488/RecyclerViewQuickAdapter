package com.blingbling.quickadapter.demo.adapter;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.base.ItemView;
import com.blingbling.quickadapter.base.view.QuickViewHolder;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.entity.News;

import java.text.SimpleDateFormat;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class QuickAdapter extends BaseQuickAdapter<News> {

    public QuickAdapter() {
        addItemView(new ItemView(R.layout.item_news).listenerOnItemClick());
    }

    @Override
    protected void onBind(QuickViewHolder holder, News item, int position) {
        holder.setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getContent())
                .setText(R.id.date, new SimpleDateFormat("MM-dd HH:mm").format(item.getDate()));
    }
}
