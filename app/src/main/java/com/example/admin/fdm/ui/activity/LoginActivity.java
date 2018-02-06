package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.fdm.MainActivity;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.MemberResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.string.StringUtils;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import java.util.Set;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by test on 2017/12/28.
 */

public class LoginActivity extends BaseActivity {

    @BindView(R.id.phone_edit)
    EditText phone_edit;
    @BindView(R.id.password_edit)
    EditText password_edit;
    @BindView(R.id.delete_phone)
    ImageView delete_phone;
    @BindView(R.id.delete_password)
    ImageView delete_password;
    @BindView(R.id.error_hint)
    TextView error_hint;
    @BindView(R.id.forget_password_btn)
    TextView forget_password_btn;
    @BindView(R.id.login_layout)
    TextView login_layout;
    @BindView(R.id.agreement_btn)
    TextView agreement_btn;

    private MemberResponse.DataBean data;
    private int member_id;


    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initEvent() {

        member_id = (Integer) SPUtil.get(mActivity, "member_id", -1);
        setView();
    }


    @Override
    public void initData() {

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

    private void setView(){
        delete_phone.setVisibility(View.GONE);
        delete_password.setVisibility(View.GONE);

        error_hint.setText("");
        phone_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(phone_edit.getText().toString().length() != 0){
                    delete_phone.setVisibility(View.VISIBLE);
                    if(password_edit.getText().toString().length() != 0){
                        login_layout.setBackgroundResource(R.drawable.green_solid_bg);
                        login_layout.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    login_layout.setBackgroundResource(R.drawable.gray_border_bg);
                    login_layout.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(password_edit.getText().toString().length() != 0){
                    delete_password.setVisibility(View.VISIBLE);
                    if(phone_edit.getText().toString().length() != 0){
                        login_layout.setBackgroundResource(R.drawable.green_solid_bg);
                        login_layout.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    login_layout.setBackgroundResource(R.drawable.gray_border_bg);
                    login_layout.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    @OnClick({R.id.delete_phone, R.id.delete_password, R.id.forget_password_btn,R.id.login_layout,R.id.agreement_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.delete_phone:
                phone_edit.setText("");
                delete_phone.setVisibility(View.GONE);
                break;
            case R.id.delete_password:
                password_edit.setText("");
                delete_password.setVisibility(View.GONE);
                break;
            case R.id.forget_password_btn:

                startActivity(ForgetPasswordAcitivity.class);
                break;
            case R.id.login_layout:
                error_hint.setText("");
                if(phone_edit.getText().toString().length() > 0 && password_edit.getText().toString().length() > 0){
                    if (StringUtils.isMobile(phone_edit.getText().toString())) {
                        if(StringUtils.isPassowrd(password_edit.getText().toString())){
                            showProgressDialog();
                            exLogin(phone_edit.getText().toString(),password_edit.getText().toString());
                        }else{
                            error_hint.setText("密码长度不正确");
                        }
                    }else{
                        error_hint.setText("手机号格式错误");
                    }
                }else{
                    error_hint.setText("账号密码有误");
                }
                break;
            case R.id.agreement_btn:
                break;
        }
    }

    private void  exLogin(final String phone,  final String password){
        RetrofitHelper.getApiWithUid().exLogin(phone,password)
                .compose(RxScheduler.<MemberResponse>defaultScheduler())
                .subscribe(new BaseObserver<MemberResponse>(LoginActivity.this) {
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
                        if(memberResponse.getData() != null){
                            data = memberResponse.getData();
                            if(member_id == -1 || member_id != data.getId()){
                                SPUtil.put(LoginActivity.this,"member_id",data.getId());
                                SPUtil.put(LoginActivity.this,"phone_number",phone);
                                SPUtil.put(LoginActivity.this,"password",password);
                                SPUtil.put(LoginActivity.this,"hx_username",data.getHx_username());
                                SPUtil.put(LoginActivity.this,"hx_password",data.getHx_password());
                                SPUtil.put(LoginActivity.this,"avatar",data.getAvatar());
                                SPUtil.put(LoginActivity.this,"my_name",data.getUsername());
                                SPUtil.put(LoginActivity.this,"alias","a"+data.getId());

                                JPushInterface.setAlias(LoginActivity.this,1,"a"+data.getId());
                                loginHX(data.getHx_username(),data.getHx_password());
                                if(data.getDistrict() != null){
                                    startActivity(MainActivity.class);
                                    finish();
                                }else{
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(LoginActivity.this,
                                            PersonalActivity.class);
                                    mIntent.putExtra("key","登录");
                                    startActivity(mIntent);
                                    finish();
                                }
                            }else if(member_id == data.getId()){
                                if(data.getDistrict() != null){
                                    startActivity(MainActivity.class);
                                    finish();
                                }else{
                                    Intent mIntent = new Intent();
                                    mIntent.setClass(LoginActivity.this,
                                            PersonalActivity.class);
                                    mIntent.putExtra("key","登录");
                                    startActivity(mIntent);
                                    finish();
                                }
                            }
                        }
                    }
                    @Override
                    public void complete() {
                        dismissProgressDialog();
                    }
                });
    }
}
