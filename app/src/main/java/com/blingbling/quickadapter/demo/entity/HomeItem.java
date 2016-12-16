package com.blingbling.quickadapter.demo.entity;

import android.app.Activity;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class HomeItem {
    private String title;
    private Class<? extends Activity> activity;
    private String colorStr;

    public HomeItem(String title, Class<? extends Activity> activity, String colorStr) {
        this.title = title;
        this.activity = activity;
        this.colorStr = colorStr;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setActivity(Class<? extends Activity> activity) {
        this.activity = activity;
    }

    public void setColorStr(String colorStr) {
        this.colorStr = colorStr;
    }

    public String getTitle() {
        return title;
    }

    public Class<? extends Activity> getActivity() {
        return activity;
    }

    public String getColorStr() {
        return colorStr;
    }
}
