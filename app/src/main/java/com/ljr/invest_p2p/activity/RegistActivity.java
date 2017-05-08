package com.ljr.invest_p2p.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.util.Util;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.util.MD5Utils;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistActivity extends BaseActivity {

    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.et_num)
    EditText mNum;
    @Bind(R.id.et_username)
    EditText mUsername;
    @Bind(R.id.et_pwd)
    EditText mPwd;
    @Bind(R.id.et_pwd_again)
    EditText mPwdAgain;
    @Bind(R.id.btn_register)
    Button mBtnRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("用户注册");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.iv_title_back, R.id.btn_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                removeCurrentActivity();
                break;
            case R.id.btn_register:
                String phoneNum = mNum.getText().toString().trim();
                String userName = mUsername.getText().toString().trim();
                String pwd = mPwd.getText().toString().trim();
                String pwdAgain = mPwdAgain.getText().toString().trim();
                if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(phoneNum) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(pwdAgain)){
                    Utils.toast("输入的信息不能为空！！！",false);
                }else if(!pwd.equals(pwdAgain)){
                    Utils.toast("输入的密码不一致！！！",false);
                    mPwd.setText("");
                    mPwdAgain.setText("");
                }else{
                    String url = AppNetConfig.USERREGISTER;
                    RequestParams params = new RequestParams();
                    try {
                        userName = new String(userName.getBytes(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    params.put("name",userName);
                    params.put("password", MD5Utils.MD5(pwd));
                    params.put("phone",phoneNum);
                    mClient.post(url,params,new AsyncHttpResponseHandler(){
                        @Override
                        public void onSuccess(String content) {
                            JSONObject jsonObject = JSON.parseObject(content);
                            boolean isExist = jsonObject.getBoolean("isExist");
                            if(isExist){
                                Utils.toast("此用户已注册",false);
                            }else{
                                Utils.toast("注册成功",false);
                                removeCurrentActivity();
                            }
                        }

                        @Override
                        public void onFailure(Throwable error, String content) {
                            Utils.toast("联网请求失败",false);
                        }
                    });

                }
                break;
        }
    }
}

