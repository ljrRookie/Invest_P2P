package com.ljr.invest_p2p.common;

import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.ljr.invest_p2p.util.Utils;


/**
 * Created by LinJiaRong on 2017/4/12.
 * TODO：捕获程序中未捕获的全局异常（单例）
 *
 * 处理：1.当出现未捕获的异常时，给用户一个友好的提示
 *      2.在出现异常时，能够将异常信息发给后台，便于在后续的版本中解决bug
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = "MainActivity";
    //系统默认的处理未捕获异常的处理器
    private Thread.UncaughtExceptionHandler mDefaultUncaughtExceptionHandler;

    //单例模式（懒汉式）
    /**
     * 本身实例化未捕获异常的处理器的操作就是系统在一个单独的线程中完成的
     * ，所以不涉及多线程问题，使用懒汉式更好
     */
    private CrashHandler() {
    }
    private static CrashHandler sCrashHandler=null;
    public static CrashHandler getInstance(){
        if(sCrashHandler==null){
            sCrashHandler = new CrashHandler();
        }
        return sCrashHandler;
    }
    public void init(){
        mDefaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        //将当前类设置为出现未捕获异常的处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    /**
     * 一旦系统出现未捕获的异常，就会调用以下方法
     * @param t
     * @param e
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        Log.d(TAG, "uncaughtException: 出现未捕获的异常啦" +e.getMessage());
        new Thread(){
            public void run(){
                //prepare()和loop()之间的操作就是在主线程中执行的！
                Looper.prepare();
                Toast.makeText(Utils.getContext(), "亲，出现了未捕获的异常了！", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();
        //收集未捕获的异常
        CollectionException(e);
        //收集异常后，退出进程
        try {
            Thread.sleep(2000);
            //移除当前Activity
            ActivityManager.getInstance().removeCurrent();
            //结束当前进程
            android.os.Process.killProcess(android.os.Process.myPid());
            //结束虚拟机
            System.exit(0);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private void CollectionException( Throwable e) {
        final String ExceptionMessage = e.getMessage();
        //收集具体客户手机信息   Build类
         final String UserMessage = Build.DEVICE + " , " + Build.MODEL + " , " + Build.PRODUCT + " + " + Build.VERSION.SDK_INT;
        new Thread(){
            public void run(){
                Log.d(TAG, "exception "+ExceptionMessage);
                Log.d(TAG, "UserMessage "+UserMessage );
            }
        }.start();
    }
}
