package com.ljr.invest_p2p.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/5/3.
 * TODO：饼状图
 */

public class PieChartActivity extends BaseActivity{
    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.pie_chart)
    PieChart mPieChart;
    private Typeface mTf;//声明字体库
    @Override
    protected int getLayoutId() {
        return R.layout.activity_pie_chart;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.VISIBLE);
        mTvTitle.setText("饼状图demo");
        mIvTitleSetting.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void initData() {
        mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        // apply styling
        mPieChart.setDescription(null);
        mPieChart.setHoleRadius(52f);//最内层的圆的半径
        mPieChart.setTransparentCircleRadius(77f);//包裹内层圆的半径
        mPieChart.setCenterText("Android\n厂商占比");
        mPieChart.setCenterTextTypeface(mTf);

        //是否使用百分比。true:各部分的百分比的和是100%。
        mPieChart.setUsePercentValues(true);

        PieData pieData = generateDataPie();
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTypeface(mTf);
        pieData.setValueTextSize(11f);
        pieData.setValueTextColor(Color.RED);
        // set data
        mPieChart.setData(pieData);

        //获取右上角的描述结构的对象
        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setYEntrySpace(10f);//相邻的entry在y轴上的间距
        l.setYOffset(30f);//第一个entry距离最顶端的间距

        // do not forget to refresh the chart
        // pieChart.invalidate();
        mPieChart.animateXY(900, 900);
    }

    private PieData generateDataPie() {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        for (int i = 0; i < 4; i++) {
            entries.add(new PieEntry((int) (Math.random() * 70) + 30, i));
        }

        PieDataSet d = new PieDataSet(entries, "资金");

        // 相邻模块的间距
        d.setSliceSpace(2f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);

        PieData cd = new PieData(d);
        return cd;
    }

    private ArrayList<String> getQuarters() {

        ArrayList<String> q = new ArrayList<String>();
        q.add("三星");
        q.add("华为");
        q.add("oppo");
        q.add("vivo");

        return q;
    }

    @OnClick(R.id.iv_title_back)
    public void onClick() {
        removeCurrentActivity();
    }
}

