package com.ljr.invest_p2p.adapter.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.bean.HomeBean;
import com.ljr.invest_p2p.ui.RoundProgress;
import com.ljr.invest_p2p.util.Utils;

import butterknife.Bind;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public class MyHolder extends BaseHolder<HomeBean.ProInfoBean> {
    @Bind(R.id.p_name)
    TextView mName;
    @Bind(R.id.p_money)
    TextView mMoney;
    @Bind(R.id.p_yearlv)
    TextView mYearlv;
    @Bind(R.id.p_suodingdays)
    TextView mSuodingdays;
    @Bind(R.id.p_minzouzi)
    TextView mMinzouzi;
    @Bind(R.id.p_minnum)
    TextView mMinnum;
    @Bind(R.id.p_progresss)
    RoundProgress mProgresss;



    @Override
    protected View initView() {
        return View.inflate(Utils.getContext(), R.layout.item_product_list, null);
    }

    @Override
    protected void refreshData() {
        HomeBean.ProInfoBean data = this.getData();
        //装配数据
        mName.setText(data.name);
        mMoney.setText(data.money);
        mMinnum.setText(data.memberNum);
        mMinzouzi.setText(data.minTouMoney);
        mYearlv.setText(data.yearRate);
        mSuodingdays.setText(data.suodingDays);
        mProgresss.setProgress(Integer.parseInt(data.progress));
    }
}
