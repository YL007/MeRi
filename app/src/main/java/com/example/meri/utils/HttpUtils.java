package com.example.meri.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 磊 on 2017/4/8.
 */

public class HttpUtils {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
