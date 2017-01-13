package com.blingbling.quickadapter.demo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.blingbling.quickadapter.base.LoadMoreManager;
import com.blingbling.quickadapter.base.view.SimpleView;
import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.adapter.TestAdapter;
import com.blingbling.quickadapter.listener.OnItemClickListener;
import com.blingbling.quickadapter.listener.OnItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener,
        LoadMoreManager.OnLoadMoreListener {

    private static final int PAGE_SIZE = 10;
    private static final long DELAY_MILLIS = 1500;
    private static final int TOTAL_COUNT = 30;

    private int currentCount = 0;
    private boolean isError = true;

    private SwipeRefreshLayout mRefresh;
    private RecyclerView mRecyclerView;

    private TestAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
        initAdapter();
        onRefresh();
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
                Log.e("TAG", "adapter-onItemClick------------->" + position);
            }
        });
        adapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder holder, View view, int position) {
                Log.e("TAG", "adapter-onItemLongClick------------->" + position);
            }
        });
    }

    private void initView() {
        mRefresh = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mRefresh.setOnRefreshListener(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initAdapter() {
        adapter = new TestAdapter();
        adapter.loadMoreManager().openLoadMore(this);
//        adapter.loadMoreViewManager().setAutoLoadMoreSize(2);
        mRecyclerView.setAdapter(adapter);
    }


    private List<String> getData(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("string------>" + i);
        }
        return data;
    }

    @Override
    public void onRefresh() {
        if (adapter.getDataViewCount() == 0) {
            adapter.emptyManager().setEmptyView(new SimpleView(R.layout.view_empty_loading));
        }
        adapter.loadMoreManager().setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isError = true;
                adapter.setNewData(getData(PAGE_SIZE));
                currentCount = PAGE_SIZE;

                mRefresh.setRefreshing(false);
                adapter.loadMoreManager().setEnableLoadMore(true);
            }
        }, DELAY_MILLIS);
    }


    @Override
    public void onLoadMoreRequested() {
        mRefresh.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentCount >= TOTAL_COUNT) {
                    adapter.loadMoreManager().loadMoreEnd();
                } else {
                    if (isError) {
                        isError = false;
                        adapter.loadMoreManager().loadMoreFail();
                    } else {
                        currentCount += PAGE_SIZE;
                        adapter.addData(getData(PAGE_SIZE));
                        adapter.loadMoreManager().loadMoreComplete();
                    }
                }
                mRefresh.setEnabled(true);
            }
        }, DELAY_MILLIS);
    }
}
