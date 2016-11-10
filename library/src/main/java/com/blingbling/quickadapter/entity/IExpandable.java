package com.blingbling.quickadapter.entity;

import java.util.List;

/**
 * Created by BlingBling on 2016/12/30.
 */

public interface IExpandable<T extends ViewType> extends ViewType {

    boolean isExpanded();

    void setExpanded(boolean expanded);

    List<T> getSubItems();
}
