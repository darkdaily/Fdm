package com.example.admin.fdm.retrofit.rx;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import com.example.admin.fdm.mvp.module.BaseResponse;
import com.example.admin.fdm.utils.log.LogUtils;
import com.google.gson.JsonSyntaxException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Serenade on 2017/8/17.
 */

public abstract class BaseObserver<T extends BaseResponse> implements Observer<T> {
    protected WeakReference<Context> mContext;
    private String clzName;
    private Disposable disposable;

    public BaseObserver(Context context, Class clz) {
        this.mContext = new WeakReference<>(context);
        this.clzName = clz.getName();
    }

    public BaseObserver(Activity activity){
        this.mContext = new WeakReference<>(activity.getBaseContext());
        this.clzName = activity.getClass().getSimpleName();
    }

    public BaseObserver(Context context, Object clzObj) {
        this.mContext = new WeakReference<>(context);
        this.clzName = clzObj.getClass().getName();
    }

    public BaseObserver(Context context, String clzName) {
        this.mContext = new WeakReference<>(context);
        this.clzName = clzName;
    }

    @Override
    public void onError(Throwable e) {
        Context context = mContext.get();
        if (context == null) return;
        if (e instanceof SocketTimeoutException) {
            Toast.makeText(context, "请求超时！", Toast.LENGTH_SHORT).show();
            NoNetwork();
        } else if (e instanceof ConnectException) {
            Toast.makeText(context, "网络中断，请检查您的网络状态！", Toast.LENGTH_SHORT).show();
            NoNetwork();
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(context, "网络错误，请检查您的网络状态！", Toast.LENGTH_SHORT).show();
            NoNetwork();
        } else if (e instanceof NullPointerException) {
//            Toast.makeText(context, "数据为空，请稍后重试！", Toast.LENGTH_SHORT).show();
            NoData();
            LogUtils.e("-----RxJava-----", "error----------->" + e.toString());
        } else if (e instanceof JsonSyntaxException){
            Toast.makeText(context, "Json数据解析异常错误！", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "未知错误！", Toast.LENGTH_SHORT).show();
            LogUtils.e("-----RxJavaRxJava-----", "error----------->" + e.toString());
        }
//        NetManager.getInstance().removeRequest(clzName, disposable);
        error(e);
    }

    public abstract void error(Throwable e);

    public abstract void NoData();

    public abstract void NoNetwork();

    @Override
    public void onSubscribe(@NonNull Disposable d) {
//        NetManager.getInstance().recordRequest(clzName, d);
        disposable = d;
    }

    @Override
    public void onNext(@NonNull T  t) {
        /**
         * 0 表示接口请求参数正常
         * 1 表示有问题
         */

        if (t.getCode()==0) {
//
                next(t);
//
        }else {
            if(t.getData() == null){
                NoData();
                Toast.makeText(mContext.get(), t.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public abstract void next(@NonNull T t);

    @Override
    public void onComplete() {
        NetManager.getInstance().removeRequest(clzName, disposable);
        complete();
    }

    public abstract void complete();
}
