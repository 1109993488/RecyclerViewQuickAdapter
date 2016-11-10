package com.blingbling.quickadapter.base.view;

import com.blingbling.quickadapter.R;

/**
 * Created by BlingBling on 2016/11/11.
 */

public class DefaultLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.quick_view_load_more;
    }

    @Override
    public int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    public int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }

    @Override
    public int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }
}