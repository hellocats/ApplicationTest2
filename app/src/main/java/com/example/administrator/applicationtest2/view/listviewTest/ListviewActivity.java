package com.example.administrator.applicationtest2.view.listviewTest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.administrator.applicationtest2.R;
import com.example.administrator.applicationtest2.adapter.BaseClsAdapter;
import com.example.administrator.applicationtest2.adapter.ViewHolder;
import com.example.administrator.applicationtest2.baseClass.BaseClsActivity;
import com.example.administrator.applicationtest2.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016-12-02.
 */
public class ListviewActivity extends BaseClsActivity {
    @BindView(R.id.test_listview_items)
    ListView listView;
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
        setContentView(R.layout.test_listview);
        ButterKnife.bind(this);
    }

    private void initController() {
        // 设置适配器
        listView.setAdapter(adapter = new BaseClsAdapter<User>(
                ListviewActivity.this, lDatas, R.layout.test_listview_item)
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                try {
                    row = (User) listView.getItemAtPosition(position);
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
