package com.example.admin.fdm.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.mvp.module.AboutResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.BaseObserver;
import com.example.admin.fdm.retrofit.rx.RxScheduler;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/25.
 */

public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.Zhage_introduce)
    RelativeLayout introduce;
    @BindView(R.id.Zhage_websit)
    TextView wevist;
    @BindView(R.id.tv_weixin)
    TextView weixin;
    @BindView(R.id.Zhage_phone)
    TextView zhage_phone;
    @BindView(R.id.Hotline_phone)
    TextView hotline_phone;
    @BindString(R.string.about)
    String title;
    private String address;

    private AboutResponse.DataBean data;


    @Override
    public int setLayout() {
        return R.layout.activity_about;
    }

    @Override
    public void initEvent() {
        tv_title.setText(title);
        getAboutData();
    }




    @Override
    public void initData() {

    }

    @OnClick({R.id.Zhage_introduce, R.id.Zhage_websit, R.id.Zhage_phone, R.id.Hotline_phone,R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.Zhage_introduce:
                Bundle bundle = new Bundle();
                bundle.putString("address",address);
                startActivity(IntroductionActivity.class);
                break;
            case R.id.Zhage_websit:
                break;
            case R.id.Zhage_phone:

                break;
            case R.id.Hotline_phone:
                CallTel(hotline_phone.getText().toString());
                break;
        }

    }


    @SuppressLint("MissingPermission")
    private void CallTel(String number){
        Intent tel = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + number));
        startActivity(tel);
    }
    private void getAboutData(){
        RetrofitHelper.getApiWithUid().getAbout()
                .compose(RxScheduler.<AboutResponse>defaultScheduler())
                .subscribe(new BaseObserver<AboutResponse>(AboutActivity.this) {
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
                    public void next(AboutResponse aboutResponse) {
                        data = aboutResponse.getData();
                        address = data.getIntroduction();
                        wevist.setText(data.getOfficial_website());
                        weixin.setText(data.getWeixin());
                        zhage_phone.setText(data.getCustomer_service());
                        hotline_phone.setText(data.getCooperation_hotline());
                    }

                    @Override
                    public void complete() {

                    }
                });
    }

}
