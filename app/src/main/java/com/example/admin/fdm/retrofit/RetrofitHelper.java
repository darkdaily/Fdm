package com.example.admin.fdm.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Serenade on 17/6/10.
 */

public class RetrofitHelper {
    public static final int DEFAULT_TIMEOUT = 10;
    private static Retrofit mRetrofit;//Retrofit对象
    private static String mBaseUrl = "http://api.zhagen.com/";//项目总地址
    private static RetrofitApi mRetrofitAPI;//地址接口

    private static RetrofitApi mRetrofitAPIWithUid;//地址接口,封装Uid

    public static long CACHE_SIZE = 1024*1024*50; // 缓存大小 50M

    private RetrofitHelper(){

    }

    public static RetrofitApi getApi() {
        if (mRetrofitAPI == null) {
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(InterceptorUtil.HeaderInterceptor())
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                    .retryOnConnectionFailure(true)   //错误重连
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)//设置项目总地址
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())//添加Gson解析工厂
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava2转换器
                    .build();
            mRetrofitAPI = mRetrofit.create(RetrofitApi.class);
        }
        return mRetrofitAPI;
    }

    public static RetrofitApi getApiWithUid() {
        if (mRetrofitAPIWithUid == null) {
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(InterceptorUtil.HeaderInterceptorWithUid())
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                    .retryOnConnectionFailure(true)   //错误重连
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)//设置项目总地址
                    .client(mClient)
                    .addConverterFactory(GsonConverterFactory.create())//添加Gson解析工厂
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava2转换器
                    .build();
            mRetrofitAPIWithUid = mRetrofit.create(RetrofitApi.class);
        }
        return mRetrofitAPIWithUid;
    }

    public static RetrofitApi getmRetrofitAPIWithUidWu(){
        if (mRetrofitAPIWithUid == null) {
            OkHttpClient mClient = new OkHttpClient.Builder()
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
//                    .addInterceptor(InterceptorUtil.HeaderInterceptorWithUid())
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
//                    .retryOnConnectionFailure(true)   //错误重连
                    .build();
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(mBaseUrl)//设置项目总地址
                    .client(mClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//添加RxJava2转换器
                    .build();
            mRetrofitAPIWithUid = mRetrofit.create(RetrofitApi.class);
        }
        return mRetrofitAPIWithUid;
    }
}
