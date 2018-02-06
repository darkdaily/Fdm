package com.example.admin.fdm.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2018/1/7.
 */

public class NewDetailActivity extends BaseActivity {

    @BindView(R.id.new_title)
    TextView new_title;

    @Override
    public int setLayout() {
        return R.layout.activity_new_detail;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.iv_left})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_left:
                finish();
                break;

        }

    }
}
