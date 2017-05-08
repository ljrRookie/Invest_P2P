package com.ljr.invest_p2p.common;

import android.app.Activity;

import java.util.Stack;

/**
 * Created by LinJiaRong on 2017/4/12.
 * TODO：统一应用程序中所有的Activity的栈管理（单例模式）
 *
 */

public class ActivityManager {
    //单例模式：饿汉式
    private ActivityManager(){}
    private static ActivityManager sActivityManager = new ActivityManager();
    public static ActivityManager getInstance(){
        return sActivityManager;
    }
    //提供栈对象
    private Stack<Activity> mActivityStack = new Stack<>();
    //activity的添加
    public void add(Activity activity){
        if(activity != null){
            mActivityStack.add(activity);
        }
    }
    //删除指定的activity
    public void remove(Activity activity){
        for(int i =mActivityStack.size();i >= 0;i--){
            Activity currentActivity = mActivityStack.get(i);
            if(currentActivity.getClass().equals(activity.getClass())){
                currentActivity.finish();
                mActivityStack.remove(i);
            }
        }
    }
    //删除当前activity
    public void removeCurrent(){
        Activity currentActivity = mActivityStack.lastElement();
        currentActivity.finish();
        mActivityStack.remove(currentActivity);
    }
    //删除所有的activity
    public void removeAll(){
        for(int i = mActivityStack.size() -1;i >= 0;i--){
            Activity activity = mActivityStack.get(i);
            activity.finish();
            mActivityStack.remove(activity);
        }
    }
    //返回栈大小
    public int size(){
        return mActivityStack.size();
    }
}
