package com.example.administrator.applicationtest2.view.test.linechart;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.common.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.widget.LineChartView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Author:hepeng
 * Date:2018-11-15
 * Description:
 */

public class LineChartActivity extends BaseClsActivity {
	@BindView(R.id.lineChartView)
	LineChartView lineChartView;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_chart);
		lineChartView.setData(getdata());
	}
	public List getdata() {
		List numble = new ArrayList();
		numble.add(74.0F);
		numble.add(65.0F);
		numble.add(23.0F);
		numble.add(34.0F);
		numble.add(12.0F);
		numble.add(8.0F);
		numble.add(56.0F);
		numble.add(23.0F);
		numble.add(27.0F);
		numble.add(78.0F);
		numble.add(67.0F);
		numble.add(45.0F);
		numble.add(98.0F);
		numble.add(2.0F);
		numble.add(8.0F);
		return numble;
	}
}
