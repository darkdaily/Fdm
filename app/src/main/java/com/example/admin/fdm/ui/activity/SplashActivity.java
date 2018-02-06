package com.example.admin.fdm.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.admin.fdm.MainActivity;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.MemberResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.utils.AppManager;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by test on 2018/1/9.
 */

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGHT = 2;
    private Handler handler;
    private int member_id;
    private String phone;
    private String password;
    private String alias;
    private String hx_username;
    private String hx_password;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        handler = new Handler();
        member_id = (Integer) SPUtil.get(SplashActivity.this, "member_id", -1);

        alias = (String) SPUtil.get(SplashActivity.this, "alias", "");

        if(!alias.equals("")){
            JPushInterface.setAlias(this,1,alias);
        }

        if (member_id == -1) {
            // 延迟SPLASH_DISPLAY_LENGHT时间然后跳转到MainActivity
            jumpLogin();
        } else {
            phone = (String) SPUtil.get(SplashActivity.this, "phone_number", "");
            password = (String) SPUtil.get(SplashActivity.this, "password", "");
            hx_username = (String) SPUtil.get(SplashActivity.this, "hx_username", "");
            hx_password = (String) SPUtil.get(SplashActivity.this, "hx_password", "");


            if(!phone.equals("") && !(password.equals(""))){
                exLogin(phone,password);
                loginHX(hx_username,hx_password);
            }else{
                jumpLogin();
            }
        }
    }

    private void loginHX(String hx_username,String hx_password){
        EMClient.getInstance().login(hx_username, hx_password, new EMCallBack() {
            @Override
            public void onSuccess() {
//                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onError(int i, String s) {
                Log.d("888888888888888888888", "onError: " + s);
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exLogin(final String phone, final String password) {
        RetrofitHelper.getApiWithUid().exLogin(phone, password)
                .compose(RxScheduler.<MemberResponse>defaultScheduler())
                .subscribe(new BaseObserver<MemberResponse>(SplashActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {

                    }

                    @Override
                    public void NoNetwork() {

                    }

                    @Override
                    public void next(MemberResponse memberResponse) {
                        if (memberResponse.getData() != null) {
                            MemberResponse.DataBean data = memberResponse.getData();
                            if (member_id != memberResponse.getData().getId()) {
                                SPUtil.put(SplashActivity.this,"member_id",data.getId());
                                SPUtil.put(SplashActivity.this,"phone_number",phone);
                                SPUtil.put(SplashActivity.this,"password",password);
                                SPUtil.put(SplashActivity.this, "hx_username", data.getHx_username());
                                SPUtil.put(SplashActivity.this, "hx_password", data.getHx_password());
                                SPUtil.put(SplashActivity.this,"avatar",data.getAvatar());
                                SPUtil.put(SplashActivity.this,"my_name",data.getUsername());
                                SPUtil.put(SplashActivity.this,"alias","a"+data.getId());
                                jumpPersonal();
                            } else if (member_id == data.getId()) {
                                if (data.getDistrict() != null) {
                                    jumpMain();
                                } else {
                                    jumpPersonal();
                                }
                            }
                        }else{
                            jumpLogin();
                        }

                    }

                    @Override
                    public void complete() {

                    }
                });
    }


    private void jumpLogin(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        LoginActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    private void jumpMain(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

    private void jumpPersonal(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this,
                        PersonalActivity.class);
                startActivity(intent);
                intent.putExtra("key", "登录");
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }
}
