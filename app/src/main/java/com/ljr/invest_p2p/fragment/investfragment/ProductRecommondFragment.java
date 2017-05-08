package com.ljr.invest_p2p.fragment.investfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.ui.randomLayout.StellarMap;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.RequestParams;

import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LinJiaRong on 2017/4/22.
 * TODO：实现随机排列布局
 */

/**
 * 如何在布局中添加子布局？
 * 1.直接在布局文件中，以标签的方式添加。--静态
 * 2.在java中，动态的添加子视图
 *     (1)-----addView() 一个一个的添加视图
 *     (2)-----设置adapter的方式，批量装配数据
 */
public class ProductRecommondFragment extends BaseFragment {
    @Bind(R.id.stellarMap)
    StellarMap mStellarMap;

    private String[] datas = new String[]{"新手福利计划", "财神道90天计划", "硅谷钱包计划", "30天理财计划(加息2%)", "180天理财计划(加息5%)", "月月升理财计划(加息10%)",
            "中情局投资商业经营", "大学老师购买车辆", "屌丝下海经商计划", "美人鱼影视拍摄投资", "Android培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "铁路局回款计划", "屌丝迎娶白富美计划"};
    //声明两个子数组
    private String[] oneData = new String[datas.length/2];
    private String[] twoData = new String[datas.length - datas.length/2];
    //随机数
    private Random mRandom = new Random();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_invest_productrecommond;
    }

    @Override
    protected void initTitle() {}

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
    //初始化子数组的数据
        for(int i = 0; i < datas.length;i++){
            if(i < datas.length/2){
                oneData[i] = datas[i];
            }else {
                twoData[i-datas.length/2] = datas[i];
            }
        }
        StellarAdapter stellarAdapter = new StellarAdapter();
        mStellarMap.setAdapter(stellarAdapter);
        //设置stellarMap的内边距
        int leftPadding = Utils.dp2px(10);
        int topPadding = Utils.dp2px(10);
        int rightPadding = Utils.dp2px(10);
        int bottomPadding = Utils.dp2px(10);
        mStellarMap.setInnerPadding(leftPadding,topPadding,rightPadding,bottomPadding);
        /**
         * 必须调用如下的两个方法，否则stellarMap不能显示数据
         * 1.设置显示的数据在X轴，Y轴方向上的稀疏度
         * 2.设置初始化显示的组别，以及是否需要使用动画
         */
        mStellarMap.setRegularity(5,7);
        mStellarMap.setGroup(0,true);

    }

    private class StellarAdapter implements StellarMap.Adapter{
        //获取组的个数
        @Override
        public int getGroupCount() {
            return 2;
        }
        //返回每组中显示的数据的个数
        @Override
        public int getCount(int group) {
            if(group == 0){
                return datas.length/2;
            }else {
                return datas.length - datas.length / 2;
            }
        }

        /**
         *
         * @param group 组数
         * @param position 不同的组别 从0开始
         * @param convertView  返回具体的View
         * @return
         */
        @Override
        public View getView(int group, int position, View convertView) {
            final TextView tv = new TextView(getActivity());
            if(group == 0){
                tv.setText(oneData[position]);
            }else{
                tv.setText(twoData[position]);
            }
            //设置字体的属性
            tv.setTextSize(Utils.dp2px(7)+Utils.dp2px(mRandom.nextInt(10)));
            int red = mRandom.nextInt(211);
            int blue = mRandom.nextInt(211);
            int green = mRandom.nextInt(211);
            tv.setTextColor(Color.rgb(red,green,blue));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utils.toast(tv.getText().toString(),false);
                }
            });
            return tv;
        }
    //从未被调用
        @Override
        public int getNextGroupOnPan(int group, float degree) {
            return 0;
        }
        //返回下一组显示缩放的组别
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn) {
            if(group == 0){
                return 1;
            }else{
                return 0;
            }
        }
    }
}
