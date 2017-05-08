package com.ljr.invest_p2p.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.ljr.invest_p2p.R;
import com.ljr.invest_p2p.bean.HomeBean;
import com.ljr.invest_p2p.ui.RoundProgress;
import com.ljr.invest_p2p.util.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public class ProductAdapter extends BaseAdapter {
    private List<HomeBean.ProInfoBean> mProInfoBeen;


    public ProductAdapter(List<HomeBean.ProInfoBean> productList) {
        this.mProInfoBeen = productList;
    }

    @Override
    public int getCount() {
        return mProInfoBeen == null ? 0 : mProInfoBeen.size();
    }

    @Override
    public Object getItem(int position) {
        return mProInfoBeen.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int itemViewType = getItemViewType(position);
        if(itemViewType == 0){
            TextView textView = new TextView(parent.getContext());
            textView.setText("RookieLjr........");
            textView.setTextColor(Utils.getColor(R.color.text_progress));
            textView.setTextSize(Utils.dp2px(20));
            return textView;
        }
        if(position > 3){
            position--;
        }
         ViewHolder mHolder;
        if (convertView == null) {
            convertView  = View.inflate(parent.getContext(), R.layout.item_product_list, null);
            mHolder = new ViewHolder(convertView);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        HomeBean.ProInfoBean proInfoBean = mProInfoBeen.get(position);
        mHolder.mName.setText(proInfoBean.name);
        mHolder.mMoney.setText(proInfoBean.money);
        mHolder.mMinnum.setText(proInfoBean.memberNum);
        mHolder.mMinzouzi.setText(proInfoBean.minTouMoney);
        mHolder.mYearlv.setText(proInfoBean.yearRate);
        mHolder.mSuodingdays.setText(proInfoBean.suodingDays);
        mHolder.mProgresss.setProgress(Integer.parseInt(proInfoBean.progress));

        return convertView;
    }

    /**
     * 在不同的position位置上显示具体的item的type的值
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position == 3) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    static class ViewHolder {
        @Bind(R.id.p_name)
        TextView mName;
        @Bind(R.id.p_money)
        TextView mMoney;
        @Bind(R.id.p_yearlv)
        TextView mYearlv;
        @Bind(R.id.p_suodingdays)
        TextView mSuodingdays;
        @Bind(R.id.p_minzouzi)
        TextView mMinzouzi;
        @Bind(R.id.p_minnum)
        TextView mMinnum;
        @Bind(R.id.p_progresss)
        RoundProgress mProgresss;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
