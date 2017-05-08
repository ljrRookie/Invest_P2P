package com.ljr.invest_p2p.common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.ljr.invest_p2p.bean.User;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;

/**
 * Created by LinJiaRong on 2017/4/26.
 * TODO：BaseActivity
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);
        initTitle();
        initData();
    }

    protected abstract int getLayoutId();

    protected abstract void initTitle();

    protected abstract void initData();

    //提供联网
    public AsyncHttpClient mClient = new AsyncHttpClient();

    //启动新的activity
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        //携带数据
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }
    //销毁当前的Activity
    public void removeCurrentActivity(){
        ActivityManager.getInstance().removeCurrent();
    }
    //销毁所有的Activity
    public void removeAll(){
        ActivityManager.getInstance().removeAll();
    }
    //保存用户信息
    public void saveUser(User user){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",user.getName());
        editor.putString("imageurl",user.getImageurl());
        editor.putBoolean("iscredit", user.isCredit());
        editor.putString("phone",user.getPhone());
        editor.commit();//必须提交，否则保存不成功
    }
    //读取用户信息
    public User readUser(){
        SharedPreferences sp = this.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        User user = new User();
        user.setName(sp.getString("name",""));
        user.setImageurl(sp.getString("imageurl", ""));
        user.setPhone(sp.getString("phone", ""));
        user.setCredit(sp.getBoolean("iscredit",false));
        return user;
    }
}
