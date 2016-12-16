package com.blingbling.quickadapter.demo.adapter;

import com.blingbling.quickadapter.BaseQuickAdapter;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.entity.News;
import com.blingbling.quickadapter.view.BaseViewHolder;

import java.text.SimpleDateFormat;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class QuickAdapter extends BaseQuickAdapter<News> {

    public QuickAdapter() {
        addItemView(R.layout.item_news);
    }

    @Override
    protected void onCreate(BaseViewHolder holder, int viewType) {
        holder.listenerOnItemClick();
    }

    @Override
    protected void onBind(BaseViewHolder holder, News item, int position) {
        holder.setText(R.id.title, item.getTitle())
                .setText(R.id.content, item.getContent())
                .setText(R.id.date, new SimpleDateFormat("MM-dd HH:mm").format(item.getDate()));
    }
}
