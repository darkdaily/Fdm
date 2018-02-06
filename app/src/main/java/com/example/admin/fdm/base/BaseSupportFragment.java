package com.example.admin.fdm.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.dialog.LoadingDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by fushuang on 2017/10/10.
 */

public abstract class BaseSupportFragment  extends Fragment {
    protected View mRootView;
    protected Activity mActivity;
    private Unbinder mUnbinder;
    private EmptyView emptyView;
    private LinearLayout linearLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(setLayout(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        mActivity = getActivity();
        return mRootView;
    }

    public View getRootView() {
        return mRootView;
    }

    protected abstract int setLayout();

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden)
            onPause();
        else
            onResume();
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
    }

    protected abstract void init();

    @Subscribe
    public void onReceiveEvent(EventCenter event) {
        onEventBusResult(event);
    }

    /**
     * EventBus回传消息重写方法
     *
     * @param event
     */
    public void onEventBusResult(EventCenter event) {

    }

    @Override
    public void onDestroy() {
        mActivity = null;
        mUnbinder.unbind();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


    protected void showLoadingDialog() {
        LoadingDialog.getInstance().show(mActivity, "加载中...", true);
    }

    protected void dismissDialog() {
        LoadingDialog.getInstance().dismissDialog();
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) mActivity.getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(view, 0);
    }

    public void setDisplayView(EmptyView emptyView,LinearLayout linearLayout){
        setEmptyView(emptyView);
        setLinearLayout(linearLayout);
    }

    public EmptyView getEmptyView() {
        return emptyView;
    }

    public void setEmptyView(EmptyView emptyView) {
        this.emptyView = emptyView;
    }

    public LinearLayout getLinearLayout() {
        return linearLayout;
    }

    public void setLinearLayout(LinearLayout linearLayout) {
        this.linearLayout = linearLayout;
    }

    public  void setNoDataView(String msg, int imgResource){
        getEmptyView().setNodata(msg,imgResource);
        getLinearLayout().setVisibility(View.GONE);
    }

    public void  setNetworkError(){
        getEmptyView().setNetworkError();
        getLinearLayout().setVisibility(View.GONE);
    }

    public void setHideLayout(){
        getEmptyView().setHideLayout();
        getLinearLayout().setVisibility(View.VISIBLE);
    }

    public  void setNetworkLoading(){
        getEmptyView().setNetworkLoading();
        getLinearLayout().setVisibility(View.GONE);
    }
}
