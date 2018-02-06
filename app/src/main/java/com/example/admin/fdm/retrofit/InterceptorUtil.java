package com.example.admin.fdm.retrofit;

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Serenade on 17/6/11.
 */

public class InterceptorUtil {
    public static String TAG = "----";


    //日志拦截器
    public static HttpLoggingInterceptor LogInterceptor() {
        return new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.w(TAG, "log: " + message);
            }
        }).setLevel(HttpLoggingInterceptor.Level.BODY);//设置打印数据的级别
    }

    public static Interceptor HeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等,PS我会在下一篇文章总写拦截token方法
                HttpUrl.Builder urlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host());
//                        .addQueryParameter("uid", "33");
//                        .addQueryParameter("showapi_sign", "274e07e744d7457bbc3e6c60682327e2");

                Request newRequest = oldRequest.newBuilder()
                        .addHeader("Accept-Charset", "UTF-8")
                        .addHeader("Accept", " application/json")
                        .addHeader("Content-type", "application/json")
                        .method(oldRequest.method(), oldRequest.body())
                        .url(urlBuilder.build())
                        .build();

                Response response = chain.proceed(newRequest);
                response.newBuilder()
                        .removeHeader("Pragma").build();
                return response;
            }
        };

    }


    public static Interceptor HeaderInterceptorWithUid() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request oldRequest = chain.request();
                //在这里你可以做一些想做的事,比如token失效时,重新获取token
                //或者添加header等等,PS我会在下一篇文章总写拦截token方法
                HttpUrl.Builder urlBuilder = oldRequest.url()
                        .newBuilder()
                        .scheme(oldRequest.url().scheme())
                        .host(oldRequest.url().host())
                        .addQueryParameter("uid", "33");
//                        .addQueryParameter("showapi_sign", "274e07e744d7457bbc3e6c60682327e2");

                Request newRequest = oldRequest.newBuilder()
                        .addHeader("Accept-Charset", "UTF-8")
                        .addHeader("Accept", " application/json")
                        .addHeader("Content-type", "application/json")
                        .method(oldRequest.method(), oldRequest.body())
                        .url(urlBuilder.build())
                        .build();

                Response response = chain.proceed(newRequest);
                response.newBuilder()
                        .removeHeader("Pragma").build();
                return response;
            }
        };

    }
}
