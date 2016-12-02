package com.example.administrator.applicationtest2.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.baseClass.BaseClsActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-02.
 */
public class TestActivity extends BaseClsActivity {
    @BindView(R.id.listview_items)
    ListView listView;
    private BaseAdapter adapter;
    private List<String> mDatas = new ArrayList<String>(Arrays.asList("Hello",
            "World", "Welcome"));
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.test_listview);
        ButterKnife.bind(this);
        listView.setAdapter(adapter = new MyAdapter(TestActivity.this,mDatas));
        adapter.notifyDataSetChanged();
    }
}
