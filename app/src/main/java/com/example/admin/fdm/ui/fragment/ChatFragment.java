package com.example.admin.fdm.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.admin.fdm.Constant;
import com.example.admin.fdm.ui.activity.HouseDetailActivity;
import com.example.admin.fdm.ui.activity.UserDetailActivity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;

/**
 * Created by fushuang on 2017/11/1.
 */

public class ChatFragment extends EaseChatFragment {

    private String user_id;

    @Override
    protected void setUpView() {
        super.setUpView();

        user_id = getArguments().getString(EaseConstant.USER_ID);

        setChatFragmentHelper(new EaseChatFragmentHelper() {
            @Override
            public void onSetMessageAttributes(EMMessage message) {

            }

            @Override
            public void onEnterToChatDetails() {

            }

            @Override
            public void onAvatarClick(String username) {
                if (!username.equals(EMClient.getInstance().getCurrentUser())){
                    //说明点击的是经纪人的头像
//                    user_id
                    Intent intent = new Intent(getActivity(), UserDetailActivity.class);
                    intent.putExtra("Uid",user_id);
                    startActivity(intent);
                }
            }

            @Override
            public void onAvatarLongClick(String username) {

            }

            @Override
            public boolean onMessageBubbleClick(EMMessage message) {
                if(message.getStringAttribute("dataType","000").equals("102")){
                    Intent intent = new Intent(getActivity(), HouseDetailActivity.class);

                    Bundle mBundle = new Bundle();
                    if(message.getStringAttribute("type","").equals("1")){ //分散式
                        mBundle.putInt("state", Constant.CANCEL_CANCEL_HOUSING_RESOURCES);
                    }else {//集中式
                        mBundle.putInt("state", Constant.CANCEL_ROOM);
                    }
                    mBundle.putString("house_id",message.getStringAttribute("houseId",""));
                    mBundle.putString("room_id", message.getStringAttribute("roomId",""));
                    intent.putExtras(mBundle);
                    startActivity(intent);
                    return true;
                }
                return false;
            }

            @Override
            public void onMessageBubbleLongClick(EMMessage message) {

            }

            @Override
            public boolean onExtendMenuItemClick(int itemId, View view) {
                return false;
            }

            @Override
            public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
                return null;
            }
        });
    }
}
