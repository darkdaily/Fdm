package com.example.admin.fdm;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.example.admin.fdm.ui.activity.LoginActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by fushuang on 2017/11/28.
 */

public class FdmApplication extends Application {
    //    static{
//        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
//            @Override
//            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
//                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
//                return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
//            }
//        });
//
//    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
//        SDKInitializer.initialize(getApplicationContext());
        EMOptions options = new EMOptions();
        options.setAcceptInvitationAlways(true);
//        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(this, options);

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);



        PlatformConfig.setWeixin("wxfe5c2b2fb06e465d", "2a10f6eaf3aff3aac7beeeb06f145ef1");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad", "http://sns.whalecloud.com");
        PlatformConfig.setAlipay("2018020102126283");

//        UMShareAPI.get(this);
        SDKInitializer.initialize(getApplicationContext());
    }
}
