package com.ljr.invest_p2p.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/5/2.
 * TODO：充值
 */

public class CZActivity extends BaseActivity {
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.cz_text)
    TextView mCzText;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.et_cz)
    EditText mEtCz;
    @Bind(R.id.cz_text2)
    TextView mCzText2;
    @Bind(R.id.tv_money)
    TextView mTvMoney;
    @Bind(R.id.btn_cz)
    Button mBtnCz;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_cz;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("充值");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        //默认情况下充值按钮不可点击
        mBtnCz.setEnabled(false);
        mEtCz.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String money = mEtCz.getText().toString().trim();
                if (TextUtils.isEmpty(money)) {
                    //设置button为不可操作的
                    mBtnCz.setClickable(false);
                    //设置背景颜色
                    mBtnCz.setBackgroundResource(R.drawable.btn_02);
                } else {
                    //设置button为可操作的
                    mBtnCz.setClickable(true);
                    //设置背景颜色
                    mBtnCz.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @OnClick({R.id.iv_title_back, R.id.btn_cz})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                this.removeCurrentActivity();
                break;
            case R.id.btn_cz:
                Utils.toast("充值", false);
                break;
        }
    }
}
