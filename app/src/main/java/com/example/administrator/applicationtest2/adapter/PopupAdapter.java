package com.example.administrator.applicationtest2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;

import java.util.List;

/**
 * Author:hepeng
 * Date:2018-12-11
 * Description:
 */

public class PopupAdapter extends BaseAdapter {
	private List<PopupBean> mDatas;
	private LayoutInflater mInflater;
	private Context mContext;
	private int nSimpleCheck = -1;
	private  boolean bSelectMore =false;


	public PopupAdapter(Context context, List<PopupBean> mDatas,boolean bSelectMore) {
		mInflater = LayoutInflater.from(context);
		this.mContext = context;
		this.mDatas = mDatas;
		this.bSelectMore = bSelectMore;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	// 选中当前选项时，让其他选项不被选中
	public void select(int position) {
		if (!mDatas.get(position).isSelected()) {
			mDatas.get(position).setSelected(true);
			for (int i = 0; i < mDatas.size(); i++) {
				if (i != position) {
					mDatas.get(i).setSelected(false);
				}
			}
		}
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null)
		{
			convertView = mInflater.inflate(R.layout.popup_window_item, parent,
					false);
			viewHolder = new ViewHolder();
			viewHolder.mTextView = (TextView) convertView
					.findViewById(R.id.popup_item_tv_content);
			viewHolder.checkbox = (CheckBox) convertView
					.findViewById(R.id.popup_item_checkbox);
			convertView.setTag(viewHolder);
		} else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.mTextView.setText(mDatas.get(position).getsName());
		if(bSelectMore){
			viewHolder.checkbox.setVisibility(View.VISIBLE);
		}else{
			viewHolder.checkbox.setVisibility(View.GONE);
		}
		viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mDatas.get(position).setSelected(isChecked);
			}
		});
		return convertView;

	}

	private final class ViewHolder
	{
		TextView mTextView;
		CheckBox checkbox;

	}

	public static class PopupBean{
		private String sName;
		private String sValue;
		private boolean Selected = false;

		public PopupBean(String sName, String sValue) {
			this.sName = sName;
			this.sValue = sValue;
		}

		public boolean isSelected() {
			return Selected;
		}

		public void setSelected(boolean selected) {
			Selected = selected;
		}

		public String getsName() {
			return sName;
		}

		public void setsName(String sName) {
			this.sName = sName;
		}

		public String getsValue() {
			return sValue;
		}

		public void setsValue(String sValue) {
			this.sValue = sValue;
		}
	}
}
