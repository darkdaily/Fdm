package com.example.admin.fdm.ui.activity;


import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.chat.EMClient;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/23.
 */

public class SetActivity extends BaseActivity {

    @BindView(R.id.check_button)
    CheckBox check_button;
    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public int setLayout() {
        return R.layout.activity_set;
    }

    @Override
    public void initEvent() {
        tv_title.setText("设置");

        check_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_button.isChecked()){

                }else{

                }
            }
        });


    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left,R.id.tv_exit})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_exit:
                SPUtil.clear(SetActivity.this);
                EMClient.getInstance().logout(true);
                finish();
                break;

        }
    }
}
