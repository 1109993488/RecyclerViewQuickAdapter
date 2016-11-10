package com.blingbling.quickadapter.base.view;

import android.support.annotation.LayoutRes;
import android.view.View;

/**
 * Created by BlingBling on 2017/1/4.
 */

public class SimpleView extends BaseView {

    private final int mLayoutId;

    public SimpleView(@LayoutRes int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public int getLayoutId() {
        return mLayoutId;
    }

    @Override
    protected void onCreateView(View view) {
    }

    @Override
    protected void onBindView(Object data) {
    }
}
