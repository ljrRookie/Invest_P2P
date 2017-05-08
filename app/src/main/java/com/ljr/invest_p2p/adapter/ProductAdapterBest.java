package com.ljr.invest_p2p.adapter;


import android.view.ViewGroup;

import com.ljr.invest_p2p.adapter.base.BaseHolder;
import com.ljr.invest_p2p.adapter.base.MyBaseAdapter;
import com.ljr.invest_p2p.adapter.base.MyHolder;
import com.ljr.invest_p2p.bean.HomeBean;

import java.util.List;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public class ProductAdapterBest extends MyBaseAdapter<HomeBean.ProInfoBean>{
    public ProductAdapterBest(List<HomeBean.ProInfoBean> list) {
        super(list);

    }

    @Override
    protected BaseHolder<HomeBean.ProInfoBean> getHolder() {
        return new MyHolder();
    }


}
