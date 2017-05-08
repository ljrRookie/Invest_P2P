package com.ljr.invest_p2p.activity;



import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.bean.User;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.util.MD5Utils;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.til_phoneNum)
    TextInputLayout mTilPhoneNum;
    @Bind(R.id.til_password)
    TextInputLayout mTilPassword;
    @Bind(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("用户登录");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_title_back, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                removeAll();
                goToActivity(MainActivity.class,null);
                break;
            case R.id.btn_login:
                String phoneNum = mTilPhoneNum.getEditText().getText().toString().trim();
                String passWord = mTilPassword.getEditText().getText().toString().trim();
                if(!TextUtils.isEmpty(phoneNum) && !TextUtils.isEmpty(passWord)){
                    String url = AppNetConfig.LOGIN;
                    RequestParams params = new RequestParams();
                    params.put("phone",phoneNum);
                    params.put("password", MD5Utils.MD5(passWord));
                    Log.d(TAG, "onClick: "+phoneNum+"=="+MD5Utils.MD5(passWord));
                    mClient.post(url,params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            Log.d(TAG, "onSuccess: "+content);
                            //解析json
                            JSONObject jsonObject = JSON.parseObject(content);

                            boolean success = jsonObject.getBoolean("success");
                            if(success){
                                //解析json数据，生成User对象
                                String data = jsonObject.getString("data");
                                User user = JSON.parseObject(data, User.class);
                                //保存用户信息
                                saveUser(user);
                                removeAll();
                                goToActivity(MainActivity.class,null);
                            }else{
                                Utils.toast("用户名不存在或密码错误！！！",false);
                            }

                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            Utils.toast("登录失败",false);
                        }
                    });
                }else {
                    Utils.toast("用户名不存在或密码不正确",false);
                }
                break;
        }
    }

}
