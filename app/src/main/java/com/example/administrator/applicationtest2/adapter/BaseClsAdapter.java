package com.example.administrator.applicationtest2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-12-02.
 */
public abstract class BaseClsAdapter<T>  extends BaseAdapter {

    protected Context mContext;//定义成protected,用来给子类访问
    protected List<T> mList;
    protected final int mItemLayoutId;
    protected LayoutInflater mInflater;

    public BaseClsAdapter(Context context, List<T> list, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = list;
        this.mItemLayoutId = itemLayoutId;
    }

    protected View.OnClickListener onClickListener;//定义成protected，让子类重写

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position < mList.size()) {
            return mList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = getViewHolder(position, convertView,
                parent);
        convert(viewHolder, getItem(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(ViewHolder helper, T item);

    private ViewHolder getViewHolder(int position, View convertView,
                                     ViewGroup parent)
    {
        return ViewHolder.get(mContext, convertView, parent, mItemLayoutId,
                position);
    }
//    /**
//     * 添加数据，
//     *
//     * @param aList
//     */
//    public void addData(List<T> aList) {
//        if (aList != null && aList.size() > 0) {
//            mList.addAll(aList);
//        }
//        notifyDataSetChanged();
//    }
//
//    /**
//     * 获取数据
//     *
//     * @return
//     */
//    public List<T> getData() {
//        return mList;
//    }
//
//    /**
//     * 设置数据，
//     *
//     * @param sList
//     */
//    public void setData(List<T> sList) {
//        mList.clear();
//        addData(sList);
//    }
}
