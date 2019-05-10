package com.example.administrator.applicationtest2.view.test.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.adapter.PopupAdapter;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:hepeng
 * Date:2018-12-08
 * Description:
 */
@Route(path = "/custom/CustomControl")////添加路由注解 并且这里的路径需要注意的是至少需要有两级
public class CustomControl extends BaseClsActivity {
	private Button btn_time;
	private Button btn_test;
	private CustomRow row3;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_custom_control);
		row3 = findViewById(R.id.custom_row3);
		btn_time = findViewById(R.id.custom_btn_time);
		btn_test = findViewById(R.id.custom_btn_test);
		btn_time.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showChooseDateDialog();
			}
		});
		btn_test.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				List<PopupAdapter.PopupBean> mDatas = new ArrayList<>();
				for (int i = 0; i < 5; i++) {
					PopupAdapter.PopupBean bean = new PopupAdapter.PopupBean("测试"+i,"测试"+i);
					mDatas.add(bean);
				}
				DemoPopup demoPopup = new DemoPopup(CustomControl.this,mDatas);
				demoPopup.showPopupWindow(btn_test);
			}
		});
		ARouter.getInstance().inject(this);
//		String sName = getIntent().getStringExtra("name");//获取参数
//		btn_test.setText(sName);
	}

	public void showChooseDateDialog(){
		//时间选择器
		TimePickerView pvTime = new TimePickerBuilder(CustomControl.this, new OnTimeSelectListener() {
			@Override
			public void onTimeSelect(Date date, View v) {
				Toast.makeText(CustomControl.this, getTime(date), Toast.LENGTH_SHORT).show();
			}
		}).setType(new boolean[]{true, true, true, true, true, true})// 默认全部显示
//				.setCancelText("Cancel")//取消按钮文字
//				.setSubmitText("Sure")//确认按钮文字
				.setTitleSize(20)//标题文字大小
//				.setTitleText("Title")//标题文字
				.setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
				.isCyclic(true)//是否循环滚动
//				.setTitleColor(Color.BLACK)//标题文字颜色
//				.setSubmitColor(Color.BLUE)//确定按钮文字颜色
//				.setCancelColor(Color.BLUE)//取消按钮文字颜色
//				.setTitleBgColor(0xFF666666)//标题背景颜色 Night mode
//				.setBgColor(0xFF333333)//滚轮背景颜色 Night mode
//				.setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
//				.setRangDate(startDate,endDate)//起始终止年月日设定
				.setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
				.isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
//				.isDialog(true)//是否显示为对话框样式
				.build();
		pvTime.show();
	}

	private String getTime(Date date){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	private void show(){
		View contentView = LayoutInflater.from(CustomControl.this).inflate(R.layout.popup_window, null);
// 第三个参数是PopupWindow的高度，第四个参数指定PopupWindow能否获得焦点
		PopupWindow mPopWindow = new PopupWindow(contentView,
				ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//				PopupWindow mPopWindow=new PopupWindow(contentView, 100, 100, true);
		List list = new ArrayList();
		list.add("1");
		list.add("2");
		list.add("3");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(CustomControl.this, android.R.layout.simple_list_item_1, list);  ;
//		ListView listView = (ListView)ctx.findViewById(R.id.list_view);
		ListView listView = (ListView)contentView.findViewById(R.id.popup_list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		mPopWindow.setContentView(contentView);
		mPopWindow.showAsDropDown(btn_test, 0, 0);
	}

}
