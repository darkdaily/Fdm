package com.example.admin.fdm.ui.activity;

import android.Manifest;
import android.support.annotation.IdRes;
import android.widget.RadioGroup;


import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

public class CMainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

//    @BindView(R.id.main_rg)  RadioGroup radioGroup;
//
//
//    private HomeFragment homeFragment;
//    private MyFragment myFragment;
//    private BaseFragment showFragment;

    @Override
    public int setLayout() {
        return R.layout.activity_cmain;
    }

    @Override
    public void initEvent() {
//        setTranslucentStatus(true);
//        setStatusBarColor(R.color.white);
//        setLightStatusBarMode();
//
//        homeFragment = new HomeFragment();
//        myFragment = new MyFragment();
//
//
//        getFragmentManager().beginTransaction().add(R.id.main_container, homeFragment).commit();
//        getFragmentManager().beginTransaction().add(R.id.main_container, myFragment).commit();
//
//        getFragmentManager().beginTransaction().hide(myFragment).show(homeFragment).commit();
//        showFragment = homeFragment;
//        radioGroup.setOnCheckedChangeListener(this);
//        radioGroup.check(R.id.rb_home);



    }

    @Override
    public void initData() {
        RxPermissions rxPermissions = new RxPermissions(this);
        String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.READ_LOGS,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.SET_DEBUG_APP,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.GET_ACCOUNTS,
                Manifest.permission.WRITE_APN_SETTINGS,
                Manifest.permission.RECORD_AUDIO};

        rxPermissions.request(mPermissionList).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
//                getFragmentManager().beginTransaction().hide(showFragment).show(homeFragment).commit();
//                showFragment=homeFragment;
                break;

            case R.id.rb_2:
//                getFragmentManager().beginTransaction().hide(showFragment).show(myFragment).commit();
//                showFragment=myFragment;
                break;

            case R.id.rb_3:
//                getFragmentManager().beginTransaction().hide(showFragment).show(homeFragment).commit();
                break;

            case R.id.rb_4:
//                getFragmentManager().beginTransaction().hide(showFragment).show(myFragment).commit();
                break;
        }
    }

}
