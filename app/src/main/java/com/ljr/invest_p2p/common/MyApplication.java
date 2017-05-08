package com.ljr.invest_p2p.common;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by LinJiaRong on 2017/4/12.
 * TODO：在整个应用执行中，提供需要的变量
 */

public class MyApplication extends Application {
    public static Context sContext;//需要使用的上下文
    public static Handler sHandler;//需要用到的handler
    public static Thread sMainThread;//提供主线程对象
    public static int mainThreadId;//提供主线程对象的Id
    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getApplicationContext();
        sHandler = new Handler();
        //实例化当前的Application的线程即为主线程
        sMainThread = Thread.currentThread();
        //获取当前线程
        mainThreadId = android.os.Process.myTid();
        //设置未捕获异常的处理器
        //CrashHandler.getInstance().init();
        ShareSDK.initSDK(this);
    }
}
