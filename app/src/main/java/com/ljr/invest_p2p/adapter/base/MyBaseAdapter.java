package com.ljr.invest_p2p.adapter.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by LinJiaRong on 2017/4/24.
 * TODO：
 */

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    public List<T> mList;


    public MyBaseAdapter(List<T> list) {
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 将具体的集合数据装配到具体的一个item layout中
     * 1.item layout的布局是不确定的。
     * 2.将集合中指定位置的数据装配到item，是不确定的
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> mHolder;
        if (convertView == null) {
            mHolder = getHolder();
        } else {
            mHolder = (BaseHolder<T>) convertView.getTag();
        }
        //装配数据
        T t = mList.get(position);
        mHolder.setData(t);

        return mHolder.getRootView();
    }

    protected abstract BaseHolder<T> getHolder();


}
