package com.ljr.invest_p2p.adapter.base;

import android.content.pm.ProviderInfo;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public abstract class BaseHolder<T> {
    private View rootView;
    private T data;

    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);
        ButterKnife.bind(this, rootView);
    }

    //提供item的布局
    protected abstract View initView();

    public View getRootView() {
        return rootView;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
        refreshData();
    }

    //装配数据
    protected abstract void refreshData();
}
