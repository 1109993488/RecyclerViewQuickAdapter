package com.blingbling.quickadapter.demo.entity;

import java.util.Date;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class News {

    private String title;
    private String content;

    public News(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    private Date date;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Date getDate() {
        return date;
    }
}
