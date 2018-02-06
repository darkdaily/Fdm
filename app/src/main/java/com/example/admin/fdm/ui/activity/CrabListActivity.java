package com.example.admin.fdm.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.ui.adapter.CrabPagerAdapter;
import com.example.admin.fdm.ui.fragment.CrabListFragment;
import com.example.admin.fdm.utils.SPUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CrabListActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    //经纪人id
    private int member_id;

    //页数
    public Integer currentPager = 1;

    //界面状态  待抢单|1，已抢单|2
    public Integer status;

    //抢单界面Fragment
    private List<CrabListFragment> fragments;

    //fragment列表
    private CrabListFragment listFragment;

    //页头
    private ArrayList<String> tabItem;

    //设置布局
    @Override
    public int setLayout() {
        return R.layout.activity_crab_list;
    }

    //初始化
    @Override
    public void initEvent() {
        setView();
    }

    @Override
    public void initData() {

    }

    //布局界面初始化
    public void setView(){
        member_id = (Integer) SPUtil.get(this,"member_id",-1);
        tv_title.setText("抢单列表");
        tabItem = new ArrayList<String>();
        tabItem.add("待抢记录");
        tabItem.add("已抢记录");
        fragments = new ArrayList<CrabListFragment>();
        for(int i = 0; i < tabItem.size();i++){
            Bundle mbundle = new Bundle();
            listFragment = new CrabListFragment();
            int state = i+1;
            mbundle.putInt("state",state);
            listFragment.setArguments(mbundle);
            fragments.add(listFragment);
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        //初始化viewpager
        viewpager.setAdapter(new CrabPagerAdapter(fragmentManager, tabItem, fragments));
        viewpager.setCurrentItem(0);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                fragments.get(position).getGrabListData(CrabListActivity.this,member_id,1,position+1);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //点击事件
    @OnClick({R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
        }
    }


}
