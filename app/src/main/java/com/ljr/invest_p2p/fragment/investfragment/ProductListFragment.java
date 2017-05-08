package com.ljr.invest_p2p.fragment.investfragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.util.LogTime;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.adapter.ProductAdapter;
import com.ljr.invest_p2p.adapter.ProductAdapterBest;
import com.ljr.invest_p2p.bean.HomeBean;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.ui.MyTextView;
import com.loopj.android.http.RequestParams;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by LinJiaRong on 2017/4/22.
 * TODO：invest的分页
 */

public class ProductListFragment extends BaseFragment {
    private static final String TAG = "ProductListFragment";
    @Bind(R.id.tv_product_title)
    MyTextView mTvProductTitle;
    @Bind(R.id.lv_product_list)
    ListView mLvProductList;
    private List<HomeBean.ProInfoBean> mProductList;


    @Override
    protected String getUrl() {
        return AppNetConfig.PRODUCT;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected void initData(String content) {
        /**
         *实现跑马灯效果。
         */
//方式一：使得当前的textView获取焦点。
        /*mTvProductTitle.setFocusable(true);
        mTvProductTitle.setFocusableInTouchMode(true);
        mTvProductTitle.requestFocus();*/
//方式二：提供TextView的子类，重写isFocus(),返回true即可。重写MyTextView
        JSONObject jsonObject = JSON.parseObject(content);
        Log.d(TAG, "initData: ===="+content);
        boolean success = jsonObject.getBoolean("success");
        if(success){
            String data = jsonObject.getString("data");
            //组装成集合数据
            mProductList = JSON.parseArray(data,HomeBean.ProInfoBean.class);

            //方式一：没有进行抽取操作
             // ProductAdapter productAdapter = new ProductAdapter(mProductList);
            //  mLvProductList.setAdapter(productAdapter);
            //方式二：进行了抽取
            ProductAdapterBest productAdapterBest = new ProductAdapterBest(mProductList);
            mLvProductList.setAdapter(productAdapterBest);
        }

    }

    @Override
    protected void initTitle() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest_productlist;
    }
}
