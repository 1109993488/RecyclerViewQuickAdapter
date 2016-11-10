package com.blingbling.quickadapter.base.view;

import com.blingbling.quickadapter.R;

/**
 * Created by BlingBling on 2016/12/14.
 */

public class DefaultEmptyView extends EmptyView {
    @Override
    public int getDefaultViewId() {
        return R.id.empty_default_view;
    }

    @Override
    public int getLoadingViewId() {
        return R.id.empty_loading_view;
    }

    @Override
    public int getNoDataViewId() {
        return R.id.empty_no_data_view;
    }

    @Override
    public int getFailViewId() {
        return R.id.empty_fail_view;
    }

    @Override
    public int getFailNetWorkViewId() {
        return R.id.empty_fail_view;
    }

    @Override
    public int getLayoutId() {
        return R.layout.quick_view_empty;
    }

}
