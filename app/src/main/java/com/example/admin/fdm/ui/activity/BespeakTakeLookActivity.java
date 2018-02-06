package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.CreatTakeLookResponse;
import com.example.admin.fdm.mvp.module.HouseSerializable;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.mvp.module.TakeLookHouseResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.view.CustomDatePicker;
import com.example.admin.fdm.utils.SPUtil;
import com.example.admin.fdm.utils.date.TimeUtil;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/7.
 */

public class BespeakTakeLookActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.take_look_date)
    TextView take_look_date;

    @BindView(R.id.et_contacts_tel)
    TextView et_contacts_tel;
    @BindView(R.id.et_contacts_name)
    EditText et_contacts_name;
    @BindView(R.id.bespeak_btn)
    TextView bespeak_btn;

    @BindView(R.id.rl_house_detail)
    RelativeLayout rl_house_detail;
    @BindView(R.id.rl_iv_house)
    ImageView rl_iv_house;
    @BindView(R.id.rl_tv_house_name)
    TextView rl_tv_house_name;
    @BindView(R.id.rl_tv_house_info)
    TextView rl_tv_house_info;
    @BindView(R.id.rl_tv_house_location)
    TextView rl_tv_house_location;
    @BindView(R.id.rl_tv_house_rent)
    TextView rl_tv_house_rent;


    @BindView(R.id.ll_choice_house)
    LinearLayout ll_choice_house;
    @BindView(R.id.tv_house_name)
    TextView tv_house_name;
    @BindView(R.id.ll_choice_user)
    LinearLayout ll_coice_user;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.ll_label)
    LinearLayout ll_label;
    private int requestCode;

    private int member_id;
    private String uid;
    private String user_name;
    private String user_tel;
    private HouseSerializable house;


    private CreatTakeLookResponse.DataBean data;

    String now;
    private CustomDatePicker customDatePicker;


    @Override
    public int setLayout() {
        return R.layout.activity_bespeaktakelook;
    }

    @Override
    public void initEvent() {

        member_id = (Integer) SPUtil.get(this,"member_id",-1);

        Bundle bundle = getIntent().getExtras();
        uid = bundle.getString("uid");
        user_name = bundle.getString("user_name");
        user_tel = bundle.getString("user_tel");
        tv_user_name.setText(user_name);
        et_contacts_tel.setText(user_tel);
        getData();
        setView();
    }

    private void setView(){
        tv_title.setText("预约带看");
        rl_house_detail.setVisibility(View.GONE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        setDatePicker(now);

        tv_house_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isValidDate(take_look_date.getText().toString()) && et_contacts_name.getText().length() > 0 && tv_house_name.getText().length()>0){
                    bespeak_btn.setTextColor(getResources().getColor(R.color.white));
                    bespeak_btn.setBackgroundResource(R.drawable.green_button_bg);
                }else{
                    bespeak_btn.setTextColor(getResources().getColor(R.color.text_green));
                    bespeak_btn.setBackgroundColor(getResources().getColor(R.color.button_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        et_contacts_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(isValidDate(take_look_date.getText().toString()) && et_contacts_name.getText().length() > 0 && tv_house_name.getText().length()>0){
                    bespeak_btn.setTextColor(getResources().getColor(R.color.white));
                    bespeak_btn.setBackgroundResource(R.drawable.green_button_bg);
                }else{
                    bespeak_btn.setTextColor(getResources().getColor(R.color.text_green));
                    bespeak_btn.setBackgroundColor(getResources().getColor(R.color.button_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        take_look_date.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(isValidDate(take_look_date.getText().toString()) && et_contacts_name.getText().length() > 0 && tv_house_name.getText().length()>0){
                    bespeak_btn.setTextColor(getResources().getColor(R.color.white));
                    bespeak_btn.setBackgroundResource(R.drawable.green_button_bg);
                }else{
                    bespeak_btn.setTextColor(getResources().getColor(R.color.text_green));
                    bespeak_btn.setBackgroundColor(getResources().getColor(R.color.button_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @Override
    public void initData() {

    }

    private void setDatePicker(String now) {
        customDatePicker = new CustomDatePicker(this, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间
                take_look_date.setText(time);
            }
        }, "2010-01-01 00:00", now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动

    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (resultCode) {
            case 0:
                house = (HouseSerializable) data.getSerializableExtra("house");
                ll_choice_house.setVisibility(View.GONE);
                    tv_house_name.setVisibility(View.VISIBLE);
                    rl_house_detail.setVisibility(View.VISIBLE);
                    tv_house_name.setText(house.getHouse_title());
                    rl_tv_house_name.setText(house.getHouse_title());
                    Glide.with(mActivity).load(house.getHouse_photo()).into(rl_iv_house);
                    rl_tv_house_info.setText(house.getHouse_info());
                    rl_tv_house_location.setText(data.getStringExtra("house_address"));
                    showThreeTag(house.getHouse_label(),ll_label);
                    rl_tv_house_rent.setText(house.getHouse_rental());
                break;
            case 1:
                break;
        }
    }

    private void showThreeTag(List<String> list, LinearLayout ll_tag_container) {
        for (int i = 0; i < 3 && list != null && list.size() > i; i++) {
            TextView childAt = ((TextView) ll_tag_container.getChildAt(i));
            childAt.setVisibility(View.VISIBLE);
            childAt.setText(list.get(i));
        }
    }

    private void exTakeLook(){
        String name = et_contacts_name.getText().toString();
        long time = TimeUtil.date2TimeStamp(TimeUtil.dateFormatYMDHM,take_look_date.getText().toString());

        RetrofitHelper.getApiWithUid().exTakeLook(data.getUid(),member_id,house.getRoom_id(),house.getType(),house.getHouse_id(),name,take_look_date.getText().toString())
                .compose(RxScheduler.<PostResponse>defaultScheduler())
                .subscribe(new BaseObserver<PostResponse>(BespeakTakeLookActivity.this) {
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
                            Toast.makeText(BespeakTakeLookActivity.this,"预约成功",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

    private void getData() {
        RetrofitHelper.getApiWithUid().getCreatTakeLook(member_id, uid)
                .compose(RxScheduler.<CreatTakeLookResponse>defaultScheduler())
                .subscribe(new BaseObserver<CreatTakeLookResponse>(BespeakTakeLookActivity.this) {
                    @Override
                    public void error(Throwable e) {

                    }
                    @Override
                    public void NoData() {
                        finish();
                    }

                    @Override
                    public void NoNetwork() {
                        finish();
                    }
                    @Override
                    public void next(CreatTakeLookResponse creatTakeLookResponse) {
                        if(creatTakeLookResponse.getData() == null){
                            finish();
                        }else{
                            data = creatTakeLookResponse.getData();
                            tv_user_name.setText(data.getNick_name());
                            et_contacts_tel.setText(data.getPhone_number());
                        }
                    }

                    @Override
                    public void complete() {

                    }
                });
    }


    @OnClick({R.id.iv_left, R.id.ll_setData, R.id.ll_name, R.id.ll_house,R.id.bespeak_btn})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.ll_house:
                Intent mIntent = new Intent();
                mIntent.setClass(BespeakTakeLookActivity.this,
                        ChoiceHouseActivity.class);
                requestCode = 2;
                startActivityForResult(mIntent, requestCode);
                break;
            case R.id.ll_name:
                break;
            case R.id.ll_setData:
                if (take_look_date.getText().equals("请选择带看时间")) {
                    take_look_date.setText(now);
                }
                customDatePicker.show(take_look_date.getText().toString());
                break;
            case R.id.bespeak_btn:
                if(isValidDate(take_look_date.getText().toString()) && et_contacts_name.getText().length() > 0 && tv_house_name.getText().length()>0){
                   //提交预约
                    exTakeLook();
                }
                break;
        }

    }

    public static boolean isValidDate(String str) {
        boolean convertSuccess=true;
// 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
// 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
            format.setLenient(false);
            format.parse(str);
        } catch (ParseException e) {
            // e.printStackTrace();
// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            convertSuccess = false;
        }
        return convertSuccess;
    }
}
