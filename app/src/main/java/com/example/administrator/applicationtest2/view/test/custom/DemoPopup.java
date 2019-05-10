package com.example.administrator.applicationtest2.view.test.custom;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.adapter.PopupAdapter;

import java.util.List;

import razerdp.basepopup.BasePopupWindow;

/**
 * Author:hepeng
 * Date:2018-12-11
 * Description:
 */

public class DemoPopup extends BasePopupWindow {
	private Context mContext ;
	private List<PopupAdapter.PopupBean> mDatas;
	public DemoPopup(Context context,List<PopupAdapter.PopupBean> mDatas) {
		super(context);
		this.mContext = context;
		this.mDatas = mDatas;
	}

	// 必须实现，这里返回您的contentView
	// 为了让库更加准确的做出适配，强烈建议使用createPopupById()进行inflate
	@Override
	public View onCreateContentView() {
		View view =createPopupById(R.layout.popup_window);
//		final PopupAdapter adapter = new PopupAdapter(mContext,mDatas);
//		ListView listView = (ListView)view.findViewById(R.id.popup_list);
//		listView.setAdapter(adapter);
//		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//				mDatas.get(position).setSelected(!mDatas.get(position).isSelected());
//				adapter.notifyDataSetChanged();
//			}
//		});
		return view;
	}

	// 以下为可选代码（非必须实现）
	// 返回作用于PopupWindow的show和dismiss动画，本库提供了默认的几款动画，这里可以自由实现
	@Override
	protected Animation onCreateShowAnimation() {
		return getDefaultScaleAnimation(true);
	}

	@Override
	protected Animation onCreateDismissAnimation() {
		return getDefaultScaleAnimation(false);
	}
}
