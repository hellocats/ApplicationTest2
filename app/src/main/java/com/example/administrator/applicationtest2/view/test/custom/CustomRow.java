package com.example.administrator.applicationtest2.view.test.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.adapter.PopupAdapter;
import com.example.administrator.applicationtest2.utils.PopupWindowUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:hepeng
 * Date:2018-12-08
 * Description:
 */

public class CustomRow  extends LinearLayout {
	private TextView tv_title;
	private  EditText edt_content;
	private ImageView iv_detail;
	private View line;
	private int lineColor;
	public CustomRow(final Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.custom_row, this, true);
		this.setOrientation(VERTICAL);
		lineColor = Color.GRAY;
		tv_title = (TextView) findViewById(R.id.custom_tv_title);
		edt_content = (EditText) findViewById(R.id.custom_edt_content);
		iv_detail = (ImageView) findViewById(R.id.custom_iv_detail);
		line = (View) findViewById(R.id.custom_line);
		TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CustomRow);
		if (attributes != null) {
			try {
				//处理背景色
				int titleBarBackGround = attributes.getColor(R.styleable.CustomRow_background_color, Color.WHITE);
				setBackgroundColor(titleBarBackGround);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//处理line色
			lineColor = attributes.getColor(R.styleable.CustomRow_line_color, Color.GRAY);
			line.setBackgroundColor(lineColor);
			//获取是否要显示详情按钮
			boolean imgVisible = attributes.getBoolean(R.styleable.CustomRow_img_detail_visible, false);
			if (imgVisible) {
				iv_detail.setVisibility(View.VISIBLE);
			} else {
				iv_detail.setVisibility(View.INVISIBLE);
			}
			//设置标题的文字
			String titleText = attributes.getString(R.styleable.CustomRow_title_text);
			if (!TextUtils.isEmpty(titleText)) {
				tv_title.setText(titleText);
				//设置左边按钮文字颜色
				int titleTextColor = attributes.getColor(R.styleable.CustomRow_title_text_color, Color.BLACK);
				tv_title.setTextColor(titleTextColor);
			}
			int titleWidth = attributes.getInteger(R.styleable.CustomRow_title_width,0);
			if(titleWidth>0){
				tv_title.setWidth(titleWidth);
			}
			//设置内容的文本
			String contentText = attributes.getString(R.styleable.CustomRow_content_text);
			if (!TextUtils.isEmpty(contentText)) {
				edt_content.setText(contentText);
				//设置左边按钮文字颜色
				int contentTextColor = attributes.getColor(R.styleable.CustomRow_content_text_color, Color.BLACK);
				edt_content.setTextColor(contentTextColor);
			}
			String contentHint = attributes.getString(R.styleable.CustomRow_content_hint);
			if(!TextUtils.isEmpty(contentHint)){
				edt_content.setHint(contentHint);
			}
			//设置内容的文本是否可编辑
			boolean contentEnable = attributes.getBoolean(R.styleable.CustomRow_content_enable, true);
			edt_content.setEnabled(contentEnable);

		}
		edt_content.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (hasFocus) {// 获得焦点
					line.setBackgroundColor(Color.parseColor("#3BB715"));
				} else {// 失去焦点
					line.setBackgroundColor(lineColor);
				}
			}
		});

		iv_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<PopupAdapter.PopupBean> mDatas = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					PopupAdapter.PopupBean bean = new PopupAdapter.PopupBean("测试123213123123"+i,"测试"+i);
					mDatas.add(bean);
				}
				PopupWindowUtils.showWindow(context, mDatas, iv_detail,true, new PopupWindowUtils.PopupClick() {
					@Override
					public boolean ok(PopupWindow mPopWindow,String sResult,String sValue) {
						edt_content.setText(sResult);
						return true;
					}
				});
			}
		});
		tv_title.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<PopupAdapter.PopupBean> mDatas = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					PopupAdapter.PopupBean bean = new PopupAdapter.PopupBean("测试"+i,"测试"+i);
					mDatas.add(bean);
				}
				PopupWindowUtils.showWindow(context, mDatas, tv_title,false, new PopupWindowUtils.PopupClick() {
					@Override
					public boolean ok(PopupWindow mPopWindow,String sResult,String sValue) {
						edt_content.setText(sResult);
						return true;
					}
				});
			}
		});
	}


	/**
	 * 获取标题控件
	 */
	public TextView getTitleView() {
		return tv_title;
	}
	/**
	 * 获取文本控件
	 */
	public EditText getContentView() {
		return edt_content;
	}

	/**
	 * 获取详情图片
	 */
	public ImageView getImageDetail() {
		return iv_detail;
	}

	/**
	 * 获取下划线
	 */
	public View getLineView() {
		return line;
	}


}
