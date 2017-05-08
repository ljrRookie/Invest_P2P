package com.ljr.invest_p2p.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.bean.HomeBean;
import com.ljr.invest_p2p.common.AppNetConfig;
import com.ljr.invest_p2p.common.BaseFragment;
import com.ljr.invest_p2p.ui.RoundProgress;
import com.loopj.android.http.RequestParams;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoaderInterface;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by LinJiaRong on 2017/4/10.
 * TODO：主页
 */

public class HomeFragment extends BaseFragment {
    private static final String TAG = "HomeFragment";

    @Bind(R.id.iv_title_back)
    ImageView mTitleBack;
    @Bind(R.id.tv_title)
    TextView mTitle;
    @Bind(R.id.iv_title_setting)
    ImageView mTitleSetting;
    @Bind(R.id.tv_home_yearrate)
    TextView mHomeYearrate;
    @Bind(R.id.banner)
    Banner mBanner;
    @Bind(R.id.tv_home_product)
    TextView mHomeProduct;
    @Bind(R.id.roundPro_home)
    RoundProgress mRoundProHome;
    /**
     * 方式一：ViewPager+indicator
     * 注释为第一种显示图片轮播方式
     */
    // @Bind(R.id.indicator)
    // CircleIndicator mIndicator;
    // @Bind(R.id.vp_home)
    // ViewPager mHome;

    private HomeBean mHomeBean;
    private List<HomeBean.ImageArrBean> mImageArrBeen;
    private HomeBean.ProInfoBean mProInfoBean;
    private int mCurrentProress;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    protected void initTitle() {
        mTitleBack.setVisibility(View.GONE);
        mTitle.setText(R.string.home);
        mTitleSetting.setVisibility(View.GONE);
    }

    @Override
    protected String getUrl() {
        return AppNetConfig.INDEX;
    }

    //访问网络请求参数
    @Override
    protected RequestParams getParams() {
        return null;
    }

    @Override
    protected void initData(String content) {
        if(!TextUtils.isEmpty(content)){
            Log.d(TAG, "onSuccess: " + content);
            //解析json
            JSONObject jsonObject = JSON.parseObject(content);
            if(!TextUtils.isEmpty(content)){
                String proInfo = jsonObject.getString("proInfo");
                mProInfoBean = JSON.parseObject(proInfo, HomeBean.ProInfoBean.class);
                String imageArr = jsonObject.getString("imageArr");
                mImageArrBeen = jsonObject.parseArray(imageArr, HomeBean.ImageArrBean.class);

                //更新Home页面数据
                mHomeProduct.setText(mProInfoBean.name);
                mHomeYearrate.setText(mProInfoBean.yearRate);
                mCurrentProress = Integer.parseInt(mProInfoBean.progress);
                //在分线程中，实现进度的动态变化
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mRoundProHome.setMax(100);
                        for (int i = 1; i <= mCurrentProress; i++) {
                            mRoundProHome.setProgress(i);
                            SystemClock.sleep(10);
                            //强制重绘
                            // mRoundProHome.invalidate();  只有主线程才可以如此调用
                            mRoundProHome.postInvalidate(); //主线程，分线程都可以调用
                        }
                    }
                }).start();
                //********************方式一：************
                //设置ViewPager
                // mHome.setAdapter(new MyAdapter());
                //设置小圆圈
                // mIndicator.setViewPager(mHome);

                /**
                 * 方式二：Banner
                 */
                //设置Banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
                //图片地址
                ArrayList<String> imageURLs = new ArrayList<>();
                for (int i = 0; i < mImageArrBeen.size(); i++) {
                    imageURLs.add(mImageArrBeen.get(i).IMAURL);
                }
                mBanner.setImages(imageURLs);
                //设置Banner动画效果
                mBanner.setBannerAnimation(Transformer.DepthPage);
                //设置标题集合（前提：Banner有设置显示标题）
                String[] titles = new String[]{"自然美女", "校园美女", "雪山美女", "汽车女郎，爱不单行"};
                mBanner.setBannerTitles(Arrays.asList(titles));
                //设置图片加载器
                mBanner.setImageLoader(new GlideImageLoader());
                //设置为自动轮播
                mBanner.setDelayTime(1500);
                //设置指示器位置
                mBanner.setIndicatorGravity(BannerConfig.CENTER);
                mBanner.start();
            }

        }

    }


    public class GlideImageLoader implements ImageLoaderInterface {
        @Override
        public View createImageView(Context context) {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            return imageView;
        }

        @Override
        public void displayImage(Context context, Object path, View imageView) {
            Glide.with(context).load(path).into((ImageView) imageView);
        }
    }
//***************第一种方式：*****************
   /* class MyAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return mImageArrBeen == null ? 0 : mImageArrBeen.size();
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getActivity());
            String imaurl = mImageArrBeen.get(position).IMAURL;
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //使用Glide加载网络图片
            Glide.with(getActivity()).load(imaurl).into(imageView);
            container.addView(imageView);
            return imageView;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }
    }
*/
}
