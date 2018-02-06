package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/28.
 */

public class ReceiveCouponsActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<String> strings;


    @Override
    public int setLayout() {
        return R.layout.activity_receive_coupons;
    }

    @Override
    public void initEvent() {

        strings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            strings.add("");
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        CommonAdapter<String> adapter = new CommonAdapter<String>(mActivity, R.layout.item_coupons, strings) {
            @Override
            protected void convert(ViewHolder holder, String o, final int position) {

            }
        };

        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_right,R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_right:
                startActivity(MyCouponsActivity.class);
                break;
            case R.id.iv_left:
                finish();
                break;
        }

    }

}
