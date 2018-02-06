package com.example.admin.fdm.utils.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AlertDialog;

import com.example.admin.fdm.R;


public class LoadingDialog {

    private ProgressDialog mDialog;
    private static LoadingDialog instance = null;
    private AlertDialog loadDialog;

    public static synchronized LoadingDialog getInstance() {
        if (instance == null) {
            instance = new LoadingDialog();
        }
        return instance;
    }

    public void show(Context context, String message, boolean cancleable) {
        mDialog = ProgressDialog.show(context, null, message);
        mDialog.setCancelable(cancleable);
        mDialog.show();
    }

    public void show(Context context, int message, boolean cancleable) {
        mDialog = ProgressDialog.show(context.getApplicationContext(), null, context.getResources().getString(message));
        mDialog.setCancelable(cancleable);
        mDialog.show();
    }

    public void dismissDialog() {
        try {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
                mDialog=null;
            }
        } catch (Exception e) {
        }
    }

    public void showLoadingDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        loadDialog = builder.setView(R.layout.loading_dialog).create();
        loadDialog.show();
    }



}
