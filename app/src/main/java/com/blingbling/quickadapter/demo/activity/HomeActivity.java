package com.blingbling.quickadapter.demo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blingbling.quickadapter.demo.R;
import com.blingbling.quickadapter.demo.adapter.HomeAdapter;
import com.blingbling.quickadapter.demo.entity.HomeItem;
import com.blingbling.quickadapter.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends BaseActivity implements OnItemClickListener {

    private RecyclerView mRecyclerView;
    private HomeAdapter mHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mHomeAdapter = new HomeAdapter();
        mRecyclerView.setAdapter(mHomeAdapter);

        mHomeAdapter.setOnItemClickListener(this);

        initData();
    }

    private void initData() {
        List<HomeItem> list = new ArrayList<>();
        list.add(new HomeItem("PullToRefreshActivity", PullToRefreshActivity.class, "#0dddb8"));
        list.add(new HomeItem("Test", TestActivity.class, "#0bd4c3"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));
        list.add(new HomeItem("Test", TestActivity.class, "#03cdcd"));

        mHomeAdapter.setNewData(list);
    }

    @Override
    public void onItemClick(RecyclerView.ViewHolder holder, View view, int position) {
        Intent intent = new Intent(HomeActivity.this, mHomeAdapter.getItem(position).getActivity());
        startActivity(intent);
    }

}
