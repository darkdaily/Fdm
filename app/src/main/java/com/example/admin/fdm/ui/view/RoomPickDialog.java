package com.example.admin.fdm.ui.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.admin.fdm.R;
import com.zhy.view.flowlayout.TagFlowLayout;

/**
 * Created by fushuang on 2017/10/16.
 */

public class RoomPickDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private TagFlowLayout fl_pay_way;
    private TagFlowLayout fl_room_num;
    ResultCallBack resultCallBack;


    public void setResultCallBack(ResultCallBack resultCallBack) {
        this.resultCallBack = resultCallBack;
    }

    public RoomPickDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public RoomPickDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.room_pick_dialog, null, false);
        fl_pay_way = ((TagFlowLayout) view.findViewById(R.id.fl_pay_way));
        fl_room_num = ((TagFlowLayout) view.findViewById(R.id.fl_room_num));
        ((TextView) view.findViewById(R.id.tv_confirm)).setOnClickListener(this);
        setContentView(view);
        Window window = getWindow();
        WindowManager.LayoutParams params  = window.getAttributes();
        window.setGravity(Gravity.BOTTOM);
        params.width= ((Activity) context).getWindowManager().getDefaultDisplay().getWidth();
        window.setAttributes(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_confirm:
                resultCallBack.resultCallBack("");
                dismiss();
                break;
        }
    }

    public interface ResultCallBack{
         void resultCallBack(String s);
    }

}
