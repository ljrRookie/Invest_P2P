package com.ljr.invest_p2p.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.util.Utils;

/**
 * Created by LinJiaRong on 2017/4/15.
 * TODO：自定义圆环进度条
 */

public class RoundProgress extends View {
    /**
     * 使用自定义属性替换
     */
 /*   private int roundColor = Color.GRAY;//圆环颜色
    private int roundProgressColor = Color.RED;//圆弧颜色
    private int textColor = Color.BLUE;//文本颜色
    private int roundWidth = Utils.dp2px(10);//圆弧的宽度
    private int textSize = Utils.dp2px(20);//本文字体大小
    private int max = 100;//圆环进度条最大值
    private int progress =60;//圆环进度条目前进度；*/
    /**
     * 自定义属性
     */
    private int roundColor;//圆环的颜色
    private int roundProgressColor ;//圆弧的颜色
    private int textColor;//文本的颜色

    private float roundWidth ;//圆环的宽度
    private float textSize ;//文本的字体大小

    private int max;//圆环的最大值
    private int progress;//圆环的进度
    private int width;//当前视图的宽度
    private Paint mPaint;

    public RoundProgress(Context context) {
        this(context,null);
    }

    public RoundProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

   
    public RoundProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化画笔
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//去除毛边
        //获取自定义属性
        //1.获取TypeArray的对象（调用两个参数的方法）
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgress);
        //2.取出所有的自定义属性
        roundColor = typedArray.getColor(R.styleable.RoundProgress_roundColor, Color.GRAY);
        roundProgressColor = typedArray.getColor(R.styleable.RoundProgress_roundProgressColor, Color.RED);
        textColor = typedArray.getColor(R.styleable.RoundProgress_textColor, Color.GREEN);
        roundWidth = typedArray.getDimension(R.styleable.RoundProgress_roundWidth,Utils.dp2px(10));
        textSize = typedArray.getDimension(R.styleable.RoundProgress_textSize,Utils.dp2px(20));
        max = typedArray.getInteger(R.styleable.RoundProgress_max,100);
        progress = typedArray.getInteger(R.styleable.RoundProgress_progress,30);
        //回收处理
        typedArray.recycle();
    }
    //测量：获取当前视图的宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
         width = this.getMeasuredWidth();
    }
    //绘制

    @Override
    protected void onDraw(Canvas canvas) {
        //1.绘制圆环
        //圆心
        int cx = width / 2;
        int cy = cx;
        float radius = width / 2 - roundWidth / 2 ;//半径
        mPaint.setColor(roundColor);//画笔颜色
        mPaint.setStyle(Paint.Style.STROKE);//设置圆环的样式
        mPaint.setStrokeWidth(roundWidth);//设置圆弧宽度
        canvas.drawCircle(cx,cy,radius,mPaint);

        //2.绘制圆弧
        RectF rectF = new RectF(roundWidth / 2, roundWidth / 2, width - roundWidth / 2, width - roundWidth / 2);
        mPaint.setColor(roundProgressColor);
        canvas.drawArc(rectF,0,progress * 360 / max,false,mPaint);

        //3.绘制文本
        String text = progress * 100 / max + "%" ;
        mPaint.setColor(roundProgressColor);
        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(0);
        Rect rect = new Rect();//此时创建的矩形没有具体宽高
        mPaint.getTextBounds(text,0,text.length(),rect);//此时矩形的宽高即为整好包裹文本的矩形宽高
        //获取左下顶点的坐标
        int x = width / 2 - rect.width() / 2;
        int y = width / 2 + rect.height() / 2;
        canvas.drawText(text,x,y,mPaint);

    }



    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
