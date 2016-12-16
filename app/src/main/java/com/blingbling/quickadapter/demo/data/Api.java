package com.blingbling.quickadapter.demo.data;

import android.os.Handler;

import com.blingbling.quickadapter.demo.entity.News;

import java.util.List;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class Api {
    private static final long DELAY_MILLIS = 1500;
    public static final int TOTAL = 35;
    public static final int PAGE_SIZE = 10;

    /**
     * @param page     page>0
     * @param callBack
     */
    public static void getNewsNoError(final int page, final HttpCallBack<News> callBack) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                final int size;
                if (page < 4) {
                    size = PAGE_SIZE;
                } else if (page == 4) {
                    size = 5;
                } else {
                    size = 0;
                }
                List<News> list = DataServer.getData(page * PAGE_SIZE, size);
                callBack.success(list);
            }
        }, DELAY_MILLIS);
    }
}
