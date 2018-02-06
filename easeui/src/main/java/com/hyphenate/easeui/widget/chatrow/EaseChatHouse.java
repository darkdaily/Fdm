package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.Spannable;
import android.util.Log;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.EaseSmileUtils;
import com.hyphenate.exceptions.HyphenateException;

/**
 * Created by test on 2018/1/30.
 */

public class EaseChatHouse extends EaseChatRow {

    ImageView iv_house;
    TextView tv_house_name;
    TextView tv_house_rent;

    public EaseChatHouse(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
        Log.d("环信","EaseChatHouse");

    }

    @Override
    protected void onInflateView() {
        Log.d("环信","onInflateView");

        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ?
                R.layout.ease_house_received_message : R.layout.ease_house_sent_message, this);
    }

    @Override
    protected void onFindViewById() {
        tv_house_name = (TextView) findViewById(R.id.tv_house_name);
        tv_house_rent = (TextView) findViewById(R.id.tv_house_rent);
        iv_house = (ImageView) findViewById(R.id.iv_house);
    }


    @Override
    protected void onUpdateView() {
        Log.d("环信","onUpdateView");
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onSetUpView() {
        Log.d("环信","onSetUpView");

        try {
            tv_house_name.setText(message.getStringAttribute("houseTitle"));
            tv_house_rent.setText(message.getStringAttribute("housePrice"));
            Glide.with(getContext()).load(message.getStringAttribute("houseImg")).into(iv_house);
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
        handleTextMessage();
        onUpdateView();
    }

    protected void handleTextMessage() {
        if (message.direct() == EMMessage.Direct.SEND) {
            setMessageSendCallback();
            switch (message.status()) {
                case CREATE:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.GONE);
                    break;
                case FAIL:
                    progressBar.setVisibility(View.GONE);
                    statusView.setVisibility(View.VISIBLE);
                    break;
                case INPROGRESS:
                    progressBar.setVisibility(View.VISIBLE);
                    statusView.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }else{
            if(!message.isAcked() && message.getChatType() == EMMessage.ChatType.Chat){
                try {
                    EMClient.getInstance().chatManager().ackMessageRead(message.getFrom(), message.getMsgId());
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    @Override
    protected void onBubbleClick() {

    }
}
