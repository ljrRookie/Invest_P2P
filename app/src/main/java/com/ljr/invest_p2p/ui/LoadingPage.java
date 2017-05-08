package com.ljr.invest_p2p.ui;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.util.Util;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.net.URL;

/**
 * Created by LinJiaRong on 2017/4/17.
 * TODO：自定义加载布局
 */

public abstract class LoadingPage extends FrameLayout {
    //定义四种不同的显示状态
    private static final int STATE_LOADING = 1;
    private static final int STATE_ERROR = 2;
    private static final int STATE_EMPTY = 3;
    private static final int STATE_SUCCESS = 4;
    //默认情况，当前状态为正在加载
    private int state_current = STATE_LOADING;
    //四种不同的加载布局
    private View view_loading;
    private View view_error;
    private View view_empty;
    private View view_success;
    //布局参数
    private LayoutParams mParams;
    private ResultState mResultState;

    private Context mContext;

    public LoadingPage(@NonNull Context context) {
        this(context, null);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingPage(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    //动态获取成功加载后的ViewGroup
    public abstract int layoutId();

    private void init() {
        mParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (view_loading == null) {
            view_loading = Utils.getView(R.layout.page_loading);
            addView(view_loading, mParams);
        }
        if (view_error == null) {
            view_error = Utils.getView(R.layout.page_error);
            addView(view_error, mParams);
        }
        if (view_empty == null) {
            view_empty = Utils.getView(R.layout.page_empty);
            addView(view_empty, mParams);
        }

        showLoadingPage();
    }


    /**
     * 确保在主线程更新UI
     */
    private void showLoadingPage() {
        Utils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showPage();
            }
        });
    }

    private void showPage() {
        //根据当前state_current的值，决定显示哪个View
        view_loading.setVisibility(state_current == STATE_LOADING ? View.VISIBLE : View.INVISIBLE);
        view_error.setVisibility(state_current == STATE_ERROR ? View.VISIBLE : View.INVISIBLE);
        view_empty.setVisibility(state_current == STATE_EMPTY ? View.VISIBLE : View.INVISIBLE);
        if (view_success == null) {
             view_success = Utils.getView(layoutId()); // 加载布局使用的是Application
           // View view_success = View.inflate(mContext, layoutId(), null);//加载布局使用的是activity
            addView(view_success, mParams);
        }
        view_success.setVisibility(state_current == STATE_SUCCESS ? View.VISIBLE : View.INVISIBLE);

    }

    //提供枚举类，封装联网后已以后的状态值和数据
    public enum ResultState {
        ERROR(2), EMPTY(3), SUCCESS(4);

        int state;

        ResultState(int state) {
            this.state = state;
        }

        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }

    //提供联网的请求参数
    protected abstract RequestParams params();

    //提供联网的请求地址
    protected abstract String url();

    //在显示布局中实现联网加载数据
    public void show() {
        String url = url();
        if (TextUtils.isEmpty(url)) {
            mResultState = ResultState.SUCCESS;
            mResultState.setContent("");
            loadImage();//修改state_current，决定显示哪个状态
            return;
        }
        Utils.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(url(), params(), new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        if(TextUtils.isEmpty(content)){
                            mResultState = ResultState.EMPTY;
                            mResultState.setContent("");
                        }else{
                            mResultState = ResultState.SUCCESS;
                            mResultState.setContent(content);
                        }
                        loadImage();
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        mResultState = ResultState.ERROR;
                        mResultState.setContent("");
                        loadImage();
                    }
                });
            }
        }, 1000);
    }

    private void loadImage() {
        switch (mResultState) {
            case ERROR:
                state_current = STATE_ERROR;
                break;
            case EMPTY:
                state_current = STATE_EMPTY;
                break;
            case SUCCESS:
                state_current = STATE_SUCCESS;
                break;

        }
        //根据修改后的state_current更新视图的显示
        showLoadingPage();
        if(state_current == STATE_SUCCESS){
            onSuccess(mResultState,view_success);
        }
    }

    protected abstract void onSuccess(ResultState resultState, View view_success);
}
