package com.example.administrator.applicationtest2.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;

import java.util.List;

/**
 * Created by Administrator on 2016-12-02.
 */
public class MyAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;

    public MyAdapter(Context context, List<String> mDatas)
    {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount()
    {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
//            convertView = mInflater.inflate(R.layout.test_listview_item, parent, false);
            convertView = mInflater.inflate(R.layout.test_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView) convertView
                    .findViewById(R.id.test_listview_name);
            viewHolder.phone = (TextView) convertView
                    .findViewById(R.id.test_listview_phone);
            convertView.setTag(viewHolder);
        } else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.mTextView.setText(mDatas.get(position));
        viewHolder.phone.setText(mDatas.get(position));
        return convertView;
    }

    private final class ViewHolder
    {
        TextView mTextView;
        TextView phone;
    }
}
