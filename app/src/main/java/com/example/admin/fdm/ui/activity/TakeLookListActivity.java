package com.example.admin.fdm.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.TakeLookListResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.FinishLoadConsumer;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.adapter.TakeLookPagerAdapter;
import com.example.admin.fdm.ui.fragment.TakeLookListFragment;
import com.example.admin.fdm.utils.event.TakeLookEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zhy.view.flowlayout.TagFlowLayout.dip2px;

public class TakeLookListActivity extends BaseActivity {

    @BindView(R.id.tablayout)
    TabLayout tablayout;

    @BindView(R.id.viewpager)
    ViewPager viewpager;


    private List<TakeLookListFragment> fragments;
    private ArrayList<String> tabItem;

    private TakeLookListFragment listFragment;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public int setLayout() {
        return R.layout.activity_take_look_list;
    }

    @Override
    public void initEvent() {
        setTakeLookView();
        FragmentManager fragmentManager = getSupportFragmentManager();
        viewpager.setAdapter(new TakeLookPagerAdapter(fragmentManager, tabItem, fragments));
        viewpager.setCurrentItem(0);
        tv_title.setText("租房带看");
        reflex(tablayout);
    }

    @Override
    public void initData() {
    }


    public void setTakeLookView(){
        tabItem = new ArrayList<String>();
        tabItem.add("全部");
        tabItem.add("用户待确认");
        tabItem.add("大秘待确认");
        tabItem.add("预约中");
        tabItem.add("已完成");
        tabItem.add("已取消");
        fragments = new ArrayList<TakeLookListFragment>();
        for(int i = 0; i < tabItem.size();i++){
            Bundle mbundle = new Bundle();
            listFragment = new TakeLookListFragment();
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
