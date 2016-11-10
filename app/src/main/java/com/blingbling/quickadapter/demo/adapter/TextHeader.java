package com.blingbling.quickadapter.demo.adapter;

import android.view.View;
import android.widget.TextView;

import com.blingbling.quickadapter.base.view.BaseView;
import com.blingbling.quickadapter.demo.R;

/**
 * Created by BlingBling on 2016/12/30.
 */

public class TextHeader extends BaseView<String> {
    private TextView tv;

    @Override
    public int getLayoutId() {
        return R.layout.view_header;
    }

    @Override
    protected void onCreateView(View view) {
        tv = (TextView) view.findViewById(R.id.tv);
    }

    @Override
    protected void onBindView(String data) {
        tv.setText(data);
    }

}
