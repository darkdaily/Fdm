package com.example.admin.fdm;

import android.*;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.MainResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.activity.AboutActivity;
import com.example.admin.fdm.ui.activity.ChatRecordActivity;
import com.example.admin.fdm.ui.activity.ClaimHouseActivity;
import com.example.admin.fdm.ui.activity.CrabListActivity;
import com.example.admin.fdm.ui.activity.CustomListActivity;
import com.example.admin.fdm.ui.activity.EvaluationRecordActivity;
import com.example.admin.fdm.ui.activity.HouseManagementActivity;
import com.example.admin.fdm.ui.activity.LoginActivity;
import com.example.admin.fdm.ui.activity.OrderListActivity;
import com.example.admin.fdm.ui.activity.PersonalActivity;
import com.example.admin.fdm.ui.activity.ReceiveCouponsActivity;
import com.example.admin.fdm.ui.activity.SecondActivity;
import com.example.admin.fdm.ui.activity.SetActivity;
import com.example.admin.fdm.ui.activity.SystemMessageActivity;
import com.example.admin.fdm.ui.activity.TakeLookListActivity;
import com.example.admin.fdm.ui.adapter.MainListAdapter;
import com.example.admin.fdm.ui.view.RoundImageView;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMChatManager;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.util.NetUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Hashtable;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.img_user_icon)
    RoundImageView img_user_icon;
    @BindView(R.id.tv_agent_name)
    TextView tv_agent_name;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_deal_count)
    TextView tv_deal_count;
    @BindView(R.id.tv_deal_money)
    TextView tv_deal_money;
    @BindView(R.id.tv_pingfen)
    TextView tv_pingfen;
    @BindView(R.id.unread_msg_number)
    TextView unread_msg_number;
    @BindView(R.id.ll_order_number)
    LinearLayout ll_order_number;
    @BindView(R.id.tv_order_num)
    TextView tv_order_num;

    private int member_id;
    private MainResponse.DataBean data;
    private String hx_username;
    private String hx_password;
    private int unreadJMessage = 0;


    @Override
    protected void onResume() {
        super.onResume();
        member_id = (Integer) SPUtil.get(this, "member_id", -1);
        if (member_id == -1) {
            startActivity(LoginActivity.class);
            finish();
        }
        unreadJMessage = 0;
        MainData();
    }

    private void loginHX(final String hx_username, String hx_password){
        EMClient.getInstance().login(hx_username, hx_password, new EMCallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onProgress(int i, String s) {

            }
        });

    }

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initEvent() {
        member_id = (Integer) SPUtil.get(this, "member_id", -1);
        hx_username = (String) SPUtil.get(this, "hx_username", "");
        hx_password = (String) SPUtil.get(this, "hx_password", "");
        loginHX(hx_username,hx_password);
        RxPermissions rxPermissions = new RxPermissions(this);
        String[] mPermissionList = new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.CALL_PHONE,
                android.Manifest.permission.READ_LOGS,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.SET_DEBUG_APP,
                android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                android.Manifest.permission.GET_ACCOUNTS,
                android.Manifest.permission.WRITE_APN_SETTINGS,
                android.Manifest.permission.RECORD_AUDIO};

        rxPermissions.request(mPermissionList).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {

            }
        });



        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

//实现ConnectionListener接口
        showProgressDialog();
        MainData();
    }

    private void getUnreadJMessage(){

    }

    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == 206) {
                        // 显示帐号在其他设备登录
                        SPUtil.clear(MainActivity.this);
                        member_id = (Integer) SPUtil.get(MainActivity.this, "member_id", -1);
                        if (member_id == -1) {
                            startActivity(LoginActivity.class);
                            finish();
                        }
                    } else {
                        if (NetUtils.hasNetwork(MainActivity.this)) ;
                            //连接不到聊天服务器
                        else {
                            //当前网络不可用，请检查网络设置
                            Toast.makeText(MainActivity.this,"当前网络不可用",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    @Override
    public void initData() {

    }

    private void MainData() {
        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
        synchronized (conversations) {
            for (EMConversation conversation : conversations.values()) {
                if (conversation.getUnreadMsgCount() != 0) {
                    Log.d("111111111111","conversation.getLastMessage().getUserName()"+conversation.getLastMessage().getUserName());
                    unreadJMessage += conversation.getUnreadMsgCount();
                }
            }
        }
        if(unreadJMessage > 0){
            unread_msg_number.setVisibility(View.VISIBLE);
            unread_msg_number.setText(unreadJMessage+"");
        }else{
            unread_msg_number.setVisibility(View.GONE);
        }

        RetrofitHelper.getApiWithUid().getMainData(member_id)
                .compose(RxScheduler.<MainResponse>defaultScheduler())
                .subscribe(new BaseObserver<MainResponse>(MainActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }

                    @Override
                    public void NoData() {
                        dismissProgressDialog();
                    }

                    @Override
                    public void NoNetwork() {
                        dismissProgressDialog();
                    }

                    @Override
                    public void next(MainResponse mainResponse) {
                        if (mainResponse.getData() != null) {
                            data = mainResponse.getData();
                            Glide.with(mActivity).load(data.getAvatar()).into(img_user_icon);
                            tv_agent_name.setText(data.getUsername());
                            tv_company.setText(data.getCompany_name());
                            tv_deal_count.setText(data.getCount());
                            tv_deal_money.setText(data.getSum());
                            tv_pingfen.setText(data.getScore());
                            if(data.getGrab_num() > 0){
                                ll_order_number.setVisibility(View.VISIBLE);
                                tv_order_num.setText(data.getGrab_num()+"");
                            }else{
                                ll_order_number.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void complete() {
                        dismissProgressDialog();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            MainData();
        }
    }

    @OnClick({R.id.ll_claim_house, R.id.iv_agent, R.id.ll_order_list, R.id.ll_take_look, R.id.ll_crab_order, R.id.ll_custom_list,
            R.id.ll_about, R.id.iv_msg, R.id.ll_house_management, R.id.ll_evaluation, R.id.ll_yaoqing, R.id.ll_coupons, R.id.iv_set
            , R.id.ll_chat_record})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_chat_record:
                unreadJMessage = 0;
                startActivity(ChatRecordActivity.class);
                break;
            case R.id.iv_set:
                startActivity(SetActivity.class);
                break;
            case R.id.ll_crab_order:
                startActivity(CrabListActivity.class);
                break;
            case R.id.ll_claim_house:
                startActivity(ClaimHouseActivity.class);
                break;
            case R.id.ll_house_management:
                startActivity(HouseManagementActivity.class);
                break;
            case R.id.iv_agent:
                Intent mIntent = new Intent();
                mIntent.setClass(MainActivity.this,
                        PersonalActivity.class);
                int requestCode = 0;
                mIntent.putExtra("key", "");
                startActivityForResult(mIntent, requestCode);
                break;
            case R.id.ll_order_list:
                startActivity(OrderListActivity.class);
                break;
            case R.id.ll_take_look:
                startActivity(TakeLookListActivity.class);
                break;
            case R.id.ll_custom_list:
                startActivity(CustomListActivity.class);
                break;
            case R.id.ll_about:
                startActivity(AboutActivity.class);
                break;
            case R.id.iv_msg:
                startActivity(SystemMessageActivity.class);
                break;
            case R.id.ll_evaluation:
                startActivity(EvaluationRecordActivity.class);
                break;
            case R.id.ll_yaoqing:

//                startActivity(LoginActivity.class);
                break;
            case R.id.ll_coupons:
//                startActivity(ReceiveCouponsActivity.class);
                break;
        }
    }
}
