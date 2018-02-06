package com.example.admin.fdm.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.ui.adapter.OrderPagerAdapter;
import com.example.admin.fdm.ui.adapter.TakeLookPagerAdapter;
import com.example.admin.fdm.ui.fragment.OrderListFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhy.view.flowlayout.TagFlowLayout.dip2px;

public class OrderListActivity extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;

    private List<OrderListFragment> fragments;
    private ArrayList<String> tabItem;

    private OrderListFragment listFragment;
    private ArrayList<String> strings;

    @Override
    public int setLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initEvent() {
        setOrderListView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewpager.setAdapter(new OrderPagerAdapter(fragmentManager, tabItem, fragments));
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_title.setText("租房订单");
        reflex(tablayout);
    }

    @Override
    public void initData() {

    }

    private void setOrderListView(){
        tabItem = new ArrayList<String>();
        tabItem.add("全部");
        tabItem.add("待支付");
        tabItem.add("已完成");
        tabItem.add("已取消");

        fragments = new ArrayList<>();
        for(int i = 0 ;i<tabItem.size();i++){
            Bundle mbundle = new Bundle();
            listFragment = new OrderListFragment();
            mbundle.putInt("state",i);
            listFragment.setArguments(mbundle);
            fragments.add(listFragment);
        }
    }



    @OnClick({R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

        }

    }

    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);

                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);

                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);

                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }

                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);

                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
