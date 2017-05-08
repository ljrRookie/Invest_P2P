package com.ljr.invest_p2p.fragment.investfragment;


import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.ui.FlowLayout;
import com.ljr.invest_p2p.util.DrawUtils;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;


/**
 * Created by LinJiaRong on 2017/4/22.
 * TODO：热门理财页面
 */

public class ProductHotFragment extends BaseFragment {
    @Bind(R.id.product_hot)
    FlowLayout mProductHot;
    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷计划", "30天理财计划",
            "180天理财计划", "月月升", "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资",
            "Android培训老师自己周转", "养猪场扩大经营", "旅游公司扩大规模", "摩托罗拉洗钱计划", "铁路局回款计划", "屌丝迎娶白富美计划"
    };

    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest_producthot;
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected String getUrl() {
        return null;
    }

    @Override
    protected RequestParams getParams() {
        return null;
    }


    @Override
    protected void initData(String content) {
        for (int i = 0; i < datas.length; i++) {
            final TextView tv = new TextView(getContext());
            //设置属性
            tv.setText(datas[i]);
            ViewGroup.MarginLayoutParams mp = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            mp.leftMargin = Utils.dp2px(5);
            mp.rightMargin = Utils.dp2px(5);
            mp.topMargin = Utils.dp2px(5);
            mp.bottomMargin = Utils.dp2px(5);
            tv.setLayoutParams(mp);
            int padding = Utils.dp2px(5);
            tv.setPadding(padding,padding,padding,padding);
            tv.setTextSize(Utils.dp2px(10));
            Random random = new Random();
            int red = random.nextInt(211);
            int green = random.nextInt(211);
            int blue = random.nextInt(211);
            //设置具有选择器功能的背景
            tv.setBackground(DrawUtils.getSelector(DrawUtils.getDrawable(Color.rgb(red,green,blue),Utils.dp2px(5)),DrawUtils.getDrawable(Color.WHITE,Utils.dp2px(5))));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.toast(tv.getText().toString(),false);
                }
            });
            mProductHot.addView(tv);
        }
    }

}
