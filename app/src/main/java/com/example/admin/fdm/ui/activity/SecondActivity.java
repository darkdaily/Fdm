package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.widget.EditText;


import com.example.admin.fdm.R;
import com.example.admin.fdm.base.BaseActivity;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;

import butterknife.BindView;
import butterknife.OnClick;

public class SecondActivity extends BaseActivity {

    @BindView(R.id.et_chatWith)
    EditText et_chatwith;

    @Override
    public int setLayout() {
        return R.layout.activity_second;
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void initData() {

    }

    @OnClick(R.id.chat)
    public void chat(){
//        EaseChatFragment easeChatFragment = new EaseChatFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(EaseConstant.EXTRA_USER_ID,et_chatwith.getText().toString());
//        bundle.putInt(EaseConstant.EXTRA_CHAT_TYPE, 1);
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_USER_ID,et_chatwith.getText().toString());
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, EMMessage.ChatType.Chat);
        startActivity(intent);
//        easeChatFragment.setArguments(bundle);
//        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,easeChatFragment).show(easeChatFragment).commit();
    }
}
