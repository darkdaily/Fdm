package com.example.admin.fdm.retrofit.rx;

import android.util.ArrayMap;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 网络请求管理类
 * Created by Serenade on 17/6/16.
 */

public class NetManager {
    private ArrayMap<String, CompositeDisposable> mDisposables;//按类名以及该类中的网络请求集合存储

    /**
     * 构造方法私有化
     */
    private NetManager() {
    }

    /**
     * 静态内部类实现单例
     */
    private static class NetworkManagerHolder {
        public static final NetManager INSTANCE = new NetManager();
    }

    /**
     * 获得单一实例
     *
     * @return 单一实例
     */
    public static NetManager getInstance() {
        return NetworkManagerHolder.INSTANCE;
    }


    public void cancelRequests(String className) {
        if (mDisposables != null) {
            CompositeDisposable compositeDisposable = mDisposables.get(className);
            if (compositeDisposable!=null)
                compositeDisposable.dispose();
        }
    }

    public void recordRequest(String className, Disposable d) {
        if (mDisposables == null)
            mDisposables = new ArrayMap<>();
        if (mDisposables.containsKey(className)) {
            CompositeDisposable compositeDisposable = mDisposables.get(className);
            compositeDisposable.add(d);
        } else {
            CompositeDisposable compositeDisposable = new CompositeDisposable();
            compositeDisposable.add(d);
            mDisposables.put(className, compositeDisposable);
        }
    }

    public void removeRequest(String className, Disposable d) {
        if (mDisposables == null)
            return;

        if (mDisposables.containsKey(className)) {
            CompositeDisposable compositeDisposable = mDisposables.get(className);
            compositeDisposable.remove(d);
        }
    }

}
