package com.example.admin.fdm.ui.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by test on 2018/1/9.
 */

public class MyCouponsDetailActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<String> strings;
    private List<String> stringss;


    @Override
    public int setLayout() {
        return R.layout.activity_couponsdetail;
    }

    @Override
    public void initEvent() {

        strings = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            strings.add("2017-10-0"+i);
        }

        stringss = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            stringss.add(""+i);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));

        CommonAdapter<String> adapter = new CommonAdapter<String>(mActivity, R.layout.item_use_coupons, strings) {
            @Override
            protected void convert(ViewHolder holder, String o, final int position) {
                //                holder.setText(R.id.coupons_use_date,strings.get(position));
                LinearLayout ll_layout = holder.getView(R.id.ll_coupons);
                LayoutInflater inflater3 = LayoutInflater.from(mContext);
//                LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                for(int i = 0; i < position; i++){
                    View view = inflater3.inflate(R.layout.item_coupons_situation, null);
                    ll_layout.addView(view);
                }
            }};


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

    @OnClick({R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }

    }

}
