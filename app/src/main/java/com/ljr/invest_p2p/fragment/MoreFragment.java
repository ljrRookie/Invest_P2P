package com.ljr.invest_p2p.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.activity.GestureEditActivity;
import com.ljr.invest_p2p.activity.GestureVerifyActivity;
import com.ljr.invest_p2p.activity.RegistActivity;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.common.BaseActivity;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by LinJiaRong on 2017/4/10.
 * TODO：
 */

public class MoreFragment extends BaseFragment {

    @Bind(R.id.iv_title_back)
    ImageView mIvTitleBack;
    @Bind(R.id.tv_title)
    TextView mTvTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mIvTitleSetting;
    @Bind(R.id.tv_regist)
    TextView mTvRegist;
    @Bind(R.id.toggle)
    ToggleButton mToggle;
    @Bind(R.id.tv_reset_gesture)
    TextView mTvResetGesture;
    @Bind(R.id.tv_phone)
    TextView mTvPhone;
    @Bind(R.id.tv_fk)
    TextView mTvFk;
    @Bind(R.id.tv_share)
    TextView mTvShare;
    @Bind(R.id.tv_about)
    TextView mTvAbout;
    @Bind(R.id.rl_contact)
    RelativeLayout mRlContact;
    private SharedPreferences mSp;
    private String department;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initTitle() {
        mIvTitleBack.setVisibility(View.GONE);
        mTvTitle.setText(R.string.more);
        mIvTitleSetting.setVisibility(View.GONE);
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
        //初始化sp，记录是否设置手势密码
        mSp = this.getActivity().getSharedPreferences("secret_protect", Context.MODE_PRIVATE);
        //查看是否设置了手势密码
        getGestureStatus();
        //设置手势密码
        setGesturePwd();
    }

    private void getGestureStatus() {
        boolean isOpen = mSp.getBoolean("isOpen", false);
        mToggle.setChecked(isOpen);

    }

    private void setGesturePwd() {
        mToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Utils.toast("开启手势密码", false);
                    mSp.edit().putBoolean("isOpen", true).commit();
                    String inputCode = mSp.getString("inputCode", "");
                    if (TextUtils.isEmpty(inputCode)) {
                        new AlertDialog.Builder(MoreFragment.this.getActivity())
                                .setTitle("设置手势密码")
                                .setMessage("是否现在设置手势密码")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mSp.edit().putBoolean("isOpen", true).commit();
                                        mToggle.setChecked(true);
                                        ((BaseActivity) MoreFragment.this.getActivity()).goToActivity(GestureVerifyActivity.class, null);

                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mSp.edit().putBoolean("isOpen", false).commit();
                                        mToggle.setChecked(false);
                                    }
                                }).show();
                    } else {
                        Utils.toast("开启手势密码", false);
                        mSp.edit().putBoolean("isOpen", true).commit();
                        mToggle.setChecked(true);
                    }
                } else {
                    Utils.toast("关闭手势密码！", false);
                    mSp.edit().putBoolean("isOpen", false).commit();
                    mToggle.setChecked(false);
                }
            }
        });
    }

    @OnClick({R.id.tv_regist, R.id.tv_reset_gesture, R.id.rl_contact, R.id.tv_fk, R.id.tv_share, R.id.tv_about})
    public void onClick(View view) {
        switch (view.getId()) {
            //跳转到注册页面
            case R.id.tv_regist:
                ((BaseActivity) MoreFragment.this.getActivity()).goToActivity(RegistActivity.class, null);
                break;
            //设置手势密码
            case R.id.tv_reset_gesture:
                boolean checked = mToggle.isChecked();
                if (checked) {
                    ((BaseActivity) MoreFragment.this.getActivity()).goToActivity(GestureEditActivity.class, null);
                }else{
                    Utils.toast("手势密码已关闭，请打开后重置密码",false);
                }
                break;
            case R.id.rl_contact:
                new AlertDialog.Builder(MoreFragment.this.getActivity())
                        .setTitle("联系客服")
                        .setIcon(R.drawable.ic_action_name)
                        .setMessage("是否现在联系客服？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phone = mTvPhone.getText().toString().trim();
                                //使用隐式意图，系统系统拨号应用界面
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + phone));
                                MoreFragment.this.getActivity().startActivity(intent
                                );
                            }
                        })
                        .setNegativeButton("取消", null).show();
                break;
            case R.id.tv_fk:
//提供一个View
                View dialogView = View.inflate(MoreFragment.this.getActivity(), R.layout.view_fk, null);
                final RadioGroup rg = (RadioGroup) dialogView.findViewById(R.id.rg_fankui);
                final EditText mFKContent = (EditText) dialogView.findViewById(R.id.et_fankui_content);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                        RadioButton rb = (RadioButton) rg.findViewById(checkedId);
                         department = rb.getText().toString();
                    }
                });
                new AlertDialog.Builder(MoreFragment.this.getActivity())
                        .setView(dialogView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String content = mFKContent.getText().toString().trim();
                                //联网发送反馈信息
                                AsyncHttpClient client = new AsyncHttpClient();
                                String url = AppNetConfig.FEEDBACK;
                                RequestParams params = new RequestParams();
                                params.put("department",department);
                                params.put("content",content);
                                client.post(url,params,new AsyncHttpResponseHandler(){
                                    @Override
                                    public void onSuccess(String content) {
                                        Utils.toast("发送成功！！",false);
                                    }

                                    @Override
                                    public void onFailure(Throwable error, String content) {
                                        Utils.toast("发送失败",false);
                                    }
                                });
                            }
                        }).setNegativeButton("取消",null)
                        .show();
                break;
            case R.id.tv_share:
                showShare();
                break;
            case R.id.tv_about:
                Utils.toast("关于RookieLjr理财，在心中！！！",false);
                break;
        }
    }

    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle(getString(R.string.app_name));
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("https://github.com/ljrRookie");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("世界上最遥远的距离，是我在if里你在else里，似乎一直相伴又永远分离；\\n\" +\n" +
                "        \"     世界上最痴心的等待，是我当case你是switch，或许永远都选不上自己；\\n\" +\n" +
                "        \"     世界上最真情的相依，是你在try我在catch。无论你发神马脾气，我都默默承受，静静处理。到那时，再来期待我们的finally。");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("https://github.com/ljrRookie");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("word天哪，说的太精辟了！");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this.getActivity());
    }


}
