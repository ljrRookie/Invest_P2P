package com.ljr.invest_p2p.common;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.util.Util;
import com.ljr.invest_p2p.ui.LoadingPage;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.RequestParams;

import butterknife.ButterKnife;

/**
 * Created by LinJiaRong on 2017/4/17.
 * TODO：抽取BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    private LoadingPage mLoadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mLoadingPage = new LoadingPage(container.getContext()) {
            @Override
            public int layoutId() {
                return getLayoutId();
            }

            @Override
            protected RequestParams params() {
                return getParams();
            }

            @Override
            protected String url() {
                return getUrl();
            }

            @Override
            protected void onSuccess(ResultState resultState, View view_success) {
            ButterKnife.bind(BaseFragment.this,view_success);
                initTitle();
                initData(resultState.getContent());
            }
        };
        return mLoadingPage;
    }

    //访问网络请求地址
    protected abstract String getUrl();
    //访问网络请求参数
    protected abstract RequestParams getParams();
    //初始化界面的数据
    protected abstract void initData(String content);
    //初始化title
    protected abstract void initTitle();
    //加载布局
    public abstract int getLayoutId();
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Utils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mLoadingPage.show();
            }
        }, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
