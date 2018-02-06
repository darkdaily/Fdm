package com.example.admin.fdm.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.admin.fdm.R;
import com.example.admin.fdm.ui.fragment.ChatFragment;
import com.example.admin.fdm.utils.SPUtil;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseConversationListFragment;


/**
 * Created by test on 2018/1/31.
 */

public class ChatRecordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        EaseConversationListFragment  cf = new EaseConversationListFragment();
        cf.setConversationListItemClickListener(new EaseConversationListFragment.EaseConversationListItemClickListener() {
            @Override
            public void onListItemClicked(EMConversation conversation) {
                Intent intent = new Intent(ChatRecordActivity.this, ChatActivity.class);
                EMMessage Othersmessage = conversation.getLatestMessageFromOthers();
                if(Othersmessage != null){
                    intent.putExtra(EaseConstant.USER_HEAD,Othersmessage.getStringAttribute("headImg",""));
                    intent.putExtra(EaseConstant.USER_ID,Othersmessage.getStringAttribute("userId",""));
                    intent.putExtra(EaseConstant.USER_NAME,Othersmessage.getStringAttribute("userName","未获取"));
                }else{
                    EMMessage message = conversation.getLastMessage();
                    intent.putExtra(EaseConstant.USER_HEAD,message.getStringAttribute("sendHeadImg",""));
                    intent.putExtra(EaseConstant.USER_ID,message.getStringAttribute("sendUserId",""));
                    intent.putExtra(EaseConstant.USER_NAME,message.getStringAttribute("sendUserName",""));
                }
                intent.putExtra(EaseConstant.EXTRA_USER_ID, conversation.conversationId());
                startActivity(intent);
            }
        });
        getSupportFragmentManager().beginTransaction().add(R.id.fl_content,cf).commit();
    }
}
