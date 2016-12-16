package com.blingbling.quickadapter.demo.data;

import com.blingbling.quickadapter.demo.entity.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class DataServer {

    public static List<News> getData(int size) {
        return getData(0, size);
    }

    public static List<News> getData(int start, int size) {
        List<News> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int id = start + i;
            list.add(new News("title-" + id, "content-" + id, new Date()));
        }
        return list;
    }
}
