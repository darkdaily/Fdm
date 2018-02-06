package com.example.admin.fdm.ui.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.admin.fdm.R;

/**
 * Created by test on 2018/1/5.
 */

public class ModificationHintsDialog extends Dialog {


    LinearLayout linearLayout;
    private TextView textView;
    private String messageStr;//从外界设置的消息文本

    public ModificationHintsDialog(@NonNull Context context) {
        super(context);
    }

    public ModificationHintsDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ModificationHintsDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_button_dialog);
        textView = (TextView)findViewById(R.id.dialog_content);
        textView.setText(messageStr);

    }

    public void setMessage(String message) {
              messageStr = message;
    }


}
