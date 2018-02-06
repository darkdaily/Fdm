package com.example.admin.fdm.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by test on 2018/1/10.
 */

public abstract class BaseFragment extends Fragment {
    protected boolean isInit = false;//视图是否已经初初始化
    protected boolean isLoad;//是否加载
    protected final String TAG = "BaseFragment";
    private View view;//视图
    private Unbinder mUnbinder;

    protected BaseFragment() {
        isLoad = false;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, view);

        isInit = true;
        initData();
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    /**
     * 视图是否可见
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint()) {
            startLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;
    }

    @Override
    public void onDestroy() {
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    protected View getContentView() {
        return view;
    }

    /**
     * findViewById
     */
    protected <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    protected abstract int setContentView();//显示的布局

    protected abstract void startLoad();//加载数据

    protected abstract void initData();//初始化数据

    protected abstract void init();//初始化布局
    /**
     * 当视图不可见并且加载过数据,调用此方法
     */
    protected void stopLoad() {}


}
