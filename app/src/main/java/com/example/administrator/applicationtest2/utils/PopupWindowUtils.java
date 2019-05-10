package com.example.administrator.applicationtest2.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.adapter.PopupAdapter;
import com.example.administrator.applicationtest2.common.ScreenUtils;

import java.util.List;

/**
 * Author:hepeng
 * Date:2018-12-08
 * Description:
 */

public class PopupWindowUtils {
	public interface PopupClick{
		/**
		 * @param mPopWindow
		 * @return 弹出框是否消失
		 */
		boolean ok(PopupWindow mPopWindow,String sResult,String sValue);
	}
	/**
	 * @param ctx
	 * @param mDatas 弹出选择内容
	 * @param view	在哪个控件附件
	 */
	public static void showWindow(Context ctx, final List<PopupAdapter.PopupBean> mDatas , View view, final boolean bSelectMore, final PopupClick click){
		View contentView =  LayoutInflater.from(ctx).inflate(R.layout.popup_window, null);
//		contentView.setBackgroundResource(R.drawable.common_radius);
		// 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
		final PopupWindow mPopWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
		ListView listView = (ListView)contentView.findViewById(R.id.popup_list);
		TextView tv_cancel = (TextView)contentView.findViewById(R.id.popup_tv_cancel);
		TextView tv_sure = (TextView)contentView.findViewById(R.id.popup_tv_sure);
		LinearLayout lay_bottom = (LinearLayout)contentView.findViewById(R.id.popup_lay_bottom);
		if(!bSelectMore){
			tv_sure.setVisibility(View.GONE);
			tv_cancel.setVisibility(View.GONE);
			lay_bottom.setVisibility(View.GONE);
		}
		final PopupAdapter adapter = new PopupAdapter(ctx,mDatas,bSelectMore);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if(!bSelectMore){
					boolean isDismiss = click.ok(mPopWindow,mDatas.get(position).getsName(),mDatas.get(position).getsValue());
					if(isDismiss){
						mPopWindow.dismiss();
					}
				}
			}
		});

		listView.getLayoutParams().width = (int) (getWidestView(ctx, adapter)*1.05);
		tv_sure.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				String sResult = "";
				String sValue = "";
				for (int i = 0; i < mDatas.size(); i++) {
					PopupAdapter.PopupBean bean = mDatas.get(i);
					if(bean.isSelected()){
						sResult+=","+bean.getsName();
						sValue+=","+bean.getsValue();
					}
				}
				sResult = sResult.replaceFirst(",","");
				sValue = sValue.replaceFirst(",","");
				boolean isDismiss = click.ok(mPopWindow,sResult,sValue);
				if(isDismiss){
					mPopWindow.dismiss();
				}
			}
		});
		tv_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mPopWindow.dismiss();
			}
		});

		int windowPos[] = calculatePopWindowPos(view, contentView);
//		int xOff = 20;// 可以自己调整偏移
//		windowPos[0] -= xOff;
		mPopWindow.showAtLocation(view, Gravity.TOP | Gravity.START, windowPos[0], windowPos[1]);

	}

	/**
	 * 计算listview最长宽度
	 * @param context
	 * @param adapter
	 * @return
	 */
	public static int getWidestView(Context context, Adapter adapter) {
		int maxWidth = 0;
		View view = null;
		FrameLayout fakeParent = new FrameLayout(context);
		for (int i=0, count=adapter.getCount(); i<count; i++) {
			view = adapter.getView(i, view, fakeParent);
			view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
			int width = view.getMeasuredWidth();
			if (width > maxWidth) {
				maxWidth = width;
			}
		}
		return maxWidth;
	}
	/**
	 * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
	 * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
	 * @param anchorView  呼出window的view
	 * @param contentView   window的内容布局
	 * @return window显示的左上角的xOff,yOff坐标
	 */
	private static int[] calculatePopWindowPos(final View anchorView, final View contentView) {
		final int windowPos[] = new int[2];
		final int anchorLoc[] = new int[2];
		// 获取锚点View在屏幕上的左上角坐标位置
		anchorView.getLocationOnScreen(anchorLoc);
		final int anchorHeight = anchorView.getHeight();
		final int anchorWidth= anchorView.getWidth();
		// 获取屏幕的高宽
		final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
		final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
		contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
		// 计算contentView的高宽
		final int windowHeight = contentView.getMeasuredHeight();
		final int windowWidth = contentView.getMeasuredWidth();
		// 判断需要向上弹出还是向下弹出显示
		final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
//		if (isNeedShowUp) {
//			windowPos[0] = screenWidth - windowWidth;
//			windowPos[1] = anchorLoc[1] - windowHeight;
//		} else {
//			windowPos[0] = screenWidth - windowWidth;
//			windowPos[1] = anchorLoc[1] + anchorHeight;
//		}
		if (isNeedShowUp) {
			windowPos[1] = anchorLoc[1] - windowHeight;
		} else {
			windowPos[1] = anchorLoc[1] + anchorHeight;
		}
		// 判断需要向左弹出还是向右弹出显示
		final boolean isNeedShowLeft = (screenWidth - anchorLoc[0] - anchorWidth < windowWidth);
		if(isNeedShowLeft){
			windowPos[0] = anchorLoc[0] - windowWidth;
		}else{
			windowPos[0] = anchorLoc[0] + anchorWidth;
		}
		return windowPos;
	}
}
