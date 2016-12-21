package com.blingbling.quickadapter.demo.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.adapter.QuickAdapter;
import com.blingbling.quickadapter.demo.data.Api;
import com.blingbling.quickadapter.demo.data.HttpCallBack;
import com.blingbling.quickadapter.demo.entity.News;
import com.blingbling.quickadapter.manager.LoadMoreManager;

import java.util.List;

/**
 * Created by BlingBling on 2016/12/16.
 */

public class PullToRefreshActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener, LoadMoreManager.OnLoadMoreListener {

    private SwipeRefreshLayout mRefreshLayout;
    private RecyclerView mRecyclerView;
    private QuickAdapter mQuickAdapter;

    private int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulltorefresh);
        initView();
        initAdapter();
        onRefresh();
    }

    private void initView() {
        mRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initAdapter() {
        mQuickAdapter = new QuickAdapter();
        mQuickAdapter.loadMoreManager().openLoadMore(this);

        mRecyclerView.setAdapter(mQuickAdapter);
    }

    @Override
    public void onRefresh() {
        if (mQuickAdapter.getDataViewCount() == 0) {
            showProgress();
        }
        mQuickAdapter.loadMoreManager().setEnableLoadMore(false);

        currentPage = 1;
        Api.getNewsNoError(currentPage, new Request(currentPage));
    }

    @Override
    public void onLoadMoreRequested() {
        mRefreshLayout.setEnabled(false);

        currentPage++;
        Api.getNewsNoError(currentPage, new Request(currentPage));
    }

    public class Request implements HttpCallBack<News> {

        private int mPage;

        public Request(int page) {
            mPage = page;
        }

        @Override
        public void success(List<News> data) {
            requestEnd();
            if (mPage == 1) {
                mQuickAdapter.setNewData(data);
                if (data.size() < Api.PAGE_SIZE) {
                    mQuickAdapter.loadMoreManager().loadMoreEnd();
                }
            } else {
                mQuickAdapter.addData(data);
                if (data.size() < Api.PAGE_SIZE) {
                    mQuickAdapter.loadMoreManager().loadMoreEnd();
                } else {
                    mQuickAdapter.loadMoreManager().loadMoreComplete();
                }
            }
        }

        @Override
        public void fail() {
            requestEnd();
            if (mPage == 1) {
                showToast("refresh fail");
            } else {
                showToast("load more fail");
                mQuickAdapter.loadMoreManager().loadMoreFail();
            }
        }

        private void requestEnd() {
            if (mPage == 1) {
                hideProgress();
                mRefreshLayout.setRefreshing(false);
                mQuickAdapter.loadMoreManager().setEnableLoadMore(true);
            } else {
                mRefreshLayout.setEnabled(true);
            }
        }
    }
}
