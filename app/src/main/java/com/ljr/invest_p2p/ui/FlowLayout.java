package com.ljr.invest_p2p.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public class FlowLayout extends ViewGroup {
    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 设置当前布局的宽度和高度
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //获取设置的宽高的模式和具体的值
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //如果用户使用的是至多模式，那么使用如下两个变量计算真实的宽高值
        int width = 0;
        int height = 0;
        //每一行的宽度
        int lineWidth = 0;
        int lineHeight = 0;
        //获取子视图
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //只有调用如下的方法，方可计算子视图的测量的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //获取子视图的宽高
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //要想保证可以获取子视图的边距参数对象，必须重写generateLayoutParams().
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();

            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= widthSize) {
                //不换行
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            } else {
                //换行
                width = Math.max(width, lineWidth);
                height += lineHeight;
                //重置
                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
            }
            //最后一个子视图
            if (i == childCount) {
                width = Math.max(width, lineWidth);
                height += lineHeight;
            }
        }
        //设置当前流式布局的宽高
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize : height);
    }

    //每一行的子视图的集合构成的集合。
    private List<List<View>> allViews = new ArrayList<>();
    //每一行的高度构成的集合
    private List<Integer> allHeights = new ArrayList<>();

    //重写的目的：给每一个子视图指定显示的位置：childView.layout();
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //1.给两个集合添加元素
        //每一行的宽高值
        int lineWidth = 0;
        int lineHeight = 0;
        //提供一个集合，保存一行childView
        List<View> lineList = new ArrayList<>();
        //获取布局的宽度
        int width = this.getMeasuredWidth();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            //获取视图的测量宽高，边距
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
            if (lineWidth + childWidth + mp.leftMargin + mp.rightMargin <= width) {
                lineList.add(childView);
                lineWidth += childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = Math.max(lineHeight, childHeight + mp.topMargin + mp.bottomMargin);
            } else {//换行
                allViews.add(lineList);
                allHeights.add(lineHeight);

                lineWidth = childWidth + mp.leftMargin + mp.rightMargin;
                lineHeight = childHeight + mp.topMargin + mp.bottomMargin;
                lineList = new ArrayList<>();
                lineList.add(childView);
            }
            if (i == childCount - 1) {//如果是最后一个元素
                allViews.add(lineList);
                allHeights.add(lineHeight);
            }
        }
        //2.给每一个子视图指定显示的位置
        int x = 0;
        int y = 0;
        //每遍历一次，对应一行元素
        for (int i = 0; i < allViews.size(); i++) {
            List<View> lineViews = allViews.get(i);
            for (int j = 0; j < lineViews.size(); j++) {
                View childView = lineViews.get(j);
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                MarginLayoutParams mp = (MarginLayoutParams) childView.getLayoutParams();
                int left = x + mp.leftMargin;
                int top = y + mp.topMargin;
                int right = left + childWidth;
                int bottom = top + childHeight;
                childView.layout(left, top, right, bottom);
                x += childWidth + mp.leftMargin + mp.rightMargin;
            }
            y += allHeights.get(i);
            x = 0;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        MarginLayoutParams mp = new MarginLayoutParams(getContext(), attrs);
        return mp;

    }
}
