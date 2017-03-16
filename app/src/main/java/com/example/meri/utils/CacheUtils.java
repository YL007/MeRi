package com.example.meri.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 磊 on 2017/3/15.
 * 缓存软件的参数和数据
 */

public class CacheUtils {
    /**
     * 获取缓存值
     * @param context 上下文
     * @param key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences("Cache",Context.MODE_PRIVATE);
        return sp.getBoolean(key,false);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = context.getSharedPreferences("Cache", Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }
}
