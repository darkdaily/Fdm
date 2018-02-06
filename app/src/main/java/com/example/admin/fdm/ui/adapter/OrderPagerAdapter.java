package com.example.admin.fdm.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.admin.fdm.ui.activity.OrderListActivity;
import com.example.admin.fdm.ui.fragment.OrderListFragment;

import java.util.List;

/**
 * Created by test on 2018/1/10.
 */

public class OrderPagerAdapter extends FragmentStatePagerAdapter {

    private List<String> mDatas;
    private List<OrderListFragment> mFragments;

    public OrderPagerAdapter(FragmentManager fm, List<String> mDatas, List<OrderListFragment> fragments) {
        super(fm);
        this.mDatas = mDatas;

        this.mFragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mDatas.size() == mFragments.size() ? mFragments.size() : 0;
    }

    /**
     * 重写此方法，返回TabLayout的内容
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return mDatas.get(position);
    }
}
