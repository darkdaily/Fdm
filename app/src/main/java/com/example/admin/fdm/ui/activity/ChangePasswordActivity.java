package com.example.admin.fdm.ui.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by test on 2017/12/28.
 */

public class ChangePasswordActivity extends BaseActivity {

    @BindView(R.id.change_password_back)
    LinearLayout change_password_back;
    @BindView(R.id.first_password)
    EditText first_password;
    @BindView(R.id.cp_delete_first)
    ImageView cp_delete_first;
    @BindView(R.id.second_password)
    EditText second_password;
    @BindView(R.id.cp_delete_second)
    ImageView cp_delete_second;
    @BindView(R.id.cp_error_hint)
    TextView cp_error_hint;
    @BindView(R.id.confirm_change)
    LinearLayout confirm_change;



    @Override
    public int setLayout() {
        return R.layout.activity_changepassword;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick({R.id.change_password_back, R.id.cp_delete_first, R.id.cp_delete_second, R.id.confirm_change})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.change_password_back:
                finish();
                break;
            case R.id.cp_delete_first:
                break;
            case R.id.cp_delete_second:
                break;
            case R.id.confirm_change:
                startActivity(IntroductionActivity.class);
                break;
        }

    }


}
