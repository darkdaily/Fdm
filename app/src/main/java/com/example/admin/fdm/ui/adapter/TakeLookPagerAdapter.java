package com.example.admin.fdm.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.admin.fdm.base.BaseSupportFragment;
import com.example.admin.fdm.ui.fragment.OrderListFragment;
import com.example.admin.fdm.ui.fragment.TakeLookListFragment;

import java.util.List;

/**
 * Created by test on 2018/1/5.
 */

public class TakeLookPagerAdapter extends FragmentStatePagerAdapter {


    private List<String> mDatas;
    private List<TakeLookListFragment> mFragments;

    public TakeLookPagerAdapter(FragmentManager fm, List<String> mDatas, List<TakeLookListFragment> fragments) {
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


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
