package com.example.admin.fdm.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.CommonAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.MultiItemTypeAdapter;
import com.example.admin.fdm.ui.adapter.ZhyBaseRecycleAdapter.base.ViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/25.
 */

public class SystemMessageActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindString(R.string.system_message)
    String str;

    @BindView(R.id.recyclerView_system)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<String> list;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public int setLayout() {
        return R.layout.activity_system_message;
    }

    @Override
    public void initEvent() {

        tv_title.setText(str);
        list = new ArrayList();

        list.add("1");
        list.add("2");
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(llm);
//        mAdapter = new SystemMessageAdapter(this, list);
//        recyclerView.setAdapter(mAdapter);
//        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        CommonAdapter<String> adapter = new CommonAdapter<String>(SystemMessageActivity.this,R.layout.new_item,list) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.new_title, list.get(position));
                ImageView new_image = holder.getView(R.id.new_image);

                Glide.with(SystemMessageActivity.this)
                        .load("http://d.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31cc4e964a88dd6277f9f2ff8c8.jpg")
                        .placeholder(R.drawable.about_logo)
                        .crossFade()
                        .into(new_image);
                holder.setOnClickListener(R.id.new_cardView, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(NewDetailActivity.class);
                    }
                });
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


        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
            }
        });


    }

    @Override
    public void initData() {

    }


    @OnClick({R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

        }

    }
}
