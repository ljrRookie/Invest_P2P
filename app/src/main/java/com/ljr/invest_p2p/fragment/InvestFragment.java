package com.ljr.invest_p2p.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.fragment.investfragment.ProductHotFragment;
import com.ljr.invest_p2p.fragment.investfragment.ProductListFragment;
import com.ljr.invest_p2p.fragment.investfragment.ProductRecommondFragment;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;

import butterknife.Bind;


/**
 * Created by LinJiaRong on 2017/4/10.
 * TODO：投资页面
 */

public class InvestFragment extends BaseFragment {


    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.vp_invest)
    ViewPager mVpInvest;
    // @Bind(R.id.tabLayout)
    // TabLayout mTabLayout;
    private ArrayList<Fragment> mFragments;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.GONE);
        mTvTitle.setText(R.string.invest);
        mIvTitleSetting.setVisibility(View.GONE);
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected void initData(String content) {
        //加载三个不同的Fragment
        initFragments();
        MyAdapter myAdapter = new MyAdapter(getFragmentManager(), mFragments);
        mVpInvest.setAdapter(myAdapter);
        /*mTabLayout.setupWithViewPager(mVpInvest);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);*/
       // mTabLayout.setupWithViewPager(mVpInvest);

    }

    private void initFragments() {
        ProductListFragment productListFragment = new ProductListFragment();
        ProductRecommondFragment productRecommondFragment = new ProductRecommondFragment();
        ProductHotFragment productHotFragment = new ProductHotFragment();
        mFragments = new ArrayList<>();
        mFragments.add(productListFragment);
        mFragments.add(productRecommondFragment);
        mFragments.add(productHotFragment);


    }



    /**
     * PagerAdapter的实现:
     * 如果ViewPager中加载的是Fragment，则提供的Adapter可以继承于具体的：FragemntStatePagerAdapter或者FragemntPagerAdapter
     * FragemntStatePagerAdapter：适用于ViewPager中加载的Fragment过多，会根据最少使用的算法，实现内存中Fragment的清理，避免溢出
     * FragmentPagerAdapter：适用于ViewPager中加载的Fragment不多时，系统不会清理已经加载的Fragment
     */
    class MyAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mFragments;

        public MyAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.mFragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments == null ? 0 : mFragments.size();
        }

        //提供TabPagerIndicator的显示
        @Override
        public CharSequence getPageTitle(int position) {
            return Utils.getStringArr(R.array.invest_tab)[position];
        }
    }
}
