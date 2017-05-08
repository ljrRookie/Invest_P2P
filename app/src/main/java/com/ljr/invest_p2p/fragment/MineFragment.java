package com.ljr.invest_p2p.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;

import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.activity.BarChartActivity;
import com.ljr.invest_p2p.activity.CZActivity;
import com.ljr.invest_p2p.activity.LineChartActivity;
import com.ljr.invest_p2p.activity.LoginActivity;
import com.ljr.invest_p2p.activity.PieChartActivity;
import com.ljr.invest_p2p.activity.TXActivity;
import com.ljr.invest_p2p.activity.UserInfoActivity;
import com.ljr.invest_p2p.bean.User;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.util.BitmapUtils;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by LinJiaRong on 2017/4/10.
 * TODO：我的页面
 */

public class MineFragment extends BaseFragment {

    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.iv_me_icon)
    ImageView mIvMeIcon;
    @Bind(R.id.rl_me_icon)
    RelativeLayout mRlMeIcon;
    @Bind(R.id.tv_me_name)
    TextView mTvMeName;
    @Bind(R.id.rl_me)
    RelativeLayout mRlMe;
    @Bind(R.id.recharge)
    ImageView mRecharge;
    @Bind(R.id.withdraw)
    ImageView mWithdraw;
    @Bind(R.id.ll_touzi)
    TextView mLlTouzi;
    @Bind(R.id.ll_touzi_zhiguan)
    TextView mLlTouziZhiguan;
    @Bind(R.id.ll_zichan)
    TextView mLlZichan;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.INVISIBLE);
        mTvTitle.setText(R.string.mine);
        mIvTitleSetting.setVisibility(View.VISIBLE);
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
        //1.判断是否已经登录
        isLogin();
    }

    private void isLogin() {
        //查看本地是否有用户的登录信息
        SharedPreferences sp = this.getActivity().getSharedPreferences("user_info", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        if (TextUtils.isEmpty(name)) {
            //本地无登录信息，弹出登录框
            Login();
        } else {
            //本地有登录信息，直接加载用户信息
            loadingUserInfo();
        }
    }

    private void Login() {
        new AlertDialog.Builder(this.getActivity())
                .setTitle("提示")
                .setIcon(R.drawable.ic_action_name)
                .setMessage("请进入登录页面")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.toast("进入登录页面", false);
                        ((BaseActivity) MineFragment.this.getActivity()).goToActivity(LoginActivity.class, null);
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void loadingUserInfo() {
        User user = ((BaseActivity) this.getActivity()).readUser();
        mTvMeName.setText(user.getName());
        boolean isExist = readImage();
        if(isExist){
            return;
        }
        Picasso.with(this.getContext()).load(user.getImageurl()).transform(new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {
                //图片压缩处理
                Bitmap bitmap = BitmapUtils.zoom(source, Utils.dp2px(63), Utils.dp2px(63));
                //图片圆形裁剪
                bitmap = BitmapUtils.circleBitmap(bitmap);
                //资源回收
                source.recycle();
                return bitmap;
            }

            @Override
            public String key() {
                //使用Picasso获取图片需要保证返回值不能为null，否则报错.
                return " ";
            }
        }).into(mIvMeIcon);
    }


    @OnClick({R.id.iv_title_setting, R.id.recharge, R.id.withdraw, R.id.ll_touzi, R.id.ll_touzi_zhiguan, R.id.ll_zichan})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_title_setting:
                //跳转到用户信息界面的Activity
                ((BaseActivity) this.getActivity()).goToActivity(UserInfoActivity.class, null);
                break;
            case R.id.recharge:
                ((BaseActivity) this.getActivity()).goToActivity(CZActivity.class, null);

                break;
            case R.id.withdraw:
                ((BaseActivity) this.getActivity()).goToActivity(TXActivity.class, null);

                break;
            case R.id.ll_touzi:
                ((BaseActivity)this.getActivity()).goToActivity(LineChartActivity.class,null);
                Utils.toast("投资管理", false);
                break;
            case R.id.ll_touzi_zhiguan:
                ((BaseActivity)this.getActivity()).goToActivity(BarChartActivity.class,null);
                Utils.toast("投资管理（直观）", false);
                break;
            case R.id.ll_zichan:
                ((BaseActivity)this.getActivity()).goToActivity(PieChartActivity.class,null);
                Utils.toast("资产管理", false);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        //读取本地保存的照片
        readImage();
    }

    private boolean readImage() {
        File filesDir;
        //判断SD卡是否可用
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //保存路径：storage/sdcard/Android/data/包名/files
            filesDir = this.getActivity().getExternalFilesDir("");
        } else {
            //手机内部存储 路径：data/data/包名/files
            filesDir = this.getActivity().getFilesDir();
        }
        File file = new File(filesDir, "icon.png");
        if (file.exists()) {
            //存储 --》 内存
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            mIvMeIcon.setImageBitmap(bitmap);
            return true;
        }
        return false;

    }

}
