package com.ljr.invest_p2p.ui;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

import com.ljr.invest_p2p.util.Utils;

/**
 * Created by LinJiaRong on 2017/4/17.
 * TODO：自定义ViewGroup
 */

public class MyScrollView extends ScrollView {

    private View mChildView;
    private boolean isFinishAnimation = true;
    private int lastY,lastX;//上一次Y轴和X轴 方向操作的坐标位置
    private int downX,downY;
    private Rect normal = new Rect();//用于记录临界状态的左，上，右，下

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    //获取子视图
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(getChildCount() > 0){
            mChildView = getChildAt(0);
        }
    }
//拦截：实现俯视图对子视图的拦截
//是否拦截成功，取决于方法的返回值，true为拦截成功

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean isIntercept = false;
        int eventX = (int) ev.getX();
        int eventY = (int) ev.getY();
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                lastX = downX = eventX;
                lastY = downY = eventY;
                   break;
            case MotionEvent.ACTION_MOVE :
                //获取水平和垂直方向的移动距离
                int absX = Math.abs(eventX - downX);
                int absY = Math.abs(eventY - downY);
                if(absY > absX && absY >= Utils.dp2px(10)){
                    isIntercept = true;//拦截
                }
                lastX = eventX;
                lastY = eventY;
                break;
        }
        return isIntercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if(mChildView == null || !isFinishAnimation){
            return  super.onTouchEvent(ev);
        }
        int eventY = (int) ev.getY();//获取当前的Y轴坐标
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN :
                lastY = eventY;
                break;
            case MotionEvent.ACTION_MOVE :
                int dy = eventY - lastY;//微小的移动量
                if(isNeedMove()){
                if(normal.isEmpty()){
                    //记录了childView的临界状态的左，上，右，下
                    normal.set(mChildView.getLeft(),mChildView.getTop(),mChildView.getRight(),mChildView.getBottom());
                }
                //重新布局
                    mChildView.layout(mChildView.getLeft(),mChildView.getTop()+dy/2,mChildView.getRight(),mChildView.getBottom()+dy/2);
                }
                //重新赋值
                lastY = eventY;
                break;
            case MotionEvent.ACTION_UP :
                if(isNeedAnimation()){
                    //使用平移动画
                    int translateY = mChildView.getBottom() - normal.bottom;
                    TranslateAnimation animation = new TranslateAnimation(0, 0, 0, translateY);
                    animation.setDuration(200);
                    animation.setFillAfter(true);
                    animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            isFinishAnimation = false;
                        }
                        @Override
                        public void onAnimationEnd(Animation animation) {
                        isFinishAnimation = true;
                            //清除动画
                            mChildView.clearAnimation();
                            //重新布局
                            mChildView.layout(normal.left,normal.top,normal.right,normal.bottom);
                            //清除normal的数据
                            normal.setEmpty();
                        }
                        @Override
                        public void onAnimationRepeat(Animation animation) {}
                    });
                    //启动动画
                    mChildView.startAnimation(animation);
                }
                break;
            default:
                   break;
        }
        return super.onTouchEvent(ev);
    }
    //判断是否需要执行平移动画
    private boolean isNeedAnimation() {
        return !normal.isEmpty();
    }

    private boolean isNeedMove() {
        int childMeasuredHeight = mChildView.getMeasuredHeight();//获取子视图的高度
        int scrollViewMeasuredHeight = this.getMeasuredHeight();//获取布局的高度

        int dy = childMeasuredHeight - scrollViewMeasuredHeight;
        int scrollY = this.getScrollY();//获取用户在Y轴方向上的偏移量（上 +  下 -）
        if(scrollY <= 0 || scrollY >= dy){
            return true;//按照我们自定义的MyScrollView的方式处理
        }
        //其他处在临界范围内的，返回false。即表示，仍按照ScrollView的处理方式
        return false;
    }
}
