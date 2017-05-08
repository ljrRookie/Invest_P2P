package com.ljr.invest_p2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;

import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/5/2.
 * TODO：
 */

public class LineChartActivity extends BaseActivity {
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.line_chart)
    LineChart mLineChart;

    private Typeface mTf;//声明字体库

    @Override
    protected int getLayoutId() {
        return R.layout.activity_line_chart;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("投资管理(折线图)");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        //设置当前的折线图的描述

        mLineChart.setDescription(null);
        //是否绘制网格背景
        mLineChart.setDrawGridBackground(false);

        //获取当前的X轴对象
        XAxis xAxis = mLineChart.getXAxis();
        //设置x轴的显示位置
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置x轴的字体
        xAxis.setTypeface(mTf);
        //是否绘制x轴的网格线
        xAxis.setDrawGridLines(false);
        //是否绘制x轴的轴线
        xAxis.setDrawAxisLine(true);


        //获取左边的y轴对象
        YAxis axisLeft = mLineChart.getAxisLeft();
        //设置左边y轴的字体
        axisLeft.setTypeface(mTf);
        //参数1:左边y轴提供的区间的个数。参数2:是否均匀分布这几个区间。false:均匀
        axisLeft.setLabelCount(5,false);
        //获取右边的y轴对象
        YAxis rightAxis = mLineChart.getAxisRight();
        //将右边的y轴设置为不显式的
        rightAxis.setEnabled(false);
        //提供折现数据（通常情况下，数据来自于服务器）
        LineData lineData = generateDataLine(1);
        mLineChart.setData(lineData);

        // 设置x轴方向的动画。执行时间是750.
        // 不需要再执行：invalidate();
        mLineChart.animateX(750);
    }


    @OnClick(R.id.iv_title_back)
    public void onClick() {
        removeCurrentActivity();
    }
    private LineData generateDataLine(int cnt) {
        //折线1：
        ArrayList<Entry> e1 = new ArrayList<Entry>();
        //提供折线中点的数据
        for (int i = 0; i < 12; i++) {
            e1.add(new Entry((int) (Math.random() * 65) + 40, i));
        }

        LineDataSet d1 = new LineDataSet(e1, "New DataSet 1");
        //设置折线的宽度
        d1.setLineWidth(4.5f);
        //设置小圆圈的尺寸
        d1.setCircleSize(4.5f);
        //设置高亮的颜色
        d1.setHighLightColor(Color.rgb(244, 0, 0));
        //是否显示小圆圈对应的数值
        d1.setDrawValues(true);



        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);

        LineData lineData = new LineData (sets);
        return lineData;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Jan");
        m.add("Feb");
        m.add("Mar");
        m.add("Apr");
        m.add("May");
        m.add("Jun");
        m.add("Jul");
        m.add("Aug");
        m.add("Sep");
        m.add("Okt");
        m.add("Nov");
        m.add("Dec");

        return m;
    }
}
