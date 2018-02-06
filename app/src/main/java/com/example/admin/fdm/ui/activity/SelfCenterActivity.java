package com.example.admin.fdm.ui.activity;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;

import butterknife.OnClick;

public class SelfCenterActivity extends BaseActivity {

    @Override
    public int setLayout() {
        return R.layout.activity_self_center;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.tv_choose_trade_area})
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.tv_choose_trade_area:
                View content = LayoutInflater.from(this).inflate(R.layout.pick_trade_area_panel, null, false);
                PopupWindow popupWindow = new PopupWindow(content, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,true);
                popupWindow.setContentView(content);
                popupWindow.showAtLocation(SelfCenterActivity.this.findViewById(R.id.ll_container), Gravity.BOTTOM,0,0);
                break;
        }
    }
}
