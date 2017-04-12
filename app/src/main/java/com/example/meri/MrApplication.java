package com.example.meri;

import android.app.Application;

import org.xutils.x;


/**
 * Created by 磊 on 2017/3/23.
 */

public class MrApplication extends Application {

    /**
     * 所有组件创建之前执行
     */
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true); // 是否输出debug日志, 开启debug会影响性能.
    }
}
