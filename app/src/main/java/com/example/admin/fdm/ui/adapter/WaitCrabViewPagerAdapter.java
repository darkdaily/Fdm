package com.example.admin.fdm.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fushuang on 2017/10/9.
 */

public class WaitCrabViewPagerAdapter extends FragmentPagerAdapter {
    private String[] titles=new String[]{"推荐大秘","推荐房源"};

    public WaitCrabViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }
}
