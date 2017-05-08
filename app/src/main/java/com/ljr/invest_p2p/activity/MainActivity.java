package com.ljr.invest_p2p.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.fragment.HomeFragment;
import com.ljr.invest_p2p.fragment.InvestFragment;
import com.ljr.invest_p2p.fragment.MineFragment;
import com.ljr.invest_p2p.fragment.MoreFragment;
import com.ljr.invest_p2p.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    @Bind(R.id.fl_main)
    FrameLayout mFlMain;
    @Bind(R.id.iv_main_home)
    ImageView mIvMainHome;
    @Bind(R.id.tv_main_home)
    TextView mTvMainHome;
    @Bind(R.id.ll_main_home)
    LinearLayout mLlMainHome;
    @Bind(R.id.iv_main_invest)
    ImageView mIvMainInvest;
    @Bind(R.id.tv_main_invest)
    TextView mTvMainInvest;
    @Bind(R.id.ll_main_invest)
    LinearLayout mLlMainInvest;
    @Bind(R.id.iv_main_me)
    ImageView mIvMainMe;
    @Bind(R.id.tv_main_me)
    TextView mTvMainMe;
    @Bind(R.id.ll_main_me)
    LinearLayout mLlMainMe;
    @Bind(R.id.iv_main_more)
    ImageView mIvMainMore;
    @Bind(R.id.tv_main_more)
    TextView mTvMainMore;
    @Bind(R.id.ll_main_more)
    LinearLayout mLlMainMore;

    private FragmentTransaction mTransaction;
    private HomeFragment mHomeFragment;
    private InvestFragment mInvestFragment;
    private MineFragment mMineFragment;
    private MoreFragment mMoreFragment;

    private boolean isFrist;
    private static final int WHAT_RESET_BACK = 1;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_RESET_BACK:
                    isFrist = true;
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        select(0);
        //模拟异常
       /* String str = null;
        try {
            if (str.equals("abc")) {
                Log.e("TAG", "abc");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void initTitle() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    //显示相应的Fragment
    private void select(int i) {
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        mTransaction = fragmentManager.beginTransaction();
        //先隐藏所有的Fragment
        //然后再重置ImageView TextView的显示状态
        hideAndResetFragement();
        switch (i) {
            case 0:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    mTransaction.add(R.id.fl_main, mHomeFragment);
                }
                mTransaction.show(mHomeFragment);
                //更改显示状态
                mIvMainHome.setImageResource(R.drawable.bottom02);
                mTvMainHome.setTextColor(Utils.getColor(R.color.home_back_selected));
                break;
            case 1:
                if (mInvestFragment == null) {
                    mInvestFragment = new InvestFragment();
                    mTransaction.add(R.id.fl_main, mInvestFragment);
                }
                mTransaction.show(mInvestFragment);
                //更改显示状态
                mIvMainInvest.setImageResource(R.drawable.bottom04);
                mTvMainInvest.setTextColor(Utils.getColor(R.color.home_back_selected));
                break;
            case 2:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                    mTransaction.add(R.id.fl_main, mMineFragment);
                }
                mTransaction.show(mMineFragment);
                //更改显示状态
                mIvMainMe.setImageResource(R.drawable.bottom06);
                mTvMainMe.setTextColor(Utils.getColor(R.color.home_back_selected01));
                break;
            case 3:
                if (mMoreFragment == null) {
                    mMoreFragment = new MoreFragment();
                    mTransaction.add(R.id.fl_main, mMoreFragment);
                }
                mTransaction.show(mMoreFragment);
                //更改显示状态
                mIvMainMore.setImageResource(R.drawable.bottom08);
                mTvMainMore.setTextColor(Utils.getColor(R.color.home_back_selected));
                break;
        }
        mTransaction.commit();
    }


    /**
     * 隐藏Fragment并重置ImageView,TextView显示状态
     */
    private void hideAndResetFragement() {
        if (mHomeFragment != null) {
            mTransaction.hide(mHomeFragment);
        }
        if (mInvestFragment != null) {
            mTransaction.hide(mInvestFragment);
        }
        if (mMineFragment != null) {
            mTransaction.hide(mMineFragment);
        }
        if (mMoreFragment != null) {
            mTransaction.hide(mMoreFragment);
        }
        mIvMainHome.setImageResource(R.drawable.bottom01);
        mIvMainInvest.setImageResource(R.drawable.bottom03);
        mIvMainMe.setImageResource(R.drawable.bottom05);
        mIvMainMore.setImageResource(R.drawable.bottom07);

        mTvMainHome.setTextColor(Utils.getColor(R.color.home_back_unselected));
        mTvMainInvest.setTextColor(Utils.getColor(R.color.home_back_unselected));
        mTvMainMe.setTextColor(Utils.getColor(R.color.home_back_unselected));
        mTvMainMore.setTextColor(Utils.getColor(R.color.home_back_unselected));
    }

    @OnClick({R.id.ll_main_home, R.id.ll_main_invest, R.id.ll_main_me, R.id.ll_main_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_main_home:
                select(0);
                break;
            case R.id.ll_main_invest:
                select(1);
                break;
            case R.id.ll_main_me:
                select(2);
                break;
            case R.id.ll_main_more:
                select(3);
                break;
        }
    }

    //重写onKeyUp()，实现连续两次点击方可退出当前应用
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && isFrist) {
            Toast.makeText(this, "两秒内再点一次，即可退出", Toast.LENGTH_SHORT).show();
            isFrist = false;
            mHandler.sendEmptyMessageDelayed(WHAT_RESET_BACK, 2000);
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    //为了防止内存泄漏，需要在onDestroy()中，移除所有未执行的消息
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //移除所有未被执行的消息
        mHandler.removeCallbacksAndMessages(null);
    }
}
