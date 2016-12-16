package com.blingbling.quickadapter.demo.data;

import java.util.List;

/**
 * Created by BlingBling on 2016/12/16.
 */

public interface HttpCallBack<T> {
    void success(List<T> data);

    void fail();
}
