package com.example.admin.fdm.retrofit;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okio.BufferedSink;

/**
 * Created by fushuang on 2017/12/25.
 */

public class RequestBody extends okhttp3.RequestBody {
    public String uid;
    public String code;

    public RequestBody(String uid, String code) {
        this.uid = uid;
        this.code = code;
    }


    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Nullable
    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

    }
}
