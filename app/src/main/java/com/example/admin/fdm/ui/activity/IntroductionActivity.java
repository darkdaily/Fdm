package com.example.admin.fdm.ui.activity;

import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/26.
 */

public class IntroductionActivity extends BaseActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.webview)
    WebView webView;

    @BindString(R.string.company_introduction)
    String title;



    @Override
    public int setLayout() {
        return R.layout.activity_introduction;
    }

    @Override
    public void initEvent() {
        tv_title.setText(title);

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
