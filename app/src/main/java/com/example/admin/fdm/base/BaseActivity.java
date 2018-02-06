package com.example.admin.fdm.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.ui.view.EmptyView;
import com.example.admin.fdm.utils.AppManager;
import com.example.admin.fdm.utils.dialog.LoadingDialog;
import com.example.admin.fdm.utils.system.SystemBarTintManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends AppCompatActivity {
    public Unbinder mBind;
    public Toolbar mToolbar;
    public View mRootView;
    public Activity mActivity;
    private boolean haveShowNetView = false;

    private EmptyView emptyView;
    private LinearLayout linearLayout;

    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        //设置Window为全透明
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        mActivity=this;
        //注册EventBus
        EventBus.getDefault().register(this);
        //取消ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置界面随键盘弹出自动上移
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        mRootView = LayoutInflater.from(this).inflate(setLayout(), null);
        //设置布局
        setContentView(mRootView);
        //绑定ButterKnife
        mBind = ButterKnife.bind(this);
        //添加Activity到管理堆栈
        AppManager.getInstance().addActivity(this);
        //初始化数据
        initData();
        //设置UI
        initEvent();
        getSupportFragmentManager();
    }

    public Activity getmActivity() {
        return mActivity;
    }

    public void setmActivity(Activity mActivity) {
        this.mActivity = mActivity;
    }

    /**
     * 设置布局
     *
     * @return 布局资源id
     */
    public abstract
    @LayoutRes
    int setLayout();

    /**
     * 设置监听
     */
    public abstract void initEvent();

    /**
     * 初始化数据
     */
    public abstract void initData();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();//解除ButterKnife绑定
        EventBus.getDefault().unregister(this);//解除EventBus注册
        //从管理堆栈移除Activity
        AppManager.getInstance().finishActivity(this);
    }

    /**
     * 隐藏输入法
     *
     * @param view
     */
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 显示输入法
     *
     * @param view
     */
    public void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.showSoftInput(view, 0);
    }

    /**
     * 获得StatusBar的高度
     *
     * @return StatusBar的高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置背景透明,设置透明后，系统默认不会让出状态栏的位置，布局延伸到状态栏位置
     *
     * @param on 是否透明
     */
    public void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        //透明状态栏
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public void setLightStatusBarMode(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    public void startActivity(Class clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivity(Class clz) {
        Intent intent = new Intent(this, clz);
        startActivity(intent);
    }



    public void startActivityForResult(Class clz, int requestCode) {
        Intent intent = new Intent(this, clz);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(Class clz, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, clz);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

//    public void initToolbar() {
//        mToolbar = (Toolbar) findViewById(R.id.toolbar);
//        if (mToolbar != null) {
//            setSupportActionBar(mToolbar);
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            }
//        }
//    }

    protected void setToolbarIndicator(int resId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(resId);
        }
    }

    protected void setToolbarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    protected void setToolbarTitle(int strId) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(strId);
        }
    }



    protected ActionBar getToolbar() {
        return getSupportActionBar();
    }

    protected View getDecorView() {
        return getWindow().getDecorView();
    }

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


    /**
     * 显示进度框
     */
    public void showProgressDialog() {
        LoadingDialog.getInstance().show(this, "加载中...", false);
    }

    /**
     * 隐藏进度框
     */
    public void dismissProgressDialog() {
        LoadingDialog.getInstance().dismissDialog();
    }

    @Override
    public void onBackPressed() {
        dismissProgressDialog();
        finish();
    }

    /**
     * 设置状态栏颜色
     *
     * @param color 颜色
     */
    public void setStatusBarColor(int color) {
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintColor(color);//状态栏所需颜色
    }

    /**
     * 设置全屏，并且不会Activity的布局让出状态栏的空间
     */
    public void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 设置全屏，并且不会Activity的布局让出状态栏的空间
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * Toast
     *
     * @param message 消息
     */
    public void showToast(String message) {
        Toast.makeText(this, "" + message, Toast.LENGTH_SHORT).show();
    }


    //MIUI判断
    public static boolean isMIUI() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    //Flyme判断
    public static boolean isFlyme() {
        try {
            final Method method = Build.class.getMethod("hasSmartBar");

            return method != null;
        } catch (final Exception e) {
            return false;
        }
    }


    public static class BuildProperties {

        private final Properties properties;

        private BuildProperties() throws IOException {
            properties = new Properties();
            properties.load(new FileInputStream(new File(Environment.getRootDirectory(), "build.prop")));
        }

        public boolean containsKey(final Object key) {
            return properties.containsKey(key);
        }

        public boolean containsValue(final Object value) {
            return properties.containsValue(value);
        }

        public Set<Map.Entry<Object, Object>> entrySet() {
            return properties.entrySet();
        }

        public String getProperty(final String name) {
            return properties.getProperty(name);
        }

        public String getProperty(final String name, final String defaultValue) {
            return properties.getProperty(name, defaultValue);
        }

        public boolean isEmpty() {
            return properties.isEmpty();
        }

        public Enumeration<Object> keys() {
            return properties.keys();
        }

        public Set<Object> keySet() {
            return properties.keySet();
        }

        public int size() {
            return properties.size();
        }

        public Collection<Object> values() {
            return properties.values();
        }

        public static BuildProperties newInstance() throws IOException {
            return new BuildProperties();
        }
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

    public void setSearchView(){
        getEmptyView().setHideLayout();
        getLinearLayout().setVisibility(View.GONE);

    }
    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public static boolean isConnected(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        if (null != connectivity) {

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null != info && info.isConnected()) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }
}
