package com.example.admin.fdm.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.fdm.Constant;
import com.example.admin.fdm.R;
import com.example.admin.fdm.mvp.module.PostResponse;
import com.example.admin.fdm.retrofit.RetrofitHelper;
import com.example.admin.fdm.retrofit.rx.RxScheduler;
import com.example.admin.fdm.ui.fragment.ChatFragment;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.easeui.EaseConstant;

public class ChatActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle extras = getIntent().getExtras();
        extras.putString(EaseConstant.MY_HEAD, (String) SPUtil.get(ChatActivity.this,"avatar",""));
        extras.putString(EaseConstant.MY_NAME,(String) SPUtil.get(ChatActivity.this,"my_name",""));
        extras.putString(EaseConstant.My_ID,(int) SPUtil.get(ChatActivity.this,"member_id",-1)+"");

        addFriends();
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(extras);
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,chatFragment).commit();
    }


    private void addFriends(){
        RetrofitHelper.getmRetrofitAPIWithUidWu().addFriend((Integer) SPUtil.get(ChatActivity.this,"member_id",-1),
                getIntent().getExtras().getString(EaseConstant.USER_ID));
    }
}
