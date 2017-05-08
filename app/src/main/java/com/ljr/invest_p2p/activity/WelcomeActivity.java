package com.ljr.invest_p2p.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.bean.UpdateInfo;
import com.ljr.invest_p2p.common.ActivityManager;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.util.Utils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import butterknife.Bind;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";
    @Bind(R.id.iv_welcome_icon)
    ImageView mIvWelcomeIcon;
    @Bind(R.id.rl_welcome)
    RelativeLayout mRlWelcome;
    @Bind(R.id.version)
    TextView mVersion;
    private long mStartTime;
    private static final int TO_MAIN = 1;
    private static final int DOWNLOAD_VERSION_SUCCESS = 2;
    private static final int DOWNLOAD_APK_FAIL = 3;
    private static final int DOWNLOAD_APK_SUCCESS = 4;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TO_MAIN:
                    finish();
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                    break;
                case DOWNLOAD_VERSION_SUCCESS:
                    String version = getVersion();
                    mVersion.setText(version);
                    //比较服务器获取的最新版本跟目前应用的版本是否一致
                    if(version.equals(mUpdateInfo.version)){
                        Utils.toast("目前为最新版本",false);
                        toMain();
                    }else{
                        new AlertDialog.Builder(WelcomeActivity.this)
                                .setIcon(R.drawable.ic_action_name)
                                .setTitle("下载最新版本")
                                .setMessage(mUpdateInfo.desc)
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //下载服务器数据
                                        downlodeApk();
                                    }
                                })
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        toMain();
                                    }
                                }).show();
                    }
                    break;
                case DOWNLOAD_APK_SUCCESS:
                    Utils.toast("下载成功，准备安装",false);
                    mDialog.dismiss();
                    //安装
                    installApk();
                    finish();
                    break;
                case DOWNLOAD_APK_FAIL:
                   Utils.toast("联网下载数据失败",false);
                    toMain();
                    break;
            }
        }
    };
    private File mApkFile;

    private void installApk() {
        Intent intent = new Intent("android.intent.action.INSTALL_PACKAGE");
        intent.setData(Uri.parse("file:" + mApkFile.getAbsolutePath()));
        Log.d(TAG, "installApk: "+mApkFile.getAbsolutePath());
        startActivity(intent);


    }

    private ProgressDialog mDialog;

    private void downlodeApk() {
        //初始化水平进度条的dialog
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mDialog.setCancelable(false);
        mDialog.show();
        //初始化数据要保持的位置
        File filesDir;
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            filesDir = this.getExternalFilesDir("");
        }else{
            filesDir = this.getFilesDir();
        }
        mApkFile = new File(filesDir, "update.apk");
        //启动一个分线程联网下载数据
        new Thread(){
            @Override
            public void run() {
                String apkUrl = mUpdateInfo.apkUrl;
                InputStream is = null;
                FileOutputStream fos = null;
                HttpURLConnection conn = null;
                try {
                    URL url = new URL(apkUrl);
                     conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    conn.connect();

                    if(conn.getResponseCode()==200){
                        mDialog.setMax(conn.getContentLength());
                         is = conn.getInputStream();
                         fos = new FileOutputStream(mApkFile);
                        byte[] buffer = new byte[1024];
                        int len;
                        while((len = is.read(buffer)) != -1){
                            //更新dialog的进度
                            mDialog.incrementProgressBy(len);
                            fos.write(buffer,0,len);
                            SystemClock.sleep(1);
                        }
                        mHandler.sendEmptyMessage(DOWNLOAD_APK_SUCCESS);
                    }else{
                        mHandler.sendEmptyMessage(DOWNLOAD_APK_FAIL);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    if(conn != null){
                        conn.disconnect();
                    }
                    if(is !=null){
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if(fos != null){
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.start();
    }

    private UpdateInfo mUpdateInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        //将当前的activity添加到ActivityManager中
        ActivityManager.getInstance().add(this);
        //启动动画
        initAnimation();
        //联网检查更新应用
        updateApkFile();
    }

    private void updateApkFile() {
        //获取系统当前时间
        mStartTime = System.currentTimeMillis();
        //判断是否可以联网
        boolean connect = isConnect();
        if (!connect) {
            //没有移动网络
            Utils.toast("无法连接网络，检测更新失败！", false);
            toMain();
        } else {
            //联网获取服务器的最新版本数据
            AsyncHttpClient client = new AsyncHttpClient();
            String url = AppNetConfig.UPDATE;
            client.post(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(String content) {
                    //解析json数据
                    mUpdateInfo = JSON.parseObject(content, UpdateInfo.class);
                    mHandler.sendEmptyMessage(DOWNLOAD_VERSION_SUCCESS);
                }

                @Override
                public void onFailure(Throwable error, String content) {
                    Utils.toast("无法连接网络，检测更新失败！", false);
                    toMain();
                }
            });

        }

    }

    private void toMain() {
        long currentTimeMillis = System.currentTimeMillis();
        long delayTime = 3000 - (currentTimeMillis - mStartTime);
        if (delayTime < 0) {
            delayTime = 0;
        }
        mHandler.sendEmptyMessage(TO_MAIN);
    }

    public boolean isConnect() {
        boolean connected = false;
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null) {
            connected = networkInfo.isConnected();
        }
        return connected;
    }

    private String getVersion() {
        String version = "未知版本";
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }

    private void initAnimation() {
        /**
         * target:代表动画执行在谁身上（找对象）
         * propertyName：动画类型（alpha：透明变化；tranlationX，translationY：平移动画；scale：缩放动画）
         * values：轨迹（选择起点和终点的变化）
         */
        ObjectAnimator alpha = ObjectAnimator.ofFloat(mRlWelcome, "alpha", 0f, 1f);
        alpha.setDuration(3000);
        alpha.start();
       /* alpha.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
                ActivityManager.getInstance().remove(WelcomeActivity.this);
            }
        });*/
    }
}
