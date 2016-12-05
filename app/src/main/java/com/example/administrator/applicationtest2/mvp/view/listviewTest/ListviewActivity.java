package com.example.administrator.applicationtest2.mvp.view.listviewTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.util.adapter.BaseClsAdapter;
import com.example.administrator.applicationtest2.util.adapter.ViewHolder;
import com.example.administrator.applicationtest2.util.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.bean.User;
import com.example.administrator.applicationtest2.util.pulltorefresh.PullListView;
import com.example.administrator.applicationtest2.util.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by hepeng on 2016-12-02.
 */
public class ListviewActivity extends BaseClsActivity implements
        PullToRefreshLayout.OnRefreshListener{
    @BindView(R.id.pullListView)
    PullListView mPullListView;
    @BindView(R.id.pullToRefreshLayout)
    PullToRefreshLayout mRefreshLayout;

//    @BindView(R.id.test_listview_items)
//    ListView listView;
    @BindView(R.id.test_listview_imgBack)
    ImageView imgBack;
    @BindView(R.id.test_listview_imgSearch)
    ImageView imgSearch;
    private User row;
    private BaseAdapter adapter;
    private List<User> lDatas = new ArrayList<User>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initController();
        setClickEvent();
        getData();
    }



    private void init() {
        setContentView(R.layout.listview_test);
        mRefreshLayout.setOnRefreshListener(this);
    }

    private void initController() {
        // 设置适配器
        mPullListView.setAdapter(adapter = new BaseClsAdapter<User>(
                ListviewActivity.this, lDatas, R.layout.listview_test_item)
        {
            @Override
            public void convert(ViewHolder helper, User item)
            {
                helper.setText(R.id.test_listview_name, item.getName());
                helper.setText(R.id.test_listview_phone, String.valueOf(item.getPhone()));
                helper.getView(R.id.test_listview_name).setOnClickListener(new ClickListener());
            }

        });
    }
    private void setClickEvent() {
        mPullListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    row = (User) mPullListView.getItemAtPosition(position);
                    showToast("姓名："+row.getName());
//                    listItemsClick(row);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgBack.setOnClickListener(new ClickListener());
        imgSearch.setOnClickListener(new ClickListener());
    }
    public void getData() {
        for (int i = 0; i < 10; i++) {
            User user =new User();
            user.setName("姓名"+i);
            user.setPhone(i);
            lDatas.add(user);
        }
    }

    @Override
    public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.refreshFinish(true);
                adapter.notifyDataSetChanged();
            }
        }, 1000); // 1秒后刷新
    }

    @Override
    public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
        mRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
                mRefreshLayout.loadMoreFinish(true);
                adapter.notifyDataSetChanged();
            }
        }, 1000); // 1秒后加载
    }


    private class ClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.test_listview_imgBack:
                    closeThisForm();
                    break;
                case R.id.test_listview_imgSearch:

                    break;
                case R.id.test_listview_name:

                    break;
            }
        }
    }
    /**
     * 关闭当前窗口
     */
    private void closeThisForm() {
        Intent intent = new Intent();
//        intent.putExtra("unreadNum", unreadNum);
        this.setResult(RESULT_OK, intent); // 设置结果数据
        this.finish(); // 关闭Activity
    }


}
