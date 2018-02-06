package com.example.admin.fdm.ui.activity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.CountDownTimer;
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

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.utils.CodeUtils;
import com.example.admin.fdm.utils.NumCode;
import com.example.admin.fdm.utils.string.StringUtils;

import org.w3c.dom.Text;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.http.POST;

/**
 * Created by test on 2017/12/28.
 */

public class ForgetPasswordAcitivity extends BaseActivity {

    //界面一
    @BindView(R.id.ll_changepassword_first)
    LinearLayout ll_changepassword_first;
    @BindView(R.id.forget_password_back)
    LinearLayout forget_password_back;
    @BindView(R.id.fp_phone_edit)
    EditText fp_phone_edit;
    @BindView(R.id.fp_delete_phone)
    ImageView fp_delete_phone;
    @BindView(R.id.fg_captcha_edit)
    EditText fg_captcha_edit;
    @BindView(R.id.fg_captcha_iv)
    ImageView fg_captcha_iv;
    @BindView(R.id.refresh_captcha)
    ImageView refresh_captcha;
    @BindView(R.id.fp_sms_edit)
    EditText fp_sms_edit;
    @BindView(R.id.fp_get_sms)
    TextView fp_get_sms;
    @BindView(R.id.next_step)
    TextView next_step;

    //界面二
    @BindView(R.id.ll_changepassword_second)
    LinearLayout ll_changepassword_second;

    @BindView(R.id.ed_first_password)
    EditText ed_first_password;
    @BindView(R.id.ed_second_password)
    EditText ed_second_password;
    @BindView(R.id.iv_first_delete)
    ImageView iv_first_delete;
    @BindView(R.id.iv_second_delete)
    ImageView iv_second_delete;
    @BindView(R.id.change_button)
    TextView change_button;


    @BindString(R.string.get_sms)
    String  str_sms;

    private NumCode NumCode;
    private String numCode;

    private String ed_code;
    private String ed_phone;
    private String ed_sms;

    private String  password_one;
    private String  password_two;

    @Override
    public int setLayout() {
        return R.layout.activity_forgetpassword;
    }

    @Override
    public void initEvent() {
        ll_changepassword_first.setVisibility(View.VISIBLE);
        ll_changepassword_second.setVisibility(View.GONE);
        setFiratView();
        setSecondView();
    }





    @Override
    public void initData() {

    }


    private void setFiratView(){
        fg_captcha_iv.setImageBitmap(NumCode.getInstance().getBitmap());
        numCode = NumCode.getInstance().getCode();
        fp_delete_phone.setVisibility(View.GONE);
        fp_phone_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        fp_phone_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fp_phone_edit.getText().length() != 0){
                    fp_delete_phone.setVisibility(View.VISIBLE);
                    if(fg_captcha_edit.getText().length() != 0 && fp_sms_edit.getText().length() != 0){
                        next_step.setBackgroundResource(R.drawable.green_solid_bg);
                        next_step.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    next_step.setBackgroundResource(R.drawable.gray_border_bg);
                    next_step.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fg_captcha_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        fg_captcha_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fg_captcha_edit.getText().toString().length() != 0){
                    if(fp_phone_edit.getText().toString().length() != 0 && fp_sms_edit.getText().toString().length() != 0){
                        next_step.setBackgroundResource(R.drawable.green_solid_bg);
                        next_step.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    next_step.setBackgroundResource(R.drawable.gray_border_bg);
                    next_step.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fp_sms_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        fp_sms_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(fp_sms_edit.getText().length() != 0){
                    if(fp_phone_edit.getText().length() != 0 && fg_captcha_edit.getText().length() != 0){
                        next_step.setBackgroundResource(R.drawable.green_solid_bg);
                        next_step.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    next_step.setBackgroundResource(R.drawable.gray_border_bg);
                    next_step.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        fp_get_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verification();
            }
        });

    }

    private void setSecondView(){
        iv_first_delete.setVisibility(View.GONE);
        iv_second_delete.setVisibility(View.GONE);
        ed_first_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        ed_first_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ed_first_password.getText().length() != 0){
                    iv_first_delete.setVisibility(View.VISIBLE);
                    if(ed_first_password.getText().length() != 0 && ed_second_password.getText().length() != 0){
                        change_button.setBackgroundResource(R.drawable.green_solid_bg);
                        change_button.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    change_button.setBackgroundResource(R.drawable.gray_border_bg);
                    change_button.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ed_second_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //完成自己的事件
                }
                return false;
            }
        });
        ed_second_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(ed_second_password.getText().length() != 0){
                    iv_second_delete.setVisibility(View.VISIBLE);
                    if(ed_first_password.getText().length() != 0 && ed_second_password.getText().length() != 0){
                        change_button.setBackgroundResource(R.drawable.green_solid_bg);
                        change_button.setTextColor(getResources().getColor(R.color.white));
                    }
                }else{
                    change_button.setBackgroundResource(R.drawable.gray_border_bg);
                    change_button.setTextColor(getResources().getColor(R.color.gray999));
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @OnClick({R.id.forget_password_back, R.id.fp_delete_phone, R.id.refresh_captcha,R.id.next_step,
                R.id.iv_first_delete,R.id.iv_second_delete,R.id.change_button})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.forget_password_back:
                finish();
                break;
            case R.id.fp_delete_phone:
                fp_phone_edit.setText("");
                fp_delete_phone.setVisibility(View.GONE);
                break;
            case R.id.refresh_captcha:
                fg_captcha_iv.setImageBitmap(NumCode.getInstance().getBitmap());
                numCode = NumCode.getInstance().getCode();
                break;
            case R.id.next_step:
                if(!fp_phone_edit.getText().toString().isEmpty() && !fg_captcha_edit.getText().toString().isEmpty() && !fp_sms_edit.getText().toString().isEmpty()){
                    if (StringUtils.isMobile(fp_phone_edit.getText().toString())) {
                        if(numCode.equals(fg_captcha_edit.getText().toString())){
                            Log.d("11111111111","ed_code"+ed_code+"ed_code2"+fp_sms_edit.getText().toString());

                            if(ed_code.equals(fp_sms_edit.getText().toString())){
                                Log.d("11111111111","ed_code"+ed_code+"ed_code2"+fp_sms_edit.getText().toString());
                                ll_changepassword_first.setVisibility(View.GONE);
                                ll_changepassword_second.setVisibility(View.VISIBLE);
                            }else{
                                Toast.makeText(ForgetPasswordAcitivity.this,"短信验证有误",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(ForgetPasswordAcitivity.this,"图片验证有误",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(ForgetPasswordAcitivity.this,"手机号格式错误",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.iv_first_delete:
                iv_first_delete.setVisibility(View.GONE);
                ed_first_password.setText("");
                break;
            case R.id.iv_second_delete:
                iv_second_delete.setVisibility(View.GONE);
                ed_second_password.setText("");
                break;
            case R.id.change_button:
                if(ed_first_password.getText().toString().length() >0 && ed_second_password.getText().toString().length() >0){
                        if(ed_first_password.getText().toString().equals(ed_second_password.getText().toString())){
                            showProgressDialog();
                            exChangePassWord();
                        }else{
                            Toast.makeText(ForgetPasswordAcitivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
                        }
                    }
                break;

        }
    }

    private void verification(){
        if(fp_phone_edit.getText().toString().length() > 0 ){
            if (StringUtils.isMobile(fp_phone_edit.getText().toString())) {
                if(fg_captcha_edit.getText().toString().length() >0){
                    if(numCode.equals(fg_captcha_edit.getText().toString())){
                        timer.start();
                        getSMS();
                    }else{
                        Toast.makeText(ForgetPasswordAcitivity.this,"图片验证有误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(ForgetPasswordAcitivity.this,"请输入图片验证",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(ForgetPasswordAcitivity.this,"手机号格式错误",Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ForgetPasswordAcitivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
        }
    }

    private CountDownTimer timer = new CountDownTimer(60000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            fp_get_sms.setText((millisUntilFinished / 1000) + "秒后可重发");
            fp_get_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }

        @Override
        public void onFinish() {
            fp_get_sms.setEnabled(true);
            fp_get_sms.setText(str_sms);
            fp_get_sms.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verification();
                }
            });
        }
    };

    private void getSMS(){
        RetrofitHelper.getApiWithUid().getSms(fp_phone_edit.getText().toString())
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(ForgetPasswordAcitivity.this) {
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
                    public void next(PostResponse postResponse) {
                        if(postResponse.getCode() == 0){
                           ed_code = String.valueOf(postResponse.getData().getVerificationCode());
                        }
                    }

                    @Override
                    public void complete() {
                    }
                });
    }

    private void exChangePassWord(){
        RetrofitHelper.getApiWithUid().exChangePassWord(fp_phone_edit.getText().toString(),ed_first_password.getText().toString(),fp_sms_edit.getText().toString())
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(ForgetPasswordAcitivity.this) {
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
                    public void next(PostResponse postResponse) {
                        if(postResponse.getCode() == 0){
                            Toast.makeText(ForgetPasswordAcitivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void complete() {
                        timer.cancel();
                        dismissProgressDialog();

                    }
                });
    }


}
