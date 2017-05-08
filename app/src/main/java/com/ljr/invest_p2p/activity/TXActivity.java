package com.ljr.invest_p2p.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/5/2.
 * TODO：提现
 */

public class TXActivity extends BaseActivity {
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.zfb)
    TextView mZfb;
    @Bind(R.id.select_bank)
    RelativeLayout mSelectBank;
    @Bind(R.id.tx_text)
    TextView mTxText;
    @Bind(R.id.view)
    View mView;
    @Bind(R.id.et_money)
    EditText mEtMoney;
    @Bind(R.id.tx_text2)
    TextView mTxText2;
    @Bind(R.id.tv_money)
    TextView mTvMoney;
    @Bind(R.id.btn_tx)
    Button mBtnTx;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_tx;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("提现");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        //设置当前的体现的button是不可操作的
        mBtnTx.setClickable(false);
        mEtMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String money = mEtMoney.getText().toString().trim();
                if(TextUtils.isEmpty(money)){
                    //设置button不可操作的
                    mBtnTx.setClickable(false);
                    //修改背景颜色
                    mBtnTx.setBackgroundResource(R.drawable.btn_02);
                }else{
                    //设置button可操作的
                    mBtnTx.setClickable(true);
                    //修改背景颜色
                    mBtnTx.setBackgroundResource(R.drawable.btn_01);
                }
            }
        });
    }

    @OnClick({R.id.iv_title_back, R.id.btn_tx})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_back:
                removeCurrentActivity();
                break;
            case R.id.btn_tx:
                Utils.toast("您的提现申请已被成功受理。审核通过后，24小时内，你的钱自然会到账",false);
                Utils.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        removeCurrentActivity();
                    }
                },2000 );
                break;
        }
    }
}
